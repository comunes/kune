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
package cc.kune.core.server;

import static cc.kune.docs.shared.DocsToolConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;

import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.content.CreationService;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.License;
import cc.kune.domain.User;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.LicenseFinder;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class PersistencePreLoadedDataTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class PersistencePreLoadedDataTest extends PersistenceTest {

  /** The Constant USER_EMAIL. */
  protected static final String USER_EMAIL = "useremail@example.com";

  /** The Constant USER_LONG_NAME. */
  protected static final String USER_LONG_NAME = "the user long name";

  /** The Constant USER_PASSWORD. */
  protected static final String USER_PASSWORD = "userPassword";

  /** The Constant USER_SHORT_NAME. */
  protected static final String USER_SHORT_NAME = "usershortname";

  /** The container. */
  protected Container container;

  /** The container manager. */
  @Inject
  protected ContainerManager containerManager;

  /** The content. */
  protected Content content;

  /** The content manager. */
  @Inject
  protected ContentManager contentManager;

  /** The country manager. */
  @Inject
  protected I18nCountryManager countryManager;

  /** The creation service. */
  @Inject
  protected CreationService creationService;

  /** The def license. */
  protected License defLicense;

  /** The english. */
  protected I18nLanguage english;

  /** The gb. */
  protected I18nCountry gb;

  /** The group finder. */
  @Inject
  protected GroupFinder groupFinder;

  /** The group manager. */
  @Inject
  protected GroupManager groupManager;

  /** The language manager. */
  @Inject
  protected I18nLanguageManager languageManager;

  /** The license finder. */
  @Inject
  protected LicenseFinder licenseFinder;

  /** The license manager. */
  @Inject
  protected LicenseManager licenseManager;

  /** The other container. */
  protected Container otherContainer;
  // @Inject
  // protected PropertyGroupManager propGroupManager;
  /** The user. */
  protected User user;

  /** The user finder. */
  @Inject
  protected UserFinder userFinder;

  /** The user manager. */
  @Inject
  protected UserManager userManager;

  /**
   * Instantiates a new persistence pre loaded data test.
   */
  public PersistencePreLoadedDataTest() {
  }

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
   * Pre load data.
   * 
   * @throws Exception
   *           the exception
   */
  @Before
  public void preLoadData() throws Exception {
    openTransaction();
    assertEquals(0, userFinder.getAll().size());
    assertEquals(0, groupFinder.getAll().size());
    assertEquals(0, licenseFinder.getAll().size());
    // final PropertyGroup groupProps = new PropertyGroup(Group.PROPS_ID);
    // final PropertyGroup userProps = new PropertyGroup(User.PROPS_ID);
    // propGroupManager.persist(userProps);
    // propGroupManager.persist(groupProps);
    english = new I18nLanguage(Long.valueOf(1819), "English", "English", "en");
    languageManager.persist(english);
    gb = new I18nCountry(Long.valueOf(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom",
        "western", ",");
    countryManager.persist(gb);
    defLicense = new License("by-sa-v3.0", "Creative Commons Attribution-ShareAlike", "",
        "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
    licenseManager.persist(defLicense);
    user = userManager.createUser(USER_SHORT_NAME, USER_LONG_NAME, USER_EMAIL, USER_PASSWORD, "en",
        "GB", TimeZone.getDefault().getID(), true);
    // groupManager.createUserGroup(user, true);
    content = new Content();
    content.setLanguage(english);
    contentManager.persist(content);
    container = new Container();
    container.setTypeId(TYPE_FOLDER);
    container.setToolName(TOOL_NAME);
    containerManager.persist(container);
    otherContainer = new Container();
    otherContainer.setTypeId(TYPE_FOLDER);
    otherContainer.setToolName(TOOL_NAME);
    containerManager.persist(otherContainer);
  }
}
