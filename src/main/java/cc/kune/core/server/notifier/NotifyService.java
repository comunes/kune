package cc.kune.core.server.notifier;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import cc.kune.core.server.mail.FormatedString;
import cc.kune.domain.Group;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotifyService {

  private final NotifyHtmlHelper helper;
  private final NotifySender sender;

  @Inject
  NotifyService(final NotifySender sender, final NotifyHtmlHelper helper) {
    this.sender = sender;
    this.helper = helper;
  }

  @SuppressWarnings("unchecked")
  public void notifyGroup(final Group groupToNotify, final Group groupSender, final String subject,
      final String message) {
    final Set<Group> admins = groupToNotify.getSocialNetwork().getAccessLists().getAdmins().getList();
    final Set<Group> collabs = groupToNotify.getSocialNetwork().getAccessLists().getEditors().getList();
    final List<Group> groups = (List<Group>) CollectionUtils.union(admins, collabs);
    notifyToAll(groupSender, subject, message, groups);
  }

  public void notifyGroupAdmins(final Group groupToNotify, final Group groupSender,
      final String subject, final String message) {
    final Set<Group> admins = groupToNotify.getSocialNetwork().getAccessLists().getAdmins().getList();
    notifyToAll(groupSender, subject, message, admins);
  }

  private void notifyToAll(final Group groupSender, final String subject, final String message,
      final Collection<Group> groups) {
    for (final Group to : groups) {
      sender.send(NotifyType.email, FormatedString.build(subject),
          helper.groupNotification(groupSender.getShortName(), groupSender.hasLogo(), message),
          to.getShortName());
    }
  }

  public void notifyUser(final String to, final Group group, final String subject, final String message) {
    sender.send(NotifyType.email, FormatedString.build(subject),
        helper.groupNotification(group.getShortName(), group.hasLogo(), message), to);
  }
}
