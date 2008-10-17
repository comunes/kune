package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.chat.client.ChatClientTool;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.actions.UserActionRegistry;
import org.ourproject.kune.platf.client.app.ApplicationComponentGroup;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.QuickTipsHelper;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.platf.client.ui.rate.RateItPanel;
import org.ourproject.kune.platf.client.ui.rate.RateItPresenter;
import org.ourproject.kune.platf.client.ui.rate.RatePanel;
import org.ourproject.kune.platf.client.ui.rate.RatePresenter;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogo;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoPanel;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.licensefoot.EntityLicensePanel;
import org.ourproject.kune.workspace.client.licensefoot.EntityLicensePresenter;
import org.ourproject.kune.workspace.client.search.GroupLiveSearcher;
import org.ourproject.kune.workspace.client.search.SiteSearcher;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.site.msg.SiteMessage;
import org.ourproject.kune.workspace.client.site.msg.SiteMessagePanel;
import org.ourproject.kune.workspace.client.site.msg.SiteMessagePresenter;
import org.ourproject.kune.workspace.client.site.msg.SiteMessageView;
import org.ourproject.kune.workspace.client.site.rpc.UserServiceAsync;
import org.ourproject.kune.workspace.client.sitebar.sitelogo.SiteLogo;
import org.ourproject.kune.workspace.client.sitebar.sitelogo.SiteLogoPanel;
import org.ourproject.kune.workspace.client.sitebar.sitelogo.SiteLogoPresenter;
import org.ourproject.kune.workspace.client.sitebar.sitenewgroup.SiteNewGroupLink;
import org.ourproject.kune.workspace.client.sitebar.sitenewgroup.SiteNewGroupLinkPanel;
import org.ourproject.kune.workspace.client.sitebar.sitenewgroup.SiteNewGroupLinkPresenter;
import org.ourproject.kune.workspace.client.sitebar.siteoptions.SiteOptions;
import org.ourproject.kune.workspace.client.sitebar.siteoptions.SiteOptionsPanel;
import org.ourproject.kune.workspace.client.sitebar.siteoptions.SiteOptionsPresenter;
import org.ourproject.kune.workspace.client.sitebar.siteprogress.SiteProgress;
import org.ourproject.kune.workspace.client.sitebar.siteprogress.SiteProgressPanel;
import org.ourproject.kune.workspace.client.sitebar.siteprogress.SiteProgressPresenter;
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLinkPanel;
import org.ourproject.kune.workspace.client.sitebar.sitepublic.SitePublicSpaceLinkPresenter;
import org.ourproject.kune.workspace.client.sitebar.sitesearch.SiteSearch;
import org.ourproject.kune.workspace.client.sitebar.sitesearch.SiteSearchPanel;
import org.ourproject.kune.workspace.client.sitebar.sitesearch.SiteSearchPresenter;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignInLink;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignInLinkPanel;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignInLinkPresenter;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLink;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLinkPanel;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLinkPresenter;
import org.ourproject.kune.workspace.client.sitebar.siteusermenu.SiteUserMenu;
import org.ourproject.kune.workspace.client.sitebar.siteusermenu.SiteUserMenuPanel;
import org.ourproject.kune.workspace.client.sitebar.siteusermenu.SiteUserMenuPresenter;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.socialnet.BuddiesSummary;
import org.ourproject.kune.workspace.client.socialnet.BuddiesSummaryPanel;
import org.ourproject.kune.workspace.client.socialnet.BuddiesSummaryPresenter;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummary;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummaryPanel;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummaryPresenter;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummaryView;
import org.ourproject.kune.workspace.client.socialnet.ParticipationSummary;
import org.ourproject.kune.workspace.client.socialnet.ParticipationSummaryPanel;
import org.ourproject.kune.workspace.client.socialnet.ParticipationSummaryPresenter;
import org.ourproject.kune.workspace.client.socialnet.ParticipationSummaryView;
import org.ourproject.kune.workspace.client.summary.GroupSummary;
import org.ourproject.kune.workspace.client.summary.GroupSummaryPanel;
import org.ourproject.kune.workspace.client.summary.GroupSummaryPresenter;
import org.ourproject.kune.workspace.client.summary.GroupSummaryView;
import org.ourproject.kune.workspace.client.tags.TagsSummary;
import org.ourproject.kune.workspace.client.tags.TagsSummaryPanel;
import org.ourproject.kune.workspace.client.tags.TagsSummaryPresenter;
import org.ourproject.kune.workspace.client.themes.WsThemePanel;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.title.EntitySubTitle;
import org.ourproject.kune.workspace.client.title.EntitySubTitlePanel;
import org.ourproject.kune.workspace.client.title.EntitySubTitlePresenter;
import org.ourproject.kune.workspace.client.title.EntityTitle;
import org.ourproject.kune.workspace.client.title.EntityTitlePanel;
import org.ourproject.kune.workspace.client.title.EntityTitlePresenter;

