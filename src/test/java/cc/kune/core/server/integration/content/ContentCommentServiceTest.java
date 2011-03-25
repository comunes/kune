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
package cc.kune.core.server.integration.content;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.shared.dto.CommentDTO;
import cc.kune.core.shared.dto.StateContainerDTO;

public class ContentCommentServiceTest extends ContentServiceIntegrationTest {

    private StateContainerDTO defaultContent;

    @Test
    public void commentDefaultContent() throws Exception {
        doLogin();
        final String commentText = "Some comment";
        final CommentDTO commentDTO = contentService.addComment(session.getHash(), defaultContent.getStateToken(),
                commentText);
        assertEquals(commentDTO.getText(), commentText);
        assertEquals(commentDTO.getPositiveVotersCount(), 0);
        assertEquals(commentDTO.getNegativeVotersCount(), 0);
        assertEquals(commentDTO.getAbuseInformersCount(), 0);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void commentWithoutLoginMustFail() throws DefaultException {
        final String commentText = "Some comment";
        contentService.addComment(session.getHash(), defaultContent.getStateToken(), commentText);
    }

    @Before
    public void init() throws Exception {
        new IntegrationTestHelper(this);
        defaultContent = getSiteDefaultContent();
    }

}
