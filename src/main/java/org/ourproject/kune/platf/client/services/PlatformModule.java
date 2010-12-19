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

import org.ourproject.kune.platf.client.actions.ui.BasicGuiBindings;
import org.ourproject.kune.platf.client.actions.ui.GuiBindingsRegister;
import org.ourproject.kune.platf.client.app.ApplicationComponentGroup;
import org.ourproject.kune.platf.client.app.ToolGroup;
import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.ui.QuickTipsHelper;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.img.ImgResources;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalettePanel;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalettePresenter;
import org.ourproject.kune.platf.client.ui.palette.SimplePalette;
import org.ourproject.kune.platf.client.ui.palette.SimplePalettePanel;
import org.ourproject.kune.platf.client.ui.palette.SimplePalettePresenter;
import org.ourproject.kune.platf.client.ui.rte.TestRTEDialog;
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
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ExternalMediaRegistry;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialogPanel;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialogPresenter;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaGroup;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.MediaUtils;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ext.InsertMediaExt;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ext.InsertMediaExtPanel;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ext.InsertMediaExtPresenter;
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
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.utf8.InsertSpecialUTF8CharPanel;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialog;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialogPanel;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialogPresenter;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditor;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditorPanel;
import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditorPresenter;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.platf.client.utils.TimerWrapper;

import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.ioc.decorator.NoDecoration;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.Factory;
import com.google.gwt.core.client.GWT;

public class PlatformModule extends AbstractExtendedModule {

