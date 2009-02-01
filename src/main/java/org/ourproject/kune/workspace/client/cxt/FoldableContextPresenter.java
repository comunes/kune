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
package org.ourproject.kune.workspace.client.cxt;

import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public abstract class FoldableContextPresenter {
    private final Provider<ContextNavigator> contextNavigatorProvider;
    protected final String toolname;
    private final Provider<ContextPropEditor> contextPropEditorProvider;

    public FoldableContextPresenter(final String toolname, final StateManager stateManager,
            final Provider<ContextNavigator> contextNavigatorProvider) {
        this(toolname, stateManager, contextNavigatorProvider, null);
    }

    public FoldableContextPresenter(final String toolname, final StateManager stateManager,
            final Provider<ContextNavigator> contextNavigatorProvider,
            Provider<ContextPropEditor> contextPropEditorProvider) {
        this.toolname = toolname;
        this.contextNavigatorProvider = contextNavigatorProvider;
        this.contextPropEditorProvider = contextPropEditorProvider;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                if (state instanceof StateContainerDTO) {
                    StateContainerDTO stateCntCtx = (StateContainerDTO) state;
                    if (toolname.equals(stateCntCtx.getToolName())) {
                        setState(stateCntCtx);
                        contextNavigatorProvider.get().attach();
                    }
                } else {
                    detach();
                }
            }
        });
    }

    protected void detach() {
        contextNavigatorProvider.get().detach();
        contextNavigatorProvider.get().clear();
        if (contextPropEditorProvider != null) {
            contextPropEditorProvider.get().detach();
            contextPropEditorProvider.get().clear();
        }
    }

    protected void setState(StateContainerDTO state) {
        contextNavigatorProvider.get().setState(state, true);
        if (contextPropEditorProvider != null && state instanceof StateContentDTO) {
            contextPropEditorProvider.get().setState((StateContentDTO) state);
        }
    }
}
