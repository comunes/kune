package org.ourproject.kune.platf.integration.socialnet;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.integration.IntegrationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.server.UserSession;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;

import com.google.inject.Inject;

public class SocialNetworkMembersTest extends IntegrationTest {
    @Inject
    UserSession session;
    @Inject
    SocialNetworkManager socialNetManager;
    @Inject
    GroupManager groupFinder;

    private Group group;

    @Before
    public void init() {
        new IntegrationTestHelper(this);
        group = groupFinder.findByShortName(getSiteAdminShortName());
    }

    @Test
    public void testAdminMembersOfGroupFinder() throws Exception {
        doLogin();
        final List<Group> result = groupFinder.findAdminInGroups(group.getId());
        assertEquals(2, result.size());
    }

    @Test
    public void testCollabMembersOfGroupFinder() throws Exception {
        doLogin();
        final List<Group> result = groupFinder.findCollabInGroups(group.getId());
        assertEquals(0, result.size());
    }

}
