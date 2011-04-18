/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

import cc.kune.core.client.registry.IconsRegistry;
import cc.kune.core.client.registry.RenamableRegistry;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener2;

public class EntityTitlePresenter implements EntityTitle {

    private final IconsRegistry iconsRegistry;
    private final RenamableRegistry renamableContentRegistry;
    private final RenameAction renameAction;
    private final Session session;
    private EntityTitleView view;

    public EntityTitlePresenter(final StateManager stateManager, final Session session,
            final IconsRegistry iconsRegistry, final RenamableRegistry renamableContentRegistry,
            final RenameAction renameAction) {
        this.session = session;
        this.iconsRegistry = iconsRegistry;
        this.renamableContentRegistry = renamableContentRegistry;
        this.renameAction = renameAction;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            @Override
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
        addRenameListeners();
    }

    private void addRenameListeners() {
        final Listener2<StateToken, String> onSuccess = new Listener2<StateToken, String>() {
            @Override
            public void onEvent(final StateToken token, final String newName) {
                view.setContentTitle(newName);
            }
        };
        final Listener2<StateToken, String> onFail = new Listener2<StateToken, String>() {
            @Override
            public void onEvent(final StateToken token, final String oldName) {
                view.setContentTitle(oldName);
            }
        };
        renameAction.onSuccess(onSuccess);
        renameAction.onFail(onFail);
    }

    @Override
    public void edit() {
        view.edit();
    }

    public View getView() {
        return view;
    }

    public void init(final EntityTitleView view) {
        this.view = view;
    }

    protected void onTitleRename(final String oldName, final String newName) {
        final StateToken token = session.getCurrentState().getStateToken();
        renameAction.rename(token, oldName, newName);
    }

    /**
     * Used renaming from context
     */
    @Override
    public void setContentTitle(final String title) {
        view.setContentTitle(title);
        view.setContentTitleVisible(true);
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
        setContentTitle(
                state.getTitle(),
                state.getContainerRights().isEditable()
                        && renamableContentRegistry.contains(state.getContainer().getTypeId()));
        final String contentTypeIcon = (String) iconsRegistry.getContentTypeIcon(state.getTypeId(), null);
        setIcon(contentTypeIcon);
        view.setContentTitleVisible(true);
    }

    private void setState(final StateContentDTO state) {
        setContentTitle(state.getTitle(),
                state.getContentRights().isEditable() && renamableContentRegistry.contains(state.getTypeId()));
        final String contentTypeIcon = (String) iconsRegistry.getContentTypeIcon(state.getTypeId(), state.getMimeType());
        setIcon(contentTypeIcon);
        view.setContentTitleVisible(true);
    }
}
