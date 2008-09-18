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
import org.ourproject.kune.platf.client.actions.ActionButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.platf.client.ui.dialogs.FileUploader;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Slot;

public class DocumentClientTool extends AbstractClientTool {
    public static final String TYPE_ROOT = "docs.root";
    public static final String TYPE_FOLDER = "docs.folder";
    public static final String TYPE_GALLERY = "docs.gallery";
    public static final String TYPE_BLOG = "docs.blog";
    public static final String TYPE_DOCUMENT = "docs.doc";
    public static final String TYPE_POST = "docs.post";
    public static final String TYPE_UPLOADEDFILE = "docs.uploaded";

    public static final String NAME = "docs";

    private final Provider<DocumentContext> documentContextProvider;
    private final ContextNavigator contextNavigator;
    private final StateManager stateManager;
    private final I18nUITranslationService i18n;
    private final Session session;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final Provider<FileUploader> fileUploaderProvider;

    public DocumentClientTool(final I18nUITranslationService i18n, final ToolSelector toolSelector,
	    final WsThemePresenter wsThemePresenter, final WorkspaceSkeleton ws,
	    final Provider<DocumentContext> documentContextProvider, final ContextNavigator contextNavigator,
	    final Session session, final StateManager stateManager,
	    final Provider<ContentServiceAsync> contentServiceProvider,
	    final Provider<FileUploader> fileUploaderProvider) {
	super(NAME, i18n.t("documents"), toolSelector, wsThemePresenter, ws);
	this.i18n = i18n;
	this.documentContextProvider = documentContextProvider;
	this.contextNavigator = contextNavigator;
	this.session = session;
	this.stateManager = stateManager;
	this.contentServiceProvider = contentServiceProvider;
	this.fileUploaderProvider = fileUploaderProvider;
	createActions();
	registerDragDropTypes();
	registerImageTypes();
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
	final ActionMenuDescriptor<StateToken> addFolder = createFolderAction(TYPE_FOLDER, "images/nav/folder_add.png",
		i18n.t("New folder"), i18n.t("File"), i18n.t("New"));
	final ActionMenuDescriptor<StateToken> addGallery = createFolderAction(TYPE_GALLERY,
		"images/nav/gallery_add.png", i18n.t("New gallery"), i18n.t("File"), i18n.t("New"));

	final ActionMenuDescriptor<StateToken> addDoc = new ActionMenuDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.topbarAndItemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			Site.showProgressProcessing();
			contentServiceProvider.get().addContent(session.getUserHash(),
				session.getCurrentState().getStateToken(), i18n.t("New document"),
				new AsyncCallbackSimple<StateDTO>() {
				    public void onSuccess(final StateDTO state) {
					contextNavigator.setEditOnNextStateChange(true);
					stateManager.setRetrievedState(state);
				    }
				});
		    }
		});
	addDoc.setTextDescription(i18n.t("New document"));
	addDoc.setParentMenuTitle(i18n.t("File"));
	addDoc.setParentSubMenuTitle(i18n.t("New"));
	addDoc.setIconUrl("images/nav/page_add.png");

	final ActionMenuDescriptor<StateToken> delContainer = new ActionMenuDescriptor<StateToken>(
		AccessRolDTO.Administrator, ActionPosition.topbarAndItemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			Site.info("Sorry, in development");
		    }
		});
	delContainer.setParentMenuTitle(i18n.t("File"));
	delContainer.setTextDescription(i18n.t("Delete folder"));
	delContainer.setMustBeConfirmed(true);
	delContainer.setConfirmationTitle(i18n.t("Please confirm"));
	delContainer.setConfirmationText(i18n.t("You will delete it and also all its contents. Are you sure?"));

	final ActionMenuDescriptor<StateToken> delContent = new ActionMenuDescriptor<StateToken>(
		AccessRolDTO.Administrator, ActionPosition.topbarAndItemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			contentServiceProvider.get().delContent(session.getUserHash(), token,
				new AsyncCallbackSimple<String>() {
				    public void onSuccess(final String result) {
					final StateToken parent = token.clone().clearDocument();
					stateManager.gotoToken(parent);
				    }
				});
		    }
		});
	delContent.setParentMenuTitle(i18n.t("File"));
	delContent.setTextDescription(i18n.t("Delete document"));
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
	go.setEnableCondition(new ActionEnableCondition<StateToken>() {
	    public boolean mustBeEnabled(final StateToken currentStateToken) {
		return !contextNavigator.isSelected(currentStateToken);
	    }
	});

	final ActionDescriptor<StateToken> rename = new ActionDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken stateToken) {
			contextNavigator.editItem(stateToken);
		    }
		});
	rename.setTextDescription(i18n.t("Rename"));

	final ActionButtonDescriptor<StateToken> goGroupHome = new ActionButtonDescriptor<StateToken>(
		AccessRolDTO.Viewer, ActionPosition.topbar, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			stateManager.gotoToken(token.getGroup());
		    }
		});
	goGroupHome.setIconUrl("images/group-home.png");
	goGroupHome.setEnableCondition(new ActionEnableCondition<StateToken>() {
	    public boolean mustBeEnabled(final StateToken currentStateToken) {
		final StateToken defContentToken = session.getCurrentState().getGroup().getDefaultContent()
			.getStateToken();
		return !currentStateToken.equals(defContentToken);
	    }
	});

	final ActionMenuDescriptor<StateToken> setAsDefGroupContent = new ActionMenuDescriptor<StateToken>(
		AccessRolDTO.Administrator, ActionPosition.itemMenu, new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			Site.showProgressProcessing();
			contentServiceProvider.get().setAsDefaultContent(session.getUserHash(), token,
				new AsyncCallbackSimple<ContentDTO>() {
				    public void onSuccess(ContentDTO defContent) {
					session.getCurrentState().getGroup().setDefaultContent(defContent);
					Site.hideProgress();
					Site.info(i18n.t("Document selected as the group homepage"));
				    }
				});
		    }
		});
	setAsDefGroupContent.setTextDescription(i18n.t("Set this as the group default page"));
	setAsDefGroupContent.setIconUrl("images/group-home.png");
	setAsDefGroupContent.setEnableCondition(new ActionEnableCondition<StateToken>() {
	    public boolean mustBeEnabled(final StateToken currentStateToken) {
		final StateToken defContentToken = session.getCurrentState().getGroup().getDefaultContent()
			.getStateToken();
		return !contextNavigator.isSelected(defContentToken);
	    }
	});

	final ActionButtonDescriptor<StateToken> refresh = new ActionButtonDescriptor<StateToken>(AccessRolDTO.Viewer,
		ActionPosition.topbar, new Slot<StateToken>() {
		    public void onEvent(StateToken stateToken) {
			stateManager.reload();
			contextNavigator.selectItem(stateToken);
		    }
		});
	refresh.setIconUrl("images/nav/refresh.png");
	refresh.setToolTip(i18n.t("Refresh"));
	refresh.setLeftSeparator(ActionButtonSeparator.fill);

	final ActionDescriptor<StateToken> uploadFile = createUploadAction(i18n.t("Upload file"),
		"images/nav/upload.png", i18n.t("Upload some files (images, PDFs, ...)"), null);

	session.onInitDataReceived(new Slot<InitDataDTO>() {
	    public void onEvent(final InitDataDTO parameter) {
		final ActionDescriptor<StateToken> uploadMedia = createUploadAction(i18n.t("Upload media"),
			"images/nav/upload.png", i18n.t("Upload some media (images, videos)"), session
				.getGalleryPermittedExtensions());
		contextNavigator.addAction(TYPE_GALLERY, uploadMedia);
	    }
	});

	contextNavigator.addAction(TYPE_FOLDER, go);
	contextNavigator.addAction(TYPE_FOLDER, addDoc);
	contextNavigator.addAction(TYPE_FOLDER, addFolder);
	contextNavigator.addAction(TYPE_FOLDER, delContainer);
	contextNavigator.addAction(TYPE_FOLDER, rename);
	contextNavigator.addAction(TYPE_FOLDER, goGroupHome);
	contextNavigator.addAction(TYPE_FOLDER, refresh);
	contextNavigator.addAction(TYPE_FOLDER, uploadFile);

	contextNavigator.addAction(TYPE_BLOG, go);
	contextNavigator.addAction(TYPE_BLOG, uploadFile);
	contextNavigator.addAction(TYPE_BLOG, setAsDefGroupContent);
	contextNavigator.addAction(TYPE_BLOG, refresh);

	contextNavigator.addAction(TYPE_GALLERY, go);
	contextNavigator.addAction(TYPE_GALLERY, goGroupHome);
	contextNavigator.addAction(TYPE_GALLERY, refresh);

	contextNavigator.addAction(TYPE_ROOT, addDoc);
	contextNavigator.addAction(TYPE_ROOT, addFolder);
	contextNavigator.addAction(TYPE_ROOT, addGallery);
	contextNavigator.addAction(TYPE_ROOT, goGroupHome);
	contextNavigator.addAction(TYPE_ROOT, refresh);
	contextNavigator.addAction(TYPE_ROOT, uploadFile);

	contextNavigator.addAction(TYPE_DOCUMENT, go);
	contextNavigator.addAction(TYPE_DOCUMENT, delContent);
	contextNavigator.addAction(TYPE_DOCUMENT, rename);
	contextNavigator.addAction(TYPE_DOCUMENT, goGroupHome);
	contextNavigator.addAction(TYPE_DOCUMENT, refresh);
	contextNavigator.addAction(TYPE_DOCUMENT, setAsDefGroupContent);
    }

    private ActionMenuDescriptor<StateToken> createFolderAction(final String contentTypeId, final String iconUrl,
	    final String textDescription, final String parentMenuTitle, final String parentMenuSubtitle) {
	final ActionMenuDescriptor<StateToken> addFolder;
	addFolder = new ActionMenuDescriptor<StateToken>(AccessRolDTO.Editor, ActionPosition.topbarAndItemMenu,
		new Slot<StateToken>() {
		    public void onEvent(final StateToken stateToken) {
			Site.showProgressProcessing();
			contentServiceProvider.get().addFolder(session.getUserHash(), stateToken, textDescription,
				contentTypeId, new AsyncCallbackSimple<StateDTO>() {

				    public void onSuccess(final StateDTO state) {
					contextNavigator.setEditOnNextStateChange(true);
					stateManager.setRetrievedState(state);
				    }
				});
		    }
		});
	addFolder.setTextDescription(textDescription);
	addFolder.setParentMenuTitle(parentMenuTitle);
	addFolder.setParentSubMenuTitle(parentMenuSubtitle);
	addFolder.setIconUrl(iconUrl);
	return addFolder;
    }

    private ActionDescriptor<StateToken> createUploadAction(final String textDescription, final String iconUrl,
	    final String toolTip, final String permitedExtensions) {
	final ActionButtonDescriptor<StateToken> uploadFile;
	uploadFile = new ActionButtonDescriptor<StateToken>(AccessRolDTO.Editor, ActionPosition.bootombarAndItemMenu,
		new Slot<StateToken>() {
		    public void onEvent(final StateToken token) {
			if (permitedExtensions != null) {
			    Log.info("Permited extensions (in dev): " + permitedExtensions);
			    fileUploaderProvider.get().setPermittedExtensions(permitedExtensions);
			}
			fileUploaderProvider.get().show();
		    }
		});
	uploadFile.setTextDescription(textDescription);
	uploadFile.setIconUrl(iconUrl);
	uploadFile.setToolTip(toolTip);
	return uploadFile;
    }

    private void registerDragDropTypes() {
	contextNavigator.registerDraggableType(TYPE_DOCUMENT);
	contextNavigator.registerDraggableType(TYPE_FOLDER);
	contextNavigator.registerDraggableType(TYPE_UPLOADEDFILE);

	contextNavigator.registerDroppableType(TYPE_ROOT);
	contextNavigator.registerDroppableType(TYPE_FOLDER);
	contextNavigator.registerDroppableType(TYPE_GALLERY);
    }

    private void registerImageTypes() {
	contextNavigator.registerContentTypeIcon(TYPE_BLOG, "images/nav/blog.png");
	contextNavigator.registerContentTypeIcon(TYPE_GALLERY, "images/nav/gallery.png");
	contextNavigator.registerContentTypeIcon(TYPE_DOCUMENT, "images/nav/page.png");
	contextNavigator.registerContentTypeIcon(TYPE_POST, "images/nav/post.png");
    }

}
