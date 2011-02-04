package testsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ourproject.kune.platf.server.access.AccessRightsServiceTest;
import org.ourproject.kune.platf.server.access.FinderTest;
import org.ourproject.kune.platf.server.auth.AuthenticatedMethodInterceptorTest;
import org.ourproject.kune.platf.server.auth.AuthorizatedMethodInterceptorTest;
import org.ourproject.kune.platf.server.domain.GroupListTest;
import org.ourproject.kune.platf.server.finders.GroupFinderTest;
import org.ourproject.kune.platf.server.finders.LicenseFinderTest;
import org.ourproject.kune.platf.server.finders.RateFinderTest;
import org.ourproject.kune.platf.server.finders.UserFinderTest;
import org.ourproject.kune.platf.server.mapper.MapperTest;
import org.ourproject.kune.platf.server.properties.KunePropertiesTest;

/**
 * Rescan with :
 * 
 * <pre>
 * find  src/test/java/org/ourproject/kune/platf/server/ -name '*.java' -exec basename \{} .java \;| paste -s - - | sed 's/     /.class, /g'
 * </pre>
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ GroupListTest.class, AccessRightsServiceTest.class, FinderTest.class, KunePropertiesTest.class,
        LicenseFinderTest.class, UserFinderTest.class, GroupFinderTest.class, RateFinderTest.class,
        AuthorizatedMethodInterceptorTest.class, AuthenticatedMethodInterceptorTest.class, MapperTest.class })
public class ServerOtherTestSuite {

}
