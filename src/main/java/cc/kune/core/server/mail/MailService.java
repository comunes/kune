package cc.kune.core.server.mail;

import cc.kune.core.server.mail.MailServiceDefault.FormatedString;

public interface MailService {

  /**
   * Sends html email with default site "from"
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in html format
   * @param to
   *          the recipient
   */
  void sendHtml(FormatedString subject, FormatedString body, String to);

  /**
   * Sends html email
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in html format
   * @param from
   *          the sender
   * @param tos
   *          the recipients
   */
  void sendHtml(FormatedString subject, FormatedString body, String from, String... tos);

  /**
   * 
   * Sends email with default site "from"
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text format
   * @param to
   *          the recipient
   */
  void sendPlain(FormatedString subject, FormatedString body, String to);

  /**
   * Sends plain email
   * 
   * @param subject
   *          the email subject
   * @param body
   *          the body of the email in text format
   * @param from
   *          the sender
   * @param tos
   *          the recipients
   */
  void sendPlain(FormatedString subject, FormatedString body, String from, String... tos);

}
