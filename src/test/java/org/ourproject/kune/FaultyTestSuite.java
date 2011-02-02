package org.ourproject.kune;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ourproject.kune.platf.server.finders.RateFinderTest;
import org.ourproject.kune.platf.server.manager.ContentManagerTest;
import org.ourproject.kune.platf.server.manager.UserManagerTest;

/**
 * A Test Suite to test (only) some tests that fails
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ RateFinderTest.class, UserManagerTest.class, ContentManagerTest.class })
public class FaultyTestSuite {

}
