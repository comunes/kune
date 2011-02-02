package org.ourproject.kune;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ourproject.kune.platf.client.actions.ActionRegistryTest;
import org.ourproject.kune.platf.client.actions.ContentIconsRegistryTest;
import org.ourproject.kune.platf.client.actions.KeyStrokeTest;
import org.ourproject.kune.platf.client.actions.ShortcutTest;
import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTOTest;
import org.ourproject.kune.platf.client.ui.KuneStringUtilsTest;
import org.ourproject.kune.platf.client.ui.TextUtilsTest;
import org.ourproject.kune.platf.client.ui.dialogs.upload.FileUploaderPresenterTest;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ExternalMediaDescriptorTest;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditorPresenterTest;
import org.ourproject.kune.platf.client.utils.UrlTest;

/**
 * Rescan with :
 * 
 * <pre>
 * find  src/test/java/org/ourproject/kune/platf/client/ -name '*.java' -exec basename \{} .java \;| paste -s - - | sed 's/     /.class, /g'
 * </pre>
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ BasicMimeTypeDTOTest.class, UrlTest.class, RTESavingEditorPresenterTest.class,
        ExternalMediaDescriptorTest.class, FileUploaderPresenterTest.class, TextUtilsTest.class,
        KuneStringUtilsTest.class, ShortcutTest.class, KeyStrokeTest.class, ContentIconsRegistryTest.class,
        ActionRegistryTest.class })
public class PlatfClientTestSuite {

}
