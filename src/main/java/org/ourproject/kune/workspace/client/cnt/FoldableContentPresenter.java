package org.ourproject.kune.workspace.client.cnt;

import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.StateContainerDTO;
import org.ourproject.kune.platf.client.dto.StateContentDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.listener.Listener;

public abstract class FoldableContentPresenter extends AbstractContentPresenter implements FoldableContent {

    private final String toolName;
    private final ActionRegistry<StateToken> actionRegistry;
    protected final Session session;
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

    public void refreshState() {
        setState(session.getContentState());
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
        ActionItemCollection<StateToken> collection = getActionCollection(state, state.getContainerRights());
        setToolbar(collection);
        attach();
    }

    protected void setState(StateContentDTO state) {
        ActionItemCollection<StateToken> collection = getActionCollection(state, state.getContentRights());
        setToolbar(collection);
    }

    private ActionItemCollection<StateToken> getActionCollection(StateContainerDTO state, AccessRightsDTO rights) {
        return actionRegistry.getCurrentActions(state.getStateToken(), state.getTypeId(), session.isLogged(), rights,
                true);
    }

    private void setToolbar(ActionItemCollection<StateToken> collection) {
        toolbar.disableMenusAndClearButtons();
        toolbar.setActions(collection);
        toolbar.attach();
    }

}
