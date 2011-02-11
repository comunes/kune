/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.actions.ui.GuiBindingsRegister;
import org.ourproject.kune.platf.client.actions.ui.SimpleGuiItem;
import org.ourproject.kune.platf.client.app.ApplicationComponentGroup;
import org.ourproject.kune.platf.client.app.GroupOptionsCollection;
import org.ourproject.kune.platf.client.app.UserOptionsCollection;
import org.ourproject.kune.platf.client.registry.AuthorableRegistry;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.registry.RenamableRegistry;
import org.ourproject.kune.platf.client.services.AbstractExtendedModule;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialog;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageGroup;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialog;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkGroup;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaGroup;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.MediaUtils;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialog;
import org.ourproject.kune.workspace.client.cnt.ActionContentToolbar;
import org.ourproject.kune.workspace.client.cnt.ActionContentToolbarPresenter;
import org.ourproject.kune.workspace.client.cnt.ContentIconsRegistry;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigatorPanel;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigatorPresenter;
import org.ourproject.kune.workspace.client.cxt.ActionContextBottomToolbar;
import org.ourproject.kune.workspace.client.cxt.ActionContextTopToolbar;
import org.ourproject.kune.workspace.client.cxt.ContextActionRegistry;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditorPanel;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditorPresenter;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditorView;
import org.ourproject.kune.workspace.client.editor.ContentEditor;
import org.ourproject.kune.workspace.client.editor.ContentEditorPanel;
import org.ourproject.kune.workspace.client.editor.ContentEditorPresenter;
import org.ourproject.kune.workspace.client.editor.insertlocalimg.InsertImageLocal;
import org.ourproject.kune.workspace.client.editor.insertlocalimg.InsertImageLocalPanel;
import org.ourproject.kune.workspace.client.editor.insertlocalimg.InsertImageLocalPresenter;
import org.ourproject.kune.workspace.client.editor.insertlocallink.InsertLinkLocal;
import org.ourproject.kune.workspace.client.editor.insertlocallink.InsertLinkLocalPanel;
import org.ourproject.kune.workspace.client.editor.insertlocallink.InsertLinkLocalPresenter;
import org.ourproject.kune.workspace.client.editor.insertlocalmedia.InsertMediaLocal;
import org.ourproject.kune.workspace.client.editor.insertlocalmedia.InsertMediaLocalPanel;
import org.ourproject.kune.workspace.client.editor.insertlocalmedia.InsertMediaLocalPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslator;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPanel;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorView;
import org.ourproject.kune.workspace.client.i18n.LanguageSelector;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorPanel;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorPresenter;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorView;
import org.ourproject.kune.workspace.client.licensewizard.LicenseChangeAction;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizard;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizardPanel;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizardPresenter;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFirstForm;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFrdForm;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFrdFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardSndForm;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardSndFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardTrdForm;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardTrdFormView;
import org.ourproject.kune.workspace.client.nohomepage.NoHomePage;
import org.ourproject.kune.workspace.client.nohomepage.NoHomePagePanel;
import org.ourproject.kune.workspace.client.nohomepage.NoHomePagePresenter;
import org.ourproject.kune.workspace.client.oldsn.toolbar.ActionBuddiesSummaryToolbar;
import org.ourproject.kune.workspace.client.oldsn.toolbar.ActionBuddiesSummaryToolbarPresenter;
import org.ourproject.kune.workspace.client.oldsn.toolbar.ActionGroupSummaryToolbar;
import org.ourproject.kune.workspace.client.oldsn.toolbar.ActionGroupSummaryToolbarPresenter;
import org.ourproject.kune.workspace.client.oldsn.toolbar.ActionParticipationSummaryToolbarPresenter;
import org.ourproject.kune.workspace.client.oldsn.toolbar.ActionParticipationToolbar;
import org.ourproject.kune.workspace.client.options.GroupOptions;
import org.ourproject.kune.workspace.client.options.GroupOptionsPanel;
import org.ourproject.kune.workspace.client.options.GroupOptionsPresenter;
import org.ourproject.kune.workspace.client.options.UserOptions;
import org.ourproject.kune.workspace.client.options.license.EntityOptionsDefLicensePanel;
import org.ourproject.kune.workspace.client.options.license.GroupOptionsDefLicense;
import org.ourproject.kune.workspace.client.options.license.GroupOptionsDefLicensePresenter;
import org.ourproject.kune.workspace.client.options.license.UserOptionsDefLicense;
import org.ourproject.kune.workspace.client.options.license.UserOptionsDefLicensePresenter;
import org.ourproject.kune.workspace.client.options.logo.EntityOptionsLogoPresenter;
import org.ourproject.kune.workspace.client.options.logo.GroupOptionsLogo;
import org.ourproject.kune.workspace.client.options.logo.GroupOptionsLogoPanel;
import org.ourproject.kune.workspace.client.options.logo.GroupOptionsLogoPresenter;
import org.ourproject.kune.workspace.client.options.logo.UserOptionsLogo;
import org.ourproject.kune.workspace.client.options.logo.UserOptionsLogoPanel;
import org.ourproject.kune.workspace.client.options.logo.UserOptionsLogoPresenter;
import org.ourproject.kune.workspace.client.options.pscape.EntityOptionsPublicSpaceConfPanel;
import org.ourproject.kune.workspace.client.options.pscape.GroupOptionsPublicSpaceConf;
import org.ourproject.kune.workspace.client.options.pscape.GroupOptionsPublicSpaceConfPresenter;
import org.ourproject.kune.workspace.client.options.pscape.UserOptionsPublicSpaceConf;
import org.ourproject.kune.workspace.client.options.pscape.UserOptionsPublicSpaceConfPresenter;
import org.ourproject.kune.workspace.client.options.tools.EntityOptionsToolsConf;
import org.ourproject.kune.workspace.client.options.tools.EntityOptionsToolsConfPanel;
import org.ourproject.kune.workspace.client.options.tools.GroupOptionsToolsConfPresenter;
import org.ourproject.kune.workspace.client.options.tools.UserOptionsToolsConf;
import org.ourproject.kune.workspace.client.options.tools.UserOptionsToolsConfPresenter;
import org.ourproject.kune.workspace.client.rate.RateIt;
import org.ourproject.kune.workspace.client.rate.RateItPanel;
import org.ourproject.kune.workspace.client.rate.RateItPresenter;
import org.ourproject.kune.workspace.client.rate.RatePanel;
import org.ourproject.kune.workspace.client.rate.RatePresenter;
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
import org.ourproject.kune.workspace.client.site.WorkspaceNotifyUser;
import org.ourproject.kune.workspace.client.site.msg.ToastMessage;
import org.ourproject.kune.workspace.client.site.msg.ToastMessagePanel;
import org.ourproject.kune.workspace.client.site.msg.ToastMessagePresenter;
import org.ourproject.kune.workspace.client.sitebar.sitelogo.SiteLogo;
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
import org.ourproject.kune.workspace.client.skel.ActionCntCtxToolbarPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.tags.TagsSummary;
import org.ourproject.kune.workspace.client.tags.TagsSummaryPanel;
import org.ourproject.kune.workspace.client.tags.TagsSummaryPresenter;
import org.ourproject.kune.workspace.client.themes.WsBackManager;
import org.ourproject.kune.workspace.client.themes.WsBackManagerImpl;
import org.ourproject.kune.workspace.client.themes.WsThemeManager;
import org.ourproject.kune.workspace.client.themes.WsThemeManagerPanel;
import org.ourproject.kune.workspace.client.themes.WsThemeSelector;
import org.ourproject.kune.workspace.client.themes.WsThemeSelectorPresenter;
import org.ourproject.kune.workspace.client.title.EntitySubTitle;
import org.ourproject.kune.workspace.client.title.EntitySubTitlePanel;
import org.ourproject.kune.workspace.client.title.EntitySubTitlePresenter;
import org.ourproject.kune.workspace.client.title.EntityTitle;
import org.ourproject.kune.workspace.client.title.EntityTitlePanel;
import org.ourproject.kune.workspace.client.title.EntityTitlePresenter;
import org.ourproject.kune.workspace.client.title.RenameAction;
import org.ourproject.kune.workspace.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.tool.ToolSelectorPresenter;
import org.ourproject.kune.workspace.client.upload.FileUploader;
import org.ourproject.kune.workspace.client.upload.FileUploaderDialog;
import org.ourproject.kune.workspace.client.upload.FileUploaderPresenter;

