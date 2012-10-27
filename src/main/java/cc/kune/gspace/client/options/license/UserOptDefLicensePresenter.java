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
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.gspace.client.licensewizard.LicenseChangeAction;
import cc.kune.gspace.client.licensewizard.LicenseWizard;
import cc.kune.gspace.client.options.UserOptions;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserOptDefLicensePresenter extends EntityOptDefLicensePresenter implements
    UserOptDefLicense {

  @Inject
  public UserOptDefLicensePresenter(final UserOptions entityOptions, final Session session,
      final UserOptDefLicenseView view, final Provider<LicenseWizard> licenseWizard,
      final Provider<LicenseChangeAction> licChangeAction) {
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
    return session.isLogged();
  }

  @Override
  protected LicenseDTO getCurrentDefLicense() {
    return session.getCurrentState().getGroup().getDefaultLicense();
  }

  @Override
  protected StateToken getOperationToken() {
    return session.getCurrentUser().getStateToken();
  }
}
