/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.notifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.manager.impl.GroupServerUtils;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.utils.FormattedString;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class NotificationService {

  public static final Log LOG = LogFactory.getLog(NotificationService.class);
  private final KuneBasicProperties basicPropierties;
  private final NotificationHtmlHelper helper;
  private final PendingNotificationSender sender;

  @Inject
  NotificationService(final PendingNotificationSender sender, final NotificationHtmlHelper helper,
      final KuneBasicProperties basicPropierties

  ) {
    this.sender = sender;
    this.helper = helper;
    this.basicPropierties = basicPropierties;
  }

  private FormattedString createPlainSubject(final String subject) {
    return FormattedString.build(subject);
  }

  private String formatPrefix(final String subjectPrefix) {
    return subjectPrefix.equals(PendingNotification.SITE_SUBJECT_PREFIX) ? basicPropierties.getSiteCommonName()
        : subjectPrefix;
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
        helper.groupNotification(group.getShortName(), group.hasLogo(), message), true, false,
        Addressee.build(to));
  }

  private void notifyToAll(final Group groupSender, final String subject, final String message,
      final Collection<User> users) {
    for (final User to : users) {
      sender.add(NotificationType.email, groupSender.getShortName(), createPlainSubject(subject),
          helper.groupNotification(groupSender.getShortName(), groupSender.hasLogo(), message), true,
          true, Addressee.build(to));
    }
  }

  public void notifyUserToUser(final User from, final User to, final String subject, final String message) {
    sender.add(NotificationType.email, PendingNotification.DEFAULT_SUBJECT_PREFIX,
        createPlainSubject(subject),
        helper.userNotification(from.getShortName(), from.hasLogo(), message), true, false,
        Addressee.build(to));
  }

  /**
   * Send an email
   * 
   * @param to
   *          the address (destination)
   * @param subjectPrefix
   *          the subject prefix for instance [somegroup]
   * @param subject
   *          the subject of the email
   * @param body
   *          the body of the email
   */
  public void sendEmail(final Addressee to, final String subjectPrefix, final FormattedString subject,
      final FormattedString body) {
    sender.add(NotificationType.email, formatPrefix(subjectPrefix), subject, body, true, true,
        new SimpleDestinationProvider(to));
  }

  /**
   * Send an email
   * 
   * @param dest
   *          the list of address (destinations)
   * @param subjectPrefix
   *          the subject prefix for instance [somegroup]
   * @param subject
   *          the subject of the email
   * @param body
   *          the body of the email
   */
  public void sendEmail(final DestinationProvider dest, final String subjectPrefix,
      final FormattedString subject, final FormattedString body) {
    sender.add(NotificationType.email, subjectPrefix, subject, body, true, true, dest);
  }

  /**
   * Send email to an User with a link. The first an unique %s in body is
   * changed by the site name.
   * 
   * @param to
   *          the Addressee to send the notification
   * @param subject
   *          the subject of the email
   * @param body
   *          the body of the email with a %s that will be replaced by the site
   *          name
   * @param hash
   *          the hash an additional link that will be added at the end
   */
  public void sendEmailToWithLink(final Addressee to, final String subject, final String body,
      final String hash) {
    sender.add(NotificationType.email, PendingNotification.DEFAULT_SUBJECT_PREFIX,
        createPlainSubject(subject), helper.createBodyWithEndLink(body, hash), true, true, to);
  }
}
