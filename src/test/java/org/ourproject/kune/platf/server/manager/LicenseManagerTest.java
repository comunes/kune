package org.ourproject.kune.platf.server.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.License;

import com.google.inject.Inject;

public class LicenseManagerTest extends PersistenceTest {
    @Inject
    LicenseManager licenseManager;
    @Inject
    License licenseFinder;
    private License license;

    @Before
    public void insertData() {
        openTransaction();
        assertEquals(0, licenseFinder.getAll().size());
        license = new License("by", "Creative Commons Attribution", "", "http://creativecommons.org/licenses/by/3.0/",
                true, false, false, "", "");
        licenseManager.persist(license);
    }

    @Test
    public void testLicenseCreation() {
        assertNotNull(license.getId());
        assertEquals(1, licenseFinder.getAll().size());
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            getTransaction().rollback();
        }
    }
}
