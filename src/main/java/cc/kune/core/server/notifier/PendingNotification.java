package cc.kune.core.server.notifier;

import cc.kune.core.server.mail.FormatedString;

/**
 * The Class PendingNotification is used to store and send notifications (for
 * instance email) via cron
 */
public class PendingNotification {

  public static final String DEFAULT_SUBJECT_PREFIX = new String();

  /**
   * The Constant NONE is used when for instance, all the destinations are not
   * local, so, we should not notify them by email
   */
  public static final PendingNotification NONE = new PendingNotification(null, null, null, null, false,
      false, null);

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

  /** The subject prefix [sitename] */
  private final String subjectPrefix;

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
    this(notifyType, DEFAULT_SUBJECT_PREFIX, subject, body, isHtml, forceSend, destProvider);
  }

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
  public PendingNotification(final NotificationType notifyType, final String subjectPrefix,
      final FormatedString subject, final FormatedString body, final boolean isHtml,
      final boolean forceSend, final DestinationProvider destProvider) {
    this.notifyType = notifyType;
    this.subjectPrefix = subjectPrefix;
    this.subject = subject;
    this.body = body;
    this.isHtml = isHtml;
    this.forceSend = forceSend;
    this.destProvider = destProvider;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final PendingNotification other = (PendingNotification) obj;
    if (body == null) {
      if (other.body != null) {
        return false;
      }
    } else if (!body.equals(other.body)) {
      return false;
    }
    if (destProvider == null) {
      if (other.destProvider != null) {
        return false;
      }
    } else if (!destProvider.equals(other.destProvider)) {
      return false;
    }
    if (forceSend != other.forceSend) {
      return false;
    }
    if (isHtml != other.isHtml) {
      return false;
    }
    if (notifyType != other.notifyType) {
      return false;
    }
    if (subject == null) {
      if (other.subject != null) {
        return false;
      }
    } else if (!subject.equals(other.subject)) {
      return false;
    }
    return true;
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

  public String getSubjectPrefix() {
    return subjectPrefix;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((body == null) ? 0 : body.hashCode());
    result = prime * result + ((destProvider == null) ? 0 : destProvider.hashCode());
    result = prime * result + (forceSend ? 1231 : 1237);
    result = prime * result + (isHtml ? 1231 : 1237);
    result = prime * result + ((notifyType == null) ? 0 : notifyType.hashCode());
    result = prime * result + ((subject == null) ? 0 : subject.hashCode());
    return result;
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
