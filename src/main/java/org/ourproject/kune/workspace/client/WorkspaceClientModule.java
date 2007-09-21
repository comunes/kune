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

package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.workspace.client.actions.AcceptJoinGroupAction;
import org.ourproject.kune.workspace.client.actions.AddAdminAction;
import org.ourproject.kune.workspace.client.actions.AddCollabAction;
import org.ourproject.kune.workspace.client.actions.AddViewerAction;
import org.ourproject.kune.workspace.client.actions.DeleteMemberAction;
import org.ourproject.kune.workspace.client.actions.DenyJoinGroupAction;
import org.ourproject.kune.workspace.client.actions.GotoGroupAction;
import org.ourproject.kune.workspace.client.actions.InitAction;
import org.ourproject.kune.workspace.client.actions.LoggedInAction;
import org.ourproject.kune.workspace.client.actions.LoggedOutAction;
import org.ourproject.kune.workspace.client.actions.RequestJoinGroupAction;
import org.ourproject.kune.workspace.client.actions.SetCollabAsAdminAction;
import org.ourproject.kune.workspace.client.actions.SetAdminAsCollabAction;

public class WorkspaceClientModule implements ClientModule {
    public void configure(final Register register) {
	register.addAction(WorkspaceEvents.START_APP, new InitAction());
	register.addAction(WorkspaceEvents.USER_LOGGED_IN, new LoggedInAction());
	register.addAction(WorkspaceEvents.USER_LOGGED_OUT, new LoggedOutAction());
	register.addAction(WorkspaceEvents.REQ_JOIN_GROUP, new RequestJoinGroupAction());
	register.addAction(WorkspaceEvents.ACCEPT_JOIN_GROUP, new AcceptJoinGroupAction());
	register.addAction(WorkspaceEvents.DENY_JOIN_GROUP, new DenyJoinGroupAction());
	register.addAction(WorkspaceEvents.DEL_MEMBER, new DeleteMemberAction());
	register.addAction(WorkspaceEvents.GOTO_GROUP, new GotoGroupAction());
	register.addAction(WorkspaceEvents.SET_COLLAB_AS_ADMIN, new SetCollabAsAdminAction());
	register.addAction(WorkspaceEvents.SET_ADMIN_AS_COLLAB, new SetAdminAsCollabAction());
	register.addAction(WorkspaceEvents.ADD_ADMIN_MEMBER, new AddAdminAction());
	register.addAction(WorkspaceEvents.ADD_COLLAB_MEMBER, new AddCollabAction());
	register.addAction(WorkspaceEvents.ADD_VIEWER_MEMBER, new AddViewerAction());
    }
}
