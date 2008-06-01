/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.actions.AddGroupLiveSearchAction;
import org.ourproject.kune.workspace.client.actions.AddUserLiveSearchAction;
import org.ourproject.kune.workspace.client.actions.AttachToExtensibleWidgetAction;
import org.ourproject.kune.workspace.client.actions.ChangeGroupWsThemeAction;
import org.ourproject.kune.workspace.client.actions.ClearExtensibleWidgetAction;
import org.ourproject.kune.workspace.client.actions.CreateNewGroupAction;
import org.ourproject.kune.workspace.client.actions.DetachFromExtensibleWidgetAction;
import org.ourproject.kune.workspace.client.actions.DisableRateItAction;
import org.ourproject.kune.workspace.client.actions.EnableRateItAction;
import org.ourproject.kune.workspace.client.actions.InitAction;
import org.ourproject.kune.workspace.client.actions.InitDataReceivedAction;
import org.ourproject.kune.workspace.client.actions.LoggedInAction;
import org.ourproject.kune.workspace.client.actions.LoggedOutAction;
import org.ourproject.kune.workspace.client.actions.OnlyCheckUserSessionAction;
import org.ourproject.kune.workspace.client.actions.RateContentAction;
import org.ourproject.kune.workspace.client.actions.RecalculateWorkspaceAction;
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
import org.ourproject.kune.workspace.client.actions.sn.AddViewerAction;
import org.ourproject.kune.workspace.client.actions.sn.DeleteMemberAction;
import org.ourproject.kune.workspace.client.actions.sn.DenyJoinGroupAction;
import org.ourproject.kune.workspace.client.actions.sn.RequestJoinGroupAction;
import org.ourproject.kune.workspace.client.actions.sn.SetAdminAsCollabAction;
import org.ourproject.kune.workspace.client.actions.sn.SetCollabAsAdminAction;
import org.ourproject.kune.workspace.client.actions.sn.UnJoinGroupAction;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class WorkspaceClientModule implements ClientModule {

    private final StateManager stateManager;
    private final Workspace workspace;
    private final Dispatcher dispacher;
    private final Session session;
    private final I18nUITranslationService i18n;

    public WorkspaceClientModule(final Session session, final StateManager stateManager, final Workspace workspace,
            final I18nUITranslationService i18n) {
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
        dispacher = DefaultDispatcher.getInstance();
        this.workspace = workspace;
    }

    public void configure(final Register register) {
        register.addAction(WorkspaceEvents.START_APP, new InitAction(session, dispacher, workspace));
        register.addAction(WorkspaceEvents.STOP_APP, new StopAction(workspace));
        register.addAction(WorkspaceEvents.INIT_DATA_RECEIVED, new InitDataReceivedAction(session, workspace));
        register.addAction(WorkspaceEvents.USER_LOGGED_IN, new LoggedInAction(session, stateManager, i18n));
        register.addAction(WorkspaceEvents.USER_LOGGED_OUT, new LoggedOutAction(session, stateManager));
        register.addAction(WorkspaceEvents.ONLY_CHECK_USER_SESSION, new OnlyCheckUserSessionAction(session));
        register.addAction(PlatformEvents.ATTACH_TO_EXTENSIBLE_WIDGET, new AttachToExtensibleWidgetAction(workspace));
        register.addAction(PlatformEvents.DETACH_FROM_EXTENSIBLE_WIDGET,
                new DetachFromExtensibleWidgetAction(workspace));
        register.addAction(PlatformEvents.CLEAR_EXTENSIBLE_WIDGET, new ClearExtensibleWidgetAction(workspace));
        register.addAction(WorkspaceEvents.REQ_JOIN_GROUP, new RequestJoinGroupAction(session, stateManager, i18n));
        register.addAction(WorkspaceEvents.ACCEPT_JOIN_GROUP, new AcceptJoinGroupAction(session, stateManager,
                workspace, i18n));
        register.addAction(WorkspaceEvents.DENY_JOIN_GROUP, new DenyJoinGroupAction(session, stateManager, i18n));
        register.addAction(WorkspaceEvents.DEL_MEMBER, new DeleteMemberAction(session, stateManager, i18n));
        register.addAction(WorkspaceEvents.SET_COLLAB_AS_ADMIN, new SetCollabAsAdminAction(session, stateManager,
                workspace, i18n));
        register.addAction(WorkspaceEvents.SET_ADMIN_AS_COLLAB, new SetAdminAsCollabAction(session, stateManager,
                workspace, i18n));
        register
                .addAction(WorkspaceEvents.ADD_ADMIN_MEMBER, new AddAdminAction(session, stateManager, workspace, i18n));
        register.addAction(WorkspaceEvents.ADD_COLLAB_MEMBER, new AddCollabAction(session, stateManager, workspace,
                i18n));
        register.addAction(WorkspaceEvents.ADD_VIEWER_MEMBER, new AddViewerAction(session, stateManager, i18n));
        register.addAction(WorkspaceEvents.UNJOIN_GROUP, new UnJoinGroupAction(session, stateManager, i18n));
        register.addAction(WorkspaceEvents.CHANGE_GROUP_WSTHEME, new ChangeGroupWsThemeAction(session, workspace));
        register.addAction(WorkspaceEvents.RATE_CONTENT, new RateContentAction(session, stateManager, i18n));
        register.addAction(WorkspaceEvents.ENABLE_RATEIT, new EnableRateItAction(workspace));
        register.addAction(WorkspaceEvents.DISABLE_RATEIT, new DisableRateItAction(workspace));
        register.addAction(WorkspaceEvents.GET_TRANSLATION, new GetTranslationAction(session));
        register.addAction(WorkspaceEvents.ADD_MEMBER_GROUPLIVESEARCH, new AddGroupLiveSearchAction(workspace));
        register.addAction(WorkspaceEvents.ADD_USERLIVESEARCH, new AddUserLiveSearchAction(workspace));
        register.addAction(WorkspaceEvents.SHOW_TRANSLATOR, new ShowTranslatorAction(session, workspace, i18n));
        register.addAction(WorkspaceEvents.SHOW_SEARCHER, new ShowSearcherAction());
        register.addAction(WorkspaceEvents.DO_TRANSLATION, new DoTranslationAction(session, i18n));
        register.addAction(WorkspaceEvents.GET_LEXICON, new GetLexiconAction(i18n));
        register.addAction(WorkspaceEvents.USER_LOGIN, new UserLoginAction());
        register.addAction(WorkspaceEvents.USER_LOGOUT, new UserLogoutAction(session));
        register.addAction(WorkspaceEvents.USER_REGISTER, new UserRegisterAction());
        register.addAction(WorkspaceEvents.CREATE_NEW_GROUP, new CreateNewGroupAction(session));
        register.addAction(WorkspaceEvents.RELOAD_CONTEXT, new ReloadContextAction(stateManager));
        register.addAction(WorkspaceEvents.RECALCULATE_WORKSPACE_SIZE, new RecalculateWorkspaceAction(workspace));
    }
}
