package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;

import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot2;

public class EntityLogoPresenter implements EntityLogo {
    private EntityLogoView view;

    public EntityLogoPresenter(final StateManager stateManager, final WsThemePresenter theme) {
	stateManager.onStateChanged(new Slot<StateDTO>() {
	    public void onEvent(final StateDTO state) {
		final GroupDTO group = state.getGroup();
		final boolean isAdmin = state.getGroupRights().isAdministrable();

		view.setLogo(group.getLongName());
		view.setPutYourLogoVisible(isAdmin);
	    }
	});
	theme.onThemeChanged(new Slot2<WsTheme, WsTheme>() {
	    public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
		view.setTheme(oldTheme, newTheme);
	    }
	});
    }

    public void init(final EntityLogoView view) {
	this.view = view;

    }

}
