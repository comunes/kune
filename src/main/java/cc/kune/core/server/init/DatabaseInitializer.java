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
package cc.kune.core.server.init;

import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.waveprotocol.box.server.authentication.PasswordDigest;

import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.i18n.impl.I18nTranslationServiceDefault;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.I18nCountryManager;
import cc.kune.core.server.manager.I18nLanguageManager;
import cc.kune.core.server.manager.I18nTranslationManager;
import cc.kune.core.server.manager.LicenseManager;
import cc.kune.core.server.manager.UserManager;
import cc.kune.core.server.manager.impl.ContentConstants;
import cc.kune.core.server.persist.KuneTransactional;
import cc.kune.core.server.properties.KuneBasicProperties;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.dto.GroupType;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.I18nCountry;
import cc.kune.domain.I18nLanguage;
import cc.kune.domain.I18nTranslation;
import cc.kune.domain.License;
import cc.kune.domain.User;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseInitializer.
 * 
 * @author danigb@gmail.com
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class DatabaseInitializer {

  /** The Constant LOG. */
  private static final Log LOG = LogFactory.getLog(DatabaseInitializer.class);

  /** The content manager. */
  private final ContentManager contentManager;

  /** The country manager. */
  private final I18nCountryManager countryManager;

  /** The group manager. */
  private final GroupManager groupManager;

  /** The language manager. */
  private final I18nLanguageManager languageManager;

  /** The license manager. */
  private final LicenseManager licenseManager;

  /** The properties. */
  private final KuneBasicProperties properties;

  /** The translation manager. */
  private final I18nTranslationManager translationManager;

  /** The translation service. */
  private final I18nTranslationServiceDefault translationService;

  /** The user manager. */
  private final UserManager userManager;

  /**
   * Instantiates a new database initializer.
   * 
   * @param properties
   *          the properties
   * @param userManager
   *          the user manager
   * @param groupManager
   *          the group manager
   * @param licenseManager
   *          the license manager
   * @param languageManager
   *          the language manager
   * @param countryManager
   *          the country manager
   * @param translationManager
   *          the translation manager
   * @param contentManager
   *          the content manager
   * @param translationService
   *          the translation service
   */
  @Inject
  public DatabaseInitializer(final KuneBasicProperties properties, final UserManager userManager,
      final GroupManager groupManager, final LicenseManager licenseManager,
      final I18nLanguageManager languageManager, final I18nCountryManager countryManager,
      final I18nTranslationManager translationManager, final ContentManager contentManager,
      final I18nTranslationServiceDefault translationService) {
    this.properties = properties;
    this.userManager = userManager;
    this.groupManager = groupManager;
    this.licenseManager = licenseManager;
    this.languageManager = languageManager;
    this.countryManager = countryManager;
    this.translationManager = translationManager;
    this.translationService = translationService;
    this.contentManager = contentManager;
  }

  /**
   * Creates the def users group.
   * 
   * @throws Exception
   *           the exception
   * @throws UserMustBeLoggedException
   *           the user must be logged exception
   */
  private void createDefUsersGroup() throws Exception, UserMustBeLoggedException {
    final String adminName = properties.getAdminUserName();
    final String adminShortName = properties.getAdminShortName();
    final String adminEmail = properties.getAdminEmail();
    final String adminPassword = properties.getAdminPassword();

    final PasswordDigest passwdDigest = new PasswordDigest(adminPassword.toCharArray());
    userManager.createWaveAccount(adminShortName, passwdDigest);
    final User user = new User(adminShortName, adminName, adminEmail, passwdDigest.getDigest(),
        passwdDigest.getSalt(), languageManager.findByCode("en"), countryManager.findByCode("GB"),
        TimeZone.getDefault());
    groupManager.createUserGroup(user, false);
    final User dummyUser = new User("dummy", "dummy user", "example@example.com",
        passwdDigest.getDigest(), passwdDigest.getSalt(), languageManager.findByCode("en"),
        countryManager.findByCode("GB"), TimeZone.getDefault());
    groupManager.createUserGroup(dummyUser, false);

    final String siteName = properties.getDefaultSiteName();
    final String siteShortName = properties.getDefaultSiteShortName();
    final String defaultLicenseId = properties.getDefaultLicense();
    final License defaultLicense = licenseManager.findByShortName(defaultLicenseId);

    final Group siteGroup = new Group(siteShortName, siteName, defaultLicense, GroupType.PROJECT);
    groupManager.createGroup(siteGroup, user,
        ContentConstants.INITIAL_CONTENT.replaceAll("\\[%s\\]", siteName));

    final Content defaultContent = siteGroup.getDefaultContent();
    contentManager.setStatus(defaultContent.getId(), ContentStatus.publishedOnline);
    contentManager.save(defaultContent);

    // This is not necessary with ehcache (I think). Even worst, the
    // initialization hangs
    // userManager.reIndex();
    // groupManager.reIndex();
  }

  /**
   * Creates the licenses.
   */
  private void createLicenses() {
    // FIXME: Add CC RDF info (seems CC is working on new forms to add
    // license metadata)
    License license = new License("by-v3.0", "Creative Commons Attribution", "None",
        "http://creativecommons.org/licenses/by/3.0/", true, false, false, "", "images/lic/by80x15.png");
    licenseManager.persist(license);
    license = new License("by-sa-v3.0", "Creative Commons Attribution-ShareAlike", "None",
        "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "",
        "images/lic/bysa80x15.png");
    licenseManager.persist(license);
    license = new License("by-nd-v3.0", "Creative Commons Attribution-NoDerivs", "None",
        "http://creativecommons.org/licenses/by-nd/3.0/", true, false, false, "",
        "images/lic/bynd80x15.png");
    licenseManager.persist(license);
    license = new License("by-nc-v3.0", "Creative Commons Attribution-NonCommercial", "None",
        "http://creativecommons.org/licenses/by-nc/3.0/", true, false, false, "",
        "images/lic/bync80x15.png");
    licenseManager.persist(license);
    license = new License("by-nc-sa-v3.0", "Creative Commons Attribution-NonCommercial-ShareAlike",
        "None", "http://creativecommons.org/licenses/by-nc-sa/3.0/", true, false, false, "",
        "images/lic/byncsa80x15.png");
    licenseManager.persist(license);
    license = new License("by-nc-nd-v3.0", "Creative Commons Attribution-NonCommercial-NoDerivs",
        "None", "http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "",
        "images/lic/byncnd80x15.png");
    licenseManager.persist(license);
    license = new License(
        "gfdl-v1-3",
        "GNU Free Documentation License",
        "The GNU Free Documentation License (GNU FDL or simply GFDL) is a copyleft license for free documentation, giving readers the rights to copy, redistribute and modify a work and requires all copies and derivatives to be available under the same license.",
        "http://www.gnu.org/copyleft/fdl.html", false, true, false, "", "images/lic/gnu-fdl.gif");
    licenseManager.persist(license);
    license = new License(
        "fal",
        "Free Art License",
        "The Free Art license is a French copyleft license for works of art. It authorises the user to freely copy, spread, and transform creative works while respecting the author's rights.",
        "http://artlibre.org/licence/lal/en/", false, true, false, "", "images/lic/fal-license.gif");
    licenseManager.persist(license);
    license = new License(
        "gpl-v3",
        "GNU General Public License",
        "This license grant the recipients of a computer program the rights of the free software definition and uses copyleft to ensure the freedoms are preserved, even when the work is changed or added to.",
        "http://www.gnu.org/licenses/gpl.html", false, true, false, "", "images/lic/gnu-gpl.gif");
    licenseManager.persist(license);
  }

  /**
   * Creates the others.
   */
  public void createOthers() {
    final I18nLanguage english = new I18nLanguage(Long.valueOf(1819), "en", "ltr", "English", "", "",
        "en", "eng", "eng", false, "", "", "", "c == 1 ? 1 : 2", null, "L", "MMM d\\, yyyy");
    final I18nLanguage spanish = new I18nLanguage(Long.valueOf(5889), "es", "ltr", "Spanish", "", "",
        "es", "spa", "spa", true, "Español", "", "", "c == 1 ? 1 : 2", null, "L", "dd/MM/yyyy");
    languageManager.persist(english);
    languageManager.persist(spanish);
    final I18nCountry gb = new I18nCountry(Long.valueOf(75), "GB", "GBP", ".", "£%n", "", ".",
        "United Kingdom", "western", ",");
    countryManager.persist(gb);
    final I18nTranslation test = new I18nTranslation("test", english, "test", "");
    translationManager.persist(test);
  }

  /**
   * Inits the conditional.
   * 
   * @throws Exception
   *           the exception
   */
  public void initConditional() throws Exception {
    if (groupManager.count() == 0) {
      initialize();
    }
    translationService.init();
  }

  /**
   * Inits the database.
   * 
   * @throws Exception
   *           the exception
   */
  @KuneTransactional
  public void initDatabase() throws Exception {
    createLicenses();
    createOthers();
    createDefUsersGroup();
  }

  /**
   * Initialize.
   * 
   * @throws Exception
   *           the exception
   */
  private void initialize() throws Exception {
    LOG.warn("The default group '" + properties.getDefaultSiteName() + "' does not exist in Database, "
        + "creating it (see kune.default.site.shortName in kune.properties for more details)");
    initDatabase();
  }
}
