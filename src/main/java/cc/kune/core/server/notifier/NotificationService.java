/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import cc.kune.core.server.utils.FormattedString;
import cc.kune.core.shared.dto.SocialNetworkSubGroup;
import cc.kune.domain.Group;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationService.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class NotificationService {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(NotificationService.class);

  /** The helper. */
  private final NotificationHtmlHelper helper;

  /** The sender. */
  private final PendingNotificationSender sender;

  /**
   * Instantiates a new notification service.
   * 
   * @param sender
   *          the sender
   * @param helper
   *          the helper
   */
  @Inject
  NotificationService(final PendingNotificationSender sender, final NotificationHtmlHelper helper) {
    this.sender = sender;
    this.helper = helper;
  }

  /**
   * Creates the plain subject.
   * 
   * @param subject
   *          the subject
   * @return the formatted string
   */
  private FormattedString createPlainSubject(final String subject) {
    return FormattedString.build(subject);
  }

  /**
   * Notify group admins.
   * 
   * @param groupToNotify
   *          the group to notify
   * @param groupSender
   *          the group sender
   * @param subject
   *          the subject
   * @param message
   *          the message
   */
  public void notifyGroupAdmins(final Group groupToNotify, final Group groupSender,
      final String subject, final String message) {
    final Set<User> adminMembers = new HashSet<User>();
    GroupServerUtils.getAllUserMembers(adminMembers, groupToNotify, SocialNetworkSubGroup.ADMINS);
    notifyToAll(groupSender, subject, message, adminMembers);
  }

  /**
   * Notify group members.
   * 
   * @param groupToNotify
   *          the group to notify
   * @param groupSender
   *          the group sender
   * @param subject
   *          the subject
   * @param message
   *          the message
   */
  public void notifyGroupMembers(final Group groupToNotify, final Group groupSender,
      final String subject, final String message) {
    final Set<User> members = new HashSet<User>();
    GroupServerUtils.getAllUserMembers(members, groupToNotify, SocialNetworkSubGroup.ALL_GROUP_MEMBERS);
    notifyToAll(groupSender, subject, message, members);
  }

  /**
   * Notify group to user.
   * 
   * @param group
   *          the group
   * @param to
   *          the to
   * @param subject
   *          the subject
   * @param message
   *          the message
   */
  public void notifyGroupToUser(final Group group, final User to, final String subject,
      final String message) {
    sender.add(NotificationType.email, group.getShortName(), createPlainSubject(subject),
        helper.groupNotification(group.getShortName(), group.hasLogo(), message), true, false,
        Addressee.build(to));
  }

  /**
   * Notify to all.
   * 
   * @param groupSender
   *          the group sender
   * @param subject
   *          the subject
   * @param message
   *          the message
   * @param users
   *          the users
   */
  private void notifyToAll(final Group groupSender, final String subject, final String message,
      final Collection<User> users) {
    for (final User to : users) {
      sender.add(NotificationType.email, groupSender.getShortName(), createPlainSubject(subject),
          helper.groupNotification(groupSender.getShortName(), groupSender.hasLogo(), message), true,
          true, Addressee.build(to));
    }
  }

  /**
   * Notify user to user by email.
   * 
   * @param from
   *          the from
   * @param to
   *          the to
   * @param subject
   *          the subject
   * @param message
   *          the message
   */
  public void notifyUserToUserByEmail(final User from, final User to, final String subject,
      final String message) {
    sender.add(NotificationType.email, PendingNotification.SITE_DEFAULT_SUBJECT_PREFIX,
        createPlainSubject(subject),
        helper.userNotification(from.getShortName(), from.hasLogo(), message), true, false,
        Addressee.build(to));
  }

  /**
   * Send an email.
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
    sender.add(NotificationType.email, subjectPrefix, subject, body, true, true,
        new SimpleDestinationProvider(to));
  }

  /**
   * Send an email.
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
    sender.add(NotificationType.email, PendingNotification.SITE_DEFAULT_SUBJECT_PREFIX,
        createPlainSubject(subject), helper.createBodyWithEndLink(body, hash), true, true, to);
  }
}
