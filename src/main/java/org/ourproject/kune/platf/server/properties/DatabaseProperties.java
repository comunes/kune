/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Sugar
 */
@Singleton
public class DatabaseProperties {
    private final KuneProperties properties;

    @Inject
    public DatabaseProperties(final KuneProperties properties) {
        this.properties = properties;
    }

    public String getDefaultSiteShortName() {
        return properties.get("kune.default.site.shortName");
    }

    public String getDefaultSiteName() {
        return properties.get("kune.default.site.name");
    }

    public String getAdminUserName() {
        return properties.get("kune.admin.name");
    }

    public String getAdminShortName() {
        return properties.get("kune.admin.shortName");
    }

    public String getAdminEmail() {
        return properties.get("kune.admin.email");
    }

    public String getAdminPassword() {
        return properties.get("kune.admin.password");
    }

    public String getDefaultLicense() {
        return properties.get("kune.default.license");
    }
}
