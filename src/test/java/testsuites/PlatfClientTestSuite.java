/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package testsuites;

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
