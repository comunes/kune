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
package cc.kune.core.server.access;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.server.TestDomainHelper;
import cc.kune.core.server.access.FinderServiceDefault;
import cc.kune.core.server.content.CommentManager;
import cc.kune.core.server.content.ContainerManager;
import cc.kune.core.server.content.ContentManager;
import cc.kune.core.server.manager.GroupManager;
import cc.kune.core.server.manager.RateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.Container;
import cc.kune.domain.Content;
import cc.kune.domain.Group;
import cc.kune.domain.ToolConfiguration;

public class FinderTest {

    private GroupManager groupManager;
    private ContainerManager containerManager;
    private ContentManager contentManager;
    private RateManager rateManager;
    private FinderServiceDefault finder;
    private CommentManager commentManager;

    @Test(expected = ContentNotFoundException.class)
    public void contentAndFolderMatch() throws Exception {
        final Content descriptor = new Content();
        final Container container = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName2");
        descriptor.setContainer(container);
        Mockito.when(contentManager.find(1L)).thenReturn(descriptor);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1"), null);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndGrouplMatch() throws Exception {
        final Content descriptor = new Content();
        final Container container = TestDomainHelper.createFolderWithIdAndGroupAndTool(5, "groupOther", "toolName");
        descriptor.setContainer(container);
        Mockito.when(contentManager.find(1L)).thenReturn(descriptor);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1"), null);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndToolMatch() throws Exception {
        final Content descriptor = new Content();
        final Container container = TestDomainHelper.createFolderWithId(1);
        descriptor.setContainer(container);
        Mockito.when(contentManager.find(1L)).thenReturn(descriptor);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1"), null);
    }

    @Before
    public void createSession() {
        this.groupManager = Mockito.mock(GroupManager.class);
        this.containerManager = Mockito.mock(ContainerManager.class);
        this.contentManager = Mockito.mock(ContentManager.class);
        this.rateManager = Mockito.mock(RateManager.class);
        this.commentManager = Mockito.mock(CommentManager.class);
        this.finder = new FinderServiceDefault(groupManager, containerManager, contentManager, rateManager,
                commentManager);
    }

    @Test
    public void getGroupDefaultContent() throws Exception {
        final Group group = new Group();
        final Content descriptor = new Content();
        group.setDefaultContent(descriptor);
        Mockito.when(groupManager.findByShortName("groupShortName")).thenReturn(group);

        final Content content = finder.getContent(new StateToken("groupShortName", null, null, null), null);
        assertSame(descriptor, content);
    }

    @Test
    public void testCompleteToken() throws Exception {
        final Container container = TestDomainHelper.createFolderWithIdAndGroupAndTool(1, "groupShortName", "toolName");
        final Content descriptor = new Content();
        descriptor.setId(1L);
        descriptor.setContainer(container);

        Mockito.when(contentManager.find(2L)).thenReturn(descriptor);

        final Content content = finder.getContent(new StateToken("groupShortName", "toolName", "1", "2"), null);
        assertSame(descriptor, content);
    }

    @Test(expected = ContentNotFoundException.class)
    public void testContainerExistsButContentNotFound() throws Exception {
        finder.getContent(new StateToken("groupShortName", "toolName", "1", "999"), null);
    }

    @Test
    public void testDefaultGroupContent() throws Exception {
        final Group userGroup = new Group();
        final Content descriptor = TestDomainHelper.createDescriptor(1L, "title", "content");
        userGroup.setDefaultContent(descriptor);

        final Content content = finder.getContent(new StateToken(), userGroup);
        assertSame(descriptor, content);
    }

    @Test
    public void testDefaultGroupContentHasDefLicense() throws Exception {
        final Group userGroup = new Group();
        final Content descriptor = TestDomainHelper.createDescriptor(1L, "title", "content");
        userGroup.setDefaultContent(descriptor);

        final Content content = finder.getContent(new StateToken(), userGroup);
        assertSame(userGroup.getDefaultLicense(), content.getLicense());
    }

    @Test
    public void testDefaultUserContent() throws Exception {
        final Content content = new Content();
        final Group group = new Group();
        group.setDefaultContent(content);
        final Content response = finder.getContent(new StateToken(), group);
        assertSame(content, response);
    }

    @Test
    public void testDocMissing() throws Exception {
        final Container container = new Container();
        Mockito.when(containerManager.find(1L)).thenReturn(container);

        final Content content = finder.getContent(new StateToken("groupShortName", "toolName", "1", null), null);
        assertNotNull(content);
        assertSame(container, content.getContainer());
    }

    @Test
    public void testFolderMissing() throws Exception {
        final Group group = new Group();
        final ToolConfiguration config = group.setToolConfig("toolName", new ToolConfiguration());
        final Container container = config.setRoot(new Container());
        Mockito.when(groupManager.findByShortName("groupShortName")).thenReturn(group);

        final StateToken token = new StateToken("groupShortName", "toolName", null, null);
        final Content content = finder.getContent(token, null);
        assertSame(container, content.getContainer());
    }

    @Test(expected = ContentNotFoundException.class)
    public void testIds() throws Exception {
        final Content descriptor = new Content();
        final Container container = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName");
        descriptor.setContainer(container);
        Mockito.when(contentManager.find(1L)).thenReturn(descriptor);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1a"), null);
    }

    @Test(expected = ContentNotFoundException.class)
    public void voyAJoder() throws Exception {
        finder.getContent(new StateToken(null, "toolName", "1", "2"), null);
    }

}