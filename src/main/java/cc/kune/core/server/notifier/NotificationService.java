package cc.kune.core.server.notifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.mail.FormatedString;
import cc.kune.domain.Group;
import cc.kune.domain.User;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotificationService {

  public static final Log LOG = LogFactory.getLog(NotificationService.class);
  private final NotificationHtmlHelper helper;
  private final PendingNotificationSender sender;
  private final UserFinder userFinder;

  @Inject
  NotificationService(final PendingNotificationSender sender, final NotificationHtmlHelper helper,
      final UserFinder userFinder) {
    this.sender = sender;
    this.helper = helper;
    this.userFinder = userFinder;
  }

  private FormatedString createPlainSubject(final String subject) {
    return FormatedString.build(subject);
  }

  @SuppressWarnings("unchecked")
  private void getAllUserMembers(final Set<User> users, final Group groupToNotify,
      final boolean onlyAdmins) {
    final Collection<Group> members;
    final Set<Group> admins = groupToNotify.getSocialNetwork().getAccessLists().getAdmins().getList();
    if (onlyAdmins) {
      members = admins;
    } else {
      final Set<Group> collabs = groupToNotify.getSocialNetwork().getAccessLists().getEditors().getList();
      members = CollectionUtils.union(admins, collabs);
    }
    for (final Group member : members) {
      if (member.isPersonal()) {
        final String shortName = member.getShortName();
        try {
          final User user = userFinder.findByShortName(shortName);
          users.add(user);
        } catch (final NoResultException e) {
          LOG.error(String.format("This personal group %s is not a local user", shortName));
        }
      } else {
        // Is a group, so go recursively
        getAllUserMembers(users, member, onlyAdmins);
      }
    }

  }

  public void notifyGroup(final Group groupToNotify, final Group groupSender, final String subject,
      final String message) {
    final Set<User> members = new HashSet<User>();
    getAllUserMembers(members, groupToNotify, false);
    notifyToAll(groupSender, subject, message, members);
  }

  public void notifyGroupAdmins(final Group groupToNotify, final Group groupSender,
      final String subject, final String message) {
    final Set<User> adminMembers = new HashSet<User>();
    getAllUserMembers(adminMembers, groupToNotify, true);
    notifyToAll(groupSender, subject, message, adminMembers);
  }

  private void notifyToAll(final Group groupSender, final String subject, final String message,
      final Collection<User> users) {
    for (final User to : users) {
      sender.add(NotificationType.email, createPlainSubject(subject),
          helper.groupNotification(groupSender.getShortName(), groupSender.hasLogo(), message), true,
          true, to);
    }
  }

  public void notifyUser(final User to, final Group group, final String subject, final String message) {
    sender.add(NotificationType.email, createPlainSubject(subject),
        helper.groupNotification(group.getShortName(), group.hasLogo(), message), true, true, to);
  }

  /**
   * Send email to an User with a link.The first an unique %s in body is changed
   * by the site name.
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
    sender.add(NotificationType.email, createPlainSubject(subject), helper.userNotification(body, hash),
        true, true, to);
  }
}
