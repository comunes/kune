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
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
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
import org.ourproject.kune.platf.client.ui.palette.SimplePalette;
import org.ourproject.kune.platf.client.ui.palette.SimplePalettePanel;
import org.ourproject.kune.platf.client.ui.palette.SimplePalettePresenter;
import org.ourproject.kune.platf.client.ui.rte.TestRTEDialog;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEActionSndToolbar;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEActionTopToolbar;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditor;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPanel;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPresenter;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialog;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialogPanel;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialogPresenter;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlGroup;
import org.ourproject.kune.platf.client.ui.rte.edithtml.editor.EditHtmlEditor;
import org.ourproject.kune.platf.client.ui.rte.edithtml.editor.EditHtmlEditorPanel;
import org.ourproject.kune.platf.client.ui.rte.edithtml.editor.EditHtmlEditorPresenter;
import org.ourproject.kune.platf.client.ui.rte.edithtml.preview.EditHtmlPreview;
import org.ourproject.kune.platf.client.ui.rte.edithtml.preview.EditHtmlPreviewPanel;
import org.ourproject.kune.platf.client.ui.rte.edithtml.preview.EditHtmlPreviewPresenter;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialogPanel;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialogPresenter;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageGroup;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ext.InsertImageExt;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ext.InsertImageExtPanel;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ext.InsertImageExtPresenter;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialog;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialogPanel;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialogPresenter;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkGroup;
import org.ourproject.kune.platf.client.ui.rte.insertlink.email.InsertLinkEmail;
import org.ourproject.kune.platf.client.ui.rte.insertlink.email.InsertLinkEmailPanel;
import org.ourproject.kune.platf.client.ui.rte.insertlink.email.InsertLinkEmailPresenter;
import org.ourproject.kune.platf.client.ui.rte.insertlink.ext.InsertLinkExt;
import org.ourproject.kune.platf.client.ui.rte.insertlink.ext.InsertLinkExtPanel;
import org.ourproject.kune.platf.client.ui.rte.insertlink.ext.InsertLinkExtPresenter;
import org.ourproject.kune.platf.client.ui.rte.insertlink.ext.InsertLinkExtView;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialogPanel;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialogPresenter;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharGroup;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.asian.InsertSpecialAsianChar;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.asian.InsertSpecialAsianCharPanel;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.asian.InsertSpecialAsianCharPresenter;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.occidental.InsertSpecialOccChar;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.occidental.InsertSpecialOccCharPanel;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.occidental.InsertSpecialOccCharPresenter;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialog;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialogPanel;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialogPresenter;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditor;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditorPanel;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditorPresenter;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.platf.client.utils.TimerWrapper;

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

public class PlatformModule extends AbstractModule {

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

