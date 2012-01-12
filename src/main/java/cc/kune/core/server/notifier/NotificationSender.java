package cc.kune.core.server.notifier;

import cc.kune.core.shared.domain.dto.EmailNotificationFrequency;

/**
 * The Interface PendingNotificationSender. Used to send pending notifications
 */
public interface NotificationSender {

  /**
   * Send the notification to users with some frequency configured
   * 
   * @param notification
   *          the notification to send
   * @param currentFrequency
   *          the current frequency (only users with this frequency should be
   *          processed
   */
  void send(PendingNotification notification, EmailNotificationFrequency currentFrequency);
}
