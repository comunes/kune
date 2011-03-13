package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsCurrentStateAPersonCondition implements GuiAddCondition {

    private final Session session;

    @Inject
    public IsCurrentStateAPersonCondition(final Session session) {
        this.session = session;
    }

    @Override
    public boolean mustBeAdded(final GuiActionDescrip descr) {
        return session.isCurrentStateAPerson();
    }
}
