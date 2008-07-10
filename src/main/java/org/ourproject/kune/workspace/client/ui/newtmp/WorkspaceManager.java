package org.ourproject.kune.workspace.client.ui.newtmp;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.ui.newtmp.licensefoot.EntityLicensePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntitySubTitlePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntityTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.ui.EntityLogo;

import com.calclab.suco.client.signal.Slot2;

public class WorkspaceManager {

    private final EntityLogo entityLogo;
    private final EntityTitlePresenter entityTitlePresenter;
    private final WsThemePresenter wsThemePresenter;
    private final EntityLicensePresenter entityLicensePresenter;
    private final EntitySubTitlePresenter entitySubTitlePresenter;

    public WorkspaceManager(final EntityLogo entityLogo, final EntityTitlePresenter entityTitlePresenter,
	    final EntitySubTitlePresenter entitySubTitlePresenter, final WsThemePresenter wsThemePresenter,
	    final EntityLicensePresenter entityLicensePresenter) {
	this.entityLogo = entityLogo;
	this.entityTitlePresenter = entityTitlePresenter;
	this.entitySubTitlePresenter = entitySubTitlePresenter;
	this.entityLicensePresenter = entityLicensePresenter;
	this.wsThemePresenter = wsThemePresenter;
	wsThemePresenter.onThemeChanged(new Slot2<WsTheme, WsTheme>() {
	    public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
		entityLogo.setTheme(oldTheme, newTheme);
	    }
	});
    }

    public void setState(final StateDTO state) {
	final GroupDTO group = state.getGroup();
	final boolean isAdmin = state.getGroupRights().isAdministrable();
	entityLogo.setLogo(group.getLongName());
	entityLogo.setPutYourLogoVisible(isAdmin);
	entityTitlePresenter.setState(state);
	entitySubTitlePresenter.setState(state);
	entityLicensePresenter.setLicense(state);
	// Only for probes:
	wsThemePresenter.setVisible(true);
	wsThemePresenter.setTheme(new WsTheme(group.getWorkspaceTheme()));
    }

    public void setTheme(final WsTheme theme) {
    }
}
