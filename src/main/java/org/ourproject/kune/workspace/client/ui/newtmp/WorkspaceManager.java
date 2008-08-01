package org.ourproject.kune.workspace.client.ui.newtmp;

import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.platf.client.ui.rate.RatePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.licensefoot.EntityLicensePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntitySubTitlePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntityTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.GroupMembersSummary;
import org.ourproject.kune.workspace.client.workspace.GroupSummary;
import org.ourproject.kune.workspace.client.workspace.ParticipationSummary;
import org.ourproject.kune.workspace.client.workspace.TagsSummary;
import org.ourproject.kune.workspace.client.workspace.ui.EntityLogo;

import com.calclab.suco.client.container.Provider;

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
    private final Provider<RateIt> rateItProvider;
    private final Provider<RatePresenter> ratePresenterProvider;

    public WorkspaceManager(final SitePublicSpaceLink publicSpaceLink, final EntityLogo entityLogo,
	    final EntityTitlePresenter entityTitlePresenter, final EntitySubTitlePresenter entitySubTitlePresenter,
	    final WsThemePresenter wsThemePresenter, final EntityLicensePresenter entityLicensePresenter,
	    final Provider<GroupMembersSummary> groupMembersSummaryProvider,
	    final Provider<ParticipationSummary> participationSummaryProvider,
	    final Provider<TagsSummary> tagsSummaryProvider, final Provider<GroupSummary> groupSummaryProvider,
	    final Provider<RateIt> rateItProvider, final Provider<RatePresenter> rateProvider) {
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
	this.rateItProvider = rateItProvider;
	this.ratePresenterProvider = rateProvider;
    }

}
