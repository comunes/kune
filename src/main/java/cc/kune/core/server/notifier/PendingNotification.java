package cc.kune.core.server.notifier;

import cc.kune.core.server.mail.FormatedString;

/**
 * The Class PendingNotification is used to store and send notifications (for
 * instance email) via cron
 */
public class PendingNotification {

  /**
   * The Constant NONE is used when for instance, all the destinations are not
   * local, so, we should not notify them by email
   */
  public static final PendingNotification NONE = new PendingNotification(null, null, null, false, false,
      null);

  /** The body. */
  private final FormatedString body;

  /** The dest provider. */
  private final DestinationProvider destProvider;

  /** The force send. */
  private final boolean forceSend;

  /** The is html. */
  private final boolean isHtml;

  /** The notify type. */
  private final NotificationType notifyType;

  /** The subject. */
  private final FormatedString subject;

  /**
   * Instantiates a new pending notification
   * 
   * @param notifyType
   *          the notify type
   * @param subject
   *          the subject
   * @param body
   *          the body
   * @param isHtml
   *          the is html
   * @param forceSend
   *          the force send
   * @param destProvider
   *          the dest provider
   */
  public PendingNotification(final NotificationType notifyType, final FormatedString subject,
      final FormatedString body, final boolean isHtml, final boolean forceSend,
      final DestinationProvider destProvider) {
    this.notifyType = notifyType;
    this.subject = subject;
    this.body = body;
    this.isHtml = isHtml;
    this.forceSend = forceSend;
    this.destProvider = destProvider;
  }

  /**
   * Gets the body.
   * 
   * @return the body
   */
  public FormatedString getBody() {
    return body;
  }

  /**
   * Gets the dest provider.
   * 
   * @return the dest provider
   */
  public DestinationProvider getDestProvider() {
    return destProvider;
  }

  /**
   * Gets the notify type.
   * 
   * @return the notify type
   */
  public NotificationType getNotifyType() {
    return notifyType;
  }

  /**
   * Gets the subject.
   * 
   * @return the subject
   */
  public FormatedString getSubject() {
    return subject;
  }

  /**
   * Checks if is force send.
   * 
   * @return true, if is force send
   */
  public boolean isForceSend() {
    return forceSend;
  }

  /**
   * Checks if is html.
   * 
   * @return true, if is html
   */
  public boolean isHtml() {
    return isHtml;
  }

}
