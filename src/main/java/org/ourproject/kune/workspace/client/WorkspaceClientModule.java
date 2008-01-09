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
import org.ourproject.kune.workspace.client.actions.AttachToExtensionPointAction;
import org.ourproject.kune.workspace.client.actions.ChangeGroupWsThemeAction;
import org.ourproject.kune.workspace.client.actions.ClearExtensionPointAction;
import org.ourproject.kune.workspace.client.actions.CreateNewGroupAction;
import org.ourproject.kune.workspace.client.actions.DetachFromExtensionPointAction;
import org.ourproject.kune.workspace.client.actions.DisableRateItAction;
import org.ourproject.kune.workspace.client.actions.EnableRateItAction;
import org.ourproject.kune.workspace.client.actions.GotoAction;
import org.ourproject.kune.workspace.client.actions.GotoContainerAction;
import org.ourproject.kune.workspace.client.actions.InitAction;
import org.ourproject.kune.workspace.client.actions.InitDataReceivedAction;
import org.ourproject.kune.workspace.client.actions.LoggedInAction;
import org.ourproject.kune.workspace.client.actions.LoggedOutAction;
import org.ourproject.kune.workspace.client.actions.OnlyCheckUserSessionAction;
import org.ourproject.kune.workspace.client.actions.RateContentAction;
import org.ourproject.kune.workspace.client.actions.RefreshContentSubTitleAction;
import org.ourproject.kune.workspace.client.actions.RefreshContentTitleAction;
import org.ourproject.kune.workspace.client.actions.ReloadContextAction;
import org.ourproject.kune.workspace.client.actions.ShowSearcherAction;
import org.ourproject.kune.workspace.client.actions.StopAction;
import org.ourproject.kune.workspace.client.actions.UserLoginAction;
import org.ourproject.kune.workspace.client.actions.UserLogoutAction;
import org.ourproject.kune.workspace.client.actions.UserRegisterAction;
import org.ourproject.kune.workspace.client.actions.i18n.DoTranslationAction;
import org.ourproject.kune.workspace.client.actions.i18n.GetLexiconAction;
import org.ourproject.kune.workspace.client.actions.i18n.GetTranslationAction;
import org.ourproject.kune.workspace.client.actions.i18n.ShowTranslatorAction;
import org.ourproject.kune.workspace.client.actions.sn.AcceptJoinGroupAction;
import org.ourproject.kune.workspace.client.actions.sn.AddAdminAction;
import org.ourproject.kune.workspace.client.actions.sn.AddCollabAction;
import org.ourproject.kune.workspace.client.actions.sn.AddMemberGroupLiveSearchAction;
import org.ourproject.kune.workspace.client.actions.sn.AddViewerAction;
import org.ourproject.kune.workspace.client.actions.sn.DeleteMemberAction;
import org.ourproject.kune.workspace.client.actions.sn.DenyJoinGroupAction;
import org.ourproject.kune.workspace.client.actions.sn.RequestJoinGroupAction;
import org.ourproject.kune.workspace.client.actions.sn.SetAdminAsCollabAction;
import org.ourproject.kune.workspace.client.actions.sn.SetCollabAsAdminAction;
import org.ourproject.kune.workspace.client.actions.sn.UnJoinGroupAction;

public class WorkspaceClientModule implements ClientModule {
    public void configure(final Register register) {
        register.addAction(WorkspaceEvents.START_APP, new InitAction());
        register.addAction(WorkspaceEvents.STOP_APP, new StopAction());
        register.addAction(WorkspaceEvents.INIT_DATA_RECEIVED, new InitDataReceivedAction());
        register.addAction(WorkspaceEvents.USER_LOGGED_IN, new LoggedInAction());
        register.addAction(WorkspaceEvents.USER_LOGGED_OUT, new LoggedOutAction());
        register.addAction(WorkspaceEvents.ONLY_CHECK_USER_SESSION, new OnlyCheckUserSessionAction());
        register.addAction(WorkspaceEvents.REQ_JOIN_GROUP, new RequestJoinGroupAction());
        register.addAction(WorkspaceEvents.ACCEPT_JOIN_GROUP, new AcceptJoinGroupAction());
        register.addAction(WorkspaceEvents.DENY_JOIN_GROUP, new DenyJoinGroupAction());
        register.addAction(WorkspaceEvents.DEL_MEMBER, new DeleteMemberAction());
        register.addAction(WorkspaceEvents.GOTO, new GotoAction());
        register.addAction(WorkspaceEvents.GOTO_CONTAINER, new GotoContainerAction());
        register.addAction(WorkspaceEvents.SET_COLLAB_AS_ADMIN, new SetCollabAsAdminAction());
        register.addAction(WorkspaceEvents.SET_ADMIN_AS_COLLAB, new SetAdminAsCollabAction());
        register.addAction(WorkspaceEvents.ADD_ADMIN_MEMBER, new AddAdminAction());
        register.addAction(WorkspaceEvents.ADD_COLLAB_MEMBER, new AddCollabAction());
        register.addAction(WorkspaceEvents.ADD_VIEWER_MEMBER, new AddViewerAction());
        register.addAction(WorkspaceEvents.UNJOIN_GROUP, new UnJoinGroupAction());
        register.addAction(WorkspaceEvents.CHANGE_GROUP_WSTHEME, new ChangeGroupWsThemeAction());
        register.addAction(WorkspaceEvents.RATE_CONTENT, new RateContentAction());
        register.addAction(WorkspaceEvents.ENABLE_RATEIT, new EnableRateItAction());
        register.addAction(WorkspaceEvents.DISABLE_RATEIT, new DisableRateItAction());
        register.addAction(WorkspaceEvents.ATTACH_TO_EXT_POINT, new AttachToExtensionPointAction());
        register.addAction(WorkspaceEvents.DETACH_FROM_EXT_POINT, new DetachFromExtensionPointAction());
        register.addAction(WorkspaceEvents.CLEAR_EXT_POINT, new ClearExtensionPointAction());
        register.addAction(WorkspaceEvents.GET_TRANSLATION, new GetTranslationAction());
        register.addAction(WorkspaceEvents.ADD_MEMBER_GROUPLIVESEARCH, new AddMemberGroupLiveSearchAction());
        register.addAction(WorkspaceEvents.SHOW_TRANSLATOR, new ShowTranslatorAction());
        register.addAction(WorkspaceEvents.SHOW_SEARCHER, new ShowSearcherAction());
        register.addAction(WorkspaceEvents.DO_TRANSLATION, new DoTranslationAction());
        register.addAction(WorkspaceEvents.GET_LEXICON, new GetLexiconAction());
        register.addAction(WorkspaceEvents.USER_LOGIN, new UserLoginAction());
        register.addAction(WorkspaceEvents.USER_LOGOUT, new UserLogoutAction());
        register.addAction(WorkspaceEvents.USER_REGISTER, new UserRegisterAction());
        register.addAction(WorkspaceEvents.CREATE_NEW_GROUP, new CreateNewGroupAction());
        register.addAction(WorkspaceEvents.RELOAD_CONTEXT, new ReloadContextAction());
        register.addAction(WorkspaceEvents.REFRESH_CONTENT_SUBTITLE, new RefreshContentSubTitleAction());
        register.addAction(WorkspaceEvents.REFRESH_CONTENT_TITLE, new RefreshContentTitleAction());
    }
}
