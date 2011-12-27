package cc.kune.core.server.notifier;

import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.mail.MailService;
import cc.kune.core.server.mail.MailServiceDefault.FormatedString;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.xmpp.XmppManager;
import cc.kune.domain.User;
import cc.kune.wave.server.KuneWaveService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotifyServiceDefault implements NotifyService {
  public static final Log LOG = LogFactory.getLog(NotifyServiceDefault.class);
  private final I18nTranslationManager i18n;
  private final MailService mailService;
  private final UserManager userManager;
  private final KuneWaveService waveService;
  private final XmppManager xmppManager;

  @Inject
  public NotifyServiceDefault(final MailService mailService, final KuneWaveService waveService,
      final XmppManager xmppManager, final UserManager userManager, final I18nTranslationManager i18n) {
    this.mailService = mailService;
    this.waveService = waveService;
    this.xmppManager = xmppManager;
    this.userManager = userManager;
    this.i18n = i18n;
  }

  @Override
  public void send(final NotifyType notifyType, final FormatedString subject, final FormatedString body,
      final boolean isHtml, final String... recipients) {
    for (final String recipient : recipients) {
      User user;
      try {
        user = userManager.findByShortname(recipient);
      } catch (final NoResultException e) {
        LOG.info(String.format("The recipient %s is not a local user, don't notify", recipient));
        continue;
      }
      // Translate per recipient language
      final String subjectTranslation = i18n.getTranslation(user.getLanguage().getCode(),
          subject.getTemplate(), "");
      if (subjectTranslation != null) {
        subject.setTemplate(subjectTranslation);
      }
      final String bodyTranslation = i18n.getTranslation(user.getLanguage().getCode(),
          body.getTemplate(), "");
      if (bodyTranslation != null) {
        body.setTemplate(bodyTranslation);
      }
      switch (notifyType) {
      case chat:
        xmppManager.sendMessage(recipient,
            String.format("<b>%s</b>%s", subject.getString(), body.getString()));
        break;
      case email:
        final String email = user.getEmail();
        if (isHtml) {
          mailService.sendHtml(subject, body, email);
        } else {
          mailService.sendPlain(subject, body, email);
        }
        break;
      case wave:
        if (isHtml) {
          LOG.error("Wave html messages not supported yet");
        }
        waveService.createWave(subject.getString(), body.getString(), recipient);
        break;
      }
    }
  }

  @Override
  public void send(final NotifyType notifyType, final FormatedString subject, final FormatedString body,
      final String... dests) {
    send(notifyType, subject, body, false, dests);
  }

}
