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

package cc.kune.core.server.manager.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.errors.IncorrectHashException;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.InvitationManager;
import cc.kune.core.server.manager.SocialNetworkManager;
import cc.kune.core.server.notifier.Addressee;
import cc.kune.core.server.notifier.NotificationHtmlHelper;
import cc.kune.core.server.notifier.NotificationService;
import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.server.notifier.PendingNotification;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.utils.FormattedString;
import cc.kune.core.shared.SessionConstants;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.SocialNetworkDataDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.Invitation;
import cc.kune.domain.User;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.InvitationFinder;
import cc.kune.lists.server.ListServerService;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class InvitationManagerDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class InvitationManagerDefault extends DefaultManager<Invitation, Long> implements
    InvitationManager {

  /** The Constant LOG. */
  public static final Log LOG = LogFactory.getLog(InvitationManagerDefault.class);

  /** The basic properties. */
  private final KuneBasicProperties basicProperties;

  /** The container manager. */
  private final ContainerManager containerManager;

  /** The em. */
  private final Provider<EntityManager> em;

  /** The finder. */
  private final InvitationFinder finder;

  /** The group finder. */
  private final GroupFinder groupFinder;

  /** The i18n language manager. */
  private final I18nLanguageManager i18nLanguageManager;

  /** The list service. */
  private final ListServerService listService;

  /** The notification html helper. */
  private final NotificationHtmlHelper notificationHtmlHelper;

  /** The notify service. */
  private final NotificationService notifyService;

  /** The sn manager. */
  private final SocialNetworkManager snManager;

  /**
   * Instantiates a new invitation manager default.
   * 
   * @param provider
   *          the provider
   * @param finder
   *          the finder
   * @param notifyService
   *          the notify service
   * @param i18nLanguageManager
   *          the i18n language manager
   * @param basicProperties
   *          the basic properties
   * @param notificationHtmlHelper
   *          the notification html helper
   * @param groupFinder
   *          the group finder
   * @param containerManager
   *          the container manager
   * @param snManager
   *          the sn manager
   * @param listService
   *          the list service
   */
  @Inject
  public InvitationManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final InvitationFinder finder, final NotificationService notifyService,
      final I18nLanguageManager i18nLanguageManager, final KuneBasicProperties basicProperties,
      final NotificationHtmlHelper notificationHtmlHelper, final GroupFinder groupFinder,
      final ContainerManager containerManager, final SocialNetworkManager snManager,
      final ListServerService listService) {
    super(provider, Invitation.class);
    em = provider;
    this.finder = finder;
    this.notifyService = notifyService;
    this.i18nLanguageManager = i18nLanguageManager;
    this.basicProperties = basicProperties;
    this.notificationHtmlHelper = notificationHtmlHelper;
    this.groupFinder = groupFinder;
    this.containerManager = containerManager;
    this.snManager = snManager;
    this.listService = listService;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.InvitationManager#confirmInvitationToGroup(
   * cc.kune.domain.User, java.lang.String)
   */
  @Override
  public SocialNetworkDataDTO confirmInvitationToGroup(final User user, final String invitationHash) {
    try {
      final Invitation invitation = finder.findByHash(invitationHash);
      invitation.setUsed(true);
      persist(invitation);
      final User from = invitation.getFrom();
      final Group group = groupFinder.findByShortName(invitation.getInvitedToToken().getGroup());
      snManager.addGroupToCollabs(from, user.getUserGroup(), group);
      return snManager.generateResponse(user, group);
    } catch (final NoResultException exp) {
      throw new IncorrectHashException();
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.InvitationManager#confirmInvitationToList(cc
   * .kune.domain.User, java.lang.String)
   */
  @Override
  public StateContainerDTO confirmInvitationToList(final User user, final String invitationHash) {
    try {
      final Invitation invitation = finder.findByHash(invitationHash);
      invitation.setUsed(true);
      persist(invitation);
      return listService.subscribeToListWithoutPermCheck(invitation.getInvitedToToken(),
          user.getUserGroup(), true);
    } catch (final NoResultException exp) {
      throw new IncorrectHashException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.InvitationManager#confirmInvitationToSite(cc
   * .kune.domain.User, java.lang.String)
   */
  @Override
  public void confirmInvitationToSite(final User user, final String invitationHash) {
    try {
      final Invitation invitation = finder.findByHash(invitationHash);
      invitation.setUsed(true);
      persist(invitation);
      final User from = invitation.getFrom();
      final String siteUrl = basicProperties.getSiteUrlWithoutHttp();
      notifyService.sendEmail(
          Addressee.build(from.getEmail(), i18nLanguageManager.getDefaultLanguage()),
          PendingNotification.SITE_DEFAULT_SUBJECT_PREFIX, FormattedString.build(
              "Some people you may know on %s", siteUrl), FormattedString.build(
              "%s has now an account in %s. You can check his/her profile here:<br>%s<br>",
              user.getName(), siteUrl, notificationHtmlHelper.createLink(user.getShortName())));
    } catch (final NoResultException exp) {
      throw new IncorrectHashException();
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.InvitationManager#deleteOlderInvitations()
   */
  @Override
  @KuneTransactional
  public void deleteOlderInvitations() {
    final Long aWeekAgo = System.currentTimeMillis() - SessionConstants.A_WEEK;
    em.get().createQuery("DELETE FROM Invitation i WHERE i.used = true AND i.date <= " + aWeekAgo).executeUpdate();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.InvitationManager#get(java.lang.String)
   */
  @Override
  public Invitation get(final String invitationHash) {
    try {
      final Invitation invitation = finder.findByHash(invitationHash);
      invitation.setUsed(true);
      persist(invitation);
      return invitation;
    } catch (final NoResultException exp) {
      throw new IncorrectHashException();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.InvitationManager#invite(cc.kune.domain.User,
   * cc.kune.core.shared.domain.InvitationType,
   * cc.kune.core.server.notifier.NotificationType,
   * cc.kune.core.shared.domain.utils.StateToken, java.lang.String[])
   */
  @Override
  public void invite(final User from, final InvitationType type, final NotificationType notifType,
      final StateToken token, final String... emails) {
    Preconditions.checkState(notifType == NotificationType.email,
        "Only email invitations are implemented");
    final String siteUrl = basicProperties.getSiteUrlWithoutHttp();
    final String fromName = from.getName();
    final I18nLanguage defLang = i18nLanguageManager.getDefaultLanguage();
    for (final String email : emails) {
      Preconditions.checkState(email.matches(TextUtils.EMAIL_REGEXP), "Wrong email: " + email);
      final String hash = UUID.randomUUID().toString();
      final String redirect = TokenUtils.addRedirect(SiteTokens.INVITATION, hash);
      final String link = notificationHtmlHelper.createLink(redirect);
      final Invitation invitation = new Invitation(from, hash, token.getEncoded(), notifType, email,
          type);
      switch (type) {
      case TO_SITE:
        notifyService.sendEmail(Addressee.build(email, defLang),
            PendingNotification.SITE_DEFAULT_SUBJECT_PREFIX,
            FormattedString.build("%s has invited you to join %s", fromName, siteUrl),
            FormattedString.build("You have been invited by %s to join %s.<br><br>"
                + "If you want to accept the invitation, just follow this link:<br>%s<br>"
                + "in other case, just ignore this email.", fromName, siteUrl, link));
        break;
      case TO_GROUP:
        final Group group = groupFinder.findByShortName(token.getGroup());
        final String longName = group.getLongName();
        notifyService.sendEmail(Addressee.build(email, defLang),
            PendingNotification.SITE_DEFAULT_SUBJECT_PREFIX,
            FormattedString.build("%s has invited you to join the group '%s'", fromName, longName),
            FormattedString.build("You have been invited by %s to join the group '%s' in %s.<br><br>"
                + "If you want to accept the invitation, just follow this link:<br>%s<br>"
                + "in other case, just ignore this email.", fromName, longName, siteUrl, link));
        break;
      case TO_LISTS:
        final String groupShortName = groupFinder.findByShortName(token.getGroup()).getShortName();
        final String listName = containerManager.find(ContentUtils.parseId(token.getFolder())).getName();
        notifyService.sendEmail(Addressee.build(email, defLang),
            PendingNotification.SITE_DEFAULT_SUBJECT_PREFIX, FormattedString.build(
                "%s has invited you to join the list '%s'", fromName, listName), FormattedString.build(
                "You have been invited by %s to join the list '%s' of group '%s' in %s.<br><br>"
                    + "If you want to accept the invitation, just follow this link::<br>%s<br>"
                    + "in other case, just ignore this email.", fromName, listName, groupShortName,
                siteUrl, link));
        break;
      default:
        throw new NotImplementedException();
      }
      persist(invitation);
    }
  }
}