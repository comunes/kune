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
package org.ourproject.kune.platf.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.calclab.suco.client.events.Listener;

public class ActionRegistryTest {

    private static final String DEF_CONTENT_TYPE_ID = "test";
    private static final ActionToolbarPosition SOME_ID = new ActionToolbarPosition("some-id");
    private ActionRegistry<StateToken> registry;
    private ActionToolbarMenuAndItemDescriptor<StateToken> adminAction;
    private ActionToolbarMenuAndItemDescriptor<StateToken> editorAction;
    private ActionMenuItemDescriptor<StateToken> viewerAction;
    private Session session;

    @Test
    public void actionsEmptyButNeverNull() {
        Mockito.when(session.isLogged()).thenReturn(true);
        checkActionLists(0, new AccessRights(true, true, true), true);
        checkActionLists(0, new AccessRights(true, true, true), false);
        checkActionLists(0, new AccessRights(false, true, true), true);
        checkActionLists(0, new AccessRights(false, true, true), false);
        checkActionLists(0, new AccessRights(false, false, true), true);
        checkActionLists(0, new AccessRights(false, false, true), false);
    }

    @Before
    public void before() {
        session = Mockito.mock(Session.class);
        registry = new ActionRegistry<StateToken>();
        adminAction = new ActionToolbarMenuAndItemDescriptor<StateToken>(AccessRolDTO.Administrator, SOME_ID,
                new Listener<StateToken>() {
                    @Override
                    public void onEvent(final StateToken parameter) {
                    }
                });
        editorAction = new ActionToolbarMenuAndItemDescriptor<StateToken>(AccessRolDTO.Editor, SOME_ID,
                new Listener<StateToken>() {
                    @Override
                    public void onEvent(final StateToken parameter) {
                    }
                });

        viewerAction = new ActionMenuItemDescriptor<StateToken>(AccessRolDTO.Viewer, new Listener<StateToken>() {
            @Override
            public void onEvent(final StateToken parameter) {
            }
        });
        viewerAction.setMustBeAuthenticated(false);
    }

    @Test
    public void mustBeAuthFalse() {
        Mockito.when(session.isLogged()).thenReturn(false);
        addDefActions();
        checkActionLists(0, new AccessRights(false, true, true), true);
        checkActionLists(1, new AccessRights(false, true, true), false);
    }

    @Test
    public void testAddWhenAdmin() {
        Mockito.when(session.isLogged()).thenReturn(true);
        addDefActions();
        checkActionLists(2, new AccessRights(true, true, true), true);
        checkActionLists(3, new AccessRights(true, true, true), false);
    }

    @Test
    public void testAddWhenEditor() {
        Mockito.when(session.isLogged()).thenReturn(true);
        addDefActions();
        checkActionLists(1, new AccessRights(false, true, true), true);
        checkActionLists(2, new AccessRights(false, true, true), false);
    }

    @Test
    public void testAddWhenViewer() {
        Mockito.when(session.isLogged()).thenReturn(true);
        addDefActions();
        checkActionLists(0, new AccessRights(false, false, true), true);
        checkActionLists(1, new AccessRights(false, false, true), false);
    }

    private void addDefActions() {
        registry.addAction(adminAction, DEF_CONTENT_TYPE_ID);
        registry.addAction(editorAction, DEF_CONTENT_TYPE_ID);
        registry.addAction(viewerAction, DEF_CONTENT_TYPE_ID);
    }

    private void checkActionLists(final int expectedActions, final AccessRights AccessRights,
            final boolean toolbarActions) {
        assertEquals(
                expectedActions,
                registry.getCurrentActions(new StateToken(), DEF_CONTENT_TYPE_ID, session.isLogged(), AccessRights,
                        toolbarActions).size());
    }
}
