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
package cc.kune.gspace.client.options.license;

import cc.kune.core.client.events.UserSignInEvent;
import cc.kune.core.client.events.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.gspace.client.licensewizard.LicenseChangeAction;
import cc.kune.gspace.client.licensewizard.LicenseWizard;
import cc.kune.gspace.client.options.GroupOptions;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupOptDefLicensePresenter extends EntityOptDefLicensePresenter implements
    GroupOptDefLicense {

  @Inject
  public GroupOptDefLicensePresenter(final GroupOptions entityOptions, final StateManager stateManager,
      final Session session, final GroupOptDefLicenseView view,
      final Provider<LicenseWizard> licenseWizard, final Provider<LicenseChangeAction> licChangeAction) {
    super(entityOptions, session, licenseWizard, licChangeAction);
    init(view);
    session.onUserSignIn(true, new UserSignInHandler() {
      @Override
      public void onUserSignIn(final UserSignInEvent event) {
        setState();
      }
    });
  }

  @Override
  protected boolean applicable() {
    return session.isCurrentStateAGroup();
  }

  @Override
  protected LicenseDTO getCurrentDefLicense() {
    return session.getCurrentState().getGroup().getDefaultLicense();
  }

  @Override
  protected StateToken getOperationToken() {
    return session.getCurrentStateToken();
  }
}
