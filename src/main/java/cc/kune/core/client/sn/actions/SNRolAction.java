/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.client.sn.actions;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolActionAutoUpdated;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.rpcservices.SocialNetServiceAsync;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class SNRolAction extends RolActionAutoUpdated {
  protected final I18nTranslationService i18n;
  protected final Provider<SocialNetServiceAsync> snServiceProvider;

  @Inject
  public SNRolAction(final StateManager stateManager, final Session session,
      final I18nTranslationService i18n, final CoreResources res,
      final Provider<SocialNetServiceAsync> snServiceProvider,
      final AccessRightsClientManager rightsManager, final AccessRolDTO rolRequired,
      final boolean authNeed, final boolean visibleForNonMemb, final boolean visibleForMembers) {
    super(stateManager, session, rightsManager, rolRequired, authNeed, visibleForNonMemb,
        visibleForMembers);
    this.i18n = i18n;
    this.snServiceProvider = snServiceProvider;
  }
}
