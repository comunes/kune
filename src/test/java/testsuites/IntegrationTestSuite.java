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
import org.ourproject.kune.platf.integration.DatabaseInitializationTest;
import org.ourproject.kune.platf.integration.content.ContentCommentServiceTest;
import org.ourproject.kune.platf.integration.content.ContentServiceAddTest;
import org.ourproject.kune.platf.integration.content.ContentServiceGetTest;
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
@SuiteClasses({ UserServiceTest.class, SiteServiceTest.class, SocialNetworkServiceTest.class,
        SocialNetworkMembersTest.class, DatabaseInitializationTest.class, ContentServiceAddTest.class,
        ContentCommentServiceTest.class, ContentServiceVariousTest.class, ContentServiceGetTest.class,
        ContentServiceSaveTest.class, GroupServiceTest.class })
public class IntegrationTestSuite {
}