        register(Singleton.class, new Factory<ErrorHandler>(ErrorHandler.class) {
            @Override
            public ErrorHandler create() {
                return new ErrorHandler($(Session.class), i18n, $$(StateManager.class));
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

        AsyncCallbackSimple.init($(ErrorHandler.class));

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

        register(Singleton.class, new Factory<SimplePalette>(SimplePalette.class) {
            @Override
            public SimplePalette create() {
                final SimplePalettePresenter presenter = new SimplePalettePresenter();
                final SimplePalettePanel panel = new SimplePalettePanel(presenter, i18n);
                presenter.init(panel);
                return presenter;
            }
        });

        register(NoDecoration.class, new Factory<RTEActionTopToolbar>(RTEActionTopToolbar.class) {
            @Override
            public RTEActionTopToolbar create() {
                final ActionToolbarPanel<Object> panel = new ActionToolbarPanel<Object>($$(ActionManager.class));
                final RTEActionTopToolbar toolbar = new RTEActionTopToolbar(panel);
                return toolbar;
            }
        }, new Factory<RTEActionSndToolbar>(RTEActionSndToolbar.class) {
            @Override
            public RTEActionSndToolbar create() {
                final ActionToolbarPanel<Object> panel = new ActionToolbarPanel<Object>($$(ActionManager.class), true);
                final RTEActionSndToolbar toolbar = new RTEActionSndToolbar(panel);
                return toolbar;
            }
        });

        register(Singleton.class, new Factory<TimerWrapper>(TimerWrapper.class) {
            @Override
            public TimerWrapper create() {
                return new TimerWrapper();
            }
        });

        register(NoDecoration.class, new Factory<RTEditor>(RTEditor.class) {
            @Override
            public RTEditor create() {
                RTEActionTopToolbar topBar = $(RTEActionTopToolbar.class);
                RTEActionSndToolbar sndBar = $(RTEActionSndToolbar.class);
                final RTEditorPresenter presenter = new RTEditorPresenter($(I18nTranslationService.class),
                        $(Session.class), topBar, sndBar, $(RTEImgResources.class), $(InsertLinkDialog.class),
                        $(ColorWebSafePalette.class), $$(EditHtmlDialog.class), $$(InsertImageDialog.class),
                        $$(InsertTableDialog.class), $$(InsertSpecialCharDialog.class), $(DeferredCommandWrapper.class));
                final RTEditorPanel panel = new RTEditorPanel(presenter, $(I18nUITranslationService.class),
                        $(ActionManager.class), $(GlobalShortcutRegister.class));
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<RTESavingEditor>(RTESavingEditor.class) {
            @Override
            public RTESavingEditor create() {
                RTESavingEditorPresenter presenter = new RTESavingEditorPresenter($(RTEditor.class), true,
                        $(I18nTranslationService.class), $(StateManager.class), $(DeferredCommandWrapper.class),
                        $(RTEImgResources.class), $(TimerWrapper.class));
                RTESavingEditorPanel panel = new RTESavingEditorPanel();
                presenter.init(panel);
                return presenter;
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
                return new NotifyUser($(I18nTranslationService.class), $(Images.class));
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

        registerDecorator(EditHtmlGroup.class, new EditHtmlGroup(container));

        register(Singleton.class, new Factory<EditHtmlDialog>(EditHtmlDialog.class) {
            @Override
            public EditHtmlDialog create() {
                final EditHtmlDialogPresenter presenter = new EditHtmlDialogPresenter();
                final EditHtmlDialogPanel panel = new EditHtmlDialogPanel(presenter, $(I18nTranslationService.class),
                        $(RTEImgResources.class), $(Images.class), $(EditHtmlGroup.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(EditHtmlGroup.class, new Factory<EditHtmlEditor>(EditHtmlEditor.class) {
            @Override
            public EditHtmlEditor create() {
                final EditHtmlEditorPresenter presenter = new EditHtmlEditorPresenter($(EditHtmlDialog.class));
                final EditHtmlEditorPanel panel = new EditHtmlEditorPanel(i18n, presenter);
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<EditHtmlPreview>(EditHtmlPreview.class) {
            @Override
            public EditHtmlPreview create() {
                final EditHtmlPreviewPresenter presenter = new EditHtmlPreviewPresenter($(EditHtmlDialog.class));
                final EditHtmlPreviewPanel panel = new EditHtmlPreviewPanel(i18n, presenter);
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<InsertImageDialog>(InsertImageDialog.class) {
            @Override
            public InsertImageDialog create() {
                final InsertImageDialogPresenter presenter = new InsertImageDialogPresenter();
                final InsertImageDialogPanel panel = new InsertImageDialogPanel(presenter,
                        $(I18nTranslationService.class), $(Images.class), $(InsertImageGroup.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(InsertImageGroup.class, new Factory<InsertImageExt>(InsertImageExt.class) {
            @Override
            public InsertImageExt create() {
                final InsertImageExtPresenter presenter = new InsertImageExtPresenter($(InsertImageDialog.class));
                final InsertImageExtPanel panel = new InsertImageExtPanel(presenter, i18n);
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<InsertLinkDialog>(InsertLinkDialog.class) {
            @Override
            public InsertLinkDialog create() {
                final InsertLinkDialogPresenter presenter = new InsertLinkDialogPresenter();
                final InsertLinkDialogPanel panel = new InsertLinkDialogPanel(presenter, $(Images.class),
                        $(I18nTranslationService.class), $(InsertLinkGroup.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(InsertLinkGroup.class, new Factory<InsertLinkExt>(InsertLinkExt.class) {
            @Override
            public InsertLinkExt create() {
                final InsertLinkExtPresenter presenter = new InsertLinkExtPresenter($(InsertLinkDialog.class));
                final InsertLinkExtView panel = new InsertLinkExtPanel(presenter, $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(InsertLinkGroup.class, new Factory<InsertLinkEmail>(InsertLinkEmail.class) {
            @Override
            public InsertLinkEmail create() {
                final InsertLinkEmailPresenter presenter = new InsertLinkEmailPresenter($(InsertLinkDialog.class));
                final InsertLinkEmailPanel panel = new InsertLinkEmailPanel(presenter, $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<InsertTableDialog>(InsertTableDialog.class) {
            @Override
            public InsertTableDialog create() {
                final InsertTableDialogPresenter presenter = new InsertTableDialogPresenter();
                final InsertTableDialogPanel panel = new InsertTableDialogPanel(presenter, i18n,
                        $$(SimplePalette.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(InsertSpecialCharGroup.class, new Factory<InsertSpecialCharDialog>(InsertSpecialCharDialog.class) {
            @Override
            public InsertSpecialCharDialog create() {
                final InsertSpecialCharDialogPresenter presenter = new InsertSpecialCharDialogPresenter();
                final InsertSpecialCharDialogPanel panel = new InsertSpecialCharDialogPanel(presenter, $(Images.class),
                        $(I18nTranslationService.class), $(InsertSpecialCharGroup.class));
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<InsertSpecialOccChar>(InsertSpecialOccChar.class) {
            @Override
            public InsertSpecialOccChar create() {
                final InsertSpecialOccCharPresenter presenter = new InsertSpecialOccCharPresenter();
                final InsertSpecialOccCharPanel panel = new InsertSpecialOccCharPanel($(InsertSpecialCharDialog.class),
                        i18n);
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<InsertSpecialAsianChar>(InsertSpecialAsianChar.class) {
            @Override
            public InsertSpecialAsianChar create() {
                final InsertSpecialAsianCharPresenter presenter = new InsertSpecialAsianCharPresenter();
                final InsertSpecialAsianCharPanel panel = new InsertSpecialAsianCharPanel(
                        $(InsertSpecialCharDialog.class), i18n);
                presenter.init(panel);
                return presenter;
            }
        });

        $(ApplicationComponentGroup.class).createAll();
        $(ToolGroup.class).createAll();
        $(Application.class).start();
        // $(HelloWorld.class);
    }
}