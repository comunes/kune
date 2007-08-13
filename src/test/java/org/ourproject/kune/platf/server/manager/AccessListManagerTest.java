package org.ourproject.kune.platf.server.manager;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.domain.User;

public class AccessListManagerTest {

    private AccessListManager accessListManager;

    @Before
    public void createSession() {
	accessListManager = new AccessListManagerDefault();
    }

    @Test
    public void getUserAccessListFromContents() {
	User user = new TestDomainHelper().createUser(1);

	// accessListManager.get(user, content);

    }

    @Test
    public void getUserAccessListFromSocialNet() {

    }
}
