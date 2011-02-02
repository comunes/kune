package org.ourproject.kune;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ourproject.kune.platf.integration.DatabaseInitializationTest;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.platf.integration.content.ContentCommentServiceTest;
import org.ourproject.kune.platf.integration.content.ContentServiceAddTest;
import org.ourproject.kune.platf.integration.content.ContentServiceGetTest;
import org.ourproject.kune.platf.integration.content.ContentServiceIntegrationTest;
import org.ourproject.kune.platf.integration.content.ContentServiceSaveTest;
import org.ourproject.kune.platf.integration.content.ContentServiceVariousTest;
import org.ourproject.kune.platf.integration.kuneservice.GroupServiceTest;
import org.ourproject.kune.platf.integration.site.SiteServiceTest;
import org.ourproject.kune.platf.integration.site.UserServiceTest;
import org.ourproject.kune.platf.integration.socialnet.SocialNetworkMembersTest;
import org.ourproject.kune.platf.integration.socialnet.SocialNetworkServiceTest;

/**
 * Rescan with :
 * 
 * <pre>
 * find  src/test/java/org/ourproject/kune/platf/integration/ -name '*.java' -exec basename \{} .java \;| paste -s - - | sed 's/     /.class, /g'
 * </pre>
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ IntegrationTestHelper.class, UserServiceTest.class, SiteServiceTest.class,
        SocialNetworkServiceTest.class, SocialNetworkMembersTest.class, DatabaseInitializationTest.class,
        ContentServiceAddTest.class, ContentServiceIntegrationTest.class, ContentCommentServiceTest.class,
        ContentServiceVariousTest.class, ContentServiceGetTest.class, ContentServiceSaveTest.class,
        GroupServiceTest.class })
public class IntegrationTestSuite {
}
