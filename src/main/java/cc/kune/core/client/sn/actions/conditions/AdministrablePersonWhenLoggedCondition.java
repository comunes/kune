package cc.kune.core.client.sn.actions.conditions;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiAddCondition;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.GroupDTO;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AdministrablePersonWhenLoggedCondition implements GuiAddCondition {

    private final Session session;

    @Inject
    public AdministrablePersonWhenLoggedCondition(final Session session) {
        this.session = session;
    }

    @Override
    public boolean mustBeAdded(final GuiActionDescrip descr) {
        return (((GroupDTO) descr.getItem()).isPersonal() && session.isLogged() && session.getContentState().getGroupRights().isAdministrable());
    }
}