import cc.kune.common.client.noti.NotifyLevelImages;
import cc.kune.common.client.utils.SchedulerManager;
import cc.kune.common.client.utils.TimerWrapper;
import cc.kune.core.client.auth.Register;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.groups.newgroup.NewGroup;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.client.rpcservices.ContentService;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.rpcservices.GroupService;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.rpcservices.I18nServiceAsync;
import cc.kune.core.client.rpcservices.SocialNetworkService;
import cc.kune.core.client.rpcservices.SocialNetworkServiceAsync;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.state.HistoryTokenCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteCommonTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.ws.entheader.EntityHeader;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateTokenUtils;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.ioc.decorator.NoDecoration;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class WorkspaceModule extends AbstractExtendedModule {
    @Override
    protected void onInstall() {

        register(Singleton.class, new Factory<SocialNetworkServiceAsync>(SocialNetworkServiceAsync.class) {
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

        register(ApplicationComponentGroup.class, new Factory<WorkspaceSkeleton>(WorkspaceSkeleton.class) {
            @Override
            public WorkspaceSkeleton create() {
                return new WorkspaceSkeleton();
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteProgress>(SiteProgress.class) {
            @Override
            public SiteProgress create() {
                final SiteProgressPresenter presenter = new SiteProgressPresenter();
                final SiteProgressPanel panel = new SiteProgressPanel(presenter, p(SitePublicSpaceLink.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<WorkspaceNotifyUser>(WorkspaceNotifyUser.class) {
            @Override
            public WorkspaceNotifyUser create() {
                return new WorkspaceNotifyUser(i(NotifyUser.class), i(I18nUITranslationService.class),
                        i(SiteProgress.class), p(ToastMessage.class), p(WorkspaceSkeleton.class));
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SitePublicSpaceLink>(SitePublicSpaceLink.class) {
            @Override
            public SitePublicSpaceLink create() {
                final SitePublicSpaceLinkPresenter presenter = new SitePublicSpaceLinkPresenter(i(StateManager.class),
                        i(StateTokenUtils.class));
                final SitePublicSpaceLinkPanel panel = new SitePublicSpaceLinkPanel(presenter,
                        i(WorkspaceSkeleton.class), i(I18nUITranslationService.class), i(Images.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<StateTokenUtils>(StateTokenUtils.class) {
            @Override
            public StateTokenUtils create() {
                return new StateTokenUtils(i(Session.class));
            }
        });

        register(NoDecoration.class, new Factory<ToastMessage>(ToastMessage.class) {
            @Override
            public ToastMessage create() {
                final ToastMessagePresenter presenter = new ToastMessagePresenter();
                final ToastMessagePanel panel = new ToastMessagePanel();
                presenter.init(panel);
                return presenter;
            }
        });

        // register(ApplicationComponentGroup.class, new
        // Factory<SiteUserOptions>(SiteUserOptions.class) {
        // @Override
        // public SiteUserOptions create() {
        // final SiteUserOptionsPresenter presenter = new
        // SiteUserOptionsPresenter(i(Session.class),
        // i(StateManager.class), p(FileDownloadUtils.class),
        // $(I18nTranslationService.class),
        // $(IconResources.class));
        // final SiteUserOptionsPanel panel = new
        // SiteUserOptionsPanel(presenter, i(WorkspaceSkeleton.class),
        // $(GuiBindingsRegister.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });

        register(ApplicationComponentGroup.class, new Factory<SiteSignInLink>(SiteSignInLink.class) {
            @Override
            public SiteSignInLink create() {
                final SiteSignInLinkPresenter presenter = new SiteSignInLinkPresenter(i(Session.class));
                final SiteSignInLinkPanel panel = new SiteSignInLinkPanel(presenter, i(I18nUITranslationService.class),
                        i(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteSignOutLink>(SiteSignOutLink.class) {
            @Override
            public SiteSignOutLink create() {
                final SiteSignOutLinkPresenter presenter = new SiteSignOutLinkPresenter(i(Session.class),
                        p(UserServiceAsync.class), p(ErrorHandler.class));
                final SiteSignOutLinkPanel panel = new SiteSignOutLinkPanel(presenter,
                        i(I18nUITranslationService.class), i(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<SiteOptions>(SiteOptions.class) {
        // @Override
        // public SiteOptions create() {
        // final SiteOptionsPresenter presenter = new
        // SiteOptionsPresenter(i(I18nUITranslationService.class),
        // i(IconResources.class));
        // final SiteOptionsPanel panel = new
        // SiteOptionsPanel(i(WorkspaceSkeleton.class),
        // i(I18nUITranslationService.class), i(GuiBindingsRegister.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });

        register(ApplicationComponentGroup.class, new Factory<SiteSearch>(SiteSearch.class) {
            @Override
            public SiteSearch create() {
                final SiteSearchPresenter presenter = new SiteSearchPresenter(p(SiteSearcher.class),
                        i(I18nTranslationService.class));
                final SiteSearchPanel panel = new SiteSearchPanel(presenter, i(WorkspaceSkeleton.class),
                        i(Images.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteLogo>(SiteLogo.class) {
            @Override
            public SiteLogo create() {
                // final SiteLogoPresenter presenter = new
                // SiteLogoPresenter(i(Session.class));
                // final SiteLogoPanel panel = new SiteLogoPanel(presenter,
                // i(WorkspaceSkeleton.class));
                // presenter.init(panel);
                return null;
            }
        });

        // register(ApplicationComponentGroup.class, new
        // Factory<EntityHeader>(EntityHeader.class) {
        // @Override
        // public EntityHeader create() {
        // final EntityHeaderPresenter presenter = new
        // EntityHeaderPresenter(i(StateManager.class),
        // i(WsThemeManager.class), i(Session.class));
        // final EntityHeaderPanel panel = new
        // EntityHeaderPanel(i(WorkspaceSkeleton.class),
        // p(FileDownloadUtils.class), i(Images.class),
        // i(GuiBindingsRegister.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });

        register(Singleton.class, new Factory<WsBackManager>(WsBackManager.class) {
            @Override
            public WsBackManager create() {
                return new WsBackManagerImpl(i(FileDownloadUtils.class));
            }
        });

        register(ApplicationComponentGroup.class, new Factory<WsThemeManager>(WsThemeManager.class) {
            @Override
            public WsThemeManager create() {
                final WsThemeManager presenter = new WsThemeManager(i(Session.class), p(GroupServiceAsync.class),
                        i(StateManager.class), i(WsBackManager.class));
                new WsThemeManagerPanel(presenter, i(WorkspaceSkeleton.class));
                return presenter;
            }
        });

        register(NoDecoration.class, new Factory<WsThemeSelector>(WsThemeSelector.class) {
            @Override
            public WsThemeSelector create() {
                final WsThemeSelectorPresenter presenter = new WsThemeSelectorPresenter(i(Session.class),
                        i(I18nTranslationService.class));
                presenter.init(new SimpleGuiItem(i(GuiBindingsRegister.class)));
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<EntityTitle>(EntityTitle.class) {
            @Override
            public EntityTitle create() {
                final EntityTitlePresenter presenter = new EntityTitlePresenter(i(StateManager.class),
                        i(Session.class), i(ContentIconsRegistry.class), i(RenamableRegistry.class),
                        i(RenameAction.class));
                final EntityTitlePanel panel = new EntityTitlePanel(i(WorkspaceSkeleton.class), presenter);
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<EntitySubTitle>(EntitySubTitle.class) {
            @Override
            public EntitySubTitle create() {
                final EntitySubTitlePresenter presenter = new EntitySubTitlePresenter(
                        i(I18nUITranslationService.class), i(StateManager.class), false, i(AuthorableRegistry.class));
                final EntitySubTitlePanel panel = new EntitySubTitlePanel(presenter, i(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        // register(ApplicationComponentGroup.class, new
        // Factory<EntityLicensePresenter>(EntityLicensePresenter.class) {
        // @Override
        // public EntityLicensePresenter create() {
        // final EntityLicensePresenter presenter = new
        // EntityLicensePresenter(i(StateManager.class));
        // final EntityLicensePanel panel = new EntityLicensePanel(presenter,
        // i(I18nUITranslationService.class),
        // i(WorkspaceSkeleton.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });

        register(ApplicationComponentGroup.class, new Factory<RateIt>(RateIt.class) {
            @Override
            public RateIt create() {
                final RateItPresenter presenter = new RateItPresenter(i(I18nUITranslationService.class),
                        i(Session.class), p(ContentServiceAsync.class), i(StateManager.class), p(RatePresenter.class),
                        i(ContentCapabilitiesRegistry.class));
                final RateItPanel panel = new RateItPanel(presenter, i(I18nUITranslationService.class),
                        i(WorkspaceSkeleton.class), i(Images.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<RatePresenter>(RatePresenter.class) {
            @Override
            public RatePresenter create() {
                final RatePresenter presenter = new RatePresenter(i(StateManager.class),
                        i(ContentCapabilitiesRegistry.class));
                final RatePanel panel = new RatePanel(null, null, i(I18nUITranslationService.class),
                        i(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ActionManager>(ActionManager.class) {
            @Override
            public ActionManager create() {
                return new ActionManager();
            }
        });

        register(Singleton.class, new Factory<ActionGroupSummaryToolbar>(ActionGroupSummaryToolbar.class) {
            @Override
            public ActionGroupSummaryToolbar create() {
                final ActionToolbarPanel<StateToken> panel = new ActionToolbarPanel<StateToken>(p(ActionManager.class));
                final ActionGroupSummaryToolbarPresenter toolbar = new ActionGroupSummaryToolbarPresenter(panel);
                return toolbar;
            }
        }, new Factory<ActionParticipationToolbar>(ActionParticipationToolbar.class) {
            @Override
            public ActionParticipationToolbar create() {
                final ActionToolbarPanel<StateToken> panel = new ActionToolbarPanel<StateToken>(p(ActionManager.class));
                final ActionParticipationSummaryToolbarPresenter toolbar = new ActionParticipationSummaryToolbarPresenter(
                        panel);
                return toolbar;
            }
        }, new Factory<ActionBuddiesSummaryToolbar>(ActionBuddiesSummaryToolbar.class) {
            @Override
            public ActionBuddiesSummaryToolbar create() {
                final ActionToolbarPanel<UserSimpleDTO> panel = new ActionToolbarPanel<UserSimpleDTO>(
                        p(ActionManager.class));
                final ActionBuddiesSummaryToolbarPresenter toolbar = new ActionBuddiesSummaryToolbarPresenter(panel);
                return toolbar;
            }
        });

        // register(ApplicationComponentGroup.class, new
        // Factory<GroupMembersSummary>(GroupMembersSummary.class) {
        // @Override
        // public GroupMembersSummary create() {
        // final GroupMembersSummaryPresenter presenter = new
        // GroupMembersSummaryPresenter(
        // i(I18nUITranslationService.class), i(StateManager.class),
        // i(ImageUtils.class),
        // i(Session.class), p(SocialNetworkServiceAsync.class),
        // p(GroupServiceAsync.class),
        // p(GroupLiveSearcher.class), p(ChatEngine.class),
        // i(GroupActionRegistry.class),
        // i(ActionGroupSummaryToolbar.class), p(FileDownloadUtils.class),
        // i(AccessRightsClientManager.class), i(IconResources.class));
        // final GroupMembersSummaryView view = new
        // GroupMembersSummaryPanel(presenter,
        // i(I18nUITranslationService.class), i(WorkspaceSkeleton.class), i(
        // ActionGroupSummaryToolbar.class).getView());
        // presenter.init(view);
        // return presenter;
        // }
        // });

        // register(ApplicationComponentGroup.class, new
        // Factory<AddAsBuddieHeaderButton>(AddAsBuddieHeaderButton.class) {
        // @Override
        // public AddAsBuddieHeaderButton create() {
        // return new AddAsBuddieHeaderButton(p(ChatEngine.class),
        // i(Session.class), i(StateManager.class),
        // i(I18nTranslationService.class), i(IconResources.class),
        // i(EntityHeader.class));
        // }
        // });

        // register(ApplicationComponentGroup.class, new
        // Factory<BuddiesSummary>(BuddiesSummary.class) {
        // @Override
        // public BuddiesSummary create() {
        // final BuddiesSummaryPresenter presenter = new
        // BuddiesSummaryPresenter(i(StateManager.class),
        // i(Session.class), p(UserServiceAsync.class),
        // i(UserActionRegistry.class),
        // i(I18nTranslationService.class), p(ChatEngine.class),
        // i(ActionBuddiesSummaryToolbar.class),
        // p(FileDownloadUtils.class), i(ImageUtils.class),
        // p(SocialNetworkServiceAsync.class),
        // i(GroupActionRegistry.class), i(AccessRightsClientManager.class),
        // i(IconResources.class));
        // final BuddiesSummaryPanel panel = new BuddiesSummaryPanel(presenter,
        // i(WorkspaceSkeleton.class),
        // i(I18nTranslationService.class), i(ActionManager.class),
        // i(ActionBuddiesSummaryToolbar.class).getView());
        // presenter.init(panel);
        // return presenter;
        // }
        // });

        // register(ApplicationComponentGroup.class, new
        // Factory<ParticipationSummary>(ParticipationSummary.class) {
        // @Override
        // public ParticipationSummary create() {
        // final ParticipationSummaryPresenter presenter = new
        // ParticipationSummaryPresenter(
        // i(I18nUITranslationService.class), i(StateManager.class),
        // i(ImageUtils.class),
        // i(Session.class), p(SocialNetworkServiceAsync.class),
        // i(GroupActionRegistry.class),
        // i(ActionParticipationToolbar.class), p(FileDownloadUtils.class),
        // i(AccessRightsClientManager.class), i(IconResources.class));
        // final ParticipationSummaryView view = new
        // ParticipationSummaryPanel(presenter,
        // i(I18nUITranslationService.class), i(WorkspaceSkeleton.class), i(
        // ActionParticipationToolbar.class).getView());
        // presenter.init(view);
        // return presenter;
        // }
        // });

        register(ApplicationComponentGroup.class, new Factory<TagsSummary>(TagsSummary.class) {
            @Override
            public TagsSummary create() {
                final TagsSummaryPresenter presenter = new TagsSummaryPresenter(i(Session.class),
                        p(SiteSearcher.class), i(StateManager.class));
                final TagsSummaryPanel panel = new TagsSummaryPanel(presenter, i(I18nUITranslationService.class),
                        i(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<NoHomePage>(NoHomePage.class) {
            @Override
            public NoHomePage create() {
                final NoHomePagePresenter presenter = new NoHomePagePresenter(i(StateManager.class),
                        p(EntityHeader.class));
                final NoHomePagePanel panel = new NoHomePagePanel(presenter, i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<GroupOptions>(GroupOptions.class) {
            @Override
            public GroupOptions create() {
                final GroupOptionsPresenter presenter = new GroupOptionsPresenter(i(StateManager.class),
                        i(I18nTranslationService.class), i(IconResources.class));
                final GroupOptionsPanel panel = new GroupOptionsPanel(presenter, i(EntityHeader.class),
                        i(I18nTranslationService.class), i(NotifyLevelImages.class), i(GroupOptionsCollection.class));
                presenter.init(panel);
                return presenter;
            }
        });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<UserOptions>(UserOptions.class) {
        // @Override
        // public UserOptions create() {
        // final UserOptionsPresenter presenter = new
        // UserOptionsPresenter(i(Session.class),
        // i(StateManager.class), i(I18nTranslationService.class),
        // i(IconResources.class),
        // i(SiteUserOptions.class));
        // final UserOptionsPanel panel = new UserOptionsPanel(presenter,
        // i(EntityHeader.class),
        // i(I18nTranslationService.class), i(NotifyLevelImages.class),
        // i(UserOptionsCollection.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });

        register(GroupOptionsCollection.class, new Factory<EntityOptionsToolsConf>(EntityOptionsToolsConf.class) {
            @Override
            public EntityOptionsToolsConf create() {
                final GroupOptionsToolsConfPresenter presenter = new GroupOptionsToolsConfPresenter(
                        i(StateManager.class), i(Session.class), i(I18nTranslationService.class),
                        i(GroupOptions.class), p(GroupServiceAsync.class));
                final EntityOptionsToolsConfPanel panel = new EntityOptionsToolsConfPanel(presenter,
                        i(WorkspaceSkeleton.class), i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(UserOptionsCollection.class, new Factory<UserOptionsToolsConf>(UserOptionsToolsConf.class) {
            @Override
            public UserOptionsToolsConf create() {
                final UserOptionsToolsConfPresenter presenter = new UserOptionsToolsConfPresenter(i(Session.class),
                        i(StateManager.class), i(I18nTranslationService.class), i(UserOptions.class),
                        p(GroupServiceAsync.class));
                final EntityOptionsToolsConfPanel panel = new EntityOptionsToolsConfPanel(presenter,
                        i(WorkspaceSkeleton.class), i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(GroupOptionsCollection.class, new Factory<GroupOptionsLogo>(GroupOptionsLogo.class) {
            @Override
            public GroupOptionsLogo create() {
                final EntityOptionsLogoPresenter presenter = new GroupOptionsLogoPresenter(i(Session.class),
                        i(EntityHeader.class), i(GroupOptions.class), i(StateManager.class), p(UserServiceAsync.class),
                        p(ChatEngine.class));
                final GroupOptionsLogoPanel panel = new GroupOptionsLogoPanel(presenter, i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(UserOptionsCollection.class, new Factory<UserOptionsLogo>(UserOptionsLogo.class) {
            @Override
            public UserOptionsLogo create() {
                final EntityOptionsLogoPresenter presenter = new UserOptionsLogoPresenter(i(Session.class),
                        i(EntityHeader.class), i(UserOptions.class), i(StateManager.class), p(UserServiceAsync.class),
                        p(ChatEngine.class));
                final UserOptionsLogoPanel panel = new UserOptionsLogoPanel(presenter, i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(GroupOptionsCollection.class, new Factory<GroupOptionsDefLicense>(GroupOptionsDefLicense.class) {
            @Override
            public GroupOptionsDefLicense create() {
                final GroupOptionsDefLicensePresenter presenter = new GroupOptionsDefLicensePresenter(
                        i(GroupOptions.class), i(StateManager.class), i(Session.class), p(LicenseWizard.class),
                        p(LicenseChangeAction.class));
                final EntityOptionsDefLicensePanel panel = new EntityOptionsDefLicensePanel(presenter,
                        i(WorkspaceSkeleton.class), i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(UserOptionsCollection.class, new Factory<UserOptionsDefLicense>(UserOptionsDefLicense.class) {
            @Override
            public UserOptionsDefLicense create() {
                final UserOptionsDefLicensePresenter presenter = new UserOptionsDefLicensePresenter(
                        i(UserOptions.class), i(Session.class), p(LicenseWizard.class), p(LicenseChangeAction.class));
                final EntityOptionsDefLicensePanel panel = new EntityOptionsDefLicensePanel(presenter,
                        i(WorkspaceSkeleton.class), i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(GroupOptionsCollection.class, new Factory<GroupOptionsPublicSpaceConf>(
                GroupOptionsPublicSpaceConf.class) {
            @Override
            public GroupOptionsPublicSpaceConf create() {
                final WsThemeSelector themeSelector = i(WsThemeSelector.class);
                final GroupOptionsPublicSpaceConfPresenter presenter = new GroupOptionsPublicSpaceConfPresenter(
                        i(Session.class), i(StateManager.class), i(GroupOptions.class), i(WsThemeManager.class),
                        themeSelector, p(GroupServiceAsync.class), i(WsBackManager.class));
                final EntityOptionsPublicSpaceConfPanel panel = new EntityOptionsPublicSpaceConfPanel(presenter,
                        i(WorkspaceSkeleton.class), i(I18nTranslationService.class), themeSelector,
                        i(FileDownloadUtils.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(UserOptionsCollection.class,
                new Factory<UserOptionsPublicSpaceConf>(UserOptionsPublicSpaceConf.class) {
                    @Override
                    public UserOptionsPublicSpaceConf create() {
                        final WsThemeSelector themeSelector = i(WsThemeSelector.class);
                        final UserOptionsPublicSpaceConfPresenter presenter = new UserOptionsPublicSpaceConfPresenter(
                                i(Session.class), i(StateManager.class), i(UserOptions.class), i(WsThemeManager.class),
                                themeSelector, p(GroupServiceAsync.class), i(WsBackManager.class));
                        final EntityOptionsPublicSpaceConfPanel panel = new EntityOptionsPublicSpaceConfPanel(
                                presenter, i(WorkspaceSkeleton.class), i(I18nTranslationService.class), themeSelector,
                                i(FileDownloadUtils.class));
                        presenter.init(panel);
                        return presenter;
                    }
                });

        register(Singleton.class, new Factory<LicenseChangeAction>(LicenseChangeAction.class) {
            @Override
            public LicenseChangeAction create() {
                return new LicenseChangeAction(p(GroupServiceAsync.class), i(Session.class),
                        i(I18nTranslationService.class), i(StateManager.class));
            }
        });

        register(Singleton.class, new Factory<RenameAction>(RenameAction.class) {
            @Override
            public RenameAction create() {
                return new RenameAction(i(I18nTranslationService.class), i(Session.class), p(ContentServiceAsync.class));
            }
        });

        register(Singleton.class, new Factory<LicenseWizard>(LicenseWizard.class) {
            @Override
            public LicenseWizard create() {
                final LicenseWizardPresenter presenter = new LicenseWizardPresenter(
                        i(LicenseWizardFirstFormView.class), i(LicenseWizardSndFormView.class),
                        i(LicenseWizardTrdFormView.class), i(LicenseWizardFrdFormView.class), i(Session.class));
                final LicenseWizardPanel panel = new LicenseWizardPanel(presenter, i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<LicenseWizardFirstFormView>(LicenseWizardFirstFormView.class) {
            @Override
            public LicenseWizardFirstFormView create() {
                return new LicenseWizardFirstForm(i(I18nTranslationService.class));
            }
        });

        register(Singleton.class, new Factory<LicenseWizardSndFormView>(LicenseWizardSndFormView.class) {
            @Override
            public LicenseWizardSndFormView create() {
                return new LicenseWizardSndForm(i(I18nTranslationService.class));
            }
        });

        register(Singleton.class, new Factory<LicenseWizardTrdFormView>(LicenseWizardTrdFormView.class) {
            @Override
            public LicenseWizardTrdFormView create() {
                return new LicenseWizardTrdForm(i(Images.class), i(I18nTranslationService.class));
            }
        });

        register(Singleton.class, new Factory<LicenseWizardFrdFormView>(LicenseWizardFrdFormView.class) {
            @Override
            public LicenseWizardFrdFormView create() {
                return new LicenseWizardFrdForm(i(I18nTranslationService.class), i(Session.class));
            }
        });

        register(Singleton.class, new Factory<FileUploader>(FileUploader.class) {
            @Override
            public FileUploader create() {
                final FileUploaderPresenter presenter = new FileUploaderPresenter(i(Session.class));
                final FileUploaderDialog panel = new FileUploaderDialog(presenter, i(I18nUITranslationService.class),
                        i(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }

            @Override
            public void onAfterCreated(final FileUploader uploader) {
                i(ContextNavigator.class).addFileUploaderListener(uploader);
            }
        });

        register(Singleton.class, new Factory<SiteSearcher>(SiteSearcher.class) {
            @Override
            public SiteSearcher create() {
                final SiteSearcherPresenter presenter = new SiteSearcherPresenter(p(StateManager.class));
                final SiteSearcherView view = new SiteSearcherPanel(presenter, i(I18nTranslationService.class),
                        i(WorkspaceSkeleton.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<I18nTranslator>(I18nTranslator.class) {
            @Override
            public I18nTranslator create() {
                final I18nTranslatorPresenter presenter = new I18nTranslatorPresenter(i(Session.class),
                        i(I18nServiceAsync.class), i(I18nUITranslationService.class));
                final I18nTranslatorView view = new I18nTranslatorPanel(presenter, i(I18nTranslationService.class),
                        i(LanguageSelector.class), i(WorkspaceSkeleton.class), i(Images.class));
                presenter.init(view);
                return presenter;
            }
        });

        // register(ApplicationComponentGroup.class, new
        // Factory<SiteOptionsI18nTranslatorAction>(
        // SiteOptionsI18nTranslatorAction.class) {
        // @Override
        // public SiteOptionsI18nTranslatorAction create() {
        // return new SiteOptionsI18nTranslatorAction(i(SiteOptions.class),
        // i(I18nTranslationService.class),
        // i(IconResources.class), p(I18nTranslator.class));
        // }
        // });

        // register(Singleton.class, new Factory<SignIn>(SignIn.class) {
        // @Override
        // public SignIn create() {
        // final SignInPresenter presenter = new
        // SignInPresenter(i(Session.class), i(StateManager.class),
        // i(I18nUITranslationService.class), p(UserServiceAsync.class),
        // p(Register.class));
        // final SignInView panel = new SignInPanel(presenter,
        // i(I18nTranslationService.class),
        // i(WorkspaceSkeleton.class), i(NotifyLevelImages.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(Singleton.class, new Factory<Register>(Register.class) {
        // @Override
        // public Register create() {
        // final RegisterPresenter presenter = new
        // RegisterPresenter(i(Session.class), i(StateManager.class),
        // i(I18nUITranslationService.class), p(UserServiceAsync.class),
        // p(SignIn.class));
        // final RegisterView panel = new RegisterPanel(presenter,
        // i(I18nTranslationService.class),
        // i(WorkspaceSkeleton.class), i(Session.class),
        // i(NotifyLevelImages.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });

        // register(Singleton.class, new Factory<NewGroup>(NewGroup.class) {
        // @Override
        // public NewGroup create() {
        // final NewGroupPresenter presenter = new
        // NewGroupPresenter(i(I18nTranslationService.class),
        // i(Session.class), i(StateManager.class),
        // p(GroupServiceAsync.class));
        // final NewGroupPanel view = new NewGroupPanel(presenter,
        // i(I18nTranslationService.class),
        // p(LicenseWizard.class), i(NotifyLevelImages.class));
        // presenter.init(view);
        // return presenter;
        // }
        // });

        register(Singleton.class, new Factory<UserLiveSearcher>(UserLiveSearcher.class) {
            @Override
            public UserLiveSearcher create() {
                final UserLiveSearcherPresenter presenter = new UserLiveSearcherPresenter();
                final EntityLiveSearcherView view = new UserLiveSearcherPanel(presenter,
                        i(I18nTranslationService.class), i(FileDownloadUtils.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<GroupLiveSearcher>(GroupLiveSearcher.class) {
            @Override
            public GroupLiveSearcher create() {
                final GroupLiveSearcherPresenter presenter = new GroupLiveSearcherPresenter();
                final EntityLiveSearcherView view = new GroupLiveSearchPanel(presenter,
                        i(I18nTranslationService.class), i(FileDownloadUtils.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ToolSelector>(ToolSelector.class) {
            @Override
            public ToolSelector create() {
                final ToolSelectorPresenter presenter = new ToolSelectorPresenter(i(StateManager.class),
                        i(WsThemeManager.class));
                return presenter;
            }
        });

        register(Singleton.class, new Factory<LanguageSelector>(LanguageSelector.class) {
            @Override
            public LanguageSelector create() {
                final LanguageSelectorPresenter presenter = new LanguageSelectorPresenter(i(Session.class));
                final LanguageSelectorView view = new LanguageSelectorPanel(presenter, i(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });

        // Register of tokens like "signin", "newgroup", "translate" etcetera
        i(StateManager.class).addSiteToken(SiteCommonTokens.SIGNIN, new HistoryTokenCallback() {

            @Override
            public void onHistoryToken() {
                i(SignIn.class).doSignIn();
            }
        });

        i(StateManager.class).addSiteToken(SiteCommonTokens.REGISTER, new HistoryTokenCallback() {

            @Override
            public void onHistoryToken() {
                i(Register.class).doRegister();
            }
        });

        i(StateManager.class).addSiteToken(SiteCommonTokens.NEWGROUP, new HistoryTokenCallback() {

            @Override
            public void onHistoryToken() {
                i(NewGroup.class).doNewGroup();
            }
        });

        i(StateManager.class).addSiteToken(SiteCommonTokens.TRANSLATE, new HistoryTokenCallback() {

            @Override
            public void onHistoryToken() {
                i(I18nTranslator.class).doShowTranslator();
            }
        });

        register(NoDecoration.class, new Factory<ActionContentToolbar>(ActionContentToolbar.class) {
            @Override
            public ActionContentToolbar create() {
                final ActionCntCtxToolbarPanel<StateToken> tbar = new ActionCntCtxToolbarPanel<StateToken>(
                        AbstractFoldableContentActions.CONTENT_TOPBAR, p(ActionManager.class),
                        i(WorkspaceSkeleton.class));
                final ActionContentToolbar toolbar = new ActionContentToolbarPresenter(tbar);
                return toolbar;
            }
        });

        register(Singleton.class, new Factory<ContentEditor>(ContentEditor.class) {
            @Override
            public ContentEditor create() {
                final ContentEditorPresenter presenter = new ContentEditorPresenter(i(I18nTranslationService.class),
                        i(Session.class), i(RTEImgResources.class), p(InsertLinkDialog.class),
                        p(ColorWebSafePalette.class), p(EditHtmlDialog.class), p(InsertImageDialog.class),
                        p(InsertMediaDialog.class), p(InsertTableDialog.class), p(InsertSpecialCharDialog.class),
                        i(SchedulerManager.class), true, i(StateManager.class), i(SiteSignOutLink.class),
                        i(WorkspaceSkeleton.class), i(TimerWrapper.class), i(EntityTitle.class));
                final ContentEditorPanel panel = new ContentEditorPanel(presenter, i(I18nUITranslationService.class),
                        i(GlobalShortcutRegister.class), i(GuiBindingsRegister.class));
                presenter.init(panel);
                return presenter;
            }
        });
        register(NoDecoration.class, new Factory<ActionContextTopToolbar>(ActionContextTopToolbar.class) {
            @Override
            public ActionContextTopToolbar create() {
                final ActionCntCtxToolbarPanel<StateToken> panel = new ActionCntCtxToolbarPanel<StateToken>(
                        AbstractFoldableContentActions.CONTEXT_TOPBAR, p(ActionManager.class),
                        i(WorkspaceSkeleton.class));
                final ActionContextTopToolbar toolbar = new ActionContextTopToolbar(panel);
                return toolbar;
            }
        });

        register(NoDecoration.class, new Factory<ActionContextBottomToolbar>(ActionContextBottomToolbar.class) {
            @Override
            public ActionContextBottomToolbar create() {
                final ActionCntCtxToolbarPanel<StateToken> panel = new ActionCntCtxToolbarPanel<StateToken>(
                        AbstractFoldableContentActions.CONTEXT_BOTTOMBAR, p(ActionManager.class),
                        i(WorkspaceSkeleton.class));
                final ActionContextBottomToolbar toolbar = new ActionContextBottomToolbar(panel);
                return toolbar;
            }
        });

        register(Singleton.class, new Factory<ContextNavigator>(ContextNavigator.class) {
            @Override
            public ContextNavigator create() {
                final ContextNavigatorPresenter presenter = new ContextNavigatorPresenter(i(StateManager.class),
                        i(Session.class), i(I18nTranslationService.class), i(ContentIconsRegistry.class),
                        i(ContentCapabilitiesRegistry.class), i(ActionContextTopToolbar.class),
                        i(ActionContextBottomToolbar.class), i(ContextActionRegistry.class),
                        p(FileDownloadUtils.class), true, i(RenameAction.class));
                final ContextNavigatorPanel panel = new ContextNavigatorPanel(presenter,
                        i(I18nTranslationService.class), i(WorkspaceSkeleton.class), i(ActionManager.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ContextPropEditor>(ContextPropEditor.class) {
            @Override
            public ContextPropEditor create() {
                final ContextPropEditorPresenter presenter = new ContextPropEditorPresenter(i(Session.class),
                        i(StateManager.class), i(ContentCapabilitiesRegistry.class), p(TagsSummary.class),
                        p(ContentServiceAsync.class), i(EntitySubTitle.class));
                final ContextPropEditorView view = new ContextPropEditorPanel(presenter,
                        i(I18nUITranslationService.class), i(WorkspaceSkeleton.class), p(LanguageSelector.class),
                        i(Images.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(InsertImageGroup.class, new Factory<InsertImageLocal>(InsertImageLocal.class) {
            @Override
            public InsertImageLocal create() {
                final InsertImageLocalPresenter presenter = new InsertImageLocalPresenter(i(InsertImageDialog.class),
                        i(Session.class));
                final InsertImageLocalPanel panel = new InsertImageLocalPanel(presenter,
                        i(I18nTranslationService.class), i(FileDownloadUtils.class), i(Session.class));
                presenter.init(panel);
                return presenter;
            }
        });

        // register(ApplicationComponentGroup.class, new
        // Factory<MaxMinWorkspace>(MaxMinWorkspace.class) {
        // @Override
        // public MaxMinWorkspace create() {
        // final MaxMinWorkspacePresenter presenter = new
        // MaxMinWorkspacePresenter(
        // i(GlobalShortcutRegister.class), i(IconResources.class),
        // i(I18nTranslationService.class),
        // i(SiteOptions.class));
        // final MaxMinWorkspacePanel panel = new
        // MaxMinWorkspacePanel(i(WorkspaceSkeleton.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });

        register(InsertLinkGroup.class, new Factory<InsertLinkLocal>(InsertLinkLocal.class) {
            @Override
            public InsertLinkLocal create() {
                final InsertLinkLocalPresenter presenter = new InsertLinkLocalPresenter(i(InsertLinkDialog.class));
                final InsertLinkLocalPanel panel = new InsertLinkLocalPanel(presenter, i(WorkspaceSkeleton.class),
                        i(I18nTranslationService.class), i(FileDownloadUtils.class), i(StateTokenUtils.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(InsertMediaGroup.class, new Factory<InsertMediaLocal>(InsertMediaLocal.class) {
            @Override
            public InsertMediaLocal create() {
                final InsertMediaLocalPresenter presenter = new InsertMediaLocalPresenter(i(InsertMediaDialog.class),
                        i(Session.class), p(MediaUtils.class));
                final InsertMediaLocalPanel panel = new InsertMediaLocalPanel(presenter,
                        i(I18nTranslationService.class), i(FileDownloadUtils.class));
                presenter.init(panel);
                return presenter;
            }
        });
        i(GlobalShortcutRegister.class).enable();
    }

}
