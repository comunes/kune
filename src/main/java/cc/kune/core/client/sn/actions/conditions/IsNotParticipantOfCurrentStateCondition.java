package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.StateContentDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsNotParticipantOfCurrentStateCondition implements GuiAddCondition {

    private final Session session;

    @Inject
    public IsNotParticipantOfCurrentStateCondition(final Session session) {
        this.session = session;
    }

    @Override
    public boolean mustBeAdded(final GuiActionDescrip descr) {
        final StateContentDTO currentState = session.getContentState();
        return !currentState.isParticipant();
    }
}
