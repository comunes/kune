/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationComponentGroup;
import org.ourproject.kune.platf.client.app.ApplicationDefault;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.app.HistoryWrapperDefault;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.I18nService;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.rpc.UserService;
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.state.ContentProvider;
import org.ourproject.kune.platf.client.state.ContentProviderDefault;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.SessionDefault;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.state.StateManagerDefault;
import org.ourproject.kune.platf.client.ui.QuickTipsHelper;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalettePanel;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalettePresenter;
import org.ourproject.kune.platf.client.ui.rte.RTEActionSndToolbar;
import org.ourproject.kune.platf.client.ui.rte.RTEActionSndToolbarPresenter;
import org.ourproject.kune.platf.client.ui.rte.RTEActionTopToolbar;
import org.ourproject.kune.platf.client.ui.rte.RTEActionTopToolbarPresenter;
import org.ourproject.kune.platf.client.ui.rte.RTESavingEditor;
import org.ourproject.kune.platf.client.ui.rte.RTESavingEditorPresenter;
import org.ourproject.kune.platf.client.ui.rte.RTEditor;
import org.ourproject.kune.platf.client.ui.rte.RTEditorPanel;
import org.ourproject.kune.platf.client.ui.rte.RTEditorPresenter;
import org.ourproject.kune.platf.client.ui.rte.TestRTEDialog;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElement;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLink;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.decorator.NoDecoration;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.client.StyleInjector;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class KunePlatformModule extends AbstractModule {

    @Override
    protected void onInstall() {

        register(Singleton.class, new Factory<Session>(Session.class) {
            @Override
            public Session create() {
                return new SessionDefault(Cookies.getCookie(Session.USERHASH), $$(UserServiceAsync.class));
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

        register(Singleton.class, new Factory<FileDownloadUtils>(FileDownloadUtils.class) {
            @Override
            public FileDownloadUtils create() {
                return new FileDownloadUtils($(Session.class), $(ImageUtils.class));
            }
        });

        register(Singleton.class, new Factory<QuickTipsHelper>(QuickTipsHelper.class) {
            @Override
            public QuickTipsHelper create() {
                return new QuickTipsHelper();
            }
        });

        $(I18nUITranslationService.class);
        $(QuickTipsHelper.class);

    }

    private void onI18nReady() {
        final I18nUITranslationService i18n = $(I18nUITranslationService.class);

        if (container.hasProvider(I18nTranslationService.class)) {
            container.removeProvider(I18nTranslationService.class);
        }

        register(Singleton.class, new Factory<Resources>(Resources.class) {
            @Override
            public Resources create() {
                return new Resources(i18n);
            }
        });

        $(Resources.class);

        register(Singleton.class, new Factory<I18nTranslationService>(I18nTranslationService.class) {
            @Override
            public I18nTranslationService create() {
                return i18n;
            }
        });

        register(Singleton.class, new Factory<KuneErrorHandler>(KuneErrorHandler.class) {
            @Override
            public KuneErrorHandler create() {
                return new KuneErrorHandler($(Session.class), i18n, $$(StateManager.class));
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
                return new ImageUtils($(Images.class));
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

        register(Singleton.class, new Factory<DeferredCommandWrapper>(DeferredCommandWrapper.class) {
            @Override
            public DeferredCommandWrapper create() {
                return new DeferredCommandWrapper();
            }
        });

        register(Singleton.class, new Factory<ColorWebSafePalette>(ColorWebSafePalette.class) {
            @Override
            public ColorWebSafePalette create() {
                final ColorWebSafePalettePresenter presenter = new ColorWebSafePalettePresenter();
                final ColorWebSafePalettePanel panel = new ColorWebSafePalettePanel(presenter);
                presenter.init(panel);
                return presenter;
            }
        });

        register(NoDecoration.class, new Factory<RTEActionTopToolbar>(RTEActionTopToolbar.class) {
            @Override
            public RTEActionTopToolbar create() {
                final ActionToolbarPanel<Object> panel = new ActionToolbarPanel<Object>($$(ActionManager.class));
                final RTEActionTopToolbarPresenter toolbar = new RTEActionTopToolbarPresenter(panel);
                return toolbar;
            }
        }, new Factory<RTEActionSndToolbar>(RTEActionSndToolbar.class) {
            @Override
            public RTEActionSndToolbar create() {
                final ActionToolbarPanel<Object> panel = new ActionToolbarPanel<Object>($$(ActionManager.class), true);
                final RTEActionSndToolbarPresenter toolbar = new RTEActionSndToolbarPresenter(panel);
                return toolbar;
            }
        });

        register(NoDecoration.class, new Factory<RTEditor>(RTEditor.class) {
            @Override
            public RTEditor create() {
                RTEActionTopToolbar topBar = $(RTEActionTopToolbar.class);
                RTEActionSndToolbar sndBar = $(RTEActionSndToolbar.class);
                final RTEditorPresenter presenter = new RTEditorPresenter($(I18nTranslationService.class),
                        $(Session.class), topBar, sndBar, $(RTEImgResources.class), $(TextEditorInsertElement.class),
                        $(ColorWebSafePalette.class), $(DeferredCommandWrapper.class));
                final RTEditorPanel panel = new RTEditorPanel(presenter, $(I18nUITranslationService.class),
                        $(ActionManager.class));
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<RTESavingEditor>(RTESavingEditor.class) {
            @Override
            public RTESavingEditor create() {
                return new RTESavingEditorPresenter($(RTEditor.class), true, $(I18nTranslationService.class),
                        $(StateManager.class), $(SiteSignOutLink.class), $(DeferredCommandWrapper.class),
                        $(RTEImgResources.class));
            }
        });

        register(NoDecoration.class, new Factory<TestRTEDialog>(TestRTEDialog.class) {
            @Override
            public TestRTEDialog create() {
                return new TestRTEDialog($(RTESavingEditor.class));
            }
        });

        register(ApplicationComponentGroup.class, new Factory<NotifyUser>(NotifyUser.class) {
            @Override
            public NotifyUser create() {
                return new NotifyUser($(I18nTranslationService.class));
            }
        });

        register(Singleton.class, new Factory<RTEImgResources>(RTEImgResources.class) {
            @Override
            public RTEImgResources create() {
                RTEImgResources instance = GWT.create(RTEImgResources.class);
                StyleInjector.injectStylesheet(instance.css().getText());
                return instance;
            }
        });

        $(ApplicationComponentGroup.class).createAll();
        $(ToolGroup.class).createAll();
        $(Application.class).start();
        // $(HelloWorld.class);
    }
}