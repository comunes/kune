package org.ourproject.kune.docs.client;

import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_BLOG;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_DOCUMENT;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_FOLDER;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_GALLERY;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_POST;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_ROOT;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_UPLOADEDFILE;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_WIKI;
import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_WIKIPAGE;

import org.ourproject.kune.docs.client.cnt.DocumentContent;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionMenuItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.upload.FileUploader;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogo;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DocumentClientActions {

    private final I18nUITranslationService i18n;
    private final ContextNavigator contextNavigator;
    private final Session session;
    private final StateManager stateManager;
    private final Provider<FileUploader> fileUploaderProvider;
    private final ContentActionRegistry contentActionRegistry;
    private final ContextActionRegistry contextActionRegistry;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final Provider<GroupServiceAsync> groupServiceProvider;
    private final Provider<FileDownloadUtils> fileDownloadProvider;
    private final EntityLogo entityLogo;
    private final Provider<TextEditor> textEditorProvider;
    private final KuneErrorHandler errorHandler;
    private final DocumentContent documentContent;

    public DocumentClientActions(final I18nUITranslationService i18n, final ContextNavigator contextNavigator,
            final Session session, final StateManager stateManager,
            final Provider<ContentServiceAsync> contentServiceProvider,
            final Provider<GroupServiceAsync> groupServiceProvider, final Provider<FileUploader> fileUploaderProvider,
            final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
            final Provider<FileDownloadUtils> fileDownloadProvider, final EntityLogo entityLogo,
            final Provider<TextEditor> textEditorProvider, final KuneErrorHandler errorHandler,
            final DocumentContent documentContent) {
        this.i18n = i18n;
        this.contextNavigator = contextNavigator;
        this.session = session;
        this.stateManager = stateManager;
        this.contentServiceProvider = contentServiceProvider;
        this.groupServiceProvider = groupServiceProvider;
        this.fileUploaderProvider = fileUploaderProvider;
        this.contextActionRegistry = contextActionRegistry;
        this.contentActionRegistry = contentActionRegistry;
        this.fileDownloadProvider = fileDownloadProvider;
        this.entityLogo = entityLogo;
        this.textEditorProvider = textEditorProvider;
        this.errorHandler = errorHandler;
        this.documentContent = documentContent;
        createActions();
    }

    private void createActions() {
        final ActionToolbarMenuAndItemDescriptor<StateToken> addFolder = createFolderAction(TYPE_FOLDER,
                "images/nav/folder_add.png", i18n.t("New folder"), i18n.t("Folder"), i18n.t("New"), i18n
                        .t("New folder"));
        final ActionToolbarMenuAndItemDescriptor<StateToken> addGallery = createFolderAction(TYPE_GALLERY,
                "images/nav/gallery_add.png", i18n.t("New gallery"), i18n.t("Folder"), i18n.t("New"), i18n
                        .t("New gallery"));
        final ActionToolbarMenuAndItemDescriptor<StateToken> addWiki = createFolderAction(TYPE_WIKI,
                "images/nav/wiki_add.png", i18n.t("New wiki"), i18n.t("Folder"), i18n.t("New"), i18n.t("wiki"));

        final ActionToolbarMenuAndItemDescriptor<StateToken> addDoc = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
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

        final ActionToolbarMenuAndItemDescriptor<StateToken> delContainer = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Administrator, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        Site.info("Sorry, in development");
                    }
                });
        delContainer.setParentMenuTitle(i18n.t("Folder"));
        delContainer.setTextDescription(i18n.t("Delete folder"));
        delContainer.setMustBeConfirmed(true);
        delContainer.setConfirmationTitle(i18n.t("Please confirm"));
        delContainer.setConfirmationText(i18n.t("You will delete it and also all its contents. Are you sure?"));

        final ActionToolbarMenuAndItemDescriptor<StateToken> delContent = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Administrator, ActionToolbarPosition.topbar, new Listener<StateToken>() {
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
        delContent.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                final StateToken defContentToken = session.getCurrentState().getGroup().getDefaultContent()
                        .getStateToken();
                return !itemToken.equals(defContentToken);
            }
        });

        final ActionMenuItemDescriptor<StateToken> go = new ActionMenuItemDescriptor<StateToken>(AccessRolDTO.Viewer,
                new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        stateManager.gotoToken(token);
                    }
                });
        go.setMustBeAuthenticated(false);
        go.setTextDescription(i18n.t("Open"));
        go.setIconUrl("images/nav/go.png");
        go.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                return !contextNavigator.isSelected(itemToken);
            }
        });

        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtn = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        contextNavigator.editItem(stateToken);
                    }
                });
        renameCtn.setTextDescription(i18n.t("Rename"));
        renameCtn.setParentMenuTitle(i18n.t("File"));

        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtx = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        contextNavigator.editItem(stateToken);
                    }
                });
        renameCtx.setTextDescription(i18n.t("Rename"));
        renameCtx.setParentMenuTitle(i18n.t("Folder"));

        final ActionToolbarButtonDescriptor<StateToken> goGroupHome = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        stateManager.gotoToken(token.getGroup());
                    }
                });
        goGroupHome.setMustBeAuthenticated(false);
        goGroupHome.setIconUrl("images/group-home.png");
        goGroupHome.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                final StateToken defContentToken = session.getCurrentState().getGroup().getDefaultContent()
                        .getStateToken();
                return !itemToken.equals(defContentToken);
            }
        });

        final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupCnt;
        setAsDefGroupCnt = createSetAsDefContent();
        setAsDefGroupCnt.setParentMenuTitle(i18n.t("File"));

        final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupCxt;
        setAsDefGroupCxt = createSetAsDefContent();
        setAsDefGroupCxt.setParentMenuTitle(i18n.t("Folder"));

        final ActionToolbarMenuDescriptor<StateToken> refreshCtx = new ActionToolbarMenuDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        Site.important("Refresh with state token: " + stateToken);
                        stateManager.reload();
                        contextNavigator.selectItem(stateToken);
                    }
                });
        refreshCtx.setMustBeAuthenticated(false);
        refreshCtx.setParentMenuTitle(i18n.t("Folder"));
        refreshCtx.setTextDescription(i18n.t("Refresh"));
        refreshCtx.setIconUrl("images/nav/refresh.png");

        final ActionToolbarMenuDescriptor<StateToken> refreshCnt = new ActionToolbarMenuDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        stateManager.reload();
                        contextNavigator.selectItem(stateToken);
                    }
                });
        refreshCnt.setMustBeAuthenticated(false);
        refreshCnt.setParentMenuTitle(i18n.t("File"));
        refreshCnt.setTextDescription(i18n.t("Refresh"));
        refreshCnt.setIconUrl("images/nav/refresh.png");

        final ActionToolbarButtonAndItemDescriptor<StateToken> uploadFile = createUploadAction(i18n.t("Upload file"),
                "images/nav/upload.png", i18n.t("Upload some files (images, PDFs, ...)"), null);

        session.onInitDataReceived(new Listener<InitDataDTO>() {
            public void onEvent(final InitDataDTO parameter) {
                final ActionToolbarButtonAndItemDescriptor<StateToken> uploadMedia = createUploadAction(i18n
                        .t("Upload media"), "images/nav/upload.png", i18n.t("Upload some media (images, videos)"),
                        session.getGalleryPermittedExtensions());
                contextActionRegistry.addAction(uploadMedia, TYPE_GALLERY);
            }
        });

        final ActionToolbarButtonDescriptor<StateToken> download = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        downloadContent(token);
                    }
                });
        download.setMustBeAuthenticated(false);
        download.setTextDescription(i18n.t("Download"));
        download.setToolTip(i18n.t("Download this file"));
        download.setIconUrl("images/nav/download.png");

        final ActionMenuItemDescriptor<StateToken> downloadCtx = new ActionMenuItemDescriptor<StateToken>(
                AccessRolDTO.Viewer, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        downloadContent(token);
                    }
                });
        downloadCtx.setMustBeAuthenticated(false);
        downloadCtx.setTextDescription(i18n.t("Download"));
        downloadCtx.setIconUrl("images/nav/download.png");

        final ActionToolbarMenuAndItemDescriptor<StateToken> setGroupLogo = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Administrator, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        groupServiceProvider.get().setGroupLogo(session.getUserHash(), token,
                                new AsyncCallbackSimple<GroupDTO>() {
                                    public void onSuccess(GroupDTO newGroup) {
                                        Site.info("Logo selected");
                                        if (session.getCurrentState().getGroup().getShortName().equals(
                                                newGroup.getShortName())) {
                                            session.getCurrentState().setGroup(newGroup);
                                        }
                                        entityLogo.refreshGroupLogo();
                                    }
                                });
                    }
                });
        setGroupLogo.setParentMenuTitle(i18n.t("File"));
        setGroupLogo.setTextDescription(i18n.t("Set this as the group logo"));
        setGroupLogo.setIconUrl("images/nav/picture.png");
        setGroupLogo.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                final BasicMimeTypeDTO mime = session.getCurrentState().getMimeType();
                return mime != null && mime.getType().equals("image");
            }
        });

        final ActionToolbarMenuDescriptor<StateToken> setPublishStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Published online"), ContentStatusDTO.publishedOnline);
        final ActionToolbarMenuDescriptor<StateToken> setEditionInProgressStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Editing in progress"), ContentStatusDTO.editingInProgress);
        final ActionToolbarMenuDescriptor<StateToken> setRejectStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Rejected"), ContentStatusDTO.rejected);
        final ActionToolbarMenuDescriptor<StateToken> setSubmittedForPublishStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Submitted for publish"), ContentStatusDTO.publishedOnline);
        final ActionToolbarMenuDescriptor<StateToken> setInTheDustBinStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("In the dustbin"), ContentStatusDTO.inTheDustbin);

        final ActionToolbarButtonDescriptor<StateToken> translateContent = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        Site.important(i18n.t("Sorry, this functionality is currently in development"));
                    }
                });
        translateContent.setTextDescription(i18n.tWithNT("Translate", "used in button"));
        translateContent.setToolTip(i18n.t("Translate this document to other languages"));
        translateContent.setIconUrl("images/language.gif");
        translateContent.setLeftSeparator(ActionToolbarButtonSeparator.spacer);

        final ActionToolbarButtonDescriptor<StateToken> editContent = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        session.check(new AsyncCallbackSimple<Object>() {
                            public void onSuccess(final Object result) {
                                final TextEditor editor = textEditorProvider.get();
                                documentContent.detach();
                                editor.editContent(session.getCurrentState().getContent(), new Listener<String>() {
                                    public void onEvent(final String html) {
                                        Site.showProgressSaving();
                                        contentServiceProvider.get().save(session.getUserHash(), stateToken, html,
                                                new AsyncCallback<Integer>() {
                                                    public void onFailure(final Throwable caught) {
                                                        Site.hideProgress();
                                                        try {
                                                            throw caught;
                                                        } catch (final SessionExpiredException e) {
                                                            errorHandler.doSessionExpired();
                                                        } catch (final Throwable e) {
                                                            Site.error(i18n.t("Error saving document. Retrying..."));
                                                            errorHandler.process(caught);
                                                            editor.onSaveFailed();
                                                        }
                                                    }

                                                    public void onSuccess(final Integer newVersion) {
                                                        Site.hideProgress();
                                                        session.getCurrentState().setVersion(newVersion);
                                                        session.getCurrentState().setContent(html);
                                                        editor.onSaved();
                                                    }
                                                });
                                    }
                                }, new Listener0() {
                                    public void onEvent() {
                                        // onClose
                                        if (session.getCurrentStateToken().equals(stateToken)) {
                                            documentContent.refreshState();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
        editContent.setTextDescription(i18n.tWithNT("Edit", "used in button"));
        editContent.setIconUrl("images/content_edit.png");
        editContent.setLeftSeparator(ActionToolbarButtonSeparator.spacer);

        final String[] all = { TYPE_ROOT, TYPE_FOLDER, TYPE_DOCUMENT, TYPE_GALLERY, TYPE_BLOG, TYPE_POST, TYPE_WIKI,
                TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };
        final String[] containersNoRoot = { TYPE_FOLDER, TYPE_GALLERY, TYPE_BLOG, TYPE_WIKI };
        final String[] containers = { TYPE_ROOT, TYPE_FOLDER, TYPE_GALLERY, TYPE_BLOG, TYPE_WIKI };
        final String[] contents = { TYPE_DOCUMENT, TYPE_POST, TYPE_WIKIPAGE, TYPE_UPLOADEDFILE };
        final String[] contentsModerated = { TYPE_DOCUMENT, TYPE_POST, TYPE_UPLOADEDFILE };

        contentActionRegistry.addAction(setPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setEditionInProgressStatus, contentsModerated);
        contentActionRegistry.addAction(setRejectStatus, contentsModerated);
        contentActionRegistry.addAction(setSubmittedForPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setInTheDustBinStatus, contentsModerated);
        contextActionRegistry.addAction(addDoc, TYPE_ROOT, TYPE_FOLDER);
        contextActionRegistry.addAction(addFolder, TYPE_ROOT, TYPE_FOLDER);
        contextActionRegistry.addAction(addGallery, TYPE_ROOT);
        contextActionRegistry.addAction(addWiki, TYPE_ROOT);
        contextActionRegistry.addAction(go, all);
        contentActionRegistry.addAction(renameCtn, contents);
        contextActionRegistry.addAction(renameCtx, containersNoRoot);
        contextActionRegistry.addAction(refreshCtx, containers);
        contentActionRegistry.addAction(refreshCnt, contents);
        contextActionRegistry.addAction(uploadFile, TYPE_ROOT, TYPE_FOLDER, TYPE_BLOG);
        contentActionRegistry.addAction(download, TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(delContent, contents);
        contextActionRegistry.addAction(delContainer, containersNoRoot);
        contentActionRegistry.addAction(setAsDefGroupCnt, TYPE_DOCUMENT, TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(setAsDefGroupCxt, TYPE_BLOG);
        contextActionRegistry.addAction(goGroupHome, containers);
        contentActionRegistry.addAction(setGroupLogo, TYPE_UPLOADEDFILE);
        contextActionRegistry.addAction(downloadCtx, TYPE_UPLOADEDFILE);
        contentActionRegistry.addAction(editContent, TYPE_DOCUMENT, TYPE_POST, TYPE_WIKIPAGE);
        contentActionRegistry.addAction(translateContent, TYPE_DOCUMENT, TYPE_FOLDER, TYPE_GALLERY, TYPE_UPLOADEDFILE,
                TYPE_WIKI, TYPE_WIKIPAGE);
    }

    private ActionToolbarMenuAndItemDescriptor<StateToken> createFolderAction(final String contentTypeId,
            final String iconUrl, final String textDescription, final String parentMenuTitle,
            final String parentMenuSubtitle, final String defaultName) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> addFolder;
        addFolder = new ActionToolbarMenuAndItemDescriptor<StateToken>(AccessRolDTO.Editor,
                ActionToolbarPosition.topbar, new Listener<StateToken>() {
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

    private ActionToolbarMenuDescriptor<StateToken> createSetAsDefContent() {
        final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupContent;
        setAsDefGroupContent = new ActionToolbarMenuDescriptor<StateToken>(AccessRolDTO.Administrator,
                ActionToolbarPosition.topbar, new Listener<StateToken>() {
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
            public boolean mustBeEnabled(final StateToken itemToken) {
                final StateToken defContentToken = session.getCurrentState().getGroup().getDefaultContent()
                        .getStateToken();
                return !itemToken.equals(defContentToken);
            }
        });
        return setAsDefGroupContent;
    }

    private ActionToolbarMenuAndItemDescriptor<StateToken> createSetStatusAction(final AccessRolDTO rol,
            final String textDescription, final ContentStatusDTO status) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> action = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                rol, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        final AsyncCallbackSimple<Object> callback = new AsyncCallbackSimple<Object>() {
                            public void onSuccess(final Object result) {
                                session.getCurrentState().setStatus(status);
                            }
                        };
                        if (status.equals(ContentStatusDTO.publishedOnline) || status.equals(ContentStatusDTO.rejected)) {
                            contentServiceProvider.get().setStatusAsAdmin(session.getUserHash(), stateToken, status,
                                    callback);
                        } else {
                            contentServiceProvider.get().setStatus(session.getUserHash(), stateToken, status, callback);
                        }
                    }
                });
        action.setTextDescription(textDescription);
        action.setParentMenuTitle(i18n.t("File"));
        action.setParentSubMenuTitle(i18n.t("Change the status"));
        return action;
    }

    private ActionToolbarButtonAndItemDescriptor<StateToken> createUploadAction(final String textDescription,
            final String iconUrl, final String toolTip, final String permitedExtensions) {
        final ActionToolbarButtonAndItemDescriptor<StateToken> uploadFile;
        uploadFile = new ActionToolbarButtonAndItemDescriptor<StateToken>(AccessRolDTO.Editor,
                ActionToolbarPosition.bottombar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        if (permitedExtensions != null) {
                            // FIXME: can't be reset ...
                            // fileUploaderProvider.get().setPermittedExtensions(permitedExtensions);
                        } else {
                            // FIXME: can't be reset ...
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
        fileDownloadProvider.get().downloadFile(token);
    }
}
