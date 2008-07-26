package org.ourproject.kune.platf.client.services;

import java.util.HashMap;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationDefault;
import org.ourproject.kune.platf.client.app.HistoryWrapperImpl;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetsManager;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.state.ContentProviderImpl;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.SessionImpl;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.state.StateManagerDefault;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
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
import org.ourproject.kune.workspace.client.ui.newtmp.WorkspaceManager;
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

import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.modules.Module;
import com.calclab.suco.client.modules.ModuleBuilder;
import com.calclab.suco.client.scopes.SingletonScope;
import com.calclab.suco.client.signal.Slot;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class KuneModule implements Module {
    private final I18nLanguageDTO initialLang;
    private final HashMap<String, String> lexicon;
    private ModuleBuilder builder;

    public KuneModule(final I18nLanguageDTO initialLang, final HashMap<String, String> lexicon) {
	this.initialLang = initialLang;
	this.lexicon = lexicon;
    }

    public Class<? extends Module> getType() {
	return KuneModule.class;
    }

    public void onLoad(final ModuleBuilder builder) {
	this.builder = builder;

	builder.registerProvider(Kune.class, new Provider<Kune>() {
	    public Kune get() {
		return new Kune(builder);
	    }
	}, SingletonScope.class);

	builder.registerProvider(I18nUITranslationService.class, new Provider<I18nUITranslationService>() {
	    public I18nUITranslationService get() {
		final I18nUITranslationService i18n = new I18nUITranslationService();
		i18n.setCurrentLanguage(initialLang.getCode());
		i18n.setLexicon(lexicon);
		return i18n;
	    }
	}, SingletonScope.class);

	builder.registerProvider(UserServiceAsync.class, new Provider<UserServiceAsync>() {
	    public UserServiceAsync get() {
		final UserServiceAsync service = (UserServiceAsync) GWT.create(UserService.class);
		((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "UserService");
		return service;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SocialNetworkServiceAsync.class, new Provider<SocialNetworkServiceAsync>() {
	    public SocialNetworkServiceAsync get() {
		final SocialNetworkServiceAsync snServiceAsync = (SocialNetworkServiceAsync) GWT
			.create(SocialNetworkService.class);
		((ServiceDefTarget) snServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL()
			+ "SocialNetworkService");
		return snServiceAsync;
	    }
	}, SingletonScope.class);

	builder.registerProvider(GroupServiceAsync.class, new Provider<GroupServiceAsync>() {
	    public GroupServiceAsync get() {
		final GroupServiceAsync groupServiceAsync = (GroupServiceAsync) GWT.create(GroupService.class);
		((ServiceDefTarget) groupServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL() + "GroupService");
		return groupServiceAsync;
	    }
	}, SingletonScope.class);

	builder.registerProvider(Session.class, new Provider<Session>() {
	    public Session get() {
		return new SessionImpl(Cookies.getCookie(Site.USERHASH), initialLang, p(UserServiceAsync.class));
	    }
	}, SingletonScope.class);

	builder.registerProvider(Images.class, new Provider<Images>() {
	    public Images get() {
		return Images.App.getInstance();
	    }
	}, SingletonScope.class);

	builder.registerProvider(ImageUtils.class, new Provider<ImageUtils>() {
	    public ImageUtils get() {
		return new ImageUtils();
	    }
	}, SingletonScope.class);

	final I18nUITranslationService i18n = i(I18nUITranslationService.class);
	builder.registerProvider(KuneErrorHandler.class, new Provider<KuneErrorHandler>() {
	    public KuneErrorHandler get() {
		return new KuneErrorHandler(i(Session.class), i18n, p(WorkspaceSkeleton.class));
	    }
	}, SingletonScope.class);

	builder.registerProvider(WorkspaceSkeleton.class, new Provider<WorkspaceSkeleton>() {
	    public WorkspaceSkeleton get() {
		return new WorkspaceSkeleton();
	    }
	}, SingletonScope.class);

	final KuneErrorHandler errorHandler = i(KuneErrorHandler.class);
	AsyncCallbackSimple.init(errorHandler);

	builder.registerProvider(KunePlatform.class, new Provider<KunePlatform>() {
	    public KunePlatform get() {
		return new KunePlatform();
	    }
	}, SingletonScope.class);

	builder.registerProvider(ExtensibleWidgetsManager.class, new Provider<ExtensibleWidgetsManager>() {
	    public ExtensibleWidgetsManager get() {
		return new ExtensibleWidgetsManager();
	    }
	}, SingletonScope.class);

	final WorkspaceSkeleton ws = i(WorkspaceSkeleton.class);
	final Images images = i(Images.class);

	builder.registerProvider(Application.class, new Provider<Application>() {
	    public Application get() {
		final Session session = i(Session.class);
		final ExtensibleWidgetsManager extensionPointManager = i(ExtensibleWidgetsManager.class);
		return new ApplicationDefault(session, extensionPointManager, i18n, i(KuneErrorHandler.class), ws);
	    }
	}, SingletonScope.class);

	builder.registerProvider(SitePublicSpaceLink.class, new Provider<SitePublicSpaceLink>() {
	    public SitePublicSpaceLink get() {
		final SitePublicSpaceLinkPresenter presenter = new SitePublicSpaceLinkPresenter();
		final SitePublicSpaceLinkPanel panel = new SitePublicSpaceLinkPanel(presenter, ws, i18n, images);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteProgress.class, new Provider<SiteProgress>() {
	    public SiteProgress get() {
		final SiteProgressPresenter presenter = new SiteProgressPresenter();
		final SiteProgressPanel panel = new SiteProgressPanel(presenter, p(SitePublicSpaceLink.class));
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(Site.class, new Provider<Site>() {
	    public Site get() {
		return new Site(i18n, i(SiteProgress.class), p(SiteMessage.class));
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteMessage.class, new Provider<SiteMessage>() {
	    public SiteMessage get() {
		final SiteMessagePresenter siteMessagePresenter = new SiteMessagePresenter();
		final SiteMessageView siteMessageView = new SiteMessagePanel(siteMessagePresenter, true, i18n);
		siteMessagePresenter.init(siteMessageView);
		return siteMessagePresenter;
	    }
	}, SingletonScope.class);

	i(SiteProgress.class);
	i(Site.class);

	builder.registerProvider(SiteSignInLink.class, new Provider<SiteSignInLink>() {
	    public SiteSignInLink get() {
		final SiteSignInLinkPresenter presenter = new SiteSignInLinkPresenter(i(Session.class));
		final SiteSignInLinkPanel panel = new SiteSignInLinkPanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteSignOutLink.class, new Provider<SiteSignOutLink>() {
	    public SiteSignOutLink get() {
		final SiteSignOutLinkPresenter presenter = new SiteSignOutLinkPresenter(i(Session.class),
			p(UserServiceAsync.class), p(KuneErrorHandler.class));
		final SiteSignOutLinkPanel panel = new SiteSignOutLinkPanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteNewGroupLink.class, new Provider<SiteNewGroupLink>() {
	    public SiteNewGroupLink get() {
		final SiteNewGroupLinkPresenter presenter = new SiteNewGroupLinkPresenter();
		final SiteNewGroupLinkPanel panel = new SiteNewGroupLinkPanel(presenter, ws, i18n);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteOptions.class, new Provider<SiteOptions>() {
	    public SiteOptions get() {
		final SiteOptionsPresenter presenter = new SiteOptionsPresenter();
		final SiteOptionsPanel panel = new SiteOptionsPanel(presenter, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteLogo.class, new Provider<SiteLogo>() {
	    public SiteLogo get() {
		final SiteLogoPresenter presenter = new SiteLogoPresenter(i(Session.class));
		final SiteLogoPanel panel = new SiteLogoPanel(presenter, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteSearcher.class, new Provider<SiteSearcher>() {
	    public SiteSearcher get() {
		final SiteSearcherPresenter presenter = new SiteSearcherPresenter();
		final SiteSearcherView view = new SiteSearcherPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteSearch.class, new Provider<SiteSearch>() {
	    public SiteSearch get() {
		final SiteSearchPresenter presenter = new SiteSearchPresenter(p(SiteSearcher.class));
		final SiteSearchPanel panel = new SiteSearchPanel(presenter, ws, i18n);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SignIn.class, new Provider<SignIn>() {
	    public SignIn get() {
		final SignInPresenter presenter = new SignInPresenter(i(Session.class), i(StateManager.class), i18n,
			i(UserServiceAsync.class));
		final SignInPanel view = new SignInPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(LicenseChoose.class, new Provider<LicenseChoose>() {
	    public LicenseChoose get() {
		final LicenseChoosePresenter presenter = new LicenseChoosePresenter(i(Session.class));
		final LicenseChoosePanel view = new LicenseChoosePanel(presenter, i18n);
		presenter.init(view);
		return presenter;
	    }
	});

	builder.registerProvider(NewGroup.class, new Provider<NewGroup>() {
	    public NewGroup get() {
		final NewGroupPresenter presenter = new NewGroupPresenter(i18n, i(Session.class),
			i(StateManager.class), p(GroupServiceAsync.class));
		final NewGroupPanel view = new NewGroupPanel(presenter, i18n, p(LicenseChoose.class));
		presenter.init(view);
		return presenter;
	    }
	}, SingletonScope.class);

	i(SitePublicSpaceLink.class);
	i(SiteMessage.class);
	i(SiteSignInLink.class);
	i(SiteSignOutLink.class);
	i(SiteNewGroupLink.class);
	i(SiteSearch.class);
	i(SiteLogo.class);

	builder.registerProvider(EntityLogo.class, new Provider<EntityLogo>() {
	    public EntityLogo get() {
		return new EntityLogoPanel(i18n, ws);
	    }
	}, SingletonScope.class);

	builder.registerProvider(WsThemePresenter.class, new Provider<WsThemePresenter>() {
	    public WsThemePresenter get() {
		final WsThemePresenter presenter = new WsThemePresenter(i(Session.class), p(GroupServiceAsync.class));
		final WsThemePanel panel = new WsThemePanel(ws, presenter, i18n);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(EntityTitlePresenter.class, new Provider<EntityTitlePresenter>() {
	    public EntityTitlePresenter get() {
		final EntityTitlePresenter presenter = new EntityTitlePresenter(i18n, i(KuneErrorHandler.class));
		final EntityTitlePanel panel = new EntityTitlePanel(ws, presenter);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(EntitySubTitlePresenter.class, new Provider<EntitySubTitlePresenter>() {
	    public EntitySubTitlePresenter get() {
		final EntitySubTitlePresenter presenter = new EntitySubTitlePresenter(i18n);
		final EntitySubTitlePanel panel = new EntitySubTitlePanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(EntityLicensePresenter.class, new Provider<EntityLicensePresenter>() {
	    public EntityLicensePresenter get() {
		final EntityLicensePresenter presenter = new EntityLicensePresenter();
		final EntityLicensePanel panel = new EntityLicensePanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(WorkspaceManager.class, new Provider<WorkspaceManager>() {
	    public WorkspaceManager get() {
		final WorkspaceManager presenter = new WorkspaceManager(i(SitePublicSpaceLink.class),
			i(EntityLogo.class), i(EntityTitlePresenter.class), i(EntitySubTitlePresenter.class),
			i(WsThemePresenter.class), i(EntityLicensePresenter.class), p(GroupMembersSummary.class),
			p(ParticipationSummary.class), p(TagsSummary.class), p(GroupSummary.class));
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(StateManager.class, new Provider<StateManager>() {
	    public StateManager get() {
		final StateManagerDefault stateManager = new StateManagerDefault(new ContentProviderImpl(
			ContentService.App.getInstance()), i(Session.class), new HistoryWrapperImpl(),
			i(WorkspaceManager.class));
		History.addHistoryListener(stateManager);
		return stateManager;
	    }
	}, SingletonScope.class);

	builder.registerProvider(UserLiveSearcher.class, new Provider<UserLiveSearcher>() {
	    public UserLiveSearcher get() {
		final UserLiveSearcherPresenter presenter = new UserLiveSearcherPresenter();
		final EntityLiveSearcherView view = new UserLiveSearcherPanel(presenter, i18n);
		presenter.init(view);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(GroupLiveSearcher.class, new Provider<GroupLiveSearcher>() {
	    public GroupLiveSearcher get() {
		final GroupLiveSearcherPresenter presenter = new GroupLiveSearcherPresenter();
		final EntityLiveSearcherView view = new GroupLiveSearchPanel(presenter, i18n);
		presenter.init(view);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(GroupMembersSummary.class, new Provider<GroupMembersSummary>() {
	    public GroupMembersSummary get() {
		final GroupMembersSummaryPresenter presenter = new GroupMembersSummaryPresenter(i18n,
			p(StateManager.class), i(ImageUtils.class), i(Session.class),
			p(SocialNetworkServiceAsync.class), p(GroupLiveSearcher.class));
		final GroupMembersSummaryView view = new GroupMembersSummaryPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(GroupSummary.class, new Provider<GroupSummary>() {
	    public GroupSummary get() {
		final GroupSummaryPresenter presenter = new GroupSummaryPresenter();
		final GroupSummaryView view = new GroupSummaryPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(ParticipationSummary.class, new Provider<ParticipationSummary>() {
	    public ParticipationSummary get() {
		final ParticipationSummaryPresenter presenter = new ParticipationSummaryPresenter(i18n,
			p(StateManager.class), i(ImageUtils.class), i(Session.class),
			p(SocialNetworkServiceAsync.class));
		final ParticipationSummaryView view = new ParticipationSummaryPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(TagsSummary.class, new Provider<TagsSummary>() {
	    public TagsSummary get() {
		final TagsSummaryPresenter presenter = new TagsSummaryPresenter(p(Session.class), p(SiteSearcher.class));
		final TagsSummaryPanel panel = new TagsSummaryPanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	// Register of tokens like "signin", "newgroup", "translate" etcetera
	i(StateManager.class).addSiteToken(SiteToken.signin.toString(), new Slot<StateToken>() {
	    public void onEvent(final StateToken previousStateToken) {
		i(SignIn.class).doSignIn(previousStateToken);
	    }
	});

	i(StateManager.class).addSiteToken(SiteToken.newgroup.toString(), new Slot<StateToken>() {
	    public void onEvent(final StateToken previousStateToken) {
		i(NewGroup.class).doNewGroup(previousStateToken);
	    }
	});

    }

    private <T> T i(final Class<T> componentType) {
	return builder.getInstance(componentType);
    }

    private <T> Provider<T> p(final Class<T> componentKey) {
	return builder.getProvider(componentKey);
    }

}
