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

import java.util.List;

import javax.persistence.EntityManager;

import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.persist.DataSourceKune;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.domain.License;
import cc.kune.domain.finders.LicenseFinder;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseManagerDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class LicenseManagerDefault extends DefaultManager<License, Long> implements LicenseManager {

  /** The license finder. */
  private LicenseFinder licenseFinder;

  /** The properties. */
  private final KuneBasicProperties properties;

  /**
   * Instantiates a new license manager default.
   * 
   * @param provider
   *          the provider
   * @param properties
   *          the properties
   * @param licenseFinder
   *          the license finder
   */
  @Inject
  public LicenseManagerDefault(@DataSourceKune final Provider<EntityManager> provider,
      final KuneBasicProperties properties, final LicenseFinder licenseFinder) {
    super(provider, License.class);
    this.properties = properties;
    this.licenseFinder = licenseFinder;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.LicenseManager#findByShortName(java.lang.String
   * )
   */
  @Override
  public License findByShortName(final String shortName) {
    return licenseFinder.findByShortName(shortName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.LicenseManager#getAll()
   */
  @Override
  public List<License> getAll() {
    return licenseFinder.getAll();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.LicenseManager#getCC()
   */
  @Override
  public List<License> getCC() {
    return licenseFinder.getCC();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.LicenseManager#getDefLicense()
   */
  @Override
  public License getDefLicense() {
    final String licenseDefId = properties.getDefaultLicense();
    return licenseFinder.findByShortName(licenseDefId);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.manager.LicenseManager#getNotCC()
   */
  @Override
  public List<License> getNotCC() {
    return licenseFinder.getNotCC();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.impl.DefaultManager#persist(java.lang.Object)
   */
  @Override
  public License persist(final License license) {
    return super.persist(license);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.manager.LicenseManager#setLicenseFinder(cc.kune.domain
   * .finders.LicenseFinder)
   */
  @Override
  @Inject
  public void setLicenseFinder(final LicenseFinder licenseFinder) {
    this.licenseFinder = licenseFinder;
  }
}
