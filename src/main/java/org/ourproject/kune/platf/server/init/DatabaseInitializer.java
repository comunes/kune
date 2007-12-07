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

import javax.persistence.NoResultException;

import org.ourproject.kune.platf.client.errors.UserMustBeLoggedException;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupType;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.I18nCountryManager;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;
import org.ourproject.kune.platf.server.users.UserManager;

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

    @Inject
    public DatabaseInitializer(final DatabaseProperties properties, final UserManager userManager,
            final GroupManager groupManager, final LicenseManager licenseManager,
            final I18nLanguageManager languageManager, final I18nCountryManager countryManager,
            final I18nTranslationManager translationManager) {
        this.properties = properties;
        this.userManager = userManager;
        this.groupManager = groupManager;
        this.licenseManager = licenseManager;
        this.languageManager = languageManager;
        this.countryManager = countryManager;
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
        // try {
        // Metainf metainf = new Metainf(0);
        // metainfManager.persist(metainf);
        // EtlExecutor.newExecutor(new
        // File("src/main/resources/db/kune-db-init.xml")).execute();
        // } catch (EtlExecutorException e) {
        // e.printStackTrace();
        // }

        I18nLanguage english = new I18nLanguage(new Long(1819), "English", "English", "en");
        languageManager.persist(english);
        I18nCountry gb = new I18nCountry(new Long(75), "GB", "United Kingdom", "", "£%n", "GBP", ",", ".", ".",
                "western");
        countryManager.merge(gb);

        // CSVReader reader;
        // String[] line = null;
        // try {
        // reader = new CSVReader(new FileReader("data/country_data.csv"), ',',
        // '"');
        // while ((line = reader.readNext()) != null) {
        // I18nCountry country = new I18nCountry(new Long(line[0]), line[1],
        // line[2], line[3], line[4], line[5],
        // line[6], line[7], line[8], line[9]);
        // countryManager.merge(country);
        // }
        //
        // reader = new CSVReader(new FileReader("data/language_data.csv"), ',',
        // '"');
        //
        // // code is iso6391 || iso6392 || rfc3306
        // while ((line = reader.readNext()) != null) {
        // I18nLanguage language = new I18nLanguage(new Long(line[0]), line[1],
        // line[2], line[3], line[4],
        // line[5], line[6], line[7], line[8], line[9], line[10],
        // line[11].equals(0), line[12], line[13],
        // line[14], (line[1].length() > 0 ? line[1] : line[2].length() > 0 ?
        // line[2] : line[4]));
        // languageManager.merge(language);
        // }
        //
        // reader = new CSVReader(new FileReader("data/translation_data.csv"),
        // ',', '"');
        //
        // while ((line = reader.readNext()) != null) {
        // I18nTranslation trans = new I18nTranslation(line[1], line[2],
        // line[3], line[4].length() == 0 ? null
        // : new Integer(line[4]), line[5], languageManager.find(new
        // Long(line[6])), line[7], new Integer(
        // line[8]));
        // translationManager.persist(trans);
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // System.err.print(line[0] + " " + line[1] + " " + line[7] + " " + new
        // Integer(line[8]));
        // }

    }

    private void createDefUsersGroup() throws SerializableException, UserMustBeLoggedException {
        String adminName = properties.getAdminUserName();
        String adminShortName = properties.getAdminShortName();
        String adminEmail = properties.getAdminEmail();
        String adminPassword = properties.getAdminPassword();
        User user = new User(adminShortName, adminName, adminEmail, adminPassword, languageManager.findByCode("en"),
                countryManager.findByCode("GB"));
        groupManager.createUserGroup(user);

        String siteName = properties.getDefaultSiteName();
        String siteShortName = properties.getDefaultSiteShortName();
        String defaultLicenseId = properties.getDefaultLicense();
        final License defaultLicense = licenseManager.findByShortName(defaultLicenseId);

        Group siteGroup = new Group(siteShortName, siteName, defaultLicense, GroupType.PROJECT);
        groupManager.createGroup(siteGroup, user);

        userManager.reIndex();
        groupManager.reIndex();

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
