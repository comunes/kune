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
package org.ourproject.kune.platf.server.finders;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.properties.DatabaseProperties;

import cc.kune.domain.License;

import com.google.inject.Inject;

public class LicenseFinderTest extends PersistenceTest {
    @Inject
    License finder;
    @Inject
    DatabaseProperties properties;
    private License license1;
    private License license2;
    private License licenseDef;

    @Before
    public void addData() {
        openTransaction();
        licenseDef = new License("by-sa-v3.0", "Creative Commons Attribution-ShareAlike", "",
                "http://creativecommons.org/licenses/by-sa/3.0/", true, true, false, "", "");
        persist(licenseDef);
        license1 = new License("by-nc-nd-v3.0", "Creative Commons Attribution-NonCommercial-NoDerivs", "",
                "http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "", "");
        persist(license1);
        license2 = new License("gfdl-v1.3", "GNU Free Documentation License", "",
                "http://www.gnu.org/copyleft/fdl.html", false, true, false, "", "");
        persist(license2);
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }

    @Test
    public void findAll() {
        List<License> all = finder.getAll();
        assertEquals(3, all.size());
    }

    @Test
    public void findById() {
        License lic = finder.findByShortName(license1.getShortName());
        assertNotNull(lic);
        assertEquals(license1.getShortName(), lic.getShortName());
        assertEquals(license1.getLongName(), lic.getLongName());
    }

    @Test
    public void findCC() {
        List<License> cc = finder.getCC();
        assertEquals(2, cc.size());
    }

    @Test
    public void findDefaultLicense() {
        String licenseDefId = properties.getDefaultLicense();
        License lic = finder.findByShortName(licenseDefId);
        assertNotNull(lic);
        assertEquals(licenseDef.getShortName(), lic.getShortName());
        assertEquals(licenseDef.getLongName(), lic.getLongName());
    }

    @Test
    public void findNotCC() {
        List<License> notCc = finder.getNotCC();
        assertEquals(1, notCc.size());
    }

}
