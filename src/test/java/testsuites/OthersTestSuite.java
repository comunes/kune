package testsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ourproject.kune.blogs.server.BlogServerToolTest;
import org.ourproject.kune.chat.ChatToolTest;
import org.ourproject.kune.chat.server.managers.XmppManagerTest;
import org.ourproject.kune.docs.DocumentToolTest;
import org.ourproject.kune.docs.server.DocumentServerToolTest;
import org.ourproject.kune.gallery.server.GalleryServerToolTest;
import org.ourproject.kune.rack.filters.rest.TestRESTMethodFinder;
import org.ourproject.kune.rack.filters.rest.TestRESTServiceDefinition;
import org.ourproject.kune.wiki.server.WikiServerToolTest;
import org.ourproject.kune.workspace.client.entityheader.EntityLogoPresenterTest;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizardPresenterTest;
import org.ourproject.kune.workspace.client.socialnet.RolActionTest;
import org.ourproject.kune.workspace.client.socialnet.RolComparatorTest;
import org.ourproject.kune.workspace.client.tags.TagsSummaryPresenterTest;
import org.ourproject.kune.workspace.client.tool.ToolSelectorPresenterTest;

/**
 * Rescan with :
 * 
 * <pre>
 * find  src/test/java/org/ourproject/kune/{chat,docs,gallery,wiki,workspace,blogs,rack}  -name '*.java' -exec basename \{} .java \;| paste -s - - | sed 's/    /.class, /g'
 * 
 * </pre>
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ ChatToolTest.class, XmppManagerTest.class, DocumentToolTest.class, DocumentServerToolTest.class,
        GalleryServerToolTest.class, WikiServerToolTest.class, EntityLogoPresenterTest.class,
        ToolSelectorPresenterTest.class, TagsSummaryPresenterTest.class, RolActionTest.class, RolComparatorTest.class,
        LicenseWizardPresenterTest.class, BlogServerToolTest.class, TestRESTServiceDefinition.class,
        TestRESTMethodFinder.class })
public class OthersTestSuite {
}
