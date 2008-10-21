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

package org.ourproject.kune.docs.client.ctx;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;

public class DocumentContextPresenter implements DocumentContext {
    private DocumentContextView view;
    private final Provider<ContextNavigator> contextNavigatorProvider;
    private final Provider<AdminContext> adminContextProvider;

    public DocumentContextPresenter(final StateManager stateManager,
            final Provider<ContextNavigator> contextNavigatorProvider, final Provider<AdminContext> adminContextProvider) {
        this.contextNavigatorProvider = contextNavigatorProvider;
        this.adminContextProvider = adminContextProvider;
        stateManager.onStateChanged(new Listener<StateDTO>() {
            public void onEvent(final StateDTO state) {
                if (DocumentClientTool.NAME.equals(state.getToolName())) {
                    setState(state);
                }
            }
        });
    }

    public void init(final DocumentContextView view) {
        this.view = view;
    }

    public void showAdmin() {
        final AdminContext adminContext = adminContextProvider.get();
        view.setContainer(adminContext.getView());
    }

    public void showFolders() {
        view.setContainer(contextNavigatorProvider.get().getView());
    }

    private void setState(final StateDTO state) {
        contextNavigatorProvider.get().setState(state, true);
        adminContextProvider.get().setState(state);
        showFolders();
    }
}
