package org.ourproject.kune.workspace.client.ui.newtmp;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.platf.client.ui.rate.RatePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.licensefoot.EntityLicensePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsTheme;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntitySubTitlePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntityTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.GroupMembersSummary;
import org.ourproject.kune.workspace.client.workspace.GroupSummary;
import org.ourproject.kune.workspace.client.workspace.ParticipationSummary;
import org.ourproject.kune.workspace.client.workspace.TagsSummary;
import org.ourproject.kune.workspace.client.workspace.ui.EntityLogo;

import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.signal.Slot2;

public class WorkspaceManager {

    private final EntityLogo entityLogo;
    private final EntityTitlePresenter entityTitlePresenter;
    private final WsThemePresenter wsThemePresenter;
    private final EntityLicensePresenter entityLicensePresenter;
    private final EntitySubTitlePresenter entitySubTitlePresenter;
    private final Provider<TagsSummary> tagsProvider;
    private final Provider<GroupMembersSummary> groupMembersSummaryProvider;
    private final Provider<ParticipationSummary> participationSummaryProvider;
    private final Provider<GroupSummary> groupSummaryProvider;
    private final SitePublicSpaceLink publicSpaceLink;
    private final RateIt rateIt;
    private final RatePresenter ratePresenter;

    public WorkspaceManager(final SitePublicSpaceLink publicSpaceLink, final EntityLogo entityLogo,
	    final EntityTitlePresenter entityTitlePresenter, final EntitySubTitlePresenter entitySubTitlePresenter,
	    final WsThemePresenter wsThemePresenter, final EntityLicensePresenter entityLicensePresenter,
	    final Provider<GroupMembersSummary> groupMembersSummaryProvider,
	    final Provider<ParticipationSummary> participationSummaryProvider,
	    final Provider<TagsSummary> tagsSummaryProvider, final Provider<GroupSummary> groupSummaryProvider,
	    final RateIt rateIt, final RatePresenter ratePresenter) {
	this.publicSpaceLink = publicSpaceLink;
	this.entityLogo = entityLogo;
	this.entityTitlePresenter = entityTitlePresenter;
	this.entitySubTitlePresenter = entitySubTitlePresenter;
	this.entityLicensePresenter = entityLicensePresenter;
	this.wsThemePresenter = wsThemePresenter;
	this.groupMembersSummaryProvider = groupMembersSummaryProvider;
	this.participationSummaryProvider = participationSummaryProvider;
	this.tagsProvider = tagsSummaryProvider;
	this.groupSummaryProvider = groupSummaryProvider;
	this.rateIt = rateIt;
	this.ratePresenter = ratePresenter;
	wsThemePresenter.onThemeChanged(new Slot2<WsTheme, WsTheme>() {
	    public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
		entityLogo.setTheme(oldTheme, newTheme);
		groupMembersSummaryProvider.get().setTheme(oldTheme, newTheme);
		participationSummaryProvider.get().setTheme(oldTheme, newTheme);
		groupSummaryProvider.get().setTheme(oldTheme, newTheme);
		tagsSummaryProvider.get().setTheme(oldTheme, newTheme);
	    }
	});
    }

    public void setSocialNetwork(final StateDTO state) {
	groupMembersSummaryProvider.get().setState(state);
	participationSummaryProvider.get().setState(state);
    }

    public void setState(final StateDTO state) {
	publicSpaceLink.setState(state);
	final GroupDTO group = state.getGroup();
	final boolean isAdmin = state.getGroupRights().isAdministrable();
	entityLogo.setLogo(group.getLongName());
	entityLogo.setPutYourLogoVisible(isAdmin);
	entityTitlePresenter.setState(state);
	entitySubTitlePresenter.setState(state);
	entityLicensePresenter.setLicense(state);
	setSocialNetwork(state);
	groupSummaryProvider.get().setState(state);
	tagsProvider.get().setState(state);
	wsThemePresenter.setState(state);
	rateIt.setState(state);
	ratePresenter.setState(state);
    }

}
