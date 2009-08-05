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
import org.ourproject.kune.platf.client.actions.RadioMustBeChecked;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.SessionExpiredException;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.ErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.workspace.client.cnt.ContentActionRegistry;
import org.ourproject.kune.workspace.client.cnt.FoldableContent;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.cxt.ContextActionRegistry;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.editor.ContentEditor;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.themes.WsBackManager;
import org.ourproject.kune.workspace.client.upload.FileUploader;
import org.ourproject.kune.workspace.client.wave.WaveInsert;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AbstractFoldableContentActions {

    protected enum Position {
        cnt, ctx
    }

    private static final String PUBLICATION_MENU = "Publication";

    public static final ActionToolbarPosition CONTENT_TOPBAR = new ActionToolbarPosition("afca-ctn-top");
    public static final ActionToolbarPosition CONTENT_BOTTOMBAR = new ActionToolbarPosition("afca-ctn-bottom");
    public static final ActionToolbarPosition CONTEXT_TOPBAR = new ActionToolbarPosition("afca-ctx-top");
    public static final ActionToolbarPosition CONTEXT_BOTTOMBAR = new ActionToolbarPosition("afca-ctx-bottom");

    protected final Session session;
    protected final StateManager stateManager;
    protected final I18nUITranslationService i18n;
    protected final Provider<ContentServiceAsync> contentServiceProvider;
    protected final Provider<FileUploader> fileUploaderProvider;
    protected final ContextNavigator contextNavigator;
    protected final ErrorHandler errorHandler;
    protected final Provider<GroupServiceAsync> groupServiceProvider;
    protected final ContentActionRegistry contentActionRegistry;
    protected final ContextActionRegistry contextActionRegistry;
    protected final Provider<FileDownloadUtils> fileDownloadProvider;
    protected final Provider<ContentEditor> textEditorProvider;
    protected final Provider<ContextPropEditor> contextPropEditorProvider;
    protected final FoldableContent foldableContent;
    protected final DeferredCommandWrapper deferredCommandWrapper;
    protected final EntityHeader entityLogo;
    private final SitePublicSpaceLink publicLink;

    private final WsBackManager wsBackManager;

    private final Provider<WaveInsert> waveInsert;

    public AbstractFoldableContentActions(final Session session, final StateManager stateManager,
            final I18nUITranslationService i18n, final ErrorHandler errorHandler,
            final DeferredCommandWrapper deferredCommandWrapper,
            final Provider<GroupServiceAsync> groupServiceProvider,
            final Provider<ContentServiceAsync> contentServiceProvider,
            final Provider<FileUploader> fileUploaderProvider, final ContextNavigator contextNavigator,
            final ContentActionRegistry contentActionRegistry, final ContextActionRegistry contextActionRegistry,
            final Provider<FileDownloadUtils> fileDownloadProvider, final Provider<ContentEditor> textEditorProvider,
            final Provider<ContextPropEditor> contextPropEditorProvider, final FoldableContent foldableContent,
            final EntityHeader entityLogo, final SitePublicSpaceLink publicLink, final WsBackManager wsBackManager,
            final Provider<WaveInsert> waveInsert) {
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
        this.wsBackManager = wsBackManager;
        this.waveInsert = waveInsert;
        createActions();
        session.onInitDataReceived(new Listener<InitDataDTO>() {
            public void onEvent(final InitDataDTO parameter) {
                createPostSessionInitActions();
            }
        });
    }

    protected abstract void createActions();

    protected void createContentModeratedActions(final String parentMenuTitle, final String... contentsModerated) {
        createSetStatusAction(AccessRolDTO.Administrator, i18n.t("Published online"), ContentStatusDTO.publishedOnline,
                contentsModerated);
        createSetStatusAction(AccessRolDTO.Editor, i18n.t("Editing in progress"), ContentStatusDTO.editingInProgress,
                contentsModerated);
        createSetStatusAction(AccessRolDTO.Administrator, i18n.t("Rejected"), ContentStatusDTO.rejected,
                contentsModerated);
        createSetStatusAction(AccessRolDTO.Editor, i18n.t("Submitted for publish"),
                ContentStatusDTO.submittedForEvaluation, contentsModerated);
        createSetStatusAction(AccessRolDTO.Administrator, i18n.t("In the rubbish bin"), ContentStatusDTO.inTheDustbin,
                contentsModerated);
    }

    protected void createContentRenameAction(final String parentMenuTitle, final String textDescription,
            final String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtn = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, CONTENT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        contextNavigator.editItem(stateToken);
                    }
                });
        renameCtn.setTextDescription(textDescription);
        renameCtn.setParentMenuTitle(parentMenuTitle);
        renameCtn.setEnableCondition(notDeleted());
        // renameCtn.setShortcut(new ShortcutDescriptor(false, Keyboard.KEY_F2,
        // i18n.tWithNT("F2", "The F2 Function key")));
        contentActionRegistry.addAction(renameCtn, registerInTypes);
    }

    protected void createDelContainerAction(final String text, final String parentMenuTitle,
            final String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> delContainer = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Administrator, CONTENT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        NotifyUser.info("Sorry, in development");
                    }
                });
        delContainer.setParentMenuTitle(parentMenuTitle);
        delContainer.setTextDescription(i18n.t(text));
        delContainer.setMustBeConfirmed(true);
        delContainer.setConfirmationTitle(i18n.t("Please confirm"));
        delContainer.setConfirmationText(i18n.t("You will delete it with all its contents. Are you sure?"));
        contextActionRegistry.addAction(delContainer, registerInTypes);
    }

    protected void createDelContentAction(final String parentMenuTitle, final String textDescription,
            final String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> delContent = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Administrator, CONTENT_TOPBAR, new Listener<StateToken>() {
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

    protected void createDownloadActions(final String typeUploadedfile) {
        final ActionToolbarButtonDescriptor<StateToken> download = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, CONTENT_TOPBAR, new Listener<StateToken>() {
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

        contentActionRegistry.addAction(download, typeUploadedfile);
        contextActionRegistry.addAction(downloadCtx, typeUploadedfile);
    }

    protected ActionToolbarButtonDescriptor<StateToken> createEditAction(final String fileMenuTitle,
            final String... registerInTypes) {
        final ActionToolbarButtonDescriptor<StateToken> editContent = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Editor, CONTENT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        NotifyUser.showProgressProcessing();
                        session.check(new AsyncCallbackSimple<Void>() {
                            public void onSuccess(final Void result) {
                                final ContentEditor editor = textEditorProvider.get();
                                foldableContent.detach();
                                contextNavigator.detach();
                                contextPropEditorProvider.get().attach();
                                editor.edit(session.getContentState().getContent(), new Listener<String>() {
                                    public void onEvent(final String html) {
                                        NotifyUser.showProgressSaving();
                                        contentServiceProvider.get().save(session.getUserHash(), stateToken, html,
                                                new AsyncCallback<Void>() {
                                                    public void onFailure(final Throwable caught) {
                                                        NotifyUser.hideProgress();
                                                        if (caught instanceof SessionExpiredException) {
                                                            errorHandler.doSessionExpired();
                                                        } else {
                                                            NotifyUser.error(i18n.t("Error saving document. Retrying..."));
                                                            errorHandler.process(caught);
                                                            editor.onSaveFailed();
                                                        }
                                                    }

                                                    public void onSuccess(final Void param) {
                                                        NotifyUser.hideProgress();
                                                        session.getContentState().setContent(html);
                                                        editor.onSavedSuccessful();
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
                                editor.setFileMenuTitle(fileMenuTitle);
                                NotifyUser.hideProgress();
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

    protected ActionMenuItemDescriptor<StateToken> createGoAction(final String... registerInTypes) {
        final ActionMenuItemDescriptor<StateToken> go = new ActionMenuItemDescriptor<StateToken>(AccessRolDTO.Viewer,
                new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        stateManager.gotoToken(token);
                    }
                });
        go.setMustBeAuthenticated(false);
        go.setTextDescription(i18n.t("Open"));
        go.setIconUrl("images/nav/go.png");
        contextActionRegistry.addAction(go, registerInTypes);
        return go;
    }

    protected ActionToolbarButtonDescriptor<StateToken> createGoHomeAction(final String... registerInTypes) {
        final ActionToolbarButtonDescriptor<StateToken> goGroupHome = new ActionToolbarButtonDescriptor<StateToken>(
                AccessRolDTO.Viewer, CONTEXT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        stateManager.gotoToken(token.getGroup());
                    }
                });
        goGroupHome.setMustBeAuthenticated(false);
        goGroupHome.setIconUrl("images/group-home.png");
        goGroupHome.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken token) {
                final StateToken defContentToken = session.getCurrentState().getGroup().getDefaultContent().getStateToken();
                return !session.getCurrentStateToken().equals(defContentToken);
            }
        });
        goGroupHome.setLeftSeparator(ActionToolbarButtonSeparator.fill);
        contextActionRegistry.addAction(goGroupHome, registerInTypes);
        return goGroupHome;
    }

    protected void createNewContainerAction(final String contentTypeId, final String iconUrl,
            final String textDescription, final String parentMenuTitle, final String parentMenuSubtitle,
            final String defaultName, final Position position, final String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> addFolder;
        addFolder = new ActionToolbarMenuAndItemDescriptor<StateToken>(AccessRolDTO.Editor, CONTEXT_TOPBAR,
                new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        NotifyUser.showProgressProcessing();
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
            final String iconUrl, final String description, final String parentMenuTitle, final Position position,
            final String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> addContent = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, CONTEXT_TOPBAR, new Listener0() {
                    public void onEvent() {
                        NotifyUser.showProgressProcessing();
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

    protected abstract void createPostSessionInitActions();

    protected ActionToolbarMenuDescriptor<StateToken> createRefreshCntAction(final String parentMenuTitle,
            final String... registerInTypes) {
        final ActionToolbarMenuDescriptor<StateToken> refreshCnt = new ActionToolbarMenuDescriptor<StateToken>(
                AccessRolDTO.Editor, CONTENT_TOPBAR, new Listener<StateToken>() {
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

    protected ActionToolbarMenuDescriptor<StateToken> createRefreshCxtAction(final String parentMenuTitleCtx,
            final String... registerInTypes) {
        final ActionToolbarMenuDescriptor<StateToken> refreshCtx = new ActionToolbarMenuDescriptor<StateToken>(
                AccessRolDTO.Editor, CONTEXT_TOPBAR, new Listener<StateToken>() {
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

    protected ActionToolbarMenuAndItemDescriptor<StateToken> createRenameContentInCtxAction(
            final String parentMenuTitleCtx, final String textDescription, final String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> renameCtx = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, CONTEXT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        contextNavigator.editItem(stateToken);
                    }
                });
        renameCtx.setTextDescription(textDescription);
        renameCtx.setParentMenuTitle(parentMenuTitleCtx);
        contextActionRegistry.addAction(renameCtx, registerInTypes);
        return renameCtx;
    }

    protected ActionToolbarMenuDescriptor<StateToken> createSetAsDefContent(final String parentMenuTitle,
            final String... registerInTypes) {
        final ActionToolbarMenuDescriptor<StateToken> setAsDefGroupContent;
        setAsDefGroupContent = new ActionToolbarMenuDescriptor<StateToken>(AccessRolDTO.Administrator, CONTENT_TOPBAR,
                new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        NotifyUser.showProgressProcessing();
                        contentServiceProvider.get().setAsDefaultContent(session.getUserHash(), token,
                                new AsyncCallbackSimple<ContentSimpleDTO>() {
                                    public void onSuccess(final ContentSimpleDTO defContent) {
                                        session.getCurrentState().getGroup().setDefaultContent(defContent);
                                        NotifyUser.hideProgress();
                                        NotifyUser.info(i18n.t("Content selected as the group homepage"));
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

    protected void createSetGroupBackImageAction(final String parentMenuTitle, final String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> setGroupBackImage = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Administrator, CONTENT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken token) {
                        groupServiceProvider.get().setGroupBackImage(session.getUserHash(), token,
                                new AsyncCallbackSimple<GroupDTO>() {
                                    public void onSuccess(final GroupDTO newGroup) {
                                        if (session.getCurrentState().getGroup().getShortName().equals(
                                                newGroup.getShortName())) {
                                            session.getCurrentState().setGroup(newGroup);
                                            wsBackManager.clearBackImage();
                                            wsBackManager.setBackImage(newGroup.getGroupBackImage().getStateToken());
                                        }
                                    }
                                });
                    }
                });
        setGroupBackImage.setParentMenuTitle(parentMenuTitle);
        setGroupBackImage.setTextDescription(i18n.t("Set this as the group background image"));
        setGroupBackImage.setIconUrl("images/nav/picture.png");
        setGroupBackImage.setEnableCondition(new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken token) {
                return session.getContentState().getMimeType().isImage();
            }
        });
        contentActionRegistry.addAction(setGroupBackImage, registerInTypes);
    }

    protected void createSetStatusAction(final AccessRolDTO rol, final String textDescription,
            final ContentStatusDTO status, final String[] contentsModerated) {
        final ActionToolbarMenuRadioDescriptor<StateToken> action = new ActionToolbarMenuRadioDescriptor<StateToken>(
                rol, CONTENT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        setContentStatus(status, stateToken);
                    }
                }, "ContentRadioStatus", new RadioMustBeChecked() {
                    public boolean mustBeChecked() {
                        if (session.getContainerState() instanceof StateContentDTO) {
                            final ContentStatusDTO currentStatus = session.getContentState().getStatus();
                            return status.equals(currentStatus);
                        }
                        return false;
                    }
                });
        action.setTextDescription(textDescription);
        action.setParentMenuTitle(PUBLICATION_MENU);
        action.setParentMenuIconUrl("images/anybody.png");
        contentActionRegistry.addAction(action, contentsModerated);
    }

    protected void createShowDeletedItems(final String parentMenuTitle, final String... registerInTypes) {
        final ActionToolbarMenuCheckItemDescriptor<StateToken> showDeletedItems = new ActionToolbarMenuCheckItemDescriptor<StateToken>(
                AccessRolDTO.Editor, CONTEXT_TOPBAR, new Listener0() {
                    public void onEvent() {
                        final boolean mustShow = !session.getCurrentUserInfo().getShowDeletedContent();
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

    protected ActionToolbarMenuDescriptor<StateToken> createTranslateAction(final String fileMenuTitle,
            final String... registerInTypes) {
        final ActionToolbarMenuDescriptor<StateToken> translateContent = new ActionToolbarMenuDescriptor<StateToken>(
                AccessRolDTO.Editor, CONTENT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken stateToken) {
                        NotifyUser.important(i18n.t("Sorry, this functionality is currently in development"));
                    }
                });
        translateContent.setParentMenuTitle(fileMenuTitle);
        translateContent.setTextDescription(i18n.tWithNT("Translate", "used in button"));
        translateContent.setToolTip(i18n.t("Translate this document to other languages"));
        translateContent.setIconUrl("images/language.gif");
        translateContent.setEnableCondition(notDeleted());
        contentActionRegistry.addAction(translateContent, registerInTypes);
        return translateContent;
    }

    protected ActionToolbarButtonAndItemDescriptor<StateToken> createUploadAction(final String textDescription,
            final String iconUrl, final String toolTip, final String permitedExtensions,
            final String... registerInTypes) {
        final ActionToolbarButtonAndItemDescriptor<StateToken> uploadFile;
        uploadFile = new ActionToolbarButtonAndItemDescriptor<StateToken>(AccessRolDTO.Editor, CONTEXT_BOTTOMBAR,
                new Listener0() {
                    public void onEvent() {
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

    protected ActionToolbarButtonAndItemDescriptor<StateToken> createUploadMediaAction(final String... registerInTypes) {
        final ActionToolbarButtonAndItemDescriptor<StateToken> uploadMedia = createUploadAction(i18n.t("Upload media"),
                "images/nav/upload.png", i18n.t("Upload some media (images, videos...)"),
                session.getGalleryPermittedExtensions());
        contextActionRegistry.addAction(uploadMedia, registerInTypes);
        return uploadMedia;
    }

    protected ActionToolbarMenuAndItemDescriptor<StateToken> createWaveAction(final String waveFileType,
            final String parentMenuTitle, final Position position, final String... registerInTypes) {
        final ActionToolbarMenuAndItemDescriptor<StateToken> addWave = new ActionToolbarMenuAndItemDescriptor<StateToken>(
                AccessRolDTO.Editor, CONTEXT_TOPBAR, new Listener<StateToken>() {
                    public void onEvent(final StateToken parentToken) {
                        waveInsert.get().show(parentToken);
                    }
                });
        addWave.setTextDescription(i18n.t("Add Wave"));
        addWave.setParentMenuTitle(parentMenuTitle);
        addWave.setMustBeAuthenticated(true);
        addWave.setParentSubMenuTitle(i18n.t("New"));
        // addContent.setIconUrl(iconUrl);
        register(addWave, position, registerInTypes);
        return addWave;
    }

    protected void downloadContent(final StateToken token) {
        fileDownloadProvider.get().downloadFile(token);
    }

    private ActionEnableCondition<StateToken> notDefAndNotDeleted() {
        return new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken token) {
                final boolean isNotDefContentToken = !session.getCurrentState().getGroup().getDefaultContent().getStateToken().equals(
                        session.getCurrentStateToken());
                return isNotDefContentToken && notDeleted().mustBeEnabled(token);
            }
        };
    }

    private ActionEnableCondition<StateToken> notDeleted() {
        return new ActionEnableCondition<StateToken>() {
            public boolean mustBeEnabled(final StateToken token) {
                final boolean isNotDeleted = !(session.isCurrentStateAContent() && session.getContentState().getStatus().equals(
                        ContentStatusDTO.inTheDustbin));
                return isNotDeleted;
            }
        };
    }

    private void register(final ActionToolbarMenuAndItemDescriptor<StateToken> action, final Position position,
            final String... registerInTypes) {
        if (position.equals(Position.ctx)) {
            contextActionRegistry.addAction(action, registerInTypes);
        } else if (position.equals(Position.cnt)) {
            contentActionRegistry.addAction(action, registerInTypes);
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
