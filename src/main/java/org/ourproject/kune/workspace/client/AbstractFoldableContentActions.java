/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import org.ourproject.kune.platf.client.actions.ActionAddCondition;
import org.ourproject.kune.platf.client.actions.ActionCheckedCondition;
import org.ourproject.kune.platf.client.actions.ActionEnableCondition;
import org.ourproject.kune.platf.client.actions.ActionMenuItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuAndItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuCheckItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuRadioDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
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
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.socialnet.RadioMustBeChecked;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
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
    protected final EntityHeader entityLogo;
    private final SitePublicSpaceLink publicLink;

    public AbstractFoldableContentActions(Session session, StateManager stateManager, I18nUITranslationService i18n,
            KuneErrorHandler errorHandler, DeferredCommandWrapper deferredCommandWrapper,
            Provider<GroupServiceAsync> groupServiceProvider, Provider<ContentServiceAsync> contentServiceProvider,
            Provider<FileUploader> fileUploaderProvider, ContextNavigator contextNavigator,
            ContentActionRegistry contentActionRegistry, ContextActionRegistry contextActionRegistry,
            Provider<FileDownloadUtils> fileDownloadProvider, Provider<TextEditor> textEditorProvider,
            Provider<ContextPropEditor> contextPropEditorProvider, FoldableContent foldableContent,
            EntityHeader entityLogo, SitePublicSpaceLink publicLink) {
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
        this.publicLink = publicLink;
        createActions();
        session.onInitDataReceived(new Listener<InitDataDTO>() {
            public void onEvent(InitDataDTO parameter) {
                createPostSessionInitActions();
            }
        });
    }

    protected void createActions() {
    }

    protected void createContentModeratedActions(String parentMenuTitle, final String... contentsModerated) {
        final ActionToolbarMenuRadioDescriptor<StateToken> setPublishStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Published online"), parentMenuTitle,
                ContentStatusDTO.publishedOnline);
        final ActionToolbarMenuRadioDescriptor<StateToken> setEditionInProgressStatus = createSetStatusAction(
                AccessRolDTO.Editor, i18n.t("Editing in progress"), parentMenuTitle, ContentStatusDTO.editingInProgress);
        final ActionToolbarMenuRadioDescriptor<StateToken> setRejectStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("Rejected"), parentMenuTitle, ContentStatusDTO.rejected);
        final ActionToolbarMenuRadioDescriptor<StateToken> setSubmittedForPublishStatus = createSetStatusAction(
                AccessRolDTO.Editor, i18n.t("Submitted for publish"), parentMenuTitle,
                ContentStatusDTO.submittedForEvaluation);
        final ActionToolbarMenuRadioDescriptor<StateToken> setInTheDustBinStatus = createSetStatusAction(
                AccessRolDTO.Administrator, i18n.t("In the rubbish bin"), parentMenuTitle,
                ContentStatusDTO.inTheDustbin);
        contentActionRegistry.addAction(setPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setEditionInProgressStatus, contentsModerated);
        contentActionRegistry.addAction(setRejectStatus, contentsModerated);
        contentActionRegistry.addAction(setSubmittedForPublishStatus, contentsModerated);
        contentActionRegistry.addAction(setInTheDustBinStatus, contentsModerated);
        createPublishAction("images/accept.png", i18n.t("Publish online"), ContentStatusDTO.publishedOnline,
                contentsModerated);
        createPublishAction("images/cancel.png", i18n.t("Reject publication"), ContentStatusDTO.rejected,
                contentsModerated);
    }

    protected void createContentRenameAction(String parentMenuTitle, String textDescription, String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtn = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        contextNavigator.editItem(stateToken);
                    }
                });
        renameCtn.setTextDescription(textDescription);
        renameCtn.setParentMenuTitle(parentMenuTitle);
        renameCtn.setEnableCondition(notDeleted());
        contentActionRegistry.addAction(renameCtn, registerInTypes);
    }

    protected void createDelContainerAction(String text, String parentMenuTitle, String... registerInTypes) {
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
        delContainer.setConfirmationText(i18n.t("You will delete it with all its contents. Are you sure?"));
        contextActionRegistry.addAction(delContainer, registerInTypes);
    }

    protected void createDelContentAction(String parentMenuTitle, String textDescription, String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> delContent = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Administrator, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        contentServiceProvider.get().delContent(session.getUserHash(), token,
                                new AsyncCallbackSimple<StateContentDTO>() {
                                    public void onSuccess(final StateContentDTO state) {
                                        session.setCurrentState(state);
                                        final StateToken parent = token.copy().clearDocument();
                                        stateManager.gotoToken(parent);
                                        contextNavigator.clear();
                                        contextNavigator.refreshState();
                                    }
                                });
                    }
                });
        delContent.setParentMenuTitle(parentMenuTitle);
        delContent.setTextDescription(textDescription);
        delContent.setMustBeConfirmed(true);
        delContent.setConfirmationTitle(i18n.t("Please confirm"));
        delContent.setConfirmationText(i18n.t("Are you sure?"));
        delContent.setEnableCondition(notDefAndNotDeleted());
        contentActionRegistry.addAction(delContent, registerInTypes);
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

        contentActionRegistry.addAction(download, typeUploadedfile);
        contextActionRegistry.addAction(downloadCtx, typeUploadedfile);
    }

    protected ActionToolbarButtonDescriptor<StateToken> createEditAction(String... registerInTypes) {
        ActionToolbarButtonDescriptor<StateToken> editContent = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
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
                                                if (session.inSameToken(stateToken)) {
                                                    contextNavigator.refreshState();
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
        editContent.setEnableCondition(notDeleted());
        contentActionRegistry.addAction(editContent, registerInTypes);
        return editContent;
    }

    protected ActionMenuItemDescriptor<StateToken> createGoAction(String... registerInTypes) {
        ActionMenuItemDescriptor<StateToken> go = new ActionMenuItemDescriptor<StateToken>(AccessRolDTO.Viewer,
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
        contextActionRegistry.addAction(go, registerInTypes);
        return go;
    }

    protected ActionToolbarButtonDescriptor<StateToken> createGoHomeAction(String... registerInTypes) {
        ActionToolbarButtonDescriptor<StateToken> goGroupHome = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, ActionToolbarPosition.topbar, new Listener<StateToken>() {
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
        contextActionRegistry.addAction(goGroupHome, registerInTypes);
        return goGroupHome;
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

    protected ActionToolbarMenuAndItemDescriptor<StateToken> createNewContentAction(final String typeId,
            String iconUrl, final String description, final String parentMenuTitle, Position position,
            String... registerInTypes) {
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
        return addContent;
    }

    protected void createPostSessionInitActions() {
    }

    protected void createPublishAction(String icon, String tooltip, final ContentStatusDTO status,
            String... registerInTypes) {
        ActionToolbarButtonDescriptor<StateToken> pubAction = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Administrator, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(StateToken stateToken) {
                        setContentStatus(status, stateToken);
                    }
                }, new ActionAddCondition<StateToken>() {
                    public boolean mustBeAdded(StateToken param) {
                        if (session.isCurrentStateAContent()
                                && session.getContentState().getStatus().equals(ContentStatusDTO.submittedForEvaluation)) {
                            return true;
                        }
                        return false;
                    }
                });
        pubAction.setMustBeAuthenticated(true);
        pubAction.setToolTip(tooltip);
        pubAction.setIconUrl(icon);
        contentActionRegistry.addAction(pubAction, registerInTypes);
    }

    protected ActionToolbarMenuDescriptor<StateToken> createRefreshCntAction(String parentMenuTitle,
            String... registerInTypes) {
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
        contentActionRegistry.addAction(refreshCnt, registerInTypes);
        return refreshCnt;
    }

    protected ActionToolbarMenuDescriptor<StateToken> createRefreshCxtAction(String parentMenuTitleCtx,
            String... registerInTypes) {
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
        contextActionRegistry.addAction(refreshCtx, registerInTypes);
        return refreshCtx;
    }

    protected ActionToolbarMenuAndItemDescriptor<StateToken> createRenameContentInCtxAction(String parentMenuTitleCtx,
            String textDescription, String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtx = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        contextNavigator.editItem(stateToken);
                    }
                });
        renameCtx.setTextDescription(textDescription);
        renameCtx.setParentMenuTitle(parentMenuTitleCtx);
        contextActionRegistry.addAction(renameCtx, registerInTypes);
        return renameCtx;
    }

    protected ActionToolbarMenuDescriptor<StateToken> createSetAsDefContent(String parentMenuTitle,
            String... registerInTypes) {
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
        setAsDefGroupContent.setTextDescription(i18n.t("Set this as the default group page"));
        setAsDefGroupContent.setIconUrl("images/group-home.png");
        setAsDefGroupContent.setEnableCondition(notDefAndNotDeleted());
        setAsDefGroupContent.setParentMenuTitle(parentMenuTitle);
        contentActionRegistry.addAction(setAsDefGroupContent, registerInTypes);
        return setAsDefGroupContent;
    }

    protected ActionToolbarMenuRadioDescriptor<StateToken> createSetStatusAction(final AccessRolDTO rol,
            final String textDescription, String parentMenuTitle, final ContentStatusDTO status) {
        final ActionToolbarMenuRadioDescriptor<StateToken> action = new ActionToolbarMenuRadioDescriptor<StateToken>(
                rol, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        setContentStatus(status, stateToken);
                    }
                }, "ContentRadioStatus", new RadioMustBeChecked() {
                    public boolean mustBeChecked() {
                        if (session.getContainerState() instanceof StateContentDTO) {
                            ContentStatusDTO currentStatus = session.getContentState().getStatus();
                            return status.equals(currentStatus);
                        }
                        return false;
                    }
                });
        action.setTextDescription(textDescription);
        action.setParentMenuTitle(parentMenuTitle);
        action.setParentSubMenuTitle(i18n.t("Change the status"));
        return action;
    }

    protected void createShowDeletedItems(String parentMenuTitle, String... registerInTypes) {
        ActionToolbarMenuCheckItemDescriptor<StateToken> showDeletedItems = new ActionToolbarMenuCheckItemDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(StateToken parameter) {
                        boolean mustShow = !session.getCurrentUserInfo().getShowDeletedContent();
                        session.getCurrentUserInfo().setShowDeletedContent(mustShow);
                        if (!mustShow && session.isCurrentStateAContent()
                                && session.getContentState().getStatus().equals(ContentStatusDTO.inTheDustbin)) {
                            stateManager.gotoToken(session.getCurrentStateToken().getGroup());
                        }
                        contextNavigator.clear();
                        contextNavigator.refreshState();
                    }
                }, new ActionCheckedCondition() {
                    public boolean mustBeChecked() {
                        return session.getShowDeletedContent();
                    }
                });
        showDeletedItems.setParentMenuTitle(parentMenuTitle);
        showDeletedItems.setTextDescription("Show deleted items");
        showDeletedItems.setMustBeAuthenticated(true);
        contextActionRegistry.addAction(showDeletedItems, registerInTypes);
    }

    protected ActionToolbarButtonDescriptor<StateToken> createTranslateAction(String... registerInTypes) {
        ActionToolbarButtonDescriptor<StateToken> translateContent = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Editor, ActionToolbarPosition.topbar, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        Site.important(i18n.t("Sorry, this functionality is currently in development"));
                    }
                });
        translateContent.setTextDescription(i18n.tWithNT("Translate", "used in button"));
        translateContent.setToolTip(i18n.t("Translate this document to other languages"));
        translateContent.setIconUrl("images/language.gif");
        translateContent.setLeftSeparator(ActionToolbarButtonSeparator.spacer);
        translateContent.setEnableCondition(notDeleted());
        contentActionRegistry.addAction(translateContent, registerInTypes);
        return translateContent;
    }

    protected ActionToolbarButtonAndItemDescriptor<StateToken> createUploadAction(final String textDescription,
            final String iconUrl, final String toolTip, final String permitedExtensions, String... registerInTypes) {
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
        contextActionRegistry.addAction(uploadFile, registerInTypes);
        return uploadFile;
    }

    protected ActionToolbarButtonAndItemDescriptor<StateToken> createUploadMediaAction(String... registerInTypes) {
        ActionToolbarButtonAndItemDescriptor<StateToken> uploadMedia = createUploadAction(i18n.t("Upload media"),
                "images/nav/upload.png", i18n.t("Upload some media (images, videos...)"),
                session.getGalleryPermittedExtensions());
        contextActionRegistry.addAction(uploadMedia, registerInTypes);
        return uploadMedia;
    }

    protected void downloadContent(final StateToken token) {
        fileDownloadProvider.get().downloadFile(token);
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

    private ActionEnableCondition<StateToken> notDefAndNotDeleted() {
        return new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                final boolean isNotDefContentToken = !session.getCurrentState().getGroup().getDefaultContent().getStateToken().equals(
                        itemToken);
                final boolean isNotDeleted = !(session.isCurrentStateAContent() && session.getContentState().getStatus().equals(
                        ContentStatusDTO.inTheDustbin));
                return isNotDefContentToken && isNotDeleted;
            }
        };
    }

    private ActionEnableCondition<StateToken> notDeleted() {
        return new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken itemToken) {
                final boolean isNotDeleted = !(session.isCurrentStateAContent() && session.getContentState().getStatus().equals(
                        ContentStatusDTO.inTheDustbin));
                return isNotDeleted;
            }
        };
    }

    private void register(ActionToolbarMenuAndItemDescriptor<StateToken> action, Position position,
            String... registerInTypes) {
        switch (position) {
        case ctx:
            contextActionRegistry.addAction(action, registerInTypes);
            break;
        case cnt:
            contentActionRegistry.addAction(action, registerInTypes);
            break;
        }
    }

    private void setContentStatus(final ContentStatusDTO status, final StateToken stateToken) {
        final AsyncCallbackSimple<StateAbstractDTO> callback = new AsyncCallbackSimple<StateAbstractDTO>() {
            public void onSuccess(final StateAbstractDTO state) {
                if (session.inSameToken(stateToken)) {
                    session.setCurrentState(state);
                    publicLink.setState(state);
                    foldableContent.refreshState();
                }
                contextNavigator.setItemStatus(stateToken, status);
            }
        };
        if (status.equals(ContentStatusDTO.publishedOnline) || status.equals(ContentStatusDTO.rejected)
                || status.equals(ContentStatusDTO.inTheDustbin)) {
            contentServiceProvider.get().setStatusAsAdmin(session.getUserHash(), stateToken, status, callback);
        } else {
            contentServiceProvider.get().setStatus(session.getUserHash(), stateToken, status, callback);
        }
    }

}
