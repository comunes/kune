package cc.kune.core.server.notifier;

import cc.kune.core.server.mail.MailServiceDefault.FormatedString;

/**
 * The Interface NotifyService.
 */
public interface NotifyService {

  /**
   * Send a message to the recipients (also translate the subject/body using the
   * user language)
   * 
   * @param notifyType
   *          the notify type (email, etc)
   * @param subject
   *          the subject of the message (not translated)
   * @param body
   *          the body of the message (not translated) but with %s
   *          {@link String.format} args
   * @param isHtml
   *          if the body is html
   * @param recipients
   *          the recipients shortnames (without domain)
   */
  void send(NotifyType notifyType, FormatedString subject, FormatedString body, boolean isHtml,
      String... recipients);

  /**
   * Send a message to the recipients (also translate the subject/body using the
   * user language)
   * 
   * @param notifyType
   *          the notify type (email, etc)
   * @param subject
   *          the subject of the message
   * @param body
   *          the body of the message (no translated) but with some %s
   *          {@link String.format} args
   * @param recipients
   *          the recipients shortnames (without domain)
   */
  void send(NotifyType notifyType, FormatedString subject, FormatedString body, String... recipients);

}
