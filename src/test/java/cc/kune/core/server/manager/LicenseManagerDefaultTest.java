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
package cc.kune.core.server.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cc.kune.core.server.PersistenceTest;
import cc.kune.domain.License;
import cc.kune.domain.finders.LicenseFinder;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseManagerDefaultTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LicenseManagerDefaultTest extends PersistenceTest {

  /** The license. */
  private License license;

  /** The license finder. */
  @Inject
  LicenseFinder licenseFinder;

  /** The license manager. */
  @Inject
  LicenseManager licenseManager;

  /**
   * Close.
   */
  @After
  public void close() {
    if (getTransaction().isActive()) {
      getTransaction().rollback();
    }
  }

  /**
   * Insert data.
   */
  @Before
  public void insertData() {
    openTransaction();
    assertEquals(0, licenseFinder.getAll().size());
    license = new License("by", "Creative Commons Attribution", "",
        "http://creativecommons.org/licenses/by/3.0/", true, false, false, "", "");
    licenseManager.persist(license);
  }

  /**
   * Test license creation.
   */
  @Test
  public void testLicenseCreation() {
    assertNotNull(license.getId());
    assertEquals(1, licenseFinder.getAll().size());
  }
}