import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;

public class KuneWorkspaceModule extends AbstractModule {
    @Override
    protected void onInstall() {

        register(ApplicationComponentGroup.class, new Factory<WorkspaceSkeleton>(WorkspaceSkeleton.class) {
            @Override
            public WorkspaceSkeleton create() {
                return new WorkspaceSkeleton();
            }
        });

        register(ApplicationComponentGroup.class, new Factory<QuickTipsHelper>(QuickTipsHelper.class) {
            @Override
            public QuickTipsHelper create() {
                return new QuickTipsHelper();
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteProgress>(SiteProgress.class) {
            @Override
            public SiteProgress create() {
                final SiteProgressPresenter presenter = new SiteProgressPresenter();
                final SiteProgressPanel panel = new SiteProgressPanel(presenter, $$(SitePublicSpaceLink.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<Site>(Site.class) {
            @Override
            public Site create() {
                return new Site($(I18nUITranslationService.class), $(SiteProgress.class), $$(SiteMessage.class));
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SitePublicSpaceLink>(SitePublicSpaceLink.class) {
            @Override
            public SitePublicSpaceLink create() {
                final SitePublicSpaceLinkPresenter presenter = new SitePublicSpaceLinkPresenter($(StateManager.class));
                final SitePublicSpaceLinkPanel panel = new SitePublicSpaceLinkPanel(presenter,
                        $(WorkspaceSkeleton.class), $(I18nUITranslationService.class), $(Images.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteMessage>(SiteMessage.class) {
            @Override
            public SiteMessage create() {
                final SiteMessagePresenter siteMessagePresenter = new SiteMessagePresenter();
                final SiteMessageView siteMessageView = new SiteMessagePanel(siteMessagePresenter, true,
                        $(I18nUITranslationService.class));
                siteMessagePresenter.init(siteMessageView);
                return siteMessagePresenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteUserMenu>(SiteUserMenu.class) {
            @Override
            public SiteUserMenu create() {
                final SiteUserMenuPresenter presenter = new SiteUserMenuPresenter($(Session.class),
                        $(StateManager.class));
                final SiteUserMenuPanel panel = new SiteUserMenuPanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nUITranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteSignInLink>(SiteSignInLink.class) {
            @Override
            public SiteSignInLink create() {
                final SiteSignInLinkPresenter presenter = new SiteSignInLinkPresenter($(Session.class));
                final SiteSignInLinkPanel panel = new SiteSignInLinkPanel(presenter, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteSignOutLink>(SiteSignOutLink.class) {
            @Override
            public SiteSignOutLink create() {
                final SiteSignOutLinkPresenter presenter = new SiteSignOutLinkPresenter($(Session.class),
                        $$(UserServiceAsync.class), $$(KuneErrorHandler.class));
                final SiteSignOutLinkPanel panel = new SiteSignOutLinkPanel(presenter,
                        $(I18nUITranslationService.class), $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteNewGroupLink>(SiteNewGroupLink.class) {
            @Override
            public SiteNewGroupLink create() {
                final SiteNewGroupLinkPresenter presenter = new SiteNewGroupLinkPresenter();
                final SiteNewGroupLinkPanel panel = new SiteNewGroupLinkPanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nUITranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteOptions>(SiteOptions.class) {
            @Override
            public SiteOptions create() {
                final SiteOptionsPresenter presenter = new SiteOptionsPresenter();
                final SiteOptionsPanel panel = new SiteOptionsPanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nUITranslationService.class), $$(I18nTranslator.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteSearch>(SiteSearch.class) {
            @Override
            public SiteSearch create() {
                final SiteSearchPresenter presenter = new SiteSearchPresenter($$(SiteSearcher.class));
                final SiteSearchPanel panel = new SiteSearchPanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nUITranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteLogo>(SiteLogo.class) {
            @Override
            public SiteLogo create() {
                final SiteLogoPresenter presenter = new SiteLogoPresenter($(Session.class));
                final SiteLogoPanel panel = new SiteLogoPanel(presenter, $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<EntityLogo>(EntityLogo.class) {
            @Override
            public EntityLogo create() {
                final EntityLogoPresenter presenter = new EntityLogoPresenter($(StateManager.class),
                        $(WsThemePresenter.class), $(Session.class));
                final EntityLogoPanel panel = new EntityLogoPanel($(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class), $$(FileDownloadUtils.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<WsThemePresenter>(WsThemePresenter.class) {
            @Override
            public WsThemePresenter create() {
                final WsThemePresenter presenter = new WsThemePresenter($(Session.class), $$(GroupServiceAsync.class),
                        $(StateManager.class));
                final WsThemePanel panel = new WsThemePanel($(WorkspaceSkeleton.class), presenter,
                        $(I18nUITranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<EntityTitle>(EntityTitle.class) {
            @Override
            public EntityTitle create() {
                final EntityTitlePresenter presenter = new EntityTitlePresenter($(I18nUITranslationService.class),
                        $(KuneErrorHandler.class), $(StateManager.class), $(Session.class),
                        $$(ContentServiceAsync.class), $$(ContextNavigator.class), $(ContentIconsRegistry.class));
                final EntityTitlePanel panel = new EntityTitlePanel($(WorkspaceSkeleton.class), presenter);
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<EntitySubTitle>(EntitySubTitle.class) {
            @Override
            public EntitySubTitle create() {
                final EntitySubTitlePresenter presenter = new EntitySubTitlePresenter(
                        $(I18nUITranslationService.class), $(StateManager.class), false);
                final EntitySubTitlePanel panel = new EntitySubTitlePanel(presenter, $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<EntityLicensePresenter>(EntityLicensePresenter.class) {
            @Override
            public EntityLicensePresenter create() {
                final EntityLicensePresenter presenter = new EntityLicensePresenter($(StateManager.class));
                final EntityLicensePanel panel = new EntityLicensePanel(presenter, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<RateIt>(RateIt.class) {
            @Override
            public RateIt create() {
                final RateItPresenter presenter = new RateItPresenter($(I18nUITranslationService.class),
                        $(Session.class), $$(ContentServiceAsync.class), $(StateManager.class));
                final RateItPanel panel = new RateItPanel(presenter, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<RatePresenter>(RatePresenter.class) {
            @Override
            public RatePresenter create() {
                final RatePresenter presenter = new RatePresenter($(StateManager.class));
                final RatePanel panel = new RatePanel(null, null, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<GroupMembersSummary>(GroupMembersSummary.class) {
            @Override
            public GroupMembersSummary create() {
                final GroupMembersSummaryPresenter presenter = new GroupMembersSummaryPresenter(
                        $(I18nUITranslationService.class), $(StateManager.class), $(ImageUtils.class),
                        $(Session.class), $$(SocialNetworkServiceAsync.class), $$(GroupLiveSearcher.class),
                        $(WsThemePresenter.class));
                final GroupMembersSummaryView view = new GroupMembersSummaryPanel(presenter,
                        $(I18nUITranslationService.class), $(WorkspaceSkeleton.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<GroupSummary>(GroupSummary.class) {
            @Override
            public GroupSummary create() {
                final GroupSummaryPresenter presenter = new GroupSummaryPresenter($(StateManager.class),
                        $(WsThemePresenter.class));
                final GroupSummaryView view = new GroupSummaryPanel(presenter, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<BuddiesSummary>(BuddiesSummary.class) {
            @Override
            public BuddiesSummary create() {
                final BuddiesSummaryPresenter presenter = new BuddiesSummaryPresenter($(StateManager.class),
                        $(Session.class), $(UserActionRegistry.class), $(I18nTranslationService.class),
                        $$(ChatClientTool.class));
                final BuddiesSummaryPanel panel = new BuddiesSummaryPanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class), $(ActionManager.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<ParticipationSummary>(ParticipationSummary.class) {
            @Override
            public ParticipationSummary create() {
                final ParticipationSummaryPresenter presenter = new ParticipationSummaryPresenter(
                        $(I18nUITranslationService.class), $(StateManager.class), $(ImageUtils.class),
                        $(Session.class), $$(SocialNetworkServiceAsync.class), $(WsThemePresenter.class));
                final ParticipationSummaryView view = new ParticipationSummaryPanel(presenter,
                        $(I18nUITranslationService.class), $(WorkspaceSkeleton.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<TagsSummary>(TagsSummary.class) {
            @Override
            public TagsSummary create() {
                final TagsSummaryPresenter presenter = new TagsSummaryPresenter($(Session.class),
                        $$(SiteSearcher.class), $(StateManager.class), $(WsThemePresenter.class));
                final TagsSummaryPanel panel = new TagsSummaryPanel(presenter, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

    }
}