    @Override
    protected void onInstall() {

        register(Singleton.class, new Factory<FileDownloadUtils>(FileDownloadUtils.class) {
            @Override
            public FileDownloadUtils create() {
                return new FileDownloadUtils(i(Session.class), i(ImageUtils.class));
            }
        });

        if (!container.hasProvider(QuickTipsHelper.class)) {
            register(Singleton.class, new Factory<QuickTipsHelper>(QuickTipsHelper.class) {
                @Override
                public QuickTipsHelper create() {
                    return new QuickTipsHelper();
                }
            });
        }

        i(QuickTipsHelper.class);

        final I18nUITranslationService i18n = i(I18nUITranslationService.class);

        if (container.hasProvider(I18nTranslationService.class)) {
            container.removeProvider(I18nTranslationService.class);
        }

        register(Singleton.class, new Factory<Resources>(Resources.class) {
            @Override
            public Resources create() {
                return new Resources(i18n);
            }
        });

        i(Resources.class);

        register(Singleton.class, new Factory<I18nTranslationService>(I18nTranslationService.class) {
            @Override
            public I18nTranslationService create() {
                return i18n;
            }
        });

        register(Singleton.class, new Factory<ErrorHandler>(ErrorHandler.class) {
            @Override
            public ErrorHandler create() {
                return null; // new ErrorHandler(i(Session.class), i18n, p(StateManager.class));
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
                return new ImageUtils(i(Images.class));
            }
        });

        //AsyncCallbackSimple.init(i(ErrorHandler.class));

//        register(Singleton.class, new Factory<AppStarter>(AppStarter.class) {
//            @Override
//            public AppStarter create() {
//                return new AppStarterDefault(i(Session.class), i(SiteServiceAsync.class));
//            }
//        });

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

        register(Singleton.class, new Factory<TimerWrapper>(TimerWrapper.class) {
            @Override
            public TimerWrapper create() {
                return new TimerWrapper();
            }
        });

        register(InsertLinkGroup.class, new Factory<InsertLinkExt>(InsertLinkExt.class) {
            @Override
            public InsertLinkExt create() {
                final InsertLinkExtPresenter presenter = new InsertLinkExtPresenter(i(InsertLinkDialog.class));
                final InsertLinkExtView panel = new InsertLinkExtPanel(presenter, i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(InsertLinkGroup.class, new Factory<InsertLinkEmail>(InsertLinkEmail.class) {;

            @Override
            public InsertLinkEmail create() {
                final InsertLinkEmailPresenter presenter = new InsertLinkEmailPresenter(i(InsertLinkDialog.class));
                final InsertLinkEmailPanel panel = new InsertLinkEmailPanel(presenter, i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ImgResources>(ImgResources.class) {
            @Override
            public ImgResources create() {
                final ImgResources instance = GWT.create(ImgResources.class);
                instance.css().ensureInjected();
                return instance;
            }
        });

        register(Singleton.class, new Factory<RTEImgResources>(RTEImgResources.class) {
            @Override
            public RTEImgResources create() {
                final RTEImgResources instance = GWT.create(RTEImgResources.class);
                instance.css().ensureInjected();
                return instance;
            }
        });

        register(NoDecoration.class,new Factory<RTEditor>(RTEditor.class) {
            @Override
            public RTEditor create() {
                final RTEditorPresenter presenter = new RTEditorPresenter(i(I18nTranslationService.class),
                        i(Session.class),  i(RTEImgResources.class), p(InsertLinkDialog.class),
                        p(ColorWebSafePalette.class), p(EditHtmlDialog.class), p(InsertImageDialog.class),
                        p(InsertMediaDialog.class),   p(InsertTableDialog.class), p(InsertSpecialCharDialog.class), i(DeferredCommandWrapper.class));
                final RTEditorPanel panel = new RTEditorPanel(presenter, i(I18nUITranslationService.class),
                         i(GlobalShortcutRegister.class), i(GuiBindingsRegister.class));
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<RTESavingEditor>(RTESavingEditor.class) {
            @Override
            public RTESavingEditor create() {
                final RTESavingEditorPresenter presenter = new RTESavingEditorPresenter(i(I18nTranslationService.class),
                        i(Session.class),  i(RTEImgResources.class), p(InsertLinkDialog.class),
                        p(ColorWebSafePalette.class), p(EditHtmlDialog.class), p(InsertImageDialog.class),
                        p(InsertMediaDialog.class),   p(InsertTableDialog.class), p(InsertSpecialCharDialog.class), i(DeferredCommandWrapper.class), true,
                         i(StateManager.class),
                        i(TimerWrapper.class));
                final RTESavingEditorPanel panel = new RTESavingEditorPanel(presenter, i(I18nUITranslationService.class),
                        i(GlobalShortcutRegister.class), i(GuiBindingsRegister.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(NoDecoration.class, new Factory<TestRTEDialog>(TestRTEDialog.class) {
            @Override
            public TestRTEDialog create() {
                return new TestRTEDialog(i(RTESavingEditor.class));
            }
        });

        register(ApplicationComponentGroup.class, new Factory<NotifyUser>(NotifyUser.class) {
            @Override
            public NotifyUser create() {
                return new NotifyUser(i(I18nTranslationService.class), i(Images.class));
            }
        });

        registerDecorator(EditHtmlGroup.class, new EditHtmlGroup(container));

        register(Singleton.class, new Factory<EditHtmlDialog>(EditHtmlDialog.class) {
            @Override
            public EditHtmlDialog create() {
                final EditHtmlDialogPresenter presenter = new EditHtmlDialogPresenter();
                final EditHtmlDialogPanel panel = new EditHtmlDialogPanel(presenter, i(I18nTranslationService.class),
                        i(RTEImgResources.class), i(Images.class), i(EditHtmlGroup.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(EditHtmlGroup.class, new Factory<EditHtmlEditor>(EditHtmlEditor.class) {
            @Override
            public EditHtmlEditor create() {
                final EditHtmlEditorPresenter presenter = new EditHtmlEditorPresenter(i(EditHtmlDialog.class));
                final EditHtmlEditorPanel panel = new EditHtmlEditorPanel(i18n, presenter);
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<EditHtmlPreview>(EditHtmlPreview.class) {
            @Override
            public EditHtmlPreview create() {
                final EditHtmlPreviewPresenter presenter = new EditHtmlPreviewPresenter(i(EditHtmlDialog.class));
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
                        i(I18nTranslationService.class), i(Images.class), i(InsertImageGroup.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(InsertImageGroup.class, new Factory<InsertImageExt>(InsertImageExt.class) {
            @Override
            public InsertImageExt create() {
                final InsertImageExtPresenter presenter = new InsertImageExtPresenter(i(InsertImageDialog.class));
                final InsertImageExtPanel panel = new InsertImageExtPanel(presenter, i18n);
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<InsertLinkDialog>(InsertLinkDialog.class) {
            @Override
            public InsertLinkDialog create() {
                final InsertLinkDialogPresenter presenter = new InsertLinkDialogPresenter();
                final InsertLinkDialogPanel panel = new InsertLinkDialogPanel(presenter, i(Images.class),
                        i(I18nTranslationService.class), i(InsertLinkGroup.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<InsertTableDialog>(InsertTableDialog.class) {
            @Override
            public InsertTableDialog create() {
                final InsertTableDialogPresenter presenter = new InsertTableDialogPresenter();
                final InsertTableDialogPanel panel = new InsertTableDialogPanel(presenter, i18n,
                        p(SimplePalette.class), i(RTEImgResources.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(InsertSpecialCharGroup.class, new Factory<InsertSpecialCharDialog>(InsertSpecialCharDialog.class) {
            @Override
            public InsertSpecialCharDialog create() {
                final InsertSpecialCharDialogPresenter presenter = new InsertSpecialCharDialogPresenter();
                final InsertSpecialCharDialogPanel panel = new InsertSpecialCharDialogPanel(presenter, i(Images.class),
                        i(I18nTranslationService.class), i(InsertSpecialCharGroup.class), i(RTEImgResources.class));
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<InsertSpecialOccChar>(InsertSpecialOccChar.class) {
            @Override
            public InsertSpecialOccChar create() {
                final InsertSpecialOccCharPresenter presenter = new InsertSpecialOccCharPresenter();
                final InsertSpecialOccCharPanel panel = new InsertSpecialOccCharPanel(i(InsertSpecialCharDialog.class),
                        i18n);
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<InsertSpecialAsianChar>(InsertSpecialAsianChar.class) {
            @Override
            public InsertSpecialAsianChar create() {
                final InsertSpecialAsianCharPresenter presenter = new InsertSpecialAsianCharPresenter();
                final InsertSpecialAsianCharPanel panel = new InsertSpecialAsianCharPanel(
                        i(InsertSpecialCharDialog.class), i18n);
                presenter.init(panel);
                return presenter;
            }
        }, new Factory<InsertSpecialUTF8CharPanel>(InsertSpecialUTF8CharPanel.class) {
            @Override
            public InsertSpecialUTF8CharPanel create() {
                return new InsertSpecialUTF8CharPanel(i18n, i(InsertSpecialCharDialog.class));
            }
        });


        register(MediaUtils.class, new Factory<MediaUtils>(MediaUtils.class) {
            @Override
            public MediaUtils create() {
                return new MediaUtils(i(Session.class), i(FileDownloadUtils.class));
            }});

        register(InsertMediaGroup.class, new Factory<InsertMediaExt>(InsertMediaExt.class) {
            @Override
            public InsertMediaExt create() {
                final InsertMediaExtPresenter presenter = new InsertMediaExtPresenter(i(InsertMediaDialog.class), i(ExternalMediaRegistry.class));
                final InsertMediaExtPanel panel = new InsertMediaExtPanel(presenter, i18n, i(ExternalMediaRegistry.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<InsertMediaDialog>(InsertMediaDialog.class) {
            @Override
            public InsertMediaDialog create() {
                final InsertMediaDialogPresenter presenter = new InsertMediaDialogPresenter();
                final InsertMediaDialogPanel panel = new InsertMediaDialogPanel(presenter, i18n, i(Images.class), i(InsertMediaGroup.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ExternalMediaRegistry>(ExternalMediaRegistry.class) {
            @Override
            public ExternalMediaRegistry create() {
                return new ExternalMediaRegistry(i(Session.class).getInitData().getExtMediaDescrips());
            }});

        register(Singleton.class, new Factory<BasicGuiBindings>(BasicGuiBindings.class) {
            @Override
            public BasicGuiBindings create() {
                return new BasicGuiBindings(i(GuiBindingsRegister.class));
            }
        });

        register(Singleton.class, new Factory<GuiBindingsRegister>(GuiBindingsRegister.class) {
            @Override
            public GuiBindingsRegister create() {
                return new GuiBindingsRegister();
            }
            @Override
            public void onAfterCreated(final GuiBindingsRegister instance) {
                i(BasicGuiBindings.class);
            }
        });

        register(Singleton.class, new Factory<AccessRightsClientManager>(AccessRightsClientManager.class) {
            @Override
            public AccessRightsClientManager create() {
                return new AccessRightsClientManager(i(StateManager.class));
            }
        });

        register(ApplicationComponentGroup.class, new Factory<GlobalShortcutRegister>(GlobalShortcutRegister.class) {
            @Override
            public GlobalShortcutRegister create() {
                return new GlobalShortcutRegister();
            }
        });
        i(ApplicationComponentGroup.class).createAll();
        i(ToolGroup.class).createAll();
//        i(AppStarter.class).start();

    }
}