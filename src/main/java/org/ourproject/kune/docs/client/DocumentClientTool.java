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

package org.ourproject.kune.docs.client;

import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Slot;

public class DocumentClientTool extends AbstractClientTool {
    public static final String TYPE_ROOT = "docs.root";
    public static final String TYPE_FOLDER = "docs.folder";
    public static final String TYPE_DOCUMENT = "docs.doc";
    public static final String NAME = "docs";
    private final Provider<DocumentContext> documentContextProvider;
    private final ContextNavigator contextNavigator;
    private final StateManager stateManager;
    private final I18nUITranslationService i18n;
    private final Session session;
    private final Provider<ContentServiceAsync> contentServiceProvider;

    public DocumentClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
	    final WsThemePresenter wsThemePresenter, final WorkspaceSkeleton ws,
	    final Provider<DocumentContext> documentContextProvider, final ContextNavigator contextNavigator,
	    final Session session, final StateManager stateManager,
	    final Provider<ContentServiceAsync> contentServiceProvider) {
	super(NAME, i18n.t("documents"), toolSelector, wsThemePresenter, ws);
	this.i18n = i18n;
	this.documentContextProvider = documentContextProvider;
	this.contextNavigator = contextNavigator;
	this.session = session;
	this.stateManager = stateManager;
	this.contentServiceProvider = contentServiceProvider;
	createActions();
	registerDragDropTypes();
    }

    public String getName() {
	return NAME;
    }

    @Deprecated
    public void onCancel() {
	documentContextProvider.get().showFolders();
    }

    @Deprecated
    public void onEdit() {
	documentContextProvider.get().showAdmin();
    }

    private void createActions() {
	final ActionDescriptor<StateToken> addFolder = new ActionDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.topbarAndItemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken parameter) {
			contentServiceProvider.get().addFolder(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), new Long(parameter.getFolder()),
				i18n.t("New folder"), new AsyncCallbackSimple<StateDTO>() {
				    public void onSuccess(final StateDTO state) {
					contextNavigator.setState(state);
					stateManager.setRetrievedState(state);
					stateManager.reload();
					contextNavigator.editItem(state.getStateToken());
				    }
				});
		    }
		});
	addFolder.setTextDescription(i18n.t("New folder"));
	addFolder.setParentMenuTitle(i18n.t("New"));
	addFolder.setIconUrl("images/nav/folder_add.png");

	final ActionDescriptor<StateToken> addDoc = new ActionDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.topbarAndItemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			contentServiceProvider.get().addContent(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(),
				session.getCurrentState().getFolder().getId(), i18n.t("New document"),
				new AsyncCallbackSimple<StateDTO>() {
				    public void onSuccess(final StateDTO state) {
					contextNavigator.setState(state);
					stateManager.setRetrievedState(state);
					stateManager.reload();
					contextNavigator.editItem(state.getStateToken());
				    }
				});

		    }
		});
	addDoc.setTextDescription(i18n.t("New document"));
	addDoc.setParentMenuTitle(i18n.t("New"));
	addDoc.setIconUrl("images/nav/page_add.png");

	final ActionDescriptor<StateToken> delContainer = new ActionDescriptor<StateToken>(AccessRolDTO.Administrator,
		ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			Site.info("Sorry, in development");
		    }
		});
	delContainer.setTextDescription(i18n.t("Delete"));
	delContainer.setMustBeConfirmed(true);
	delContainer.setConfirmationTitle(i18n.t("Please confirm"));
	delContainer.setConfirmationText(i18n.t("You will delete it and also all its contents. Are you sure?"));

	final ActionDescriptor<StateToken> delContent = new ActionDescriptor<StateToken>(AccessRolDTO.Administrator,
		ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			contentServiceProvider.get().delContent(session.getUserHash(),
				session.getCurrentState().getGroup().getShortName(), token.getDocument(),
				new AsyncCallbackSimple<String>() {
				    public void onSuccess(final String result) {
					final StateToken parent = token.clone();
					parent.setDocument(null);
					stateManager.gotoToken(parent);
				    }
				});
		    }
		});
	delContent.setTextDescription(i18n.t("Delete"));
	delContent.setMustBeConfirmed(true);
	delContent.setConfirmationTitle(i18n.t("Please confirm"));
	delContent.setConfirmationText(i18n.t("Are you sure?"));

	final ActionDescriptor<StateToken> go = new ActionDescriptor<StateToken>(AccessRolDTO.Viewer,
		ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			stateManager.gotoToken(token);
		    }
		});
	go.setTextDescription(i18n.t("Open"));
	go.setIconUrl("images/nav/go.png");

	final ActionDescriptor<StateToken> rename = new ActionDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken stateToken) {
			contextNavigator.editItem(stateToken);
		    }
		});
	rename.setTextDescription(i18n.t("Rename"));

	final ActionDescriptor<StateToken> refresh = new ActionDescriptor<StateToken>(AccessRolDTO.Viewer,
		ActionPosition.topbar, new Slot<StateToken>() {
		    public void onEvent(StateToken stateToken) {
			stateManager.reload();
			contextNavigator.selectItem(stateToken);

		    }
		});
	refresh.setIconUrl("images/nav/refresh.png");
	refresh.setToolTip(i18n.t("Refresh"));

	contextNavigator.addAction(TYPE_FOLDER, go);
	contextNavigator.addAction(TYPE_FOLDER, addDoc);
	contextNavigator.addAction(TYPE_FOLDER, addFolder);
	contextNavigator.addAction(TYPE_FOLDER, delContainer);
	contextNavigator.addAction(TYPE_FOLDER, rename);
	contextNavigator.addAction(TYPE_FOLDER, refresh);

	contextNavigator.addAction(TYPE_ROOT, addDoc);
	contextNavigator.addAction(TYPE_ROOT, addFolder);
	contextNavigator.addAction(TYPE_ROOT, refresh);

	contextNavigator.addAction(TYPE_DOCUMENT, go);
	contextNavigator.addAction(TYPE_DOCUMENT, delContent);
	contextNavigator.addAction(TYPE_DOCUMENT, rename);
	contextNavigator.addAction(TYPE_DOCUMENT, refresh);
    }

    private void registerDragDropTypes() {
	contextNavigator.registerDraggableType(TYPE_DOCUMENT);
	contextNavigator.registerDraggableType(TYPE_FOLDER);
	contextNavigator.registerDroppableType(TYPE_ROOT);
	contextNavigator.registerDroppableType(TYPE_FOLDER);
	contextNavigator.registerDroppableType(TYPE_DOCUMENT);
    }

}
