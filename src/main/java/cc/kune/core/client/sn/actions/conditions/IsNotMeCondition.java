package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsNotMeCondition implements GuiAddCondition {

    private final Session session;

    @Inject
    public IsNotMeCondition(final Session session) {
        this.session = session;
    }

    private boolean isNotThisGroup(final GuiActionDescrip descr) {
        return descr.getTarget() instanceof GroupDTO
                && !session.getCurrentUser().getShortName().equals(((GroupDTO) descr.getTarget()).getShortName());
    }

    private boolean isNotThisPerson(final GuiActionDescrip descr) {
        return (!session.getCurrentUser().getShortName().equals(((UserSimpleDTO) descr.getTarget()).getShortName()));
    }

    @Override
    public boolean mustBeAdded(final GuiActionDescrip descr) {
        if (descr.getTarget() instanceof UserSimpleDTO) {
            return isNotThisPerson(descr);
        }
        return (session.isNotLogged() || (session.isLogged() && isNotThisGroup(descr)));
    }
}
