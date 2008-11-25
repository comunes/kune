package org.ourproject.kune.workspace.client;

import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_UPLOADEDFILE;

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
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
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
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.workspace.client.cnt.FoldableContent;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogo;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AbstractFoldableContentActions {

    protected enum Position {
        cnt, ctx
    }

    protected final Session session;
    protected final StateManager stateManager;
    protected final I18nUITranslationService i18n;
    protected final Provider<ContentServiceAsync> contentServiceProvider;
    protected final Provider<FileUploader> fileUploaderProvider;
    protected final ContextNavigator contextNavigator;
    protected final KuneErrorHandler errorHandler;
    protected final Provider<GroupServiceAsync> groupServiceProvider;
    protected final ContentActionRegistry contentActionRegistry;
    protected final ContextActionRegistry contextActionRegistry;
    protected final Provider<FileDownloadUtils> fileDownloadProvider;
    protected final Provider<TextEditor> textEditorProvider;
    protected final Provider<ContextPropEditor> contextPropEditorProvider;
    protected final FoldableContent foldableContent;
    protected final DeferredCommandWrapper deferredCommandWrapper;
    protected final EntityLogo entityLogo;
    protected ActionMenuItemDescriptor<StateToken> go;
    protected ActionToolbarButtonDescriptor<StateToken> goGroupHome;
    protected ActionToolbarButtonAndItemDescriptor<StateToken> uploadMedia;
    protected ActionToolbarButtonDescriptor<StateToken> translateContent;
    protected ActionToolbarButtonDescriptor<StateToken> editContent;

    public AbstractFoldableContentActions(Session session, StateManager stateManager, I18nUITranslationService i18n,
            KuneErrorHandler errorHandler, DeferredCommandWrapper deferredCommandWrapper,
            Provider<GroupServiceAsync> groupServiceProvider, Provider<ContentServiceAsync> contentServiceProvider,
            Provider<FileUploader> fileUploaderProvider, ContextNavigator contextNavigator,
            ContentActionRegistry contentActionRegistry, ContextActionRegistry contextActionRegistry,
            Provider<FileDownloadUtils> fileDownloadProvider, Provider<TextEditor> textEditorProvider,
            Provider<ContextPropEditor> contextPropEditorProvider, FoldableContent foldableContent,
            EntityLogo entityLogo) {
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
        this.errorHandler = errorHandler;
        this.deferredCommandWrapper = deferredCommandWrapper;
        this.groupServiceProvider = groupServiceProvider;
        this.contentServiceProvider = contentServiceProvider;
        this.fileUploaderProvider = fileUploaderProvider;
        this.contextNavigator = contextNavigator;
        this.contentActionRegistry = contentActionRegistry;
        this.contextActionRegistry = contextActionRegistry;
        this.fileDownloadProvider = fileDownloadProvider;
        this.textEditorProvider = textEditorProvider;
        this.contextPropEditorProvider = contextPropEditorProvider;
        this.foldableContent = foldableContent;
        this.entityLogo = entityLogo;
        createCommonActions();
        createActions();
        session.onInitDataReceived(new Listener<InitDataDTO>() {
            public void onEvent(InitDataDTO parameter) {
                createCommonPostSessionInitActions();
                createPostSessionInitActions();
            }
        });
    }

    protected void createActions() {
    }

    protected void createContentModeratedActions(String parentMenuTitle, final String... contentsModerated) {
        final ActionToolbarMenuDescriptor<StateToken> setPublishStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Published online"), parentMenuTitle,
                ContentStatusDTO.publishedOnline);
        final ActionToolbarMenuDescriptor<StateToken> setEditionInProgressStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Editing in progress"), parentMenuTitle,
                ContentStatusDTO.editingInProgress);
        final ActionToolbarMenuDescriptor<StateToken> setRejectStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Rejected"), parentMenuTitle, ContentStatusDTO.rejected);
        final ActionToolbarMenuDescriptor<StateToken> setSubmittedForPublishStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Submitted for publish"), parentMenuTitle,
                ContentStatusDTO.publishedOnline);
        final ActionToolbarMenuDescriptor<StateToken> setInTheDustBinStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("In the dustbin"), parentMenuTitle, ContentStatusDTO.inTheDustbin);
        contentActionRegistry.addAction(setPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setEditionInProgressStatus, contentsModerated);
        contentActionRegistry.addAction(setRejectStatus, contentsModerated);
        contentActionRegistry.addAction(setSubmittedForPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setInTheDustBinStatus, contentsModerated);
    }

    protected ActionToolbarMenuAndItemDescriptor<StateToken> createContentRenameAction(String parentMenuTitle,
            String textDescription) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtn = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        contextNavigator.editItem(stateToken);
                    }
                });
        renameCtn.setTextDescription(textDescription);
        renameCtn.setParentMenuTitle(parentMenuTitle);
        return renameCtn;
    }

    protected ActionToolbarMenuAndItemDescriptor<StateToken> createDelContainerAction(String text,
            String parentMenuTitle) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> delContainer = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Administrator, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        Site.info("Sorry, in development");
                    }
                });
        delContainer.setParentMenuTitle(parentMenuTitle);
        delContainer.setTextDescription(i18n.t(text));
        delContainer.setMustBeConfirmed(true);
        delContainer.setConfirmationTitle(i18n.t("Please confirm"));
        delContainer.setConfirmationText(i18n.t("You will delete it and also all its contents. Are you sure?"));
        return delContainer;
    }

    protected ActionToolbarMenuAndItemDescriptor<StateToken> createDelContentAction(String parentMenuTitle,
            String textDescription) {
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
        delContent.setParentMenuTitle(parentMenuTitle);
        delContent.setTextDescription(textDescription);
        delContent.setMustBeConfirmed(true);
        delContent.setConfirmationTitle(i18n.t("Please confirm"));
        delContent.setConfirmationText(i18n.t("Are you sure?"));
        delContent.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                final StateToken defContentToken = session.getCurrentState().getGroup().getDefaultContent().getStateToken();
                return !itemToken.equals(defContentToken);
            }
        });
        return delContent;
    }

    protected void createDownloadActions(String typeUploadedfile) {
        ActionToolbarButtonDescriptor<StateToken> download = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        downloadContent(token);
                    }
                });
        download.setMustBeAuthenticated(false);
        download.setTextDescription(i18n.t("Download"));
        download.setToolTip(i18n.t("Download this file"));
        download.setIconUrl("images/nav/download.png");

        ActionMenuItemDescriptor<StateToken> downloadCtx = new ActionMenuItemDescriptor<StateToken>(
                AccessRolDTO.Viewer, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        downloadContent(token);
                    }
                });
        downloadCtx.setMustBeAuthenticated(false);
        downloadCtx.setTextDescription(i18n.t("Download"));
        downloadCtx.setIconUrl("images/nav/download.png");

        contentActionRegistry.addAction(download, TYPE_UPLOADEDFILE);
        contextActionRegistry.addAction(downloadCtx, TYPE_UPLOADEDFILE);
    }

    protected void createNewContainerAction(final String contentTypeId, final String iconUrl,
            final String textDescription, final String parentMenuTitle, final String parentMenuSubtitle,
            final String defaultName, Position position, String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> addFolder;
        addFolder = new ActionToolbarMenuAndItemDescriptor<StateToken>(AccessRolDTO.Editor,
                ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        Site.showProgressProcessing();
                        contentServiceProvider.get().addFolder(session.getUserHash(), stateToken, defaultName,
                                contentTypeId, new AsyncCallbackSimple<StateContainerDTO>() {
                                    public void onSuccess(final StateContainerDTO state) {
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
        register(addFolder, position, registerInTypes);
    }

    protected void createNewContentAction(final String typeId, String iconUrl, final String description,
            final String parentMenuTitle, Position position, String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> addContent = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        Site.showProgressProcessing();
                        contentServiceProvider.get().addContent(session.getUserHash(),
                                session.getCurrentState().getStateToken(), description, typeId,
                                new AsyncCallbackSimple<StateContentDTO>() {
                                    public void onSuccess(final StateContentDTO state) {
                                        contextNavigator.setEditOnNextStateChange(true);
                                        stateManager.setRetrievedState(state);
                                    }
                                });
                    }
                });
        addContent.setTextDescription(description);
        addContent.setParentMenuTitle(parentMenuTitle);
        addContent.setParentSubMenuTitle(i18n.t("New"));
        addContent.setIconUrl(iconUrl);
        register(addContent, position, registerInTypes);
    }

    protected void createPostSessionInitActions() {
    }

    protected ActionToolbarMenuDescriptor<StateToken> createRefreshCntAction(String parentMenuTitle) {
        final ActionToolbarMenuDescriptor<StateToken> refreshCnt = new ActionToolbarMenuDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        stateManager.reload();
                        contextNavigator.selectItem(stateToken);
                    }
                });
        refreshCnt.setMustBeAuthenticated(false);
        refreshCnt.setParentMenuTitle(parentMenuTitle);
        refreshCnt.setTextDescription(i18n.t("Refresh"));
        refreshCnt.setIconUrl("images/nav/refresh.png");
        return refreshCnt;
    }

    protected ActionToolbarMenuDescriptor<StateToken> createRefreshCxtAction(String parentMenuTitleCtx) {
        final ActionToolbarMenuDescriptor<StateToken> refreshCtx = new ActionToolbarMenuDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        stateManager.reload();
                        contextNavigator.selectItem(stateToken);
                    }
                });
        refreshCtx.setMustBeAuthenticated(false);
        refreshCtx.setParentMenuTitle(parentMenuTitleCtx);
        refreshCtx.setTextDescription(i18n.t("Refresh"));
        refreshCtx.setIconUrl("images/nav/refresh.png");
        return refreshCtx;
    }

    protected ActionToolbarMenuAndItemDescriptor<StateToken> createRenameContentInCtxAction(String parentMenuTitleCtx,
            String textDescription) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtx = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        contextNavigator.editItem(stateToken);
                    }
                });
        renameCtx.setTextDescription(textDescription);
        renameCtx.setParentMenuTitle(parentMenuTitleCtx);
        return renameCtx;
    }

    protected ActionToolbarMenuDescriptor<StateToken> createSetAsDefContent(String parentMenuTitle) {
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
                                        Site.info(i18n.t("Content selected as the group homepage"));
                                    }
                                });
                    }
                });
        setAsDefGroupContent.setTextDescription(i18n.t("Set this as the group default page"));
        setAsDefGroupContent.setIconUrl("images/group-home.png");
        setAsDefGroupContent.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                final StateToken defContentToken = session.getCurrentState().getGroup().getDefaultContent().getStateToken();
                return !itemToken.equals(defContentToken);
            }
        });
        setAsDefGroupContent.setParentMenuTitle(parentMenuTitle);
        return setAsDefGroupContent;
    }

    protected ActionToolbarMenuAndItemDescriptor<StateToken> createSetStatusAction(final AccessRolDTO rol,
            final String textDescription, String parentMenuTitle, final ContentStatusDTO status) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> action = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                rol, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        final AsyncCallbackSimple<Object> callback = new AsyncCallbackSimple<Object>() {
                            public void onSuccess(final Object result) {
                                session.getContentState().setStatus(status);
                                contextNavigator.setItemStatus(stateToken, status);
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
        action.setParentMenuTitle(parentMenuTitle);
        action.setParentSubMenuTitle(i18n.t("Change the status"));
        return action;
    }

    protected ActionToolbarButtonAndItemDescriptor<StateToken> createUploadAction(final String textDescription,
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

    protected void downloadContent(final StateToken token) {
        fileDownloadProvider.get().downloadFile(token);
    }

    private void createCommonActions() {
        go = new ActionMenuItemDescriptor<StateToken>(AccessRolDTO.Viewer, new Listener<StateToken>() {
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

        goGroupHome = new ActionToolbarButtonDescriptor<StateToken>(AccessRolDTO.Viewer, ActionToolbarPosition.topbar,
                new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        stateManager.gotoToken(token.getGroup());
                    }
                });
        goGroupHome.setMustBeAuthenticated(false);
        goGroupHome.setIconUrl("images/group-home.png");
        goGroupHome.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                final StateToken defContentToken = session.getCurrentState().getGroup().getDefaultContent().getStateToken();
                return !itemToken.equals(defContentToken);
            }
        });
        goGroupHome.setLeftSeparator(ActionToolbarButtonSeparator.fill);

        translateContent = new ActionToolbarButtonDescriptor<StateToken>(AccessRolDTO.Editor,
                ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        Site.important(i18n.t("Sorry, this functionality is currently in development"));
                    }
                });
        translateContent.setTextDescription(i18n.tWithNT("Translate", "used in button"));
        translateContent.setToolTip(i18n.t("Translate this document to other languages"));
        translateContent.setIconUrl("images/language.gif");
        translateContent.setLeftSeparator(ActionToolbarButtonSeparator.spacer);

        editContent = new ActionToolbarButtonDescriptor<StateToken>(AccessRolDTO.Editor, ActionToolbarPosition.topbar,
                new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        session.check(new AsyncCallbackSimple<Object>() {
                            public void onSuccess(final Object result) {
                                final TextEditor editor = textEditorProvider.get();
                                foldableContent.detach();
                                contextNavigator.detach();
                                contextPropEditorProvider.get().attach();
                                editor.editContent(session.getContentState().getContent(), new Listener<String>() {
                                    public void onEvent(final String html) {
                                        Site.showProgressSaving();
                                        contentServiceProvider.get().save(session.getUserHash(), stateToken, html,
                                                new AsyncCallback<Object>() {
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

                                                    public void onSuccess(Object param) {
                                                        Site.hideProgress();
                                                        session.getContentState().setContent(html);
                                                        editor.onSaved();
                                                    }
                                                });
                                    }
                                }, new Listener0() {
                                    public void onEvent() {
                                        // onClose
                                        deferredCommandWrapper.addCommand(new Listener0() {
                                            public void onEvent() {
                                                foldableContent.attach();
                                                contextPropEditorProvider.get().detach();
                                                contextNavigator.attach();
                                                if (session.getCurrentStateToken().equals(stateToken)) {
                                                    foldableContent.refreshState();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
        editContent.setTextDescription(i18n.tWithNT("Edit", "used in button"));
        editContent.setIconUrl("images/content_edit.png");
        editContent.setLeftSeparator(ActionToolbarButtonSeparator.spacer);

    }

    private void createCommonPostSessionInitActions() {
        uploadMedia = createUploadAction(i18n.t("Upload media"), "images/nav/upload.png",
                i18n.t("Upload some media (images, videos)"), session.getGalleryPermittedExtensions());
    }

    /**
     * For future use contentActionRegistry.addAction(setGroupLogo,
     * TYPE_UPLOADEDFILE);
     */
    @SuppressWarnings("unused")
    private ActionToolbarMenuAndItemDescriptor<StateToken> createSetGroupLogoAction(String parentMenuTitle) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> setGroupLogo = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Administrator, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        groupServiceProvider.get().setGroupFullLogo(session.getUserHash(), token,
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
        setGroupLogo.setParentMenuTitle(parentMenuTitle);
        setGroupLogo.setTextDescription(i18n.t("Set this as the group logo"));
        setGroupLogo.setIconUrl("images/nav/picture.png");
        setGroupLogo.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                final BasicMimeTypeDTO mime = session.getContentState().getMimeType();
                return mime != null && mime.getType().equals("image");
            }
        });
        return setGroupLogo;
    }

    private void register(ActionToolbarMenuAndItemDescriptor<StateToken> action, Position position,
            String... registerInTypes) {
        switch (position) {
        case ctx:
            contentActionRegistry.addAction(action, registerInTypes);
            break;
        case cnt:
            contextActionRegistry.addAction(action, registerInTypes);
            break;
        }
    }
}
