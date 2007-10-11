/*
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

public interface WorkspaceEvents {
    public static final String START_APP = "plaft.StartApplication";
    public static final String STOP_APP = "platf.StopApplication";
    public static final String USER_LOGGED_IN = "ws.UserLoggedIn";
    public static final String USER_LOGGED_OUT = "ws.UserLoggedOut";
    public static final String INIT_DATA_RECEIVED = "ws.InitDataReceived";
    public static final String REQ_JOIN_GROUP = "ws.RequestJoinGroup";
    public static final String DEL_MEMBER = "ws.DeleteMember";
    public static final String GOTO_GROUP = "ws.GotoGroup";
    public static final String DENY_JOIN_GROUP = "ws.DenyJoinGroup";
    public static final String ACCEPT_JOIN_GROUP = "ws.AcceptJoinGroup";
    public static final String SET_COLLAB_AS_ADMIN = "ws.SetMemberAsAsmin";
    public static final String SET_ADMIN_AS_COLLAB = "ws.SetMemberAsCollab";
    public static final String ADD_ADMIN_MEMBER = "ws.AddAdminMember";
    public static final String ADD_COLLAB_MEMBER = "ws.AddCollabMember";
    public static final String ADD_VIEWER_MEMBER = "ws.AddViewerMember";
    public static final String UNJOIN_GROUP = "ws.UnJoinGroup";
}
