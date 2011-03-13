package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.StateAbstractDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsCurrentStateAdministrableCondition implements GuiAddCondition {

    private final Session session;

    @Inject
    public IsCurrentStateAdministrableCondition(final Session session) {
        this.session = session;
    }

    @Override
    public boolean mustBeAdded(final GuiActionDescrip descr) {
        final StateAbstractDTO currentState = session.getCurrentState();
        if (currentState instanceof StateContentDTO) {
            return ((StateContentDTO) currentState).getGroupRights().isAdministrable();
        } else {
            // session.getContainerState() instanceof StateContentDTO)
            return ((StateContainerDTO) currentState).getGroupRights().isAdministrable();
        }
    }
}
