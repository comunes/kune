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

package org.ourproject.kune.workspace.client.ctxnav;

import static org.ourproject.kune.docs.client.DocumentClientTool.TYPE_FOLDER;

import java.util.HashMap;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.actions.DragDropContentRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContainerSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentStatusDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.download.ImageSize;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.title.EntityTitle;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContextNavigatorPresenter implements ContextNavigator {

    private ContextNavigatorView view;
    private final StateManager stateManager;
    private final Session session;
    private final Provider<ContentServiceAsync> contentServiceProvider;
    private final I18nUITranslationService i18n;
    private final HashMap<StateToken, ActionItemCollection<StateToken>> actionsByItem;
    private final EntityTitle entityTitle;
    private boolean editOnNextStateChange;
    private final ContentIconsRegistry contentIconsRegistry;
    private final DragDropContentRegistry dragDropContentRegistry;
    private final ActionRegistry<StateToken> actionRegistry;
    private final ActionToolbar<StateToken> toolbar;
    private final Provider<FileDownloadUtils> downloadUtilsProvider;
    private final boolean useGenericImageIcon;

    public ContextNavigatorPresenter(final StateManager stateManager, final Session session,
            final Provider<ContentServiceAsync> contentServiceProvider, final I18nUITranslationService i18n,
            final EntityTitle entityTitle, final ContentIconsRegistry contentIconsRegistry,
            final DragDropContentRegistry dragDropContentRegistry, final ActionToolbar<StateToken> toolbar,
            final ActionRegistry<StateToken> actionRegistry, Provider<FileDownloadUtils> downloadUtilsProvider,
            boolean useGenericImageIcon) {
        this.stateManager = stateManager;
        this.session = session;
        this.contentServiceProvider = contentServiceProvider;
        this.i18n = i18n;
        this.entityTitle = entityTitle;
        this.contentIconsRegistry = contentIconsRegistry;
        this.dragDropContentRegistry = dragDropContentRegistry;
        this.actionRegistry = actionRegistry;
        this.toolbar = toolbar;
        this.downloadUtilsProvider = downloadUtilsProvider;
        this.useGenericImageIcon = useGenericImageIcon;
        actionsByItem = new HashMap<StateToken, ActionItemCollection<StateToken>>();
        editOnNextStateChange = false;
    }

    public void editItem(final StateToken stateToken) {
        view.editItem(genId(stateToken));
    }

    public StateToken getCurrentStateToken() {
        return session.getCurrentState().getStateToken();
    }

    public View getView() {
        return view;
    }

    public void gotoToken(final String token) {
        stateManager.gotoToken(token);
    }

    public void init(final ContextNavigatorView view) {
        this.view = view;
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                clear();
            }
        });
        session.onUserSignOut(new Listener0() {
            public void onEvent() {
                clear();
            }
        });
    }

    public boolean isSelected(final StateToken stateToken) {
        return view.isSelected(genId(stateToken));
    }

    public boolean mustEditOnNextStateChange() {
        return editOnNextStateChange;
    }

    public void onItemRename(final String token, final String newName, final String oldName) {
        if (!newName.equals(oldName)) {
            Site.showProgress(i18n.t("Renaming"));
            final StateToken stateToken = new StateToken(token);
            final AsyncCallback<String> asyncCallback = new AsyncCallback<String>() {
                public void onFailure(final Throwable caught) {
                    view.setFireOnTextChange(false);
                    setItemText(stateToken, oldName);
                    view.setFireOnTextChange(true);
                    Site.error(i18n.t("Error renaming"));
                    Site.hideProgress();
                }

                public void onSuccess(final String result) {
                    Site.hideProgress();
                    if (session.getCurrentState().getStateToken().getEncoded().equals(token)) {
                        // I have to update EntityTitle
                        entityTitle.setContentTitle(newName);
                    }
                }
            };
            if (stateToken.isComplete()) {
                contentServiceProvider.get().renameContent(session.getUserHash(), stateToken, newName, asyncCallback);
            } else {
                contentServiceProvider.get().renameContainer(session.getUserHash(), stateToken, newName, asyncCallback);
            }
        }
    }

    public void refresh(final StateToken stateToken) {
        contentServiceProvider.get().getContent(session.getUserHash(), stateToken, new AsyncCallbackSimple<StateDTO>() {
            public void onSuccess(final StateDTO result) {
                setState(result, false);
            }
        });
    }

    public void selectItem(final StateToken stateToken) {
        view.selectItem(genId(stateToken));
        toolbar.disableMenusAndClearButtons();
        toolbar.setActions(actionsByItem.get(stateToken));
    }

    public void setEditOnNextStateChange(final boolean edit) {
        editOnNextStateChange = edit;
    }

    public void setItemText(final StateToken stateToken, final String name) {
        view.setItemText(genId(stateToken), name);
    }

    public void setState(final StateDTO state, final boolean select) {
        final ContainerDTO container = state.getContainer();
        final StateToken stateToken = state.getStateToken();
        final AccessRightsDTO containerRights = state.getContainerRights();
        AccessRightsDTO rights;

        if (stateToken.hasAll()) {
            rights = state.getContentRights();
        } else {
            rights = containerRights;
        }
        view.setEditable(rights.isEditable());

        // If root sended (container is not a root folder) process root (add
        // childs to view)
        final ContainerDTO root = state.getRootContainer();
        if (root != null) {
            view.setRootItem(genId(root.getStateToken()), i18n.t(root.getName()), root.getStateToken());
            createChildItems(root, containerRights);
        }

        // Do the path to our current content
        createTreePath(stateToken, container.getAbsolutePath(), containerRights);

        // Process our current content/container
        final ActionItemCollection<StateToken> actionItems = new ActionItemCollection<StateToken>();
        if (state.hasDocument()) {
            rights = state.getContentRights();
            final ActionItemCollection<StateToken> contentActions = addItem(state.getTitle(), state.getTypeId(), state
                    .getMimeType(), state.getStatus(), stateToken, container.getStateToken(), rights, false);
            final ActionItemCollection<StateToken> containerActions = actionRegistry.getCurrentActions(container
                    .getStateToken(), container.getTypeId(), session.isLogged(), containerRights, true);
            actionItems.addAll(containerActions);
            actionItems.addAll(contentActions);

        } else {
            rights = containerRights;
            final ActionItemCollection<StateToken> containerActions = addItem(container.getName(), container
                    .getTypeId(), null, ContentStatusDTO.publishedOnline, container.getStateToken(), container
                    .getStateToken().clone().setFolder(container.getParentFolderId()), containerRights, false);
            actionItems.addAll(containerActions);
        }

        actionsByItem.put(stateToken, actionItems);
        // Process container childs
        createChildItems(container, containerRights);

        // Finaly
        if (mustEditOnNextStateChange()) {
            // Code smell
            selectItem(stateToken);
            editItem(stateToken);
            setEditOnNextStateChange(false);
        } else {
            if (select) {
                selectItem(stateToken);
            }
        }
        toolbar.attach();
    }

    protected void clear() {
        toolbar.clear();
        view.clear();
        actionsByItem.clear();
    }

    private ActionItemCollection<StateToken> addItem(final String title, final String contentTypeId,
            final BasicMimeTypeDTO mimeType, final ContentStatusDTO status, final StateToken stateToken,
            final StateToken parentStateToken, final AccessRightsDTO rights, final boolean isNodeSelected) {

        final ActionItemCollection<StateToken> toolbarActions = actionRegistry.getCurrentActions(stateToken,
                contentTypeId, session.isLogged(), rights, true);

        final String contentTypeIcon = getIcon(stateToken, contentTypeId, mimeType);
        final String tooltip = getTooltip(stateToken, mimeType);
        final ContextNavigatorItem item = new ContextNavigatorItem(genId(stateToken), genId(parentStateToken),
                contentTypeIcon, title, tooltip, status, stateToken, dragDropContentRegistry.isDraggable(contentTypeId,
                        rights.isAdministrable()), dragDropContentRegistry.isDroppable(contentTypeId, rights
                        .isAdministrable()), actionRegistry.getCurrentActions(stateToken, contentTypeId, session
                        .isLogged(), rights, false));
        view.addItem(item);
        return toolbarActions;
    }

    private void createChildItems(final ContainerDTO container, final AccessRightsDTO containerRights) {
        for (final ContentSimpleDTO content : container.getContents()) {
            addItem(content.getTitle(), content.getTypeId(), content.getMimeType(), content.getStatus(), content
                    .getStateToken(), content.getStateToken().clone().clearDocument(), content.getRights(), false);
        }

        for (final ContainerSimpleDTO siblingFolder : container.getChilds()) {
            addItem(siblingFolder.getName(), siblingFolder.getTypeId(), null, ContentStatusDTO.publishedOnline,
                    siblingFolder.getStateToken(), siblingFolder.getStateToken().clone().setFolder(
                            siblingFolder.getParentFolderId()), containerRights, false);
        }
    }

    private void createTreePath(final StateToken state, final ContainerSimpleDTO[] absolutePath,
            final AccessRightsDTO rights) {
        for (final ContainerSimpleDTO folder : absolutePath) {
            final StateToken folderStateToken = folder.getStateToken();
            final StateToken parentStateToken = state.clone().clearDocument().setFolder(folder.getParentFolderId());

            if (folder.getParentFolderId() != null) {
                addItem(folder.getName(), folder.getTypeId(), null, ContentStatusDTO.publishedOnline, folderStateToken,
                        parentStateToken, rights, false);
            } else {
                // Root must be already created
            }
        }
    }

    private String genId(final StateToken token) {
        return "k-" + token.toString().replace(StateToken.SEPARATOR, "-");
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

    private String getTooltip(StateToken token, BasicMimeTypeDTO mimeType) {
        if (mimeType != null && mimeType.getType().equals("image")) {
            return KuneUiUtils.genQuickTipWithImage(downloadUtilsProvider.get().getImageResizedUrl(token,
                    ImageSize.thumb));
        } else {
            return null;
        }
    }

}
