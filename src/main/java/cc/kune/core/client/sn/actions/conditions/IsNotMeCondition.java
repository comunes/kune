package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.common.client.errors.UIException;
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

    private String currentName() {
        return session.getCurrentUser().getShortName();
    }

    private boolean isNotThisGroup(final GuiActionDescrip descr) {
        final String targetName = ((GroupDTO) descr.getTarget()).getShortName();
        final String currentName = currentName();
        return !currentName.equals(targetName);
    }

    private boolean isNotThisPerson(final GuiActionDescrip descr) {
        final String targetName = ((UserSimpleDTO) descr.getTarget()).getShortName();
        final String currentName = currentName();
        return !currentName.equals(targetName);
    }

    @Override
    public boolean mustBeAdded(final GuiActionDescrip descr) {
        if (session.isNotLogged()) {
            return true;
        }
        if (descr.getTarget() instanceof UserSimpleDTO) {
            return isNotThisPerson(descr);
        } else if (descr.getTarget() instanceof GroupDTO) {
            return isNotThisGroup(descr);
        } else {
            throw new UIException("Unsupported target");
        }
    }
}
