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
public class NotifyService {

  public static final Log LOG = LogFactory.getLog(NotifyService.class);
  private final NotifyHtmlHelper helper;
  private final NotifySender sender;
  private final UserFinder userFinder;

  @Inject
  NotifyService(final NotifySender sender, final NotifyHtmlHelper helper, final UserFinder userFinder) {
    this.sender = sender;
    this.helper = helper;
    this.userFinder = userFinder;
  }

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

  @SuppressWarnings("unchecked")
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
      sender.send(NotifyType.email, FormatedString.build(subject),
          helper.groupNotification(groupSender.getShortName(), groupSender.hasLogo(), message), to);
    }
  }

  public void notifyUser(final User to, final Group group, final String subject, final String message) {
    sender.send(NotifyType.email, FormatedString.build(subject),
        helper.groupNotification(group.getShortName(), group.hasLogo(), message), to);
  }
}
