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
package cc.kune.core.server.manager.file;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.SessionExpiredException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.server.integration.content.ContentServiceIntegrationTest;
import cc.kune.core.server.manager.file.FileUploadManager;

import com.google.inject.Inject;

public class FileUploadManagerTest extends ContentServiceIntegrationTest {

    @Inject
    FileUploadManager fileUploadManager;

    @Before
    public void create() {
        new IntegrationTestHelper(this);
    }

    @Test(expected = SessionExpiredException.class)
    public void testSessionExp() throws Exception {
        fileUploadManager.createUploadedFile("otherhash", null, null, null, null);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void testUserMustBeAuth() throws Exception {
        fileUploadManager.createUploadedFile(null, null, null, null, null);
    }
}
