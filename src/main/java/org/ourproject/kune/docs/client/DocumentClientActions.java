package org.ourproject.kune.docs.client;

import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_BLOG;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_DOCUMENT;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_FOLDER;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_GALLERY;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_ROOT;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_UPLOADEDFILE;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_WIKI;

import org.ourproject.kune.platf.client.actions.ActionButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionDescriptor;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionPosition;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.dialogs.upload.FileUploader;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class DocumentClientActions {

    private final I18nUITranslationService i18n;
    private final ContextNavigator contextNavigator;
    private final Session session;
    private final StateManager stateManager;
    private final Provider<FileUploader> fileUploaderProvider;
    private final ContentActionRegistry contentActionRegistry;
    private final ContextActionRegistry contextActionRegistry;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final WorkspaceSkeleton ws;

    public DocumentClientActions(final I18nUITranslationService i18n, final ContextNavigator contextNavigator,
	    final Session session, final StateManager stateManager, final WorkspaceSkeleton ws,
	    final Provider<ContentServiceAsync> contentServiceProvider,
	    final Provider<FileUploader> fileUploaderProvider, final ContentActionRegistry contentActionRegistry,
	    final ContextActionRegistry contextActionRegistry) {
	this.i18n = i18n;
	this.contextNavigator = contextNavigator;
	this.session = session;
	this.stateManager = stateManager;
	this.ws = ws;
	this.contentServiceProvider = contentServiceProvider;
	this.fileUploaderProvider = fileUploaderProvider;
	this.contextActionRegistry = contextActionRegistry;
	this.contentActionRegistry = contentActionRegistry;
	createActions();
    }

    private void createActions() {
	final ActionMenuDescriptor<StateToken> addFolder = createFolderAction(TYPE_FOLDER, "images/nav/folder_add.png",
		i18n.t("New folder"), i18n.t("Folder"), i18n.t("New"), i18n.t("New folder"));
	final ActionMenuDescriptor<StateToken> addGallery = createFolderAction(TYPE_GALLERY,
		"images/nav/gallery_add.png", i18n.t("New gallery"), i18n.t("Folder"), i18n.t("New"), i18n
			.t("New gallery"));
	final ActionMenuDescriptor<StateToken> addWiki = createFolderAction(TYPE_WIKI, "images/nav/wiki_add.png", i18n
		.t("New wiki"), i18n.t("Folder"), i18n.t("New"), i18n.t("wiki"));

	final ActionMenuDescriptor<StateToken> addDoc = new ActionMenuDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.topbarAndItemMenu, new Listener<StateToken>() {
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
	addDoc.setParentMenuTitle(i18n.t("Folder"));
	addDoc.setParentSubMenuTitle(i18n.t("New"));
	addDoc.setIconUrl("images/nav/page_add.png");

	final ActionMenuDescriptor<StateToken> delContainer = new ActionMenuDescriptor<StateToken>(
		AccessRolDTO.Administrator, ActionPosition.topbarAndItemMenu, new Listener<StateToken>() {
		    public void onEvent(final StateToken token) {
			Site.info("Sorry, in development");
		    }
		});
	delContainer.setParentMenuTitle(i18n.t("Folder"));
	delContainer.setTextDescription(i18n.t("Delete folder"));
	delContainer.setMustBeConfirmed(true);
	delContainer.setConfirmationTitle(i18n.t("Please confirm"));
	delContainer.setConfirmationText(i18n.t("You will delete it and also all its contents. Are you sure?"));

	final ActionMenuDescriptor<StateToken> delContent = new ActionMenuDescriptor<StateToken>(
		AccessRolDTO.Administrator, ActionPosition.topbarAndItemMenu, new Listener<StateToken>() {
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
	delContent.setTextDescription(i18n.t("Delete"));
	delContent.setMustBeConfirmed(true);
	delContent.setConfirmationTitle(i18n.t("Please confirm"));
	delContent.setConfirmationText(i18n.t("Are you sure?"));

	final ActionDescriptor<StateToken> go = new ActionDescriptor<StateToken>(AccessRolDTO.Viewer,
		ActionPosition.itemMenu, new Listener<StateToken>() {
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

	final ActionMenuDescriptor<StateToken> rename = new ActionMenuDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.topbarAndItemMenu, new Listener<StateToken>() {
		    public void onEvent(final StateToken stateToken) {
			contextNavigator.editItem(stateToken);
		    }
		});
	rename.setTextDescription(i18n.t("Rename"));
	rename.setParentMenuTitle(i18n.t("File"));

	final ActionMenuDescriptor<StateToken> renameCtx = new ActionMenuDescriptor<StateToken>(AccessRolDTO.Editor,
		ActionPosition.topbarAndItemMenu, new Listener<StateToken>() {
		    public void onEvent(final StateToken stateToken) {
			contextNavigator.editItem(stateToken);
		    }
		});
	renameCtx.setTextDescription(i18n.t("Rename"));
	renameCtx.setParentMenuTitle(i18n.t("Folder"));

	final ActionButtonDescriptor<StateToken> goGroupHome = new ActionButtonDescriptor<StateToken>(
		AccessRolDTO.Viewer, ActionPosition.topbar, new Listener<StateToken>() {
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
		AccessRolDTO.Administrator, ActionPosition.itemMenu, new Listener<StateToken>() {
		    public void onEvent(final StateToken token) {
			Site.showProgressProcessing();
			contentServiceProvider.get().setAsDefaultContent(session.getUserHash(), token,
				new AsyncCallbackSimple<ContentSimpleDTO>() {
				    public void onSuccess(final ContentSimpleDTO defContent) {
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

	final ActionMenuDescriptor<StateToken> refreshCtx = new ActionMenuDescriptor<StateToken>(AccessRolDTO.Viewer,
		ActionPosition.topbar, new Listener<StateToken>() {
		    public void onEvent(final StateToken stateToken) {
			stateManager.reload();
			contextNavigator.selectItem(stateToken);
		    }
		});
	refreshCtx.setParentMenuTitle(i18n.t("Folder"));
	refreshCtx.setTextDescription(i18n.t("Refresh"));
	refreshCtx.setIconUrl("images/nav/refresh.png");

	final ActionMenuDescriptor<StateToken> refreshCnt = new ActionMenuDescriptor<StateToken>(AccessRolDTO.Viewer,
		ActionPosition.topbar, new Listener<StateToken>() {
		    public void onEvent(final StateToken stateToken) {
			stateManager.reload();
			contextNavigator.selectItem(stateToken);
		    }
		});
	refreshCnt.setParentMenuTitle(i18n.t("File"));
	refreshCnt.setTextDescription(i18n.t("Refresh"));
	refreshCnt.setIconUrl("images/nav/refresh.png");

	final ActionDescriptor<StateToken> uploadFile = createUploadAction(i18n.t("Upload file"),
		"images/nav/upload.png", i18n.t("Upload some files (images, PDFs, ...)"), null);

	session.onInitDataReceived(new Listener<InitDataDTO>() {
	    public void onEvent(final InitDataDTO parameter) {
		final ActionDescriptor<StateToken> uploadMedia = createUploadAction(i18n.t("Upload media"),
			"images/nav/upload.png", i18n.t("Upload some media (images, videos)"), session
				.getGalleryPermittedExtensions());
		contextActionRegistry.addAction(TYPE_GALLERY, uploadMedia);
	    }
	});

	final ActionButtonDescriptor<StateToken> download = new ActionButtonDescriptor<StateToken>(AccessRolDTO.Viewer,
		ActionPosition.topbar, new Listener<StateToken>() {
		    public void onEvent(final StateToken token) {
			downloadContent(token);
		    }
		});
	download.setTextDescription(i18n.t("Download"));
	download.setToolTip(i18n.t("Download this file"));
	download.setIconUrl("images/nav/download.png");

	final ActionDescriptor<StateToken> downloadCtx = new ActionDescriptor<StateToken>(AccessRolDTO.Viewer,
		ActionPosition.itemMenu, new Listener<StateToken>() {
		    public void onEvent(final StateToken token) {
			downloadContent(token);
		    }
		});
	downloadCtx.setTextDescription(i18n.t("Download"));
	downloadCtx.setIconUrl("images/nav/download.png");

	contextActionRegistry.addAction(TYPE_FOLDER, go);
	contextActionRegistry.addAction(TYPE_FOLDER, addDoc);
	contextActionRegistry.addAction(TYPE_FOLDER, addFolder);
	contextActionRegistry.addAction(TYPE_FOLDER, renameCtx);
	contextActionRegistry.addAction(TYPE_FOLDER, refreshCtx);
	contextActionRegistry.addAction(TYPE_FOLDER, delContainer);
	contextActionRegistry.addAction(TYPE_FOLDER, goGroupHome);
	contextActionRegistry.addAction(TYPE_FOLDER, uploadFile);

	contextActionRegistry.addAction(TYPE_BLOG, go);
	contextActionRegistry.addAction(TYPE_BLOG, uploadFile);
	contextActionRegistry.addAction(TYPE_BLOG, refreshCtx);
	contextActionRegistry.addAction(TYPE_BLOG, setAsDefGroupContent);

	contextActionRegistry.addAction(TYPE_GALLERY, go);
	contextActionRegistry.addAction(TYPE_GALLERY, goGroupHome);
	contextActionRegistry.addAction(TYPE_GALLERY, refreshCtx);

	contextActionRegistry.addAction(TYPE_UPLOADEDFILE, go);
	contextActionRegistry.addAction(TYPE_UPLOADEDFILE, setAsDefGroupContent);
	contentActionRegistry.addAction(TYPE_UPLOADEDFILE, rename);
	contentActionRegistry.addAction(TYPE_UPLOADEDFILE, refreshCnt);
	contentActionRegistry.addAction(TYPE_UPLOADEDFILE, delContent);
	contentActionRegistry.addAction(TYPE_UPLOADEDFILE, download);
	contextActionRegistry.addAction(TYPE_UPLOADEDFILE, downloadCtx);

	contextActionRegistry.addAction(TYPE_WIKI, go);
	contextActionRegistry.addAction(TYPE_WIKI, goGroupHome);
	contextActionRegistry.addAction(TYPE_WIKI, refreshCtx);

	contextActionRegistry.addAction(TYPE_ROOT, addDoc);
	contextActionRegistry.addAction(TYPE_ROOT, addFolder);
	contextActionRegistry.addAction(TYPE_ROOT, addGallery);
	contextActionRegistry.addAction(TYPE_ROOT, addWiki);
	contextActionRegistry.addAction(TYPE_ROOT, refreshCtx);
	contextActionRegistry.addAction(TYPE_ROOT, goGroupHome);
	contextActionRegistry.addAction(TYPE_ROOT, uploadFile);

	contextActionRegistry.addAction(TYPE_DOCUMENT, go);
	contextActionRegistry.addAction(TYPE_DOCUMENT, setAsDefGroupContent);
	contentActionRegistry.addAction(TYPE_DOCUMENT, rename);
	contentActionRegistry.addAction(TYPE_DOCUMENT, refreshCnt);
	contentActionRegistry.addAction(TYPE_DOCUMENT, delContent);
    }

    private ActionMenuDescriptor<StateToken> createFolderAction(final String contentTypeId, final String iconUrl,
	    final String textDescription, final String parentMenuTitle, final String parentMenuSubtitle,
	    final String defaultName) {
	final ActionMenuDescriptor<StateToken> addFolder;
	addFolder = new ActionMenuDescriptor<StateToken>(AccessRolDTO.Editor, ActionPosition.topbarAndItemMenu,
		new Listener<StateToken>() {
		    public void onEvent(final StateToken stateToken) {
			Site.showProgressProcessing();
			contentServiceProvider.get().addFolder(session.getUserHash(), stateToken, defaultName,
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

    private ActionButtonDescriptor<StateToken> createUploadAction(final String textDescription, final String iconUrl,
	    final String toolTip, final String permitedExtensions) {
	final ActionButtonDescriptor<StateToken> uploadFile;
	uploadFile = new ActionButtonDescriptor<StateToken>(AccessRolDTO.Editor, ActionPosition.bootombarAndItemMenu,
		new Listener<StateToken>() {
		    public void onEvent(final StateToken token) {
			if (permitedExtensions != null) {
			    // fileUploaderProvider.get().setPermittedExtensions(permitedExtensions);
			} else {
			    // fileUploaderProvider.get().resetPermittedExtensions();
			}
			fileUploaderProvider.get().show();
		    }
		});
	uploadFile.setTextDescription(textDescription);
	uploadFile.setIconUrl(iconUrl);
	uploadFile.setToolTip(toolTip);
	return uploadFile;
    }

    private void downloadContent(final StateToken token) {
	final String url = "/kune/servlets/FileDownloadManager?token=" + token + "&hash=" + session.getUserHash()
		+ "&download=true";
	ws.openUrl(url);
    }
}
