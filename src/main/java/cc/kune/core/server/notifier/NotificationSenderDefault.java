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
import cc.kune.core.shared.dto.EmailNotificationFrequency;
import cc.kune.domain.User;
import cc.kune.wave.server.kspecific.KuneWaveService;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotificationSenderDefault implements NotificationSender {
  public static final Log LOG = LogFactory.getLog(NotificationSenderDefault.class);
  private final String emailTemplate;
  private final I18nTranslationServiceMultiLang i18n;
  private final MailService mailService;
  private final String subjectPrefix;
  private final UsersOnline usersOnline;
  private final KuneWaveService waveService;
  private final XmppManager xmppManager;

  @Inject
  public NotificationSenderDefault(final MailService mailService, final KuneWaveService waveService,
      final XmppManager xmppManager, final I18nTranslationServiceMultiLang i18n,
      final UsersOnline usersOnline, final KuneProperties kuneProperties) throws IOException {
    this.mailService = mailService;
    this.waveService = waveService;
    this.xmppManager = xmppManager;
    this.i18n = i18n;
    this.usersOnline = usersOnline;
    emailTemplate = FileUtils.readFileToString(new File(
        kuneProperties.get(KuneProperties.SITE_EMAIL_TEMPLATE)));
    Preconditions.checkNotNull(emailTemplate);
    Preconditions.checkArgument(TextUtils.notEmpty(emailTemplate));
    subjectPrefix = new StringBuffer("[").append(kuneProperties.get(KuneProperties.SITE_NAME)).append(
        "] ").toString();
  }

  private boolean noOnline(final String username) {
    final boolean logged = usersOnline.isLogged(username);
    LOG.debug(String.format("User '%s' is online for notifications? %s", username, logged));
    return !logged;
  }

  @Override
  public void send(final PendingNotification notification, final EmailNotificationFrequency withFrequency) {
    final FormatedString subject = notification.getSubject().copy();
    final FormatedString body = notification.getBody();
    final NotificationType notifyType = notification.getNotifyType();
    final boolean forceSend = notification.isForceSend();
    final boolean isHtml = notification.isHtml();

    subject.setTemplate(subjectPrefix + subject.getTemplate());
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

    for (final User user : notification.getDestProvider().getDest()) {
      final String username = user.getShortName();

      switch (notifyType) {
      case chat:
        // FIXME seems that html is not sending correctly... check server specs
        xmppManager.sendMessage(username,
            String.format("<b>%s</b>%s", subject.getString(), body.getString()));
        break;
      case email:
        if (forceSend
            || (user.isEmailVerified() && noOnline(username) && withFrequency == user.getEmailNotifFreq())) {
          // we'll send this notification if is mandatory or this user is not
          // only and has this freq configured
          mailService.send(subject, FormatedString.build(emailTemplate.replace("%s", body.getString())),
              isHtml, user.getEmail());
        }
        break;
      case wave:
        if (isHtml) {
          LOG.error("Wave html messages not supported yet");
        }
        waveService.createWave(subject.getString(), body.getString(), KuneWaveService.DO_NOTHING_CBACK,
            username);
        break;
      }
    }

  }

}
