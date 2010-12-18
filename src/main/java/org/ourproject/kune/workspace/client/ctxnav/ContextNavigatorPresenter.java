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
package org.ourproject.kune.workspace.client.ctxnav;

import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_FOLDER;

import java.util.HashMap;

import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.download.ImageSize;
import org.ourproject.kune.workspace.client.AbstractFoldableContentActions;
import org.ourproject.kune.workspace.client.cnt.ContentIconsRegistry;
import org.ourproject.kune.workspace.client.cxt.ActionContextBottomToolbar;
import org.ourproject.kune.workspace.client.title.RenameAction;
import org.ourproject.kune.workspace.client.upload.FileUploader;

import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.ContentStatus;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContainerSimpleDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;
import cc.kune.core.shared.dto.UserInfoDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class ContextNavigatorPresenter implements ContextNavigator {

    private ContextNavigatorView view;
    private final StateManager stateManager;
    private final Session session;
    private final I18nTranslationService i18n;
    private final HashMap<StateToken, ActionItemCollection<StateToken>> actionsByItem;
    private boolean editOnNextStateChange;
    private final ContentIconsRegistry contentIconsRegistry;
    private final ActionRegistry<StateToken> actionRegistry;
    private final ActionToolbar<StateToken> topToolbar;
    private final Provider<FileDownloadUtils> downloadUtilsProvider;
    private final boolean useGenericImageIcon;
    private final ContentCapabilitiesRegistry capabilitiesRegistry;
    private final RenameAction renameAction;
    private final ActionContextBottomToolbar bottomToolbar;

    public ContextNavigatorPresenter(final StateManager stateManager, final Session session,
            final I18nTranslationService i18n, final ContentIconsRegistry contentIconsRegistry,
            final ContentCapabilitiesRegistry capabilitiesRegistry, final ActionToolbar<StateToken> toolbar,
            final ActionContextBottomToolbar bottomToolbar, final ActionRegistry<StateToken> actionRegistry,
            final Provider<FileDownloadUtils> downloadUtilsProvider, final boolean useGenericImageIcon,
            final RenameAction renameAction) {
        this.stateManager = stateManager;
        this.session = session;
        this.i18n = i18n;
        this.contentIconsRegistry = contentIconsRegistry;
        this.capabilitiesRegistry = capabilitiesRegistry;
        this.topToolbar = toolbar;
        this.bottomToolbar = bottomToolbar;
        this.actionRegistry = actionRegistry;
        this.downloadUtilsProvider = downloadUtilsProvider;
        this.useGenericImageIcon = useGenericImageIcon;
        this.renameAction = renameAction;
        actionsByItem = new HashMap<StateToken, ActionItemCollection<StateToken>>();
        editOnNextStateChange = false;
        addRenameListener();
    }

    @Override
    public void addFileUploaderListener(final FileUploader uploader) {
        uploader.addOnUploadCompleteListener(new Listener<StateToken>() {
            @Override
            public void onEvent(final StateToken currentUploadStateToken) {
                if (currentUploadStateToken.hasSameContainer(session.getCurrentStateToken())) {
                    stateManager.reload();
                }
            }
        });
    }

    @Override
    public void attach() {
        // FIXME At the moment detach (removeFromParent) destroy the gwt-ext
        // TreePanel and the widget must be recreated (cannot be attached again
        // like in gwt)
        topToolbar.attach();
        bottomToolbar.attach();
    }

    @Override
    public void clear() {
        topToolbar.clear();
        bottomToolbar.clear();
        view.clear();
        actionsByItem.clear();
    }

    @Override
    public void detach() {
        view.detach();
        topToolbar.detach();
        bottomToolbar.detach();
    }

    @Override
    public void editItem(final StateToken stateToken) {
        view.editItem(genId(stateToken));
    }

    public void init(final ContextNavigatorView view) {
        this.view = view;
        addListeners();
    }

    @Override
    public boolean isSelected(final StateToken stateToken) {
        return view.isSelected(genId(stateToken));
    }

    @Override
    public void refreshState() {
        final StateAbstractDTO currentState = session.getCurrentState();
        if (currentState instanceof StateContainerDTO) {
            setState((StateContainerDTO) currentState, true);
        }
    }

    @Override
    public void selectItem(final StateToken stateToken) {
        view.selectItem(genId(stateToken));
        topToolbar.disableMenusAndClearButtons();
        bottomToolbar.disableMenusAndClearButtons();
        final ActionItemCollection<StateToken> itemCollection = actionsByItem.get(stateToken);
        topToolbar.addActions(itemCollection, AbstractFoldableContentActions.CONTEXT_TOPBAR);
        bottomToolbar.addActions(itemCollection, AbstractFoldableContentActions.CONTEXT_BOTTOMBAR);
    }

    @Override
    public void setEditOnNextStateChange(final boolean edit) {
        editOnNextStateChange = edit;
    }

    @Override
    public void setItemStatus(final StateToken stateToken, final ContentStatus status) {
        clear();
        refreshState();
    }

    @Override
    public void setState(final StateContainerDTO state, final boolean select) {
        setStateContainer(state, select);
    }

    protected void gotoToken(final String token) {
        stateManager.gotoToken(token);
    }

    protected void onItemRename(final String token, final String newName, final String oldName) {
        renameAction.rename(new StateToken(token), oldName, newName);
    }

    private ActionItemCollection<StateToken> addItem(final String title, final String contentTypeId,
            final BasicMimeTypeDTO mimeType, final ContentStatus status, final StateToken stateToken,
            final StateToken parentStateToken, final AccessRights rights, final boolean isNodeSelected) {
        final ActionItemCollection<StateToken> toolbarActions = actionRegistry.getCurrentActions(stateToken,
                contentTypeId, session.isLogged(), rights, true);

        final String contentTypeIcon = getIcon(stateToken, contentTypeId, mimeType);
        final String tooltip = getTooltip(stateToken, mimeType);
        final ContextNavigatorItem item = new ContextNavigatorItem(genId(stateToken), genId(parentStateToken),
                contentTypeIcon, title, tooltip, status, stateToken, capabilitiesRegistry.isDragable(contentTypeId)
                        && rights.isAdministrable(), capabilitiesRegistry.isDropable(contentTypeId)
                        && rights.isAdministrable(), actionRegistry.getCurrentActions(stateToken, contentTypeId,
                        session.isLogged(), rights, false));
        if (status.equals(ContentStatus.inTheDustbin) && !session.getShowDeletedContent()) {
            // Don't show
        } else {
            view.addItem(item);
        }
        return toolbarActions;
    }

    private void addListeners() {
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            @Override
            public void onEvent(final UserInfoDTO parameter) {
                clear();
            }
        });
        session.onUserSignOut(new Listener0() {
            @Override
            public void onEvent() {
                clear();
            }
        });
    }

    private void addRenameListener() {
        final Listener2<StateToken, String> onSuccess = new Listener2<StateToken, String>() {
            @Override
            public void onEvent(final StateToken token, final String newName) {
                setItemText(token, newName);
            }
        };
        final Listener2<StateToken, String> onFail = new Listener2<StateToken, String>() {
            @Override
            public void onEvent(final StateToken token, final String oldName) {
                setItemText(token, oldName);
            }
        };
        renameAction.onSuccess(onSuccess);
        renameAction.onFail(onFail);
    }

    private void createChildItems(final ContainerDTO container, final AccessRights containerRights) {
        for (final ContainerSimpleDTO siblingFolder : container.getChilds()) {
            addItem(siblingFolder.getName(), siblingFolder.getTypeId(), null, ContentStatus.publishedOnline,
                    siblingFolder.getStateToken(),
                    siblingFolder.getStateToken().copy().setFolder(siblingFolder.getParentFolderId()), containerRights,
                    false);
        }
        for (final ContentSimpleDTO content : container.getContents()) {
            addItem(content.getTitle(), content.getTypeId(), content.getMimeType(), content.getStatus(),
                    content.getStateToken(), content.getStateToken().copy().clearDocument(), content.getRights(), false);
        }
    }

    private void createTreePath(final StateToken state, final ContainerSimpleDTO[] absolutePath,
            final AccessRights rights) {
        for (final ContainerSimpleDTO folder : absolutePath) {
            final StateToken folderStateToken = folder.getStateToken();
            final StateToken parentStateToken = state.copy().clearDocument().setFolder(folder.getParentFolderId());

            if (folder.getParentFolderId() != null) {
                addItem(folder.getName(), folder.getTypeId(), null, ContentStatus.publishedOnline, folderStateToken,
                        parentStateToken, rights, false);
            } else {
                // Root must be already created
            }
        }
    }

    private String genId(final StateToken token) {
        return "k-cnav-" + token.toString().replace(StateToken.SEPARATOR, "-");
    }

    private String getIcon(final StateToken token, final String contentTypeId, final BasicMimeTypeDTO mimeType) {
        if (contentTypeId.equals(TYPE_FOLDER)) {
            return null;
        } else if (!useGenericImageIcon && mimeType != null && mimeType.getType().equals("image")) {
            return downloadUtilsProvider.get().getImageResizedUrl(token, ImageSize.ico);
        } else {
            return contentIconsRegistry.getContentTypeIcon(contentTypeId, mimeType);
        }
    }

    private String getTooltip(final StateToken token, final BasicMimeTypeDTO mimeType) {
        if (mimeType != null && (mimeType.isImage() || mimeType.isPdf())) {
            return KuneUiUtils.genQuickTipWithImage(
                    downloadUtilsProvider.get().getImageResizedUrl(token, ImageSize.thumb), session.getImgCropsize());
        } else {
            return null;
        }
    }

    private void selectOrEditNode(final boolean select, final StateToken stateToken) {
        // Finaly
        if (editOnNextStateChange) {
            // Code smell
            selectItem(stateToken);
            editItem(stateToken);
            setEditOnNextStateChange(false);
        } else {
            if (select) {
                selectItem(stateToken);
            }
        }
    }

    private void setItemText(final StateToken stateToken, final String name) {
        if (view.isAttached()) {
            view.setItemText(genId(stateToken), name);
        }
    }

    private void setStateContainer(final StateContainerDTO state, final boolean select) {
        final StateToken stateToken = state.getStateToken();
        final boolean isContent = state instanceof StateContentDTO;
        StateContentDTO stateContent = null;

        final ContainerDTO container = state.getContainer();
        final AccessRights containerRights = state.getContainerRights();
        AccessRights rights;

        showRootFolder(state, containerRights);

        // Do the path to our current content
        createTreePath(stateToken, container.getAbsolutePath(), containerRights);

        // Process our current content/container
        final ActionItemCollection<StateToken> actionItems = new ActionItemCollection<StateToken>();

        if (isContent) {
            stateContent = (StateContentDTO) state;
            rights = stateContent.getContentRights();
            final ActionItemCollection<StateToken> contentActions = addItem(state.getTitle(), state.getTypeId(),
                    stateContent.getMimeType(), stateContent.getStatus(), stateToken, container.getStateToken(),
                    rights, false);
            final ActionItemCollection<StateToken> containerActions = actionRegistry.getCurrentActions(
                    container.getStateToken(), container.getTypeId(), session.isLogged(), containerRights, true);
            actionItems.addAll(containerActions);
            actionItems.addAll(contentActions);
        } else {
            rights = containerRights;
            final ActionItemCollection<StateToken> containerActions = addItem(container.getName(),
                    container.getTypeId(), null, ContentStatus.publishedOnline, container.getStateToken(),
                    container.getStateToken().copy().setFolder(container.getParentFolderId()), containerRights, false);
            actionItems.addAll(containerActions);
        }
        view.setEditable(rights.isEditable());

        actionsByItem.put(stateToken, actionItems);

        // Process container childs
        createChildItems(container, containerRights);

        selectOrEditNode(select, stateToken);

        topToolbar.attach();
        bottomToolbar.attach();
    }

    private void showRootFolder(final StateContainerDTO state, final AccessRights containerRights) {
        // If container is not a root folder process root (add
        // childs to view)
        final ContainerDTO root = state.getRootContainer();
        if (root != null) {
            // container is not root
            view.setRootItem(genId(root.getStateToken()), i18n.t(root.getName()), root.getStateToken());
            createChildItems(root, containerRights);
        }
    }
}
