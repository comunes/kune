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
package cc.kune.core.server.properties;

import cc.kune.common.shared.utils.TextUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneBasicProperties.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class KuneBasicProperties {

  /** The properties. */
  private final KuneProperties properties;

  /** The site url without http. */
  private String siteUrlWithoutHttp;

  /**
   * Instantiates a new kune basic properties.
   * 
   * @param properties
   *          the properties
   */
  @Inject
  public KuneBasicProperties(final KuneProperties properties) {
    this.properties = properties;
  }

  /**
   * Gets the admin email.
   * 
   * @return the admin email
   */
  public String getAdminEmail() {
    return properties.get(KuneProperties.SITE_ADMIN_EMAIL);
  }

  /**
   * Gets the admin password.
   * 
   * @return the admin password
   */
  public String getAdminPassword() {
    return properties.get(KuneProperties.SITE_ADMIN_PASSWD);
  }

  /**
   * Gets the admin short name.
   * 
   * @return the admin short name
   */
  public String getAdminShortName() {
    return properties.get(KuneProperties.SITE_ADMIN_SHORTNAME);
  }

  /**
   * Gets the admin user name.
   * 
   * @return the admin user name
   */
  public String getAdminUserName() {
    return properties.get(KuneProperties.SITE_ADMIN_NAME);
  }

  /**
   * Gets the default license.
   * 
   * @return the default license
   */
  public String getDefaultLicense() {
    return properties.get(KuneProperties.SITE_DEF_LICENSE);
  }

  /**
   * Gets the default site name.
   * 
   * @return the default site name
   */
  public String getDefaultSiteName() {
    return properties.get(KuneProperties.SITE_NAME);
  }

  /**
   * Gets the default site short name.
   * 
   * @return the default site short name
   */
  public String getDefaultSiteShortName() {
    return properties.get(KuneProperties.SITE_SHORTNAME);
  }

  /**
   * Gets the site common name (can be something like "this foo site" not
   * necessary a domain).
   * 
   * @return the site common name
   */
  public String getSiteCommonName() {
    return properties.get(KuneProperties.SITE_COMMON_NAME);
  }

  /**
   * Gets the site url.
   * 
   * @return the site url
   */
  public String getSiteUrl() {
    return properties.get(KuneProperties.SITE_URL);
  }

  /**
   * Gets the site url without http.
   * 
   * @return the site url without http
   */
  public String getSiteUrlWithoutHttp() {
    if (siteUrlWithoutHttp == null) {
      siteUrlWithoutHttp = TextUtils.removeHttp(properties.get(KuneProperties.SITE_URL));
    }
    return siteUrlWithoutHttp;
  }

  /**
   * Gets the welcomewave.
   * 
   * @return the welcomewave
   */
  public String getWelcomewave() {
    return properties.get(KuneProperties.WELCOME_WAVE);
  }
}
