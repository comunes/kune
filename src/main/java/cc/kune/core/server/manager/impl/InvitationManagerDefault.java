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
import cc.kune.core.server.manager.InvitationManager;
import cc.kune.core.server.notifier.NotificationService;
import cc.kune.core.server.notifier.NotificationType;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.shared.domain.InvitationType;
import cc.kune.domain.Invitation;
import cc.kune.domain.User;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class InvitationManagerDefault extends DefaultManager<Invitation, Long> implements
    InvitationManager {

  public static final Log LOG = LogFactory.getLog(InvitationManagerDefault.class);
  private final NotificationService notifyService;

  @Inject
  public InvitationManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final NotificationService notifyService) {
    super(provider, Invitation.class);
    this.notifyService = notifyService;
  }

  @Override
  public void invite(final User from, final InvitationType type, final NotificationType notifType,
      final String toToken, final String... emails) {
    Preconditions.checkState(notifType == NotificationType.email,
        "Only email invitations are implemented");
    for (final String email : emails) {
      final String hash = UUID.randomUUID().toString();
      final Invitation invitation = new Invitation(from, hash, toToken, notifType, email, type);
      switch (type) {
      case TO_SITE:
        // notifyService.sendEmailToWithLink(
        // user,
        // "Verify password reset",
        // "You are receiving this email because a request has been made to change the password associated with this email address in %s.<br><br>"
        // +
        // "If this was a mistake, just ignore this email and nothing will happen.<br><br>"
        // +
        // "If you would like to reset the password for this account simply click on the link below or paste it into the url field on your favorite browser:",
        // TokenUtils.addRedirect(SiteTokens.RESET_PASSWD, hash));
        break;
      case TO_GROUP:

        break;
      case TO_LISTS:
        break;
      default:
        throw new NotImplementedException();
      }
    }
  }
}