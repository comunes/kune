package cc.kune.core.server.notifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import cc.kune.core.server.mail.FormatedString;
import cc.kune.core.shared.domain.dto.EmailNotificationFrequency;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class PendingNotificationSender is used to store and send pending
 * notifications via cron tasks.
 */
@Singleton
public class PendingNotificationSender {

  /** The Constant NO_NEXT. */
  private static final Collection<PendingNotification> NO_NEXT = null;

  /** The daily pend notif. list */
  private final Set<PendingNotification> dailyPendNotif;

  /** The houry pend notif. list */
  private final Set<PendingNotification> hourlyPendNotif;

  /** The immediate pend notif. list */
  private final Set<PendingNotification> immediatePendNotif;

  /** The sender. */
  private final NotificationSender sender;

  /**
   * Instantiates a new pending notifications manager.
   * 
   * @param sender
   *          the sender
   */
  @Inject
  public PendingNotificationSender(final NotificationSender sender) {
    this.sender = sender;
    immediatePendNotif = new HashSet<PendingNotification>();
    hourlyPendNotif = new HashSet<PendingNotification>();
    dailyPendNotif = new HashSet<PendingNotification>();
  }

  public void add(final NotificationType type, final FormatedString subject, final FormatedString body,
      final boolean isHtml, final boolean forceSend, final User to) {
    add(new PendingNotification(type, subject, body, isHtml, forceSend,
        new SimpleDestinationProvider(to)));
  }

  /**
   * Adds a pending notification.
   * 
   * @param notification
   *          the notification
   */
  public void add(final PendingNotification notification) {
    if (!hourlyPendNotif.contains(notification)) {
      // If we have send a similar notification this hour just ignore
      immediatePendNotif.add(notification);
    }
  }

  /**
   * Gets the daily pending notifications count.
   * 
   * @return the daily count
   */
  public int getDailyCount() {
    return dailyPendNotif.size();
  }

  /**
   * Gets the hourly pending notifications count.
   * 
   * @return the hourly count
   */
  public int getHourlyCount() {
    return hourlyPendNotif.size();
  }

  /**
   * Gets the immediate pending notifications count.
   * 
   * @return the immediate count
   */
  public int getImmediateCount() {
    return immediatePendNotif.size();
  }

  /**
   * Send some list of notifications (called vía cron).
   * 
   * @param list
   *          the list to process
   * @param nextList
   *          the next list to store (we have a list of Users some that wants an
   *          immediate notification and other that want periodical
   *          notifications instead.
   * @param currentFreq
   *          The current frequency of notifications, that is, we'll send
   *          notifications only to users with this frequency configured
   */
  private void send(final Collection<PendingNotification> list,
      final Collection<PendingNotification> nextList, final EmailNotificationFrequency currentFreq) {
    for (final PendingNotification notification : list) {
      sender.send(notification, currentFreq);
      if (!notification.isForceSend() && nextList != NO_NEXT) {
        // Forced Send, are send immediately (independent of how User has its
        // notification configured)!
        nextList.add(notification);
      }
    }
    list.clear();
  }

  /**
   * Send daily notifications.
   */
  public void sendDailyNotifications() {
    send(dailyPendNotif, NO_NEXT, EmailNotificationFrequency.immediately);
  }

  /**
   * Send hourly notifications.
   */
  public void sendHourlyNotifications() {
    send(hourlyPendNotif, dailyPendNotif, EmailNotificationFrequency.hourly);
  }

  /**
   * Send immediate notifications.
   */
  public void sendImmediateNotifications() {
    send(immediatePendNotif, hourlyPendNotif, EmailNotificationFrequency.daily);
  }

  @Override
  public String toString() {
    return "pendingNotifications: [" + getImmediateCount() + ", " + getHourlyCount() + ", "
        + getDailyCount() + "]";
  }
}
