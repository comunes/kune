package org.ourproject.kune.platf.integration.site;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.I18nLanguageManager;

import com.google.inject.Inject;

public class SiteServiceTest extends IntegrationTest {

    @Inject
    SiteService service;
    @Inject
    UserSession session;
    @Inject
    Group groupFinder;
    @Inject
    I18nLanguageManager i18nLangManager;

    @Before
    public void init() {
        new IntegrationTestHelper(this);
    }

    @Test
    public void testGetInitData() throws Exception {
        final InitDataDTO initData = service.getInitData(null);
        assertNotNull(initData);
        assertValidLicenseDTOList(initData.getLicenses());
        assertTrue(initData.getLanguages().size() > 0);
        assertTrue(initData.getCountries().size() > 0);
        assertNotNull(initData.getLanguages().get(0).getCode());
        assertNotNull(initData.getCountries().get(0).getCode());
    }

    private void assertValidLicenseDTOList(final ArrayList<LicenseDTO> licenseList) {
        assertTrue(licenseList.size() > 0);
        for (final Object o : licenseList) {
            assertNotNull(o);
            assertEquals(LicenseDTO.class, o.getClass());
        }
    }

}
