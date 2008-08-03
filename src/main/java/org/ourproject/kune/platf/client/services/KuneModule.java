package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.chat.client.ChatClientNewModule;
import org.ourproject.kune.docs.client.DocumentClientNewModule;
import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationDefault;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.app.HistoryWrapperDefault;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.state.ContentProvider;
import org.ourproject.kune.platf.client.state.ContentProviderImpl;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.SessionImpl;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.state.StateManagerDefault;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.platf.client.tool.ToolSelectorPanel;
import org.ourproject.kune.platf.client.tool.ToolSelectorPresenter;
import org.ourproject.kune.platf.client.ui.QuickTipsHelper;
import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.platf.client.ui.rate.RateItPanel;
import org.ourproject.kune.platf.client.ui.rate.RateItPresenter;
import org.ourproject.kune.platf.client.ui.rate.RatePanel;
import org.ourproject.kune.platf.client.ui.rate.RatePresenter;
import org.ourproject.kune.workspace.client.WorkspaceClientModule;
import org.ourproject.kune.workspace.client.i18n.I18nTranslator;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorView;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.i18n.ui.I18nTranslatorPanel;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoose;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoosePanel;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoosePresenter;
import org.ourproject.kune.workspace.client.newgroup.NewGroup;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.workspace.client.newgroup.ui.NewGroupPanel;
import org.ourproject.kune.workspace.client.presence.ui.GroupSummaryPanel;
import org.ourproject.kune.workspace.client.search.SiteSearcher;
import org.ourproject.kune.workspace.client.search.SiteSearcherPanel;
import org.ourproject.kune.workspace.client.search.SiteSearcherPresenter;
import org.ourproject.kune.workspace.client.search.SiteSearcherView;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.sitebar.SiteToken;
import org.ourproject.kune.workspace.client.sitebar.login.SignIn;
import org.ourproject.kune.workspace.client.sitebar.login.SignInPanel;
import org.ourproject.kune.workspace.client.sitebar.login.SignInPresenter;
import org.ourproject.kune.workspace.client.sitebar.msg.SiteMessage;
import org.ourproject.kune.workspace.client.sitebar.msg.SiteMessagePanel;
import org.ourproject.kune.workspace.client.sitebar.msg.SiteMessagePresenter;
import org.ourproject.kune.workspace.client.sitebar.msg.SiteMessageView;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserService;
import org.ourproject.kune.workspace.client.sitebar.rpc.UserServiceAsync;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearcherView;
import org.ourproject.kune.workspace.client.socialnet.GroupLiveSearcherPresenter;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummaryPanel;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummaryPresenter;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersSummaryView;
import org.ourproject.kune.workspace.client.socialnet.ParticipationSummaryPresenter;
import org.ourproject.kune.workspace.client.socialnet.ParticipationSummaryView;
import org.ourproject.kune.workspace.client.socialnet.UserLiveSearcherPresenter;
import org.ourproject.kune.workspace.client.socialnet.ui.GroupLiveSearchPanel;
import org.ourproject.kune.workspace.client.socialnet.ui.ParticipationSummaryPanel;
import org.ourproject.kune.workspace.client.socialnet.ui.UserLiveSearcherPanel;
import org.ourproject.kune.workspace.client.summary.GroupSummaryPresenter;
import org.ourproject.kune.workspace.client.summary.GroupSummaryView;
import org.ourproject.kune.workspace.client.tags.TagsSummaryPanel;
import org.ourproject.kune.workspace.client.tags.TagsSummaryPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.licensefoot.EntityLicensePanel;
import org.ourproject.kune.workspace.client.ui.newtmp.licensefoot.EntityLicensePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitelogo.SiteLogo;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitelogo.SiteLogoPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitelogo.SiteLogoPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitenewgroup.SiteNewGroupLink;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitenewgroup.SiteNewGroupLinkPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitenewgroup.SiteNewGroupLinkPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteoptions.SiteOptions;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteoptions.SiteOptionsPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteoptions.SiteOptionsPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteprogress.SiteProgress;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteprogress.SiteProgressPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteprogress.SiteProgressPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitepublic.SitePublicSpaceLink;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitepublic.SitePublicSpaceLinkPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitepublic.SitePublicSpaceLinkPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesearch.SiteSearch;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesearch.SiteSearchPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesearch.SiteSearchPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign.SiteSignInLink;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign.SiteSignInLinkPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign.SiteSignInLinkPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign.SiteSignOutLink;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign.SiteSignOutLinkPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesign.SiteSignOutLinkPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteusermenu.SiteUserMenu;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteusermenu.SiteUserMenuPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.siteusermenu.SiteUserMenuPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePanel;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntitySubTitlePanel;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntitySubTitlePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntityTitlePanel;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntityTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.GroupLiveSearcher;
import org.ourproject.kune.workspace.client.workspace.GroupMembersSummary;
import org.ourproject.kune.workspace.client.workspace.GroupSummary;
import org.ourproject.kune.workspace.client.workspace.ParticipationSummary;
import org.ourproject.kune.workspace.client.workspace.TagsSummary;
import org.ourproject.kune.workspace.client.workspace.UserLiveSearcher;
import org.ourproject.kune.workspace.client.workspace.ui.EntityLogo;
import org.ourproject.kune.workspace.client.workspace.ui.EntityLogoPanel;
import org.ourproject.kune.workspace.client.workspace.ui.EntityLogoPresenter;

