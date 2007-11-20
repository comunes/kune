/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.app.client;

import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceMocked;
import org.ourproject.kune.platf.client.rpc.SiteService;
import org.ourproject.kune.platf.client.rpc.SiteServiceMocked;
import org.ourproject.kune.platf.client.rpc.MockedService;
import org.ourproject.kune.sitebar.client.rpc.UserService;
import org.ourproject.kune.sitebar.client.rpc.SiteBarServiceMocked;

public class MockServer {
    public static final boolean GWT = false;
    public static final boolean TEST = true;

    public static void start(final boolean isTest) {
	MockedService.isTest = isTest;
	SiteService.App.setMock(new SiteServiceMocked());
	ContentService.App.setMock(new ContentServiceMocked());
	UserService.App.setMock(new SiteBarServiceMocked());
    }

}
