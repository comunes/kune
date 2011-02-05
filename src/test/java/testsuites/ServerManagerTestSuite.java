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
