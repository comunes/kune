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
import org.ourproject.kune.workspace.client.actions.ClearExtensibleWidgetAction;
import org.ourproject.kune.workspace.client.actions.DetachFromExtensibleWidgetAction;
import org.ourproject.kune.workspace.client.actions.DisableRateItAction;
import org.ourproject.kune.workspace.client.actions.EnableRateItAction;
import org.ourproject.kune.workspace.client.actions.RateContentAction;
import org.ourproject.kune.workspace.client.actions.RecalculateWorkspaceAction;
import org.ourproject.kune.workspace.client.actions.ReloadContextAction;
import org.ourproject.kune.workspace.client.actions.i18n.DoTranslationAction;
import org.ourproject.kune.workspace.client.actions.i18n.GetLexiconAction;
import org.ourproject.kune.workspace.client.actions.i18n.GetTranslationAction;
import org.ourproject.kune.workspace.client.actions.i18n.ShowTranslatorAction;
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
	register.addAction(PlatformEvents.ATTACH_TO_EXTENSIBLE_WIDGET, new AttachToExtensibleWidgetAction(workspace));
	register.addAction(PlatformEvents.DETACH_FROM_EXTENSIBLE_WIDGET,
		new DetachFromExtensibleWidgetAction(workspace));
	register.addAction(PlatformEvents.CLEAR_EXTENSIBLE_WIDGET, new ClearExtensibleWidgetAction(workspace));
	register.addAction(WorkspaceEvents.RATE_CONTENT, new RateContentAction(session, stateManager, i18n));
	register.addAction(WorkspaceEvents.ENABLE_RATEIT, new EnableRateItAction(workspace));
	register.addAction(WorkspaceEvents.DISABLE_RATEIT, new DisableRateItAction(workspace));
	register.addAction(WorkspaceEvents.GET_TRANSLATION, new GetTranslationAction(session));
	register.addAction(WorkspaceEvents.ADD_MEMBER_GROUPLIVESEARCH, new AddGroupLiveSearchAction(workspace));
	register.addAction(WorkspaceEvents.ADD_USERLIVESEARCH, new AddUserLiveSearchAction(workspace));
	register.addAction(WorkspaceEvents.SHOW_TRANSLATOR, new ShowTranslatorAction(session, workspace, i18n));
	register.addAction(WorkspaceEvents.DO_TRANSLATION, new DoTranslationAction(session, i18n));
	register.addAction(WorkspaceEvents.GET_LEXICON, new GetLexiconAction(i18n));
	register.addAction(WorkspaceEvents.RELOAD_CONTEXT, new ReloadContextAction(stateManager));
	register.addAction(WorkspaceEvents.RECALCULATE_WORKSPACE_SIZE, new RecalculateWorkspaceAction(workspace));
    }
}
