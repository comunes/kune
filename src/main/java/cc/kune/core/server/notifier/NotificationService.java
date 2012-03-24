package cc.kune.core.server.notifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.mail.FormatedString;
import cc.kune.core.server.manager.impl.GroupServerUtils;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotificationService {

  public static final Log LOG = LogFactory.getLog(NotificationService.class);
  private final NotificationHtmlHelper helper;
  private final PendingNotificationSender sender;

  @Inject
  NotificationService(final PendingNotificationSender sender, final NotificationHtmlHelper helper) {
    this.sender = sender;
    this.helper = helper;
  }

  private FormatedString createPlainSubject(final String subject) {
    return FormatedString.build(subject);
  }

  public void notifyGroupAdmins(final Group groupToNotify, final Group groupSender,
      final String subject, final String message) {
    final Set<User> adminMembers = new HashSet<User>();
    GroupServerUtils.getAllUserMembers(adminMembers, groupToNotify, SocialNetworkSubGroup.ADMINS);
    notifyToAll(groupSender, subject, message, adminMembers);
  }

  public void notifyGroupMembers(final Group groupToNotify, final Group groupSender,
      final String subject, final String message) {
    final Set<User> members = new HashSet<User>();
    GroupServerUtils.getAllUserMembers(members, groupToNotify, SocialNetworkSubGroup.ALL_GROUP_MEMBERS);
    notifyToAll(groupSender, subject, message, members);
  }

  public void notifyGroupToUser(final Group group, final User to, final String subject,
      final String message) {
    sender.add(NotificationType.email, group.getShortName(), createPlainSubject(subject),
        helper.groupNotification(group.getShortName(), group.hasLogo(), message), true, false, to);
  }

  private void notifyToAll(final Group groupSender, final String subject, final String message,
      final Collection<User> users) {
    for (final User to : users) {
      sender.add(NotificationType.email, groupSender.getShortName(), createPlainSubject(subject),
          helper.groupNotification(groupSender.getShortName(), groupSender.hasLogo(), message), true,
          true, to);
    }
  }

  public void notifyUserToUser(final User from, final User to, final String subject, final String message) {
    sender.add(NotificationType.email, PendingNotification.DEFAULT_SUBJECT_PREFIX,
        createPlainSubject(subject),
        helper.userNotification(from.getShortName(), from.hasLogo(), message), true, false, to);
  }

  /**
   * Send email to an User with a link. The first an unique %s in body is
   * changed by the site name.
   * 
   * @param to
   *          the User to send the notification
   * @param subject
   *          the subject of the email
   * @param body
   *          the body of the email with a %s that will be replaced by the site
   *          name
   * @param hash
   *          the hash an additional link that will be added at the end
   */
  public void sendEmailToWithLink(final User to, final String subject, final String body,
      final String hash) {
    sender.add(NotificationType.email, PendingNotification.DEFAULT_SUBJECT_PREFIX,
        createPlainSubject(subject), helper.userNotification(body, hash), true, true, to);
  }
}
