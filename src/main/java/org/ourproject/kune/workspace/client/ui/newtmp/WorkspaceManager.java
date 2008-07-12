package org.ourproject.kune.workspace.client.ui.newtmp;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.workspace.client.ui.newtmp.licensefoot.EntityLicensePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntitySubTitlePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntityTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.GroupMembersSummary;
import org.ourproject.kune.workspace.client.workspace.GroupSummary;
import org.ourproject.kune.workspace.client.workspace.ParticipationSummary;
import org.ourproject.kune.workspace.client.workspace.TagsSummary;
import org.ourproject.kune.workspace.client.workspace.ui.EntityLogo;

import com.calclab.suco.client.signal.Slot2;

public class WorkspaceManager {

    private final EntityLogo entityLogo;
    private final EntityTitlePresenter entityTitlePresenter;
    private final WsThemePresenter wsThemePresenter;
    private final EntityLicensePresenter entityLicensePresenter;
    private final EntitySubTitlePresenter entitySubTitlePresenter;
    private final TagsSummary tags;
    private final GroupMembersSummary groupMembersSummary;
    private final ParticipationSummary participationSummary;
    private final GroupSummary groupSummary;

    public WorkspaceManager(final EntityLogo entityLogo, final EntityTitlePresenter entityTitlePresenter,
	    final EntitySubTitlePresenter entitySubTitlePresenter, final WsThemePresenter wsThemePresenter,
	    final EntityLicensePresenter entityLicensePresenter, final GroupMembersSummary groupMembersSummary,
	    final ParticipationSummary participationSummary, final TagsSummary tags, final GroupSummary groupSummary) {
	this.entityLogo = entityLogo;
	this.entityTitlePresenter = entityTitlePresenter;
	this.entitySubTitlePresenter = entitySubTitlePresenter;
	this.entityLicensePresenter = entityLicensePresenter;
	this.wsThemePresenter = wsThemePresenter;
	this.groupMembersSummary = groupMembersSummary;
	this.participationSummary = participationSummary;
	this.tags = tags;
	this.groupSummary = groupSummary;
	wsThemePresenter.onThemeChanged(new Slot2<WsTheme, WsTheme>() {
	    public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
		entityLogo.setTheme(oldTheme, newTheme);
		groupMembersSummary.setTheme(oldTheme, newTheme);
		participationSummary.setTheme(oldTheme, newTheme);
		groupSummary.setTheme(oldTheme, newTheme);
		tags.setTheme(oldTheme, newTheme);
	    }
	});
    }

    public void setSocialNetwork(final StateDTO state) {
	groupMembersSummary.setState(state);
	participationSummary.setState(state);
    }

    public void setState(final StateDTO state) {
	final GroupDTO group = state.getGroup();
	final boolean isAdmin = state.getGroupRights().isAdministrable();
	entityLogo.setLogo(group.getLongName());
	entityLogo.setPutYourLogoVisible(isAdmin);
	entityTitlePresenter.setState(state);
	entitySubTitlePresenter.setState(state);
	entityLicensePresenter.setLicense(state);
	setSocialNetwork(state);
	groupSummary.setState(state);
	tags.setState(state);
	// Only for probes:
	wsThemePresenter.setVisible(true);
	wsThemePresenter.setTheme(new WsTheme(group.getWorkspaceTheme()));
    }

}
