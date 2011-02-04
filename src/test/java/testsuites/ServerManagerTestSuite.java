package testsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
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

/**
 * Rescan with :
 * 
 * <pre>
 * find  src/test/java/org/ourproject/kune/platf/server/ -name '*.java' -exec basename \{} .java \;| paste -s - - | sed 's/     /.class, /g'
 * </pre>
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ I18nManagerTest.class, TagManagerTest.class, LicenseManagerTest.class, UserManagerTest.class,
        ContentManagerTest.class, FileUploadManagerTest.class, FileDownloadManagerTest.class,
        ImageUtilsDefaultTest.class, EntityLogoUploadManagerTest.class, FileManagerTest.class, FileUtilsTest.class,
        SocialNetworkManagerTest.class, SocialNetworkManagerMoreTest.class, GroupManagerTest.class,
        TagUserContentTest.class })
public class ServerManagerTestSuite {

}
