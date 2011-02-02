package org.ourproject.kune;

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
import org.ourproject.kune.platf.server.manager.ContentManagerTest;
import org.ourproject.kune.platf.server.manager.GroupManagerTest;
import org.ourproject.kune.platf.server.manager.I18nManagerTest;
import org.ourproject.kune.platf.server.manager.LicenseManagerTest;
import org.ourproject.kune.platf.server.manager.TagManagerTest;
import org.ourproject.kune.platf.server.manager.TagUserContentTest;
import org.ourproject.kune.platf.server.manager.UserManagerTest;
import org.ourproject.kune.platf.server.manager.file.EntityLogoUploadManagerTest;
import org.ourproject.kune.platf.server.manager.file.FileDownloadManagerTest;
import org.ourproject.kune.platf.server.manager.file.FileManagerTest;
import org.ourproject.kune.platf.server.manager.file.FileUploadManagerTest;
import org.ourproject.kune.platf.server.manager.file.FileUtilsTest;
import org.ourproject.kune.platf.server.manager.file.ImageUtilsDefaultTest;
import org.ourproject.kune.platf.server.manager.impl.SocialNetworkManagerMoreTest;
import org.ourproject.kune.platf.server.manager.impl.SocialNetworkManagerTest;
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
        I18nManagerTest.class, TagManagerTest.class, LicenseManagerTest.class, UserManagerTest.class,
        ContentManagerTest.class, FileUploadManagerTest.class, FileDownloadManagerTest.class,
        ImageUtilsDefaultTest.class, EntityLogoUploadManagerTest.class, FileManagerTest.class, FileUtilsTest.class,
        SocialNetworkManagerTest.class, SocialNetworkManagerMoreTest.class, GroupManagerTest.class,
        TagUserContentTest.class, LicenseFinderTest.class, UserFinderTest.class, GroupFinderTest.class,
        RateFinderTest.class, AuthorizatedMethodInterceptorTest.class, AuthenticatedMethodInterceptorTest.class,
        MapperTest.class })
public class ServerManagerTestSuite {

}
