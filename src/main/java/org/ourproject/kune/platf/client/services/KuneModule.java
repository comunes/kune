/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ContentActionRegistry;
import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.actions.DragDropContentRegistry;
import org.ourproject.kune.platf.client.actions.GroupActionRegistry;
import org.ourproject.kune.platf.client.actions.UserActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPresenter;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationComponentGroup;
import org.ourproject.kune.platf.client.app.ApplicationDefault;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.app.HistoryWrapperDefault;
import org.ourproject.kune.platf.client.app.ToolGroup;
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
import org.ourproject.kune.platf.client.state.ContentProviderDefault;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.SessionDefault;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.state.StateManagerDefault;
import org.ourproject.kune.platf.client.tool.ToolSelector;
import org.ourproject.kune.platf.client.tool.ToolSelectorPresenter;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigatorPanel;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigatorPresenter;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorPanel;
import org.ourproject.kune.workspace.client.editor.TextEditorPresenter;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogo;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoSelector;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoSelectorPanel;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogoSelectorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslator;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorView;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.i18n.LanguageSelector;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorPresenter;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorView;
import org.ourproject.kune.workspace.client.i18n.ui.I18nTranslatorPanel;
import org.ourproject.kune.workspace.client.i18n.ui.LanguageSelectorPanel;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoose;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoosePanel;
import org.ourproject.kune.workspace.client.licensechoose.LicenseChoosePresenter;
import org.ourproject.kune.workspace.client.newgroup.NewGroup;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPanel;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.workspace.client.search.EntityLiveSearcherView;
import org.ourproject.kune.workspace.client.search.GroupLiveSearchPanel;
import org.ourproject.kune.workspace.client.search.GroupLiveSearcher;
import org.ourproject.kune.workspace.client.search.GroupLiveSearcherPresenter;
import org.ourproject.kune.workspace.client.search.SiteSearcher;
import org.ourproject.kune.workspace.client.search.SiteSearcherPanel;
import org.ourproject.kune.workspace.client.search.SiteSearcherPresenter;
import org.ourproject.kune.workspace.client.search.SiteSearcherView;
import org.ourproject.kune.workspace.client.search.UserLiveSearcher;
import org.ourproject.kune.workspace.client.search.UserLiveSearcherPanel;
import org.ourproject.kune.workspace.client.search.UserLiveSearcherPresenter;
import org.ourproject.kune.workspace.client.signin.Register;
import org.ourproject.kune.workspace.client.signin.RegisterPanel;
import org.ourproject.kune.workspace.client.signin.RegisterPresenter;
import org.ourproject.kune.workspace.client.signin.RegisterView;
import org.ourproject.kune.workspace.client.signin.SignIn;
import org.ourproject.kune.workspace.client.signin.SignInPanel;
import org.ourproject.kune.workspace.client.signin.SignInPresenter;
import org.ourproject.kune.workspace.client.signin.SignInView;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.site.rpc.UserService;
import org.ourproject.kune.workspace.client.site.rpc.UserServiceAsync;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.title.EntityTitle;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class KuneModule extends AbstractModule {

    @Override
    public void onInstall() {
        register(Singleton.class, new Factory<Session>(Session.class) {
            @Override
            public Session create() {
                return new SessionDefault(Cookies.getCookie(Site.USERHASH), $$(UserServiceAsync.class));
            }
        }, new Factory<I18nServiceAsync>(I18nServiceAsync.class) {
            @Override
            public I18nServiceAsync create() {
                final I18nServiceAsync service = (I18nServiceAsync) GWT.create(I18nService.class);
                ((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "I18nService");
                return service;
            }
        }, new Factory<UserServiceAsync>(UserServiceAsync.class) {
            @Override
            public UserServiceAsync create() {
                final UserServiceAsync service = (UserServiceAsync) GWT.create(UserService.class);
                ((ServiceDefTarget) service).setServiceEntryPoint(GWT.getModuleBaseURL() + "UserService");
                return service;
            }
        }, new Factory<SocialNetworkServiceAsync>(SocialNetworkServiceAsync.class) {
            @Override
            public SocialNetworkServiceAsync create() {
                final SocialNetworkServiceAsync snServiceAsync = (SocialNetworkServiceAsync) GWT.create(SocialNetworkService.class);
                ((ServiceDefTarget) snServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL()
                        + "SocialNetworkService");
                return snServiceAsync;
            }
        }, new Factory<GroupServiceAsync>(GroupServiceAsync.class) {
            @Override
            public GroupServiceAsync create() {
                final GroupServiceAsync groupServiceAsync = (GroupServiceAsync) GWT.create(GroupService.class);
                ((ServiceDefTarget) groupServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL() + "GroupService");
                return groupServiceAsync;
            }
        }, new Factory<ContentServiceAsync>(ContentServiceAsync.class) {
            @Override
            public ContentServiceAsync create() {
                final ContentServiceAsync contentServiceAsync = (ContentServiceAsync) GWT.create(ContentService.class);
                ((ServiceDefTarget) contentServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL() + "ContentService");
                return contentServiceAsync;
            }
        });

        register(Singleton.class, new Factory<I18nUITranslationService>(I18nUITranslationService.class) {
            @Override
            public I18nUITranslationService create() {
                final I18nUITranslationService i18n = new I18nUITranslationService();
                i18n.init($(I18nServiceAsync.class), $(Session.class), new Listener0() {
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

        if (container.hasProvider(I18nTranslationService.class)) {
            container.removeProvider(I18nTranslationService.class);
        }

        register(Singleton.class, new Factory<I18nTranslationService>(I18nTranslationService.class) {
            @Override
            public I18nTranslationService create() {
                return i18n;
            }
        });

        register(Singleton.class, new Factory<HistoryWrapper>(HistoryWrapper.class) {
            @Override
            public HistoryWrapper create() {
                return new HistoryWrapperDefault();
            }
        }, new Factory<ContentProvider>(ContentProvider.class) {
            @Override
            public ContentProvider create() {
                return new ContentProviderDefault($(ContentServiceAsync.class));
            }
        }, new Factory<StateManager>(StateManager.class) {
            @Override
            public StateManager create() {
                final StateManagerDefault stateManager = new StateManagerDefault($(ContentProvider.class),
                        $(Session.class), $(HistoryWrapper.class));
                History.addHistoryListener(stateManager);
                return stateManager;
            }
        });

        register(Singleton.class, new Factory<KuneErrorHandler>(KuneErrorHandler.class) {
            @Override
            public KuneErrorHandler create() {
                return new KuneErrorHandler($(Session.class), i18n, $$(WorkspaceSkeleton.class), $$(StateManager.class));
            }
        });

        register(Singleton.class, new Factory<Images>(Images.class) {
            @Override
            public Images create() {
                return Images.App.getInstance();
            }
        }, new Factory<ImageUtils>(ImageUtils.class) {
            @Override
            public ImageUtils create() {
                return new ImageUtils();
            }
        });

        AsyncCallbackSimple.init($(KuneErrorHandler.class));

        register(Singleton.class, new Factory<Application>(Application.class) {
            @Override
            public Application create() {
                return new ApplicationDefault($(Session.class));
            }

            @Override
            public void onAfterCreated(final Application instance) {
            }
        });

        register(Singleton.class, new Factory<SiteSearcher>(SiteSearcher.class) {
            @Override
            public SiteSearcher create() {
                final SiteSearcherPresenter presenter = new SiteSearcherPresenter($$(StateManager.class));
                final SiteSearcherView view = new SiteSearcherPanel(presenter, i18n, $(WorkspaceSkeleton.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<I18nTranslator>(I18nTranslator.class) {
            @Override
            public I18nTranslator create() {
                final I18nTranslatorPresenter presenter = new I18nTranslatorPresenter($(Session.class),
                        $(I18nServiceAsync.class), i18n);
                final I18nTranslatorView view = new I18nTranslatorPanel(presenter, i18n, $(LanguageSelector.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<SignIn>(SignIn.class) {
            @Override
            public SignIn create() {
                final SignInPresenter presenter = new SignInPresenter($(Session.class), $(StateManager.class), i18n,
                        $$(UserServiceAsync.class), $$(Register.class));
                final SignInView panel = new SignInPanel(presenter, i18n, $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<Register>(Register.class) {
            @Override
            public Register create() {
                final RegisterPresenter presenter = new RegisterPresenter($(Session.class), $(StateManager.class),
                        i18n, $$(UserServiceAsync.class), $$(SignIn.class));
                final RegisterView panel = new RegisterPanel(presenter, i18n, $(WorkspaceSkeleton.class),
                        $(Session.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<LicenseChoose>(LicenseChoose.class) {
            @Override
            public LicenseChoose create() {
                final LicenseChoosePresenter presenter = new LicenseChoosePresenter($(Session.class));
                final LicenseChoosePanel view = new LicenseChoosePanel(presenter, i18n);
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<NewGroup>(NewGroup.class) {
            @Override
            public NewGroup create() {
                final NewGroupPresenter presenter = new NewGroupPresenter(i18n, $(Session.class),
                        $(StateManager.class), $$(GroupServiceAsync.class));
                final NewGroupPanel view = new NewGroupPanel(presenter, i18n, $$(LicenseChoose.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<UserLiveSearcher>(UserLiveSearcher.class) {
            @Override
            public UserLiveSearcher create() {
                final UserLiveSearcherPresenter presenter = new UserLiveSearcherPresenter();
                final EntityLiveSearcherView view = new UserLiveSearcherPanel(presenter, i18n);
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<GroupLiveSearcher>(GroupLiveSearcher.class) {
            @Override
            public GroupLiveSearcher create() {
                final GroupLiveSearcherPresenter presenter = new GroupLiveSearcherPresenter();
                final EntityLiveSearcherView view = new GroupLiveSearchPanel(presenter, i18n);
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ToolSelector>(ToolSelector.class) {
            @Override
            public ToolSelector create() {
                final ToolSelectorPresenter presenter = new ToolSelectorPresenter($(StateManager.class),
                        $(WsThemePresenter.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<TextEditor>(TextEditor.class) {
            @Override
            public TextEditor create() {
                final ActionToolbarPanel<StateToken> contentNavigatorToolbar = new ActionToolbarPanel<StateToken>(
                        ActionToolbarPanel.Position.content, $$(ActionManager.class), $(WorkspaceSkeleton.class));
                final ActionToolbar<StateToken> toolbar = new ActionToolbarPresenter<StateToken>(
                        contentNavigatorToolbar);

                final TextEditorPresenter presenter = new TextEditorPresenter(true, toolbar,
                        $(I18nUITranslationService.class));
                final TextEditorPanel panel = new TextEditorPanel(presenter, $(I18nTranslationService.class),
                        $(WorkspaceSkeleton.class), $(ColorWebSafePalette.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<LanguageSelector>(LanguageSelector.class) {
            @Override
            public LanguageSelector create() {
                final LanguageSelectorPresenter presenter = new LanguageSelectorPresenter($(Session.class));
                final LanguageSelectorView view = new LanguageSelectorPanel(presenter, i18n);
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ActionManager>(ActionManager.class) {
            @Override
            public ActionManager create() {
                return new ActionManager($(WorkspaceSkeleton.class));
            }
        });

        register(Singleton.class, new Factory<ContextActionRegistry>(ContextActionRegistry.class) {
            @Override
            public ContextActionRegistry create() {
                return new ContextActionRegistry();
            }
        });

        register(Singleton.class, new Factory<ContentActionRegistry>(ContentActionRegistry.class) {
            @Override
            public ContentActionRegistry create() {
                return new ContentActionRegistry();
            }
        });

        register(Singleton.class, new Factory<GroupActionRegistry>(GroupActionRegistry.class) {
            @Override
            public GroupActionRegistry create() {
                return new GroupActionRegistry();
            }
        });

        register(Singleton.class, new Factory<UserActionRegistry>(UserActionRegistry.class) {
            @Override
            public UserActionRegistry create() {
                return new UserActionRegistry();
            }
        });

        register(Singleton.class, new Factory<DragDropContentRegistry>(DragDropContentRegistry.class) {
            @Override
            public DragDropContentRegistry create() {
                return new DragDropContentRegistry();
            }
        });

        register(Singleton.class, new Factory<ContentIconsRegistry>(ContentIconsRegistry.class) {
            @Override
            public ContentIconsRegistry create() {
                return new ContentIconsRegistry();
            }
        });

        register(Singleton.class, new Factory<ContextNavigator>(ContextNavigator.class) {
            @Override
            public ContextNavigator create() {
                final ActionToolbarPanel<StateToken> contextNavigatorToolbar = new ActionToolbarPanel<StateToken>(
                        ActionToolbarPanel.Position.context, $$(ActionManager.class), $(WorkspaceSkeleton.class));
                final ActionToolbar<StateToken> toolbar = new ActionToolbarPresenter<StateToken>(
                        contextNavigatorToolbar);

                final ContextNavigatorPresenter presenter = new ContextNavigatorPresenter($(StateManager.class),
                        $(Session.class), $$(ContentServiceAsync.class), i18n, $(EntityTitle.class),
                        $(ContentIconsRegistry.class), $(DragDropContentRegistry.class), toolbar,
                        $(ContextActionRegistry.class), $$(FileDownloadUtils.class), true);
                final ContextNavigatorPanel panel = new ContextNavigatorPanel(presenter, i18n,
                        $(WorkspaceSkeleton.class), $(ActionManager.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<FileDownloadUtils>(FileDownloadUtils.class) {
            @Override
            public FileDownloadUtils create() {
                return new FileDownloadUtils($(Session.class));
            }
        });

        // Register of tokens like "signin", "newgroup", "translate" etcetera
        $(StateManager.class).addSiteToken(SiteToken.signin.toString(), new Listener<StateToken>() {
            public void onEvent(final StateToken previousStateToken) {
                $(SignIn.class).doSignIn(previousStateToken);
            }
        });

        $(StateManager.class).addSiteToken(SiteToken.register.toString(), new Listener<StateToken>() {
            public void onEvent(final StateToken previousStateToken) {
                $(Register.class).doRegister(previousStateToken);
            }
        });

        $(StateManager.class).addSiteToken(SiteToken.newgroup.toString(), new Listener<StateToken>() {
            public void onEvent(final StateToken previousStateToken) {
                $(NewGroup.class).doNewGroup(previousStateToken);
            }
        });

        $(StateManager.class).addSiteToken(SiteToken.translate.toString(), new Listener<StateToken>() {
            public void onEvent(final StateToken previousStateToken) {
                $(I18nTranslator.class).doShowTranslator();
            }
        });

        register(Singleton.class, new Factory<EntityLogoSelector>(EntityLogoSelector.class) {
            @Override
            public EntityLogoSelector create() {
                final EntityLogoSelectorPresenter presenter = new EntityLogoSelectorPresenter($(Session.class),
                        $(EntityLogo.class));
                final EntityLogoSelectorPanel panel = new EntityLogoSelectorPanel(presenter,
                        $(WorkspaceSkeleton.class), $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        $(ApplicationComponentGroup.class).createAll();
        $(ToolGroup.class).createAll();
        $(Application.class).start();
    }
}
