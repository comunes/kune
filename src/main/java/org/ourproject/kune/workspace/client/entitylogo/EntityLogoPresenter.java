package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener2;

public class EntityLogoPresenter implements EntityLogo {
    private EntityLogoView view;
    private final Session session;

    public EntityLogoPresenter(final StateManager stateManager, final WsThemePresenter theme, final Session session) {
	this.session = session;
	stateManager.onGroupChanged(new Listener2<GroupDTO, GroupDTO>() {
	    public void onEvent(final GroupDTO oldGroup, final GroupDTO newGroup) {
		setGroupLogo(newGroup);
	    }
	});
	stateManager.onStateChanged(new Listener<StateDTO>() {
	    public void onEvent(final StateDTO state) {
		final boolean isAdmin = state.getGroupRights().isAdministrable();
		view.setPutYourLogoVisible(isAdmin);
	    }
	});
	theme.onThemeChanged(new Listener2<WsTheme, WsTheme>() {
	    public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
		view.setTheme(oldTheme, newTheme);
	    }
	});
    }

    public void init(final EntityLogoView view) {
	this.view = view;
    }

    public void refreshGroupLogo() {
	setGroupLogo(session.getCurrentState().getGroup());
    }

    private void setGroupLogo(final GroupDTO group) {
	final ContentSimpleDTO groupLogo = group.getGroupLogo();
	if (groupLogo != null) {
	    view.setLogo(groupLogo.getStateToken(), true);
	} else {
	    view.setLogo(group.getLongName());
	}
    }

}