import com.calclab.emiteuimodule.client.EmiteUIModule;
import com.calclab.suco.client.container.Container;
import com.calclab.suco.client.modules.AbstractModule;
import com.calclab.suco.client.provider.Factory;
import com.calclab.suco.client.scopes.SingletonScope;
import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot0;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class KuneModule extends AbstractModule {
    public KuneModule() {
	super(KuneModule.class);
    }

    @Override
    public void onLoad() {
	register(SingletonScope.class, new Factory<Kune>(Kune.class) {
	    public Kune create() {
		return new Kune($(Container.class));
	    }
	});

	register(SingletonScope.class, new Factory<Session>(Session.class) {
	    public Session create() {
		return new SessionImpl(Cookies.getCookie(Site.USERHASH), $p(UserServiceAsync.class));
	    }
	}, new Factory<I18nServiceAsync>(I18nServiceAsync.class) {
	    public I18nServiceAsync create() {
		final I18nServiceAsync service = (I18nServiceAsync) GWT.create(I18nService.class);
		((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "I18nService");
		return service;
	    }
	}, new Factory<UserServiceAsync>(UserServiceAsync.class) {
	    public UserServiceAsync create() {
		final UserServiceAsync service = (UserServiceAsync) GWT.create(UserService.class);
		((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "UserService");
		return service;
	    }
	}, new Factory<SocialNetworkServiceAsync>(SocialNetworkServiceAsync.class) {
	    public SocialNetworkServiceAsync create() {
		final SocialNetworkServiceAsync snServiceAsync = (SocialNetworkServiceAsync) GWT
			.create(SocialNetworkService.class);
		((ServiceDefTarget) snServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL()
			+ "SocialNetworkService");
		return snServiceAsync;
	    }
	}, new Factory<GroupServiceAsync>(GroupServiceAsync.class) {
	    public GroupServiceAsync create() {
		final GroupServiceAsync groupServiceAsync = (GroupServiceAsync) GWT.create(GroupService.class);
		((ServiceDefTarget) groupServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL() + "GroupService");
		return groupServiceAsync;
	    }
	}, new Factory<ContentServiceAsync>(ContentServiceAsync.class) {
	    public ContentServiceAsync create() {
		// FIXME
		return ContentService.App.getInstance();
	    }
	});

	register(SingletonScope.class, new Factory<I18nUITranslationService>(I18nUITranslationService.class) {
	    public I18nUITranslationService create() {
		final I18nUITranslationService i18n = new I18nUITranslationService();
		i18n.init($(I18nServiceAsync.class), $(Session.class), new Slot0() {
		    public void onEvent() {
			onI18nReady();
		    }
		});
		return i18n;
	    }
	});

	$(I18nUITranslationService.class);
    }

    private void onI18nReady() {
	final I18nUITranslationService i18n = $(I18nUITranslationService.class);

	register(SingletonScope.class, new Factory<I18nTranslationService>(I18nTranslationService.class) {
	    public I18nTranslationService create() {
		return i18n;
	    }
	});

	register(SingletonScope.class, new Factory<QuickTipsHelper>(QuickTipsHelper.class) {
	    public QuickTipsHelper create() {
		return new QuickTipsHelper();
	    }
	});

	$(QuickTipsHelper.class);

	register(SingletonScope.class, new Factory<HistoryWrapper>(HistoryWrapper.class) {
	    public HistoryWrapper create() {
		return new HistoryWrapperDefault();
	    }
	}, new Factory<ContentProvider>(ContentProvider.class) {
	    public ContentProvider create() {
		return new ContentProviderImpl($(ContentServiceAsync.class));
	    }
	}, new Factory<StateManager>(StateManager.class) {
	    public StateManager create() {
		final StateManagerDefault stateManager = new StateManagerDefault($(ContentProvider.class),
			$(Session.class), $(HistoryWrapper.class));
		History.addHistoryListener(stateManager);
		return stateManager;
	    }
	});

	register(SingletonScope.class, new Factory<KuneErrorHandler>(KuneErrorHandler.class) {
	    public KuneErrorHandler create() {
		return new KuneErrorHandler($(Session.class), i18n, $p(WorkspaceSkeleton.class), $p(StateManager.class));
	    }
	});

	register(SingletonScope.class, new Factory<Images>(Images.class) {
	    public Images create() {
		return Images.App.getInstance();
	    }
	}, new Factory<ImageUtils>(ImageUtils.class) {
	    public ImageUtils create() {
		return new ImageUtils();
	    }
	});

	register(SingletonScope.class, new Factory<WorkspaceSkeleton>(WorkspaceSkeleton.class) {
	    public WorkspaceSkeleton create() {
		return new WorkspaceSkeleton();
	    }
	});

	final KuneErrorHandler errorHandler = $(KuneErrorHandler.class);
	AsyncCallbackSimple.init(errorHandler);

	register(SingletonScope.class, new Factory<KunePlatform>(KunePlatform.class) {
	    public KunePlatform create() {
		return new KunePlatform();
	    }
	});

	final WorkspaceSkeleton ws = $(WorkspaceSkeleton.class);
	final Images images = $(Images.class);

	register(SingletonScope.class, new Factory<Application>(Application.class) {
	    public Application create() {
		final Session session = $(Session.class);
		return new ApplicationDefault(session, $(KuneErrorHandler.class), ws);
	    }
	});

	register(SingletonScope.class, new Factory<SitePublicSpaceLink>(SitePublicSpaceLink.class) {
	    public SitePublicSpaceLink create() {
		final SitePublicSpaceLinkPresenter presenter = new SitePublicSpaceLinkPresenter($(StateManager.class));
		final SitePublicSpaceLinkPanel panel = new SitePublicSpaceLinkPanel(presenter, ws, i18n, images);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<SiteProgress>(SiteProgress.class) {
	    public SiteProgress create() {
		final SiteProgressPresenter presenter = new SiteProgressPresenter();
		final SiteProgressPanel panel = new SiteProgressPanel(presenter, $p(SitePublicSpaceLink.class));
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<Site>(Site.class) {
	    public Site create() {
		return new Site(i18n, $(SiteProgress.class), $p(SiteMessage.class));
	    }
	});

	register(SingletonScope.class, new Factory<SiteMessage>(SiteMessage.class) {
	    public SiteMessage create() {
		final SiteMessagePresenter siteMessagePresenter = new SiteMessagePresenter();
		final SiteMessageView siteMessageView = new SiteMessagePanel(siteMessagePresenter, true, i18n);
		siteMessagePresenter.init(siteMessageView);
		return siteMessagePresenter;
	    }
	});

	$(SiteProgress.class);
	$(Site.class);

	register(SingletonScope.class, new Factory<SiteSignInLink>(SiteSignInLink.class) {
	    public SiteSignInLink create() {
		final SiteSignInLinkPresenter presenter = new SiteSignInLinkPresenter($(Session.class));
		final SiteSignInLinkPanel panel = new SiteSignInLinkPanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<SiteUserMenu>(SiteUserMenu.class) {
	    public SiteUserMenu create() {
		final SiteUserMenuPresenter presenter = new SiteUserMenuPresenter($(Session.class));
		final SiteUserMenuPanel panel = new SiteUserMenuPanel(presenter, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<SiteSignOutLink>(SiteSignOutLink.class) {
	    public SiteSignOutLink create() {
		final SiteSignOutLinkPresenter presenter = new SiteSignOutLinkPresenter($(Session.class),
			$p(UserServiceAsync.class), $p(KuneErrorHandler.class));
		final SiteSignOutLinkPanel panel = new SiteSignOutLinkPanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<SiteNewGroupLink>(SiteNewGroupLink.class) {
	    public SiteNewGroupLink create() {
		final SiteNewGroupLinkPresenter presenter = new SiteNewGroupLinkPresenter();
		final SiteNewGroupLinkPanel panel = new SiteNewGroupLinkPanel(presenter, ws, i18n);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<SiteOptions>(SiteOptions.class) {
	    public SiteOptions create() {
		final SiteOptionsPresenter presenter = new SiteOptionsPresenter();
		final SiteOptionsPanel panel = new SiteOptionsPanel(presenter, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<SiteLogo>(SiteLogo.class) {
	    public SiteLogo create() {
		final SiteLogoPresenter presenter = new SiteLogoPresenter($(Session.class));
		final SiteLogoPanel panel = new SiteLogoPanel(presenter, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<SiteSearcher>(SiteSearcher.class) {
	    public SiteSearcher create() {
		final SiteSearcherPresenter presenter = new SiteSearcherPresenter($p(StateManager.class));
		final SiteSearcherView view = new SiteSearcherPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<I18nTranslator>(I18nTranslator.class) {
	    public I18nTranslator create() {
		final I18nTranslatorPresenter presenter = new I18nTranslatorPresenter($(Session.class),
			$(I18nServiceAsync.class), i18n);
		final I18nTranslatorView view = new I18nTranslatorPanel(presenter, i18n);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<SiteSearch>(SiteSearch.class) {
	    public SiteSearch create() {
		final SiteSearchPresenter presenter = new SiteSearchPresenter($p(SiteSearcher.class));
		final SiteSearchPanel panel = new SiteSearchPanel(presenter, ws, i18n);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<SignIn>(SignIn.class) {
	    public SignIn create() {
		final SignInPresenter presenter = new SignInPresenter($(Session.class), $(StateManager.class), i18n,
			$(UserServiceAsync.class));
		final SignInPanel view = new SignInPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<LicenseChoose>(LicenseChoose.class) {
	    public LicenseChoose create() {
		final LicenseChoosePresenter presenter = new LicenseChoosePresenter($(Session.class));
		final LicenseChoosePanel view = new LicenseChoosePanel(presenter, i18n);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<NewGroup>(NewGroup.class) {
	    public NewGroup create() {
		final NewGroupPresenter presenter = new NewGroupPresenter(i18n, $(Session.class),
			$(StateManager.class), $p(GroupServiceAsync.class));
		final NewGroupPanel view = new NewGroupPanel(presenter, i18n, $p(LicenseChoose.class));
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<EntityLogo>(EntityLogo.class) {
	    public EntityLogo create() {
		final EntityLogoPresenter presenter = new EntityLogoPresenter($(StateManager.class),
			$(WsThemePresenter.class));
		final EntityLogoPanel panel = new EntityLogoPanel(i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<WsThemePresenter>(WsThemePresenter.class) {
	    public WsThemePresenter create() {
		final WsThemePresenter presenter = new WsThemePresenter($(Session.class), $p(GroupServiceAsync.class),
			$(StateManager.class));
		final WsThemePanel panel = new WsThemePanel(ws, presenter, i18n);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<EntityTitlePresenter>(EntityTitlePresenter.class) {
	    public EntityTitlePresenter create() {
		final EntityTitlePresenter presenter = new EntityTitlePresenter(i18n, $(KuneErrorHandler.class),
			$(StateManager.class));
		final EntityTitlePanel panel = new EntityTitlePanel(ws, presenter);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<EntitySubTitlePresenter>(EntitySubTitlePresenter.class) {
	    public EntitySubTitlePresenter create() {
		final EntitySubTitlePresenter presenter = new EntitySubTitlePresenter(i18n, $(StateManager.class));
		final EntitySubTitlePanel panel = new EntitySubTitlePanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<EntityLicensePresenter>(EntityLicensePresenter.class) {
	    public EntityLicensePresenter create() {
		final EntityLicensePresenter presenter = new EntityLicensePresenter($(StateManager.class));
		final EntityLicensePanel panel = new EntityLicensePanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<RatePresenter>(RatePresenter.class) {
	    public RatePresenter create() {
		final RatePresenter presenter = new RatePresenter($(StateManager.class));
		final RatePanel panel = new RatePanel(null, null, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<RateIt>(RateIt.class) {
	    public RateIt create() {
		final RateItPresenter presenter = new RateItPresenter(i18n, $(Session.class),
			$p(ContentServiceAsync.class), $(StateManager.class));
		final RateItPanel panel = new RateItPanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<GroupMembersSummary>(GroupMembersSummary.class) {
	    public GroupMembersSummary create() {
		final GroupMembersSummaryPresenter presenter = new GroupMembersSummaryPresenter(i18n,
			$(StateManager.class), $(ImageUtils.class), $(Session.class),
			$p(SocialNetworkServiceAsync.class), $p(UserLiveSearcher.class), $(WsThemePresenter.class));
		final GroupMembersSummaryView view = new GroupMembersSummaryPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<GroupSummary>(GroupSummary.class) {
	    public GroupSummary create() {
		final GroupSummaryPresenter presenter = new GroupSummaryPresenter($(StateManager.class),
			$(WsThemePresenter.class));
		final GroupSummaryView view = new GroupSummaryPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<ParticipationSummary>(ParticipationSummary.class) {
	    public ParticipationSummary create() {
		final ParticipationSummaryPresenter presenter = new ParticipationSummaryPresenter(i18n,
			$(StateManager.class), $(ImageUtils.class), $(Session.class),
			$p(SocialNetworkServiceAsync.class), $(WsThemePresenter.class));
		final ParticipationSummaryView view = new ParticipationSummaryPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<TagsSummary>(TagsSummary.class) {
	    public TagsSummary create() {
		final TagsSummaryPresenter presenter = new TagsSummaryPresenter($p(Session.class),
			$p(SiteSearcher.class), $(StateManager.class), $(WsThemePresenter.class));
		final TagsSummaryPanel panel = new TagsSummaryPanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<UserLiveSearcher>(UserLiveSearcher.class) {
	    public UserLiveSearcher create() {
		final UserLiveSearcherPresenter presenter = new UserLiveSearcherPresenter();
		final EntityLiveSearcherView view = new UserLiveSearcherPanel(presenter, i18n);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<GroupLiveSearcher>(GroupLiveSearcher.class) {
	    public GroupLiveSearcher create() {
		final GroupLiveSearcherPresenter presenter = new GroupLiveSearcherPresenter();
		final EntityLiveSearcherView view = new GroupLiveSearchPanel(presenter, i18n);
		presenter.init(view);
		return presenter;
	    }
	});

	register(SingletonScope.class, new Factory<ToolSelector>(ToolSelector.class) {
	    public ToolSelector create() {
		final ToolSelectorPresenter presenter = new ToolSelectorPresenter($(StateManager.class),
			$(WsThemePresenter.class));
		final ToolSelectorPanel panel = new ToolSelectorPanel(presenter, ws);
		presenter.init(panel);
		return presenter;
	    }
	});

	load(new EmiteUIModule(), new DocumentClientNewModule(), new ChatClientNewModule());

	$(SitePublicSpaceLink.class);
	$(SiteMessage.class);
	$(SiteUserMenu.class);
	$(SiteSignInLink.class);
	$(SiteSignOutLink.class);
	$(SiteNewGroupLink.class);
	$(SiteSearch.class);
	$(SiteLogo.class);

	$(EntityLogo.class);
	$(EntityTitlePresenter.class);
	$(EntitySubTitlePresenter.class);
	$(WsThemePresenter.class);
	$(EntityLicensePresenter.class);
	$(RateIt.class);
	$(RatePresenter.class);

	$(GroupMembersSummary.class);
	$(ParticipationSummary.class);
	$(TagsSummary.class);
	$(GroupSummary.class);

	// Register of tokens like "signin", "newgroup", "translate" etcetera
	$(StateManager.class).addSiteToken(SiteToken.signin.toString(), new Slot<StateToken>() {
	    public void onEvent(final StateToken previousStateToken) {
		$(SignIn.class).doSignIn(previousStateToken);
	    }
	});

	$(StateManager.class).addSiteToken(SiteToken.newgroup.toString(), new Slot<StateToken>() {
	    public void onEvent(final StateToken previousStateToken) {
		$(NewGroup.class).doNewGroup(previousStateToken);
	    }
	});

	final KunePlatform platform = $(KunePlatform.class);
	platform.install(new WorkspaceClientModule($(Session.class), $(StateManager.class), $(Application.class)
		.getWorkspace(), i18n));
	$(Application.class).init($(StateManager.class), platform.getIndexedTools());
	$(Application.class).subscribeActions(platform.getActions());
	// $(Application.class).getWorkspace().attachTools(platform.getTools().iterator());
	$(Application.class).start();

    }
}
