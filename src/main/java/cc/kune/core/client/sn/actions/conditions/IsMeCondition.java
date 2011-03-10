package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsMeCondition extends IsNotMeCondition {

    @Inject
    public IsMeCondition(final Session session) {
        super(session);
    }

    @Override
    public boolean mustBeAdded(final GuiActionDescrip descr) {
        return !super.mustBeAdded(descr);
    }
}
