package cc.kune.core.server.mail;

import cc.kune.core.server.mail.MailManagerDefault.FormatedString;

public interface MailManager {

  /**
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in html format
   * @param from
   *          the sender
   * @param to
   *          the recipients
   * @return true if sender (also we can change this interface if it's more
   *         useful to return other thing)
   */
  boolean sendHtml(FormatedString subject, FormatedString body, String from, String... to);

  /**
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text format
   * @param from
   *          the sender
   * @param to
   *          the recipient
   * @return true if sender (also we can change this interface if it's more
   *         useful to return other thing)
   */
  boolean sendPlain(FormatedString subject, FormatedString body, String from, String... to);

}
