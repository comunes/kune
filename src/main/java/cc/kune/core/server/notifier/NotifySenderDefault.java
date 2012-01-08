package cc.kune.core.server.notifier;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.server.i18n.I18nTranslationServiceMultiLang;
import cc.kune.core.server.mail.FormatedString;
import cc.kune.core.server.mail.MailService;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.server.xmpp.XmppManager;
import cc.kune.core.shared.domain.dto.EmailNotificationFrequency;
import cc.kune.domain.User;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotifySenderDefault implements NotifySender {
  public static final Log LOG = LogFactory.getLog(NotifySenderDefault.class);
  private final String emailTemplate;
  private final I18nTranslationServiceMultiLang i18n;
  private final MailService mailService;
  private final KuneWaveService waveService;
  private final XmppManager xmppManager;

  @Inject
  public NotifySenderDefault(final MailService mailService, final KuneWaveService waveService,
      final XmppManager xmppManager, final I18nTranslationServiceMultiLang i18n,
      final KuneProperties kuneProperties) throws IOException {
    this.mailService = mailService;
    this.waveService = waveService;
    this.xmppManager = xmppManager;
    this.i18n = i18n;
    emailTemplate = FileUtils.readFileToString(new File(
        kuneProperties.get(KuneProperties.SITE_EMAIL_TEMPLATE)));
    Preconditions.checkNotNull(emailTemplate);
    Preconditions.checkArgument(TextUtils.notEmpty(emailTemplate));
  }

  @Override
  public void send(final NotifyType notifyType, final FormatedString subject, final FormatedString body,
      final boolean isHtml, final User... recipients) {
    for (final User user : recipients) {
      final String username = user.getShortName();
      if (subject.shouldBeTranslated()) {
        // Translate per recipient language
        // final String subjectTranslation = i18n.tWithNT(user.getLanguage(),
        // subject.getTemplate(), "");
        // if (subjectTranslation != null) {
        // Right now commented because we are only testing
        // subject.setTemplate(subjectTranslation);
        // }
      }
      if (body.shouldBeTranslated()) {
        // final String bodyTranslation = i18n.tWithNT(user.getLanguage(),
        // body.getTemplate(), "");
        // if (bodyTranslation != null) {
        // Right now commented because we are only testing
        // body.setTemplate(bodyTranslation);
        // }
      }
      switch (notifyType) {
      case chat:
        xmppManager.sendMessage(username,
            String.format("<b>%s</b>%s", subject.getString(), body.getString()));
        break;
      case email:
        if (user.getEmailNotifFreq().equals(EmailNotificationFrequency.immediately)) {
          mailService.send(subject, FormatedString.build(emailTemplate.replace("%s", body.getString())),
              isHtml, user.getEmail());
        } else {
          // TODO: handle other types of notifications frequencies
        }
        break;
      case wave:
        if (isHtml) {
          LOG.error("Wave html messages not supported yet");
        }
        waveService.createWave(subject.getString(), body.getString(), username);
        break;
      }
    }
  }

  @Override
  public void send(final NotifyType notifyType, final FormatedString subject, final FormatedString body,
      final User... dests) {
    send(notifyType, subject, body, false, dests);
  }

}
