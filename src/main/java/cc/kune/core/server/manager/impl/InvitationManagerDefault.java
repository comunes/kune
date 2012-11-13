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

package cc.kune.core.server.manager.impl;

import java.util.UUID;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentUtils;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.InvitationManager;
import cc.kune.core.server.notifier.Addressee;
import cc.kune.core.server.notifier.NotificationHtmlHelper;
import cc.kune.core.server.notifier.NotificationService;
import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.server.notifier.PendingNotification;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.server.utils.FormattedString;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Group;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.Invitation;
import cc.kune.domain.User;
import cc.kune.domain.finders.GroupFinder;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class InvitationManagerDefault extends DefaultManager<Invitation, Long> implements
    InvitationManager {

  public static final Log LOG = LogFactory.getLog(InvitationManagerDefault.class);
  private final KuneBasicProperties basicProperties;
  private final ContainerManager containerManager;
  private final GroupFinder groupFinder;
  private final I18nLanguageManager i18nLanguageManager;
  private final NotificationHtmlHelper notificationHtmlHelper;
  private final NotificationService notifyService;

  @Inject
  public InvitationManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final NotificationService notifyService, final I18nLanguageManager i18nLanguageManager,
      final KuneBasicProperties basicProperties, final NotificationHtmlHelper notificationHtmlHelper,
      final GroupFinder groupFinder, final ContainerManager containerManager

  ) {
    super(provider, Invitation.class);
    this.notifyService = notifyService;
    this.i18nLanguageManager = i18nLanguageManager;
    this.basicProperties = basicProperties;
    this.notificationHtmlHelper = notificationHtmlHelper;
    this.groupFinder = groupFinder;
    this.containerManager = containerManager;
  }

  @Override
  public void invite(final User from, final InvitationType type, final NotificationType notifType,
      final StateToken token, final String... emails) {
    Preconditions.checkState(notifType == NotificationType.email,
        "Only email invitations are implemented");
    final String siteUrl = basicProperties.getSiteUrlWithoutHttp();
    final String fromName = from.getName();
    final I18nLanguage defLang = i18nLanguageManager.getDefaultLanguage();
    for (final String email : emails) {
      final String redirect = TokenUtils.addRedirect(SiteTokens.INVITATION, UUID.randomUUID().toString());
      final String link = notificationHtmlHelper.createLink(redirect);
      final Invitation invitation = new Invitation(from, UUID.randomUUID().toString(),
          token.getEncoded(), notifType, email, type);
      switch (type) {
      case TO_SITE:
        notifyService.sendEmail(Addressee.build(email, defLang),
            PendingNotification.DEFAULT_SUBJECT_PREFIX,
            FormattedString.build("%s has invited you to join %s", fromName, siteUrl),
            FormattedString.build("You have been invited by %s to join %s.<br><br>"
                + "If you want to accept the invitation, just follow this link:<br>%s<br>"
                + "in other case, just ignore this email.", fromName, siteUrl, link));
        break;
      case TO_GROUP:
        final Group group = groupFinder.findByShortName(token.getGroup());
        final String longName = group.getLongName();
        notifyService.sendEmail(Addressee.build(email, defLang),
            PendingNotification.DEFAULT_SUBJECT_PREFIX,
            FormattedString.build("%s has invited you to join the group '%s'", fromName, longName),
            FormattedString.build("You have been invited by %s to join the group '%s' in %s."
                + "If you want to accept the invitation, just follow this link:<br>%s<br>"
                + "in other case, just ignore this email.", fromName, longName, siteUrl, link));
        break;
      case TO_LISTS:
        final String groupShortName = groupFinder.findByShortName(token.getGroup()).getShortName();
        final String listName = containerManager.find(ContentUtils.parseId(token.getFolder())).getName();
        notifyService.sendEmail(Addressee.build(email, defLang),
            PendingNotification.DEFAULT_SUBJECT_PREFIX, FormattedString.build(
                "%s has invited you to join the lists '%s'", fromName, listName), FormattedString.build(
                "You have been invited by %s to join the list '%s' of group '%s' in %s."
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