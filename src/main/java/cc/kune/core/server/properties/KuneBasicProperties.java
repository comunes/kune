/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.properties;

import cc.kune.common.shared.utils.TextUtils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KuneBasicProperties {
  private final KuneProperties properties;
  private String siteUrlWithoutHttp;

  @Inject
  public KuneBasicProperties(final KuneProperties properties) {
    this.properties = properties;
  }

  public String getAdminEmail() {
    return properties.get(KuneProperties.SITE_ADMIN_EMAIL);
  }

  public String getAdminPassword() {
    return properties.get(KuneProperties.SITE_ADMIN_PASSWD);
  }

  public String getAdminShortName() {
    return properties.get(KuneProperties.SITE_ADMIN_SHORTNAME);
  }

  public String getAdminUserName() {
    return properties.get(KuneProperties.SITE_ADMIN_NAME);
  }

  public String getDefaultLicense() {
    return properties.get(KuneProperties.SITE_DEF_LICENSE);
  }

  public String getDefaultSiteName() {
    return properties.get(KuneProperties.SITE_NAME);
  }

  public String getDefaultSiteShortName() {
    return properties.get(KuneProperties.SITE_SHORTNAME);
  }

  /**
   * Gets the site common name (can be something like "this foo site" not
   * necessary a domain)
   * 
   * @return the site common name
   */
  public String getSiteCommonName() {
    return properties.get(KuneProperties.SITE_COMMON_NAME);
  }

  public String getSiteUrl() {
    return properties.get(KuneProperties.SITE_URL);
  }

  public String getSiteUrlWithoutHttp() {
    if (siteUrlWithoutHttp == null) {
      siteUrlWithoutHttp = TextUtils.removeHttp(properties.get(KuneProperties.SITE_URL));
    }
    return siteUrlWithoutHttp;
  }

  public String getWelcomewave() {
    return properties.get(KuneProperties.WELCOME_WAVE);
  }
}
