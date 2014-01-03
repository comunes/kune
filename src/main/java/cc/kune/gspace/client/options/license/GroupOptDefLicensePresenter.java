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

// TODO: Auto-generated Javadoc
/**
 * The Class GroupOptDefLicensePresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GroupOptDefLicensePresenter extends EntityOptDefLicensePresenter implements
    GroupOptDefLicense {

  /**
   * Instantiates a new group opt def license presenter.
   * 
   * @param entityOptions
   *          the entity options
   * @param stateManager
   *          the state manager
   * @param session
   *          the session
   * @param view
   *          the view
   * @param licenseWizard
   *          the license wizard
   * @param licChangeAction
   *          the lic change action
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.license.EntityOptDefLicensePresenter#applicable
   * ()
   */
  @Override
  protected boolean applicable() {
    return session.isCurrentStateAGroup();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.license.EntityOptDefLicensePresenter#
   * getCurrentDefLicense()
   */
  @Override
  protected LicenseDTO getCurrentDefLicense() {
    return session.getCurrentState().getGroup().getDefaultLicense();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.license.EntityOptDefLicensePresenter#
   * getOperationToken()
   */
  @Override
  protected StateToken getOperationToken() {
    return session.getCurrentStateToken();
  }
}
