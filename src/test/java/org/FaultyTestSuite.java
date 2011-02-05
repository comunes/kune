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
