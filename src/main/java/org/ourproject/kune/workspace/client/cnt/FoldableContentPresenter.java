package org.ourproject.kune.workspace.client.cnt;

import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.listener.Listener;

public class FoldableContentPresenter extends AbstractContentPresenter {

    private final String toolName;
    private final ActionRegistry<StateToken> actionRegistry;
    private final Session session;
    private final ActionContentToolbar toolbar;

    public FoldableContentPresenter(final String toolName, StateManager stateManager, Session session,
            final ActionContentToolbar toolbar, ActionRegistry<StateToken> actionRegistry) {
        this.toolName = toolName;
        this.session = session;
        this.toolbar = toolbar;
        this.actionRegistry = actionRegistry;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(final StateAbstractDTO state) {
                setState(state);
            }
        });
    }

    public String getToolName() {
        return toolName;
    }

    protected void setState(StateAbstractDTO state) {
        toolbar.detach();
        if (state instanceof StateContainerDTO) {
            StateContainerDTO stateCntCtx = (StateContainerDTO) state;
            if (stateCntCtx.getToolName().equals(toolName)) {
                // This tool
                if (stateCntCtx instanceof StateContentDTO) {
                    setState((StateContentDTO) stateCntCtx);
                } else if (stateCntCtx instanceof StateContainerDTO) {
                    setState(stateCntCtx);
                }
            }
        }
    }

    protected void setState(StateContainerDTO state) {
        ActionItemCollection<StateToken> collection = actionRegistry.getCurrentActions(state.getStateToken(),
                state.getTypeId(), session.isLogged(), state.getContainerRights(), true);
        setToolbar(collection);
        attach();
    }

    protected void setState(StateContentDTO state) {
    }

    private void setToolbar(ActionItemCollection<StateToken> collection) {
        toolbar.disableMenusAndClearButtons();
        toolbar.setActions(collection);
        toolbar.attach();
    }

}
