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
package org.ourproject.kune.workspace.client.title;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.registry.RenamableRegistry;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener2;

public class EntityTitlePresenter implements EntityTitle {

    private EntityTitleView view;
    private final Session session;
    private final ContentIconsRegistry iconsRegistry;
    private final RenamableRegistry renamableContentRegistry;
    private final RenameAction renameAction;

    public EntityTitlePresenter(final StateManager stateManager, final Session session,
            final ContentIconsRegistry iconsRegistry, RenamableRegistry renamableContentRegistry,
            RenameAction renameAction) {
        this.session = session;
        this.iconsRegistry = iconsRegistry;
        this.renamableContentRegistry = renamableContentRegistry;
        this.renameAction = renameAction;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (state instanceof StateContentDTO) {
                    setState((StateContentDTO) state);
                } else if (state instanceof StateContainerDTO) {
                    setState((StateContainerDTO) state);
                } else {
                    view.setContentIconVisible(false);
                    view.setContentTitleVisible(false);
                }
            }
        });
        createRenameListeners();
    }

    public View getView() {
        return view;
    }

    public void init(final EntityTitleView view) {
        this.view = view;
    }

    /**
     * Used renaming from context
     */
    public void setContentTitle(final String title) {
        view.setContentTitle(title);
        view.setContentTitleVisible(true);
    }

    protected void onTitleRename(final String oldName, final String newName) {
        final StateToken token = session.getCurrentState().getStateToken();
        renameAction.rename(token, oldName, newName);
    }

    private void createRenameListeners() {
        Listener2<StateToken, String> onSuccess = new Listener2<StateToken, String>() {
            public void onEvent(StateToken token, String newName) {
                view.setContentTitle(newName);
            }
        };
        renameAction.onSuccess(onSuccess);
        Listener2<StateToken, String> onFail = new Listener2<StateToken, String>() {
            public void onEvent(StateToken token, String oldName) {
                view.setContentTitle(oldName);
            }
        };
        renameAction.onFail(onFail);
    }

    private void setContentTitle(final String title, final boolean editable) {
        setContentTitle(title);
        view.setContentTitleEditable(editable);
        view.setContentTitleVisible(true);
    }

    private void setIcon(final String contentTypeIcon) {
        if (contentTypeIcon.length() > 0) {
            view.setContentIcon(contentTypeIcon);
            view.setContentIconVisible(true);
        } else {
            view.setContentIconVisible(false);
        }
    }

    private void setState(final StateContainerDTO state) {
        setContentTitle(state.getTitle(), state.getContainerRights().isEditable()
                && renamableContentRegistry.contains(state.getContainer().getTypeId()));
        final String contentTypeIcon = iconsRegistry.getContentTypeIcon(state.getTypeId(), null);
        setIcon(contentTypeIcon);
        view.setContentTitleVisible(true);
    }

    private void setState(final StateContentDTO state) {
        setContentTitle(state.getTitle(), state.getContentRights().isEditable()
                && renamableContentRegistry.contains(state.getTypeId()));
        final String contentTypeIcon = iconsRegistry.getContentTypeIcon(state.getTypeId(), state.getMimeType());
        setIcon(contentTypeIcon);
        view.setContentTitleVisible(true);
    }
}
