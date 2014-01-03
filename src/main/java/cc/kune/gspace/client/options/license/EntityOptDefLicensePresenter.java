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

import cc.kune.common.shared.utils.SimpleResponseCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.gspace.client.licensewizard.LicenseChangeAction;
import cc.kune.gspace.client.licensewizard.LicenseChooseCallback;
import cc.kune.gspace.client.licensewizard.LicenseWizard;
import cc.kune.gspace.client.options.EntityOptions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityOptDefLicensePresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class EntityOptDefLicensePresenter {

  /** The entity options. */
  private final EntityOptions entityOptions;

  /** The lic change action. */
  private final Provider<LicenseChangeAction> licChangeAction;

  /** The license wizard. */
  private final Provider<LicenseWizard> licenseWizard;

  /** The session. */
  protected final Session session;

  /** The view. */
  private EntityOptDefLicenseView view;

  /**
   * Instantiates a new entity opt def license presenter.
   * 
   * @param entityOptions
   *          the entity options
   * @param session
   *          the session
   * @param licenseWizard
   *          the license wizard
   * @param licChangeAction
   *          the lic change action
   */
  public EntityOptDefLicensePresenter(final EntityOptions entityOptions, final Session session,
      final Provider<LicenseWizard> licenseWizard, final Provider<LicenseChangeAction> licChangeAction) {
    this.entityOptions = entityOptions;
    this.session = session;
    this.licenseWizard = licenseWizard;
    this.licChangeAction = licChangeAction;
  }

  /**
   * Applicable.
   * 
   * @return true, if successful
   */
  protected abstract boolean applicable();

  /**
   * Gets the current def license.
   * 
   * @return the current def license
   */
  protected abstract LicenseDTO getCurrentDefLicense();

  /**
   * Gets the operation token.
   * 
   * @return the operation token
   */
  protected abstract StateToken getOperationToken();

  /**
   * Gets the view.
   * 
   * @return the view
   */
  public IsWidget getView() {
    return view;
  }

  /**
   * Inits the.
   * 
   * @param view
   *          the view
   */
  protected void init(final EntityOptDefLicenseView view) {
    this.view = view;
    entityOptions.addTab(view, view.getTabTitle());
    setState();
    view.getChange().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        onChange();
      }
    });
    view.getLicenseImage().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        onLicenseClick();
      }
    });
  }

  /**
   * On change.
   */
  public void onChange() {
    licenseWizard.get().start(new LicenseChooseCallback() {

      @Override
      public void onSelected(final LicenseDTO license) {
        licChangeAction.get().changeLicense(getOperationToken(), license, new SimpleResponseCallback() {

          @Override
          public void onCancel() {
          }

          @Override
          public void onSuccess() {
            setLicense(license);
          }
        });
      }
    });
  }

  /**
   * On license click.
   */
  public void onLicenseClick() {
    view.openWindow(getCurrentDefLicense().getUrl());
  }

  /**
   * Sets the license.
   * 
   * @param license
   *          the new license
   */
  private void setLicense(final LicenseDTO license) {
    view.setLicense(license);
  }

  /**
   * Sets the state.
   */
  protected void setState() {
    if (applicable()) {
      // Is a user and app is not starting up
      setLicense(getCurrentDefLicense());
    }
  }
}
