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
package org.ourproject.kune.platf.server;

import static org.junit.Assert.assertEquals;

import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.UserManager;

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

public abstract class PersistencePreLoadedDataTest extends PersistenceTest {
    protected static final String USER_EMAIL = "useremail@example.com";
    protected static final String USER_LONG_NAME = "the user long name";
    protected static final String USER_PASSWORD = "userPassword";
    protected static final String USER_SHORT_NAME = "usershortname";

    protected Container container;
    @Inject
    protected ContainerManager containerManager;
    protected Content content;
    @Inject
    protected ContentManager contentManager;
    @Inject
    protected I18nCountryManager countryManager;
    protected License defLicense;
    protected I18nLanguage english;
    protected I18nCountry gb;
    @Inject
    protected GroupFinder groupFinder;
    @Inject
    protected GroupManager groupManager;
    @Inject
    protected I18nLanguageManager languageManager;

    @Inject
    protected LicenseFinder licenseFinder;
    @Inject
    protected LicenseManager licenseManager;
    // @Inject
    // protected PropertyGroupManager propGroupManager;
    protected User user;
    @Inject
    protected UserFinder userFinder;
    @Inject
    protected UserManager userManager;

    public PersistencePreLoadedDataTest() {
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }

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
        gb = new I18nCountry(Long.valueOf(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom", "western", ",");
        countryManager.persist(gb);
        user = userManager.createUser(USER_SHORT_NAME, USER_LONG_NAME, USER_EMAIL, USER_PASSWORD, "en", "GB",
                TimeZone.getDefault().getID());
        defLicense = new License("by-sa-v3.0", "Creative Commons Attribution-ShareAlike", "",
                "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
        licenseManager.persist(defLicense);
        groupManager.createUserGroup(user, true);
        content = new Content();
        content.setLanguage(english);
        contentManager.persist(content);
        container = new Container();
        container.setTypeId(DocumentServerTool.TYPE_FOLDER);
        containerManager.persist(container);
    }
}
