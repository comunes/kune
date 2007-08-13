package org.ourproject.kune.platf.server.manager;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.domain.User;

public class AccessListManagerTest {

    private AccessListManager accessManager;

    @Before
    public void createSession() {
	accessManager = new AccessListManagerDefault();
    }

    @Test
    public void checkUserHasAccess() {
	User user = new TestDomainHelper().createUser(1);

	// accessManager.check(user, content);

    }
}
