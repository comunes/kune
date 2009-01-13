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
