package org.ourproject.kune.platf.integration.kuneservice;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;

public class KuneServiceTest {

    @Inject
    KuneService service;

    @Before
    public void init() {
	new IntegrationTestHelper(this);
    }

    @Test
    public void testGetLicenses() throws SerializableException {
	List licenses = service.getAllLicenses();
	assertTrue(licenses.size() > 0);
    }
}
