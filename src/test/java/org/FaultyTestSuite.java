package org;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ourproject.kune.platf.integration.content.ContentServiceGetTest;
import org.ourproject.kune.platf.integration.content.ContentServiceIntegrationTest;
import org.ourproject.kune.platf.server.finders.RateFinderTest;
import org.ourproject.kune.platf.server.manager.ContentManagerTest;
import org.ourproject.kune.platf.server.manager.UserManagerTest;
import org.ourproject.kune.platf.server.manager.impl.SocialNetworkManagerMoreTest;

/**
 * A Test Suite to test (only) some tests that fails
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ RateFinderTest.class, UserManagerTest.class, SocialNetworkManagerMoreTest.class,
        ContentManagerTest.class, ContentServiceIntegrationTest.class, ContentServiceGetTest.class })
public class FaultyTestSuite {

}
