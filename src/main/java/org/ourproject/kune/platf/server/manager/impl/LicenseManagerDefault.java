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
package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.manager.LicenseManager;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;

import cc.kune.domain.License;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class LicenseManagerDefault extends DefaultManager<License, Long> implements LicenseManager {
    private License licenseFinder;
    private final DatabaseProperties properties;

    @Inject
    public LicenseManagerDefault(final Provider<EntityManager> provider, final DatabaseProperties properties,
            final License licenseFinder) {
        super(provider, License.class);
        this.properties = properties;
        this.licenseFinder = licenseFinder;
    }

    public License findByShortName(final String shortName) {
        return licenseFinder.findByShortName(shortName);
    }

    public List<License> getAll() {
        return licenseFinder.getAll();
    }

    public List<License> getCC() {
        return licenseFinder.getCC();
    }

    public License getDefLicense() {
        final String licenseDefId = properties.getDefaultLicense();
        return licenseFinder.findByShortName(licenseDefId);
    }

    public List<License> getNotCC() {
        return licenseFinder.getNotCC();
    }

    @Override
    public License persist(final License license) {
        return super.persist(license);
    }

    @Inject
    public void setLicenseFinder(final License licenseFinder) {
        this.licenseFinder = licenseFinder;
    }
}
