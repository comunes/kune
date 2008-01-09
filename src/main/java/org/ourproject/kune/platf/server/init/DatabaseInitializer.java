/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.server.init;

import java.util.TimeZone;

import javax.persistence.NoResultException;

import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupType;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.I18nTranslation;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.manager.UserManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.wideplay.warp.persist.TransactionType;
import com.wideplay.warp.persist.Transactional;

public class DatabaseInitializer {
    private final LicenseManager licenseManager;
    private final DatabaseProperties properties;
    private final GroupManager groupManager;
    private final UserManager userManager;
    private final I18nLanguageManager languageManager;
    private final I18nCountryManager countryManager;
    private final I18nTranslationManager translationManager;
    private final ContentManager contentManager;

    @Inject
    public DatabaseInitializer(final DatabaseProperties properties, final UserManager userManager,
            final GroupManager groupManager, final LicenseManager licenseManager,
            final I18nLanguageManager languageManager, final I18nCountryManager countryManager,
            final I18nTranslationManager translationManager, final ContentManager contentManager) {
        this.properties = properties;
        this.userManager = userManager;
        this.groupManager = groupManager;
        this.licenseManager = licenseManager;
        this.languageManager = languageManager;
        this.countryManager = countryManager;
        this.translationManager = translationManager;
        this.contentManager = contentManager;
    }

    public void initConditional() throws SerializableException {
        try {
            groupManager.getDefaultGroup();
        } catch (NoResultException e) {
            initDatabase();
        }
    }

    @Transactional(type = TransactionType.READ_WRITE)
    public void initDatabase() throws SerializableException {
        createOthers();
        createLicenses();
        createDefUsersGroup();
    }

    public void createOthers() {
        I18nLanguage english = new I18nLanguage(new Long(1819), "en", "ltr", "English", "", "", "en", "eng", "eng",
                false, "", "", "", "c == 1 ? 1 : 2", null, "L");
        I18nLanguage spanish = new I18nLanguage(new Long(5889), "es", "ltr", "Spanish", "", "", "es", "spa", "spa",
                true, "Español", "", "", "c == 1 ? 1 : 2", null, "L");
        languageManager.persist(english);
        languageManager.persist(spanish);
        I18nCountry gb = new I18nCountry(new Long(75), "GB", "GBP", ".", "£%n", "", ".", "United Kingdom", "western",
                ",");
        countryManager.persist(gb);
        I18nTranslation test = new I18nTranslation("test", english, "test");
        translationManager.persist(test);
    }

    private void createDefUsersGroup() throws SerializableException, UserMustBeLoggedException {
        String adminName = properties.getAdminUserName();
        String adminShortName = properties.getAdminShortName();
        String adminEmail = properties.getAdminEmail();
        String adminPassword = properties.getAdminPassword();
        // FIXME:
        User user = new User(adminShortName, adminName, adminEmail, adminPassword, languageManager.findByCode("en"),
                countryManager.findByCode("GB"), TimeZone.getDefault());
        groupManager.createUserGroup(user);

        String siteName = properties.getDefaultSiteName();
        String siteShortName = properties.getDefaultSiteShortName();
        String defaultLicenseId = properties.getDefaultLicense();
        final License defaultLicense = licenseManager.findByShortName(defaultLicenseId);

        Group siteGroup = new Group(siteShortName, siteName, defaultLicense, GroupType.PROJECT);
        groupManager.createGroup(siteGroup, user);

        userManager.reIndex();
        groupManager.reIndex();

        Content defaultContent = siteGroup.getDefaultContent();
        Revision revision = new Revision(defaultContent);
        revision.setTitle("Welcome to kune demo");
        revision
                .setDataContent("<img src=\"http://kune.ourproject.org/IMG/cache-200x37/siteon0-200x37.png\" align=\"right\">This a initial demo of <a href=\"http://kune.ourproject.org\">kune</a>.<br><div style=\"text-align: right;\"><br></div>Kune is currently under initial development.<br><br>To test it, <a href=\"#login\">sign in registering an user</a>, but take into account that:<br><ul><li>kune is not optimized yet, then the initial load and other operations maybe are slow.</li><li>Don\'t use passwords that you are using in other sites (kune isn\'t secure yet storing passwords).</li><li>The site is divided in two main areas: the public space and the workspace:</li><ul><li>The workspace is the most important part of kune, because is where users collaborate and communicates which each others. This part is the most dynamic and we are using modern (and sometimes experimental) web technologies to improve the end user experience. I heavily under development.<br></li><li>The public space is currently lest developed. Our main goal is to offer customs and configurable styles for groups/users contents.</li></ul></ul><font size=\"4\"><br>We need your feedback<br><br></font>Please help us to improve this software reporting <a href=\"http://code.google.com/p/kune/issues\">bugs and/or suggestions</a>. Also you <a href=\"#translate\">help us to translate it</a> into other languages.<br><br>Thank you,<br><br>--<br><span style=\"font-style: italic;\">The kune development team</span><br>");
        defaultContent.addRevision(revision);
        contentManager.persist(defaultContent);
    }

    private void createLicenses() {
        // FIXME: add version to name
        License license = new License("by", "Creative Commons Attribution", "None",
                "http://creativecommons.org/licenses/by/3.0/", true, false, false, "FIXME: Here CC RDF",
                "images/lic/by80x15.png");
        licenseManager.persist(license);
        license = new License("by-sa", "Creative Commons Attribution-ShareAlike", "None",
                "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "FIXME: Here CC RDF",
                "images/lic/bysa80x15.png");
        licenseManager.persist(license);
        license = new License("by-nd", "Creative Commons Attribution-NoDerivs", "None",
                "http://creativecommons.org/licenses/by-nd/3.0/", true, false, false, "FIXME: Here CC RDF",
                "images/lic/bynd80x15.png");
        licenseManager.persist(license);
        license = new License("by-nc", "Creative Commons Attribution-NonCommercial", "None",
                "http://creativecommons.org/licenses/by-nc/3.0/", true, false, false, "FIXME: Here CC RDF",
                "images/lic/bync80x15.png");
        licenseManager.persist(license);
        license = new License("by-nc-sa", "Creative Commons Attribution-NonCommercial-ShareAlike", "None",
                "http://creativecommons.org/licenses/by-nc-sa/3.0/", true, false, false, "FIXME: Here CC RDF",
                "images/lic/byncsa80x15.png");
        licenseManager.persist(license);
        license = new License("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "None",
                "http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "FIXME: Here CC RDF",
                "images/lic/byncnd80x15.png");
        licenseManager.persist(license);
        license = new License("gfdl", "GNU Free Documentation License", "None", "http://www.gnu.org/copyleft/fdl.html",
                false, true, false, "", "images/lic/gnu-fdl.gif");
        licenseManager.persist(license);
        license = new License("fal", "Free Art License", "None", "http://artlibre.org/licence/lal/en/", false, true,
                false, "", "images/lic/fal-license.gif");
        licenseManager.persist(license);

    }

}
