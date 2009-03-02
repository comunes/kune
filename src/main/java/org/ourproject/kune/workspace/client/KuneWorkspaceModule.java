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
package org.ourproject.kune.workspace.client;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.actions.ContextActionRegistry;
import org.ourproject.kune.platf.client.actions.GroupActionRegistry;
import org.ourproject.kune.platf.client.actions.UserActionRegistry;
import org.ourproject.kune.platf.client.actions.toolbar.ActionBuddiesSummaryToolbar;
import org.ourproject.kune.platf.client.actions.toolbar.ActionBuddiesSummaryToolbarPresenter;
import org.ourproject.kune.platf.client.actions.toolbar.ActionCntCtxToolbarPanel;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbar;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContentToolbarPresenter;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContextToolbar;
import org.ourproject.kune.platf.client.actions.toolbar.ActionContextToolbarPresenter;
import org.ourproject.kune.platf.client.actions.toolbar.ActionGroupSummaryToolbar;
import org.ourproject.kune.platf.client.actions.toolbar.ActionGroupSummaryToolbarPresenter;
import org.ourproject.kune.platf.client.actions.toolbar.ActionParticipationSummaryToolbarPresenter;
import org.ourproject.kune.platf.client.actions.toolbar.ActionParticipationToolbar;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPanel;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbarPresenter;
import org.ourproject.kune.platf.client.app.ApplicationComponentGroup;
import org.ourproject.kune.platf.client.app.EntityOptionsGroup;
import org.ourproject.kune.platf.client.app.TextEditorInsertElementGroup;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.registry.AuthorableRegistry;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.registry.RenamableRegistry;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.rpc.ContentServiceAsync;
import org.ourproject.kune.platf.client.rpc.GroupService;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.rpc.I18nServiceAsync;
import org.ourproject.kune.platf.client.rpc.SocialNetworkService;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.I18nUITranslationService;
import org.ourproject.kune.platf.client.services.ImageUtils;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.upload.FileUploader;
import org.ourproject.kune.platf.client.ui.upload.FileUploaderDialog;
import org.ourproject.kune.platf.client.ui.upload.FileUploaderPresenter;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigatorPanel;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigatorPresenter;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditor;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditorPanel;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditorPresenter;
import org.ourproject.kune.workspace.client.cxt.ContextPropEditorView;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorPanel;
import org.ourproject.kune.workspace.client.editor.TextEditorPresenter;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElement;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElementPanel;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElementPresenter;
import org.ourproject.kune.workspace.client.editor.insert.linkemail.TextEditorInsertLinkEmail;
import org.ourproject.kune.workspace.client.editor.insert.linkemail.TextEditorInsertLinkEmailPanel;
import org.ourproject.kune.workspace.client.editor.insert.linkemail.TextEditorInsertLinkEmailPresenter;
import org.ourproject.kune.workspace.client.editor.insert.linkext.TextEditorInsertLinkExt;
import org.ourproject.kune.workspace.client.editor.insert.linkext.TextEditorInsertLinkExtPanel;
import org.ourproject.kune.workspace.client.editor.insert.linkext.TextEditorInsertLinkExtPresenter;
import org.ourproject.kune.workspace.client.editor.insert.linkext.TextEditorInsertLinkExtView;
import org.ourproject.kune.workspace.client.editor.insert.linklocal.TextEditorInsertLinkLocal;
import org.ourproject.kune.workspace.client.editor.insert.linklocal.TextEditorInsertLinkLocalPanel;
import org.ourproject.kune.workspace.client.editor.insert.linklocal.TextEditorInsertLinkLocalPresenter;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.entityheader.EntityHeaderPanel;
import org.ourproject.kune.workspace.client.entityheader.EntityHeaderPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslator;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPanel;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorView;
import org.ourproject.kune.workspace.client.i18n.LanguageSelector;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorPanel;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorPresenter;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorView;
import org.ourproject.kune.workspace.client.licensefoot.EntityLicensePanel;
import org.ourproject.kune.workspace.client.licensefoot.EntityLicensePresenter;
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
import org.ourproject.kune.workspace.client.newgroup.NewGroup;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPanel;
import org.ourproject.kune.workspace.client.newgroup.NewGroupPresenter;
import org.ourproject.kune.workspace.client.nohomepage.NoHomePage;
import org.ourproject.kune.workspace.client.nohomepage.NoHomePagePanel;
import org.ourproject.kune.workspace.client.nohomepage.NoHomePagePresenter;
import org.ourproject.kune.workspace.client.options.EntityOptions;
import org.ourproject.kune.workspace.client.options.EntityOptionsPanel;
import org.ourproject.kune.workspace.client.options.EntityOptionsPresenter;
import org.ourproject.kune.workspace.client.options.license.EntityOptionsDefLicense;
import org.ourproject.kune.workspace.client.options.license.EntityOptionsDefLicensePanel;
import org.ourproject.kune.workspace.client.options.license.EntityOptionsDefLicensePresenter;
import org.ourproject.kune.workspace.client.options.logo.EntityOptionsLogo;
import org.ourproject.kune.workspace.client.options.logo.EntityOptionsLogoPanel;
import org.ourproject.kune.workspace.client.options.logo.EntityOptionsLogoPresenter;
import org.ourproject.kune.workspace.client.options.pscape.EntityOptionsPublicSpaceConf;
import org.ourproject.kune.workspace.client.options.pscape.EntityOptionsPublicSpaceConfPanel;
import org.ourproject.kune.workspace.client.options.pscape.EntityOptionsPublicSpaceConfPresenter;
import org.ourproject.kune.workspace.client.options.tools.EntityOptionsToolsConf;
import org.ourproject.kune.workspace.client.options.tools.EntityOptionsToolsConfPanel;
import org.ourproject.kune.workspace.client.options.tools.EntityOptionsToolsConfPresenter;
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
import org.ourproject.kune.workspace.client.signin.Register;
import org.ourproject.kune.workspace.client.signin.RegisterPanel;
import org.ourproject.kune.workspace.client.signin.RegisterPresenter;
import org.ourproject.kune.workspace.client.signin.RegisterView;
import org.ourproject.kune.workspace.client.signin.SignIn;
import org.ourproject.kune.workspace.client.signin.SignInPanel;
import org.ourproject.kune.workspace.client.signin.SignInPresenter;
import org.ourproject.kune.workspace.client.signin.SignInView;
import org.ourproject.kune.workspace.client.site.SiteToken;
import org.ourproject.kune.workspace.client.site.WorkspaceNotifyUser;
import org.ourproject.kune.workspace.client.site.msg.SiteToastMessage;
import org.ourproject.kune.workspace.client.site.msg.SiteToastMessagePanel;
import org.ourproject.kune.workspace.client.site.msg.SiteToastMessagePresenter;
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
import org.ourproject.kune.workspace.client.socialnet.other.AddAsBuddie;
import org.ourproject.kune.workspace.client.socialnet.other.AddAsBuddiePanel;
import org.ourproject.kune.workspace.client.socialnet.other.AddAsBuddiePresenter;
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
import org.ourproject.kune.workspace.client.title.RenameAction;
import org.ourproject.kune.workspace.client.tool.ToolSelector;
import org.ourproject.kune.workspace.client.tool.ToolSelectorPresenter;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.decorator.NoDecoration;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class KuneWorkspaceModule extends AbstractModule {
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
                final SiteProgressPanel panel = new SiteProgressPanel(presenter, $$(SitePublicSpaceLink.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<WorkspaceNotifyUser>(WorkspaceNotifyUser.class) {
            @Override
            public WorkspaceNotifyUser create() {
                return new WorkspaceNotifyUser($(NotifyUser.class), $(I18nUITranslationService.class),
                        $(SiteProgress.class), $$(SiteToastMessage.class), $$(WorkspaceSkeleton.class));
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

        register(NoDecoration.class, new Factory<SiteToastMessage>(SiteToastMessage.class) {
            @Override
            public SiteToastMessage create() {
                final SiteToastMessagePresenter presenter = new SiteToastMessagePresenter();
                final SiteToastMessagePanel panel = new SiteToastMessagePanel();
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<SiteUserMenu>(SiteUserMenu.class) {
            @Override
            public SiteUserMenu create() {
                final SiteUserMenuPresenter presenter = new SiteUserMenuPresenter($(Session.class),
                        $(StateManager.class), $$(EntityOptions.class), $$(FileDownloadUtils.class));
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
                final SiteSearchPresenter presenter = new SiteSearchPresenter($$(SiteSearcher.class),
                        $(I18nTranslationService.class));
                final SiteSearchPanel panel = new SiteSearchPanel(presenter, $(WorkspaceSkeleton.class),
                        $(Images.class));
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

        register(ApplicationComponentGroup.class, new Factory<EntityHeader>(EntityHeader.class) {
            @Override
            public EntityHeader create() {
                final EntityHeaderPresenter presenter = new EntityHeaderPresenter($(StateManager.class),
                        $(WsThemePresenter.class), $(Session.class));
                final EntityHeaderPanel panel = new EntityHeaderPanel($(WorkspaceSkeleton.class),
                        $$(FileDownloadUtils.class), $(Images.class));
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
                final EntityTitlePresenter presenter = new EntityTitlePresenter($(StateManager.class),
                        $(Session.class), $(ContentIconsRegistry.class), $(RenamableRegistry.class),
                        $(RenameAction.class));
                final EntityTitlePanel panel = new EntityTitlePanel($(WorkspaceSkeleton.class), presenter);
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<EntitySubTitle>(EntitySubTitle.class) {
            @Override
            public EntitySubTitle create() {
                final EntitySubTitlePresenter presenter = new EntitySubTitlePresenter(
                        $(I18nUITranslationService.class), $(StateManager.class), false, $(AuthorableRegistry.class));
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
                        $(Session.class), $$(ContentServiceAsync.class), $(StateManager.class),
                        $$(RatePresenter.class), $(ContentCapabilitiesRegistry.class));
                final RateItPanel panel = new RateItPanel(presenter, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class), $(Images.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<RatePresenter>(RatePresenter.class) {
            @Override
            public RatePresenter create() {
                final RatePresenter presenter = new RatePresenter($(StateManager.class),
                        $(ContentCapabilitiesRegistry.class));
                final RatePanel panel = new RatePanel(null, null, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ActionManager>(ActionManager.class) {
            @Override
            public ActionManager create() {
                return new ActionManager($(WorkspaceSkeleton.class));
            }
        });

        register(Singleton.class, new Factory<ActionGroupSummaryToolbar>(ActionGroupSummaryToolbar.class) {
            @Override
            public ActionGroupSummaryToolbar create() {
                final ActionToolbarPanel<StateToken> panel = new ActionToolbarPanel<StateToken>(
                        $$(ActionManager.class), $(WorkspaceSkeleton.class));
                final ActionGroupSummaryToolbarPresenter toolbar = new ActionGroupSummaryToolbarPresenter(panel);
                return toolbar;
            }
        }, new Factory<ActionParticipationToolbar>(ActionParticipationToolbar.class) {
            @Override
            public ActionParticipationToolbar create() {
                final ActionToolbarPanel<StateToken> panel = new ActionToolbarPanel<StateToken>(
                        $$(ActionManager.class), $(WorkspaceSkeleton.class));
                final ActionParticipationSummaryToolbarPresenter toolbar = new ActionParticipationSummaryToolbarPresenter(
                        panel);
                return toolbar;
            }
        }, new Factory<ActionBuddiesSummaryToolbar>(ActionBuddiesSummaryToolbar.class) {
            @Override
            public ActionBuddiesSummaryToolbar create() {
                final ActionToolbarPanel<UserSimpleDTO> panel = new ActionToolbarPanel<UserSimpleDTO>(
                        $$(ActionManager.class), $(WorkspaceSkeleton.class));
                final ActionBuddiesSummaryToolbarPresenter toolbar = new ActionBuddiesSummaryToolbarPresenter(panel);
                return toolbar;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<GroupMembersSummary>(GroupMembersSummary.class) {
            @Override
            public GroupMembersSummary create() {
                final GroupMembersSummaryPresenter presenter = new GroupMembersSummaryPresenter(
                        $(I18nUITranslationService.class), $(StateManager.class), $(ImageUtils.class),
                        $(Session.class), $$(SocialNetworkServiceAsync.class), $$(GroupServiceAsync.class),
                        $$(GroupLiveSearcher.class), $(WsThemePresenter.class), $$(ChatEngine.class),
                        $(GroupActionRegistry.class), $(ActionGroupSummaryToolbar.class), $$(FileDownloadUtils.class));
                final GroupMembersSummaryView view = new GroupMembersSummaryPanel(presenter,
                        $(I18nUITranslationService.class), $(WorkspaceSkeleton.class), $(
                                ActionGroupSummaryToolbar.class).getView());
                presenter.init(view);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<AddAsBuddie>(AddAsBuddie.class) {
            @Override
            public AddAsBuddie create() {
                final AddAsBuddiePresenter presenter = new AddAsBuddiePresenter($$(ChatEngine.class),
                        $(StateManager.class), $(Session.class));
                final AddAsBuddiePanel panel = new AddAsBuddiePanel(presenter, $(EntityHeader.class), $(Images.class),
                        $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<BuddiesSummary>(BuddiesSummary.class) {
            @Override
            public BuddiesSummary create() {
                final BuddiesSummaryPresenter presenter = new BuddiesSummaryPresenter($(StateManager.class),
                        $(Session.class), $$(UserServiceAsync.class), $(UserActionRegistry.class),
                        $(I18nTranslationService.class), $$(ChatEngine.class), $(ActionBuddiesSummaryToolbar.class),
                        $$(FileDownloadUtils.class), $(ImageUtils.class), $$(SocialNetworkServiceAsync.class),
                        $(GroupActionRegistry.class));
                final BuddiesSummaryPanel panel = new BuddiesSummaryPanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class), $(ActionManager.class),
                        $(ActionBuddiesSummaryToolbar.class).getView());
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<ParticipationSummary>(ParticipationSummary.class) {
            @Override
            public ParticipationSummary create() {
                final ParticipationSummaryPresenter presenter = new ParticipationSummaryPresenter(
                        $(I18nUITranslationService.class), $(StateManager.class), $(ImageUtils.class),
                        $(Session.class), $$(SocialNetworkServiceAsync.class), $(WsThemePresenter.class),
                        $(GroupActionRegistry.class), $(ActionParticipationToolbar.class), $$(FileDownloadUtils.class));
                final ParticipationSummaryView view = new ParticipationSummaryPanel(presenter,
                        $(I18nUITranslationService.class), $(WorkspaceSkeleton.class),
                        ($(ActionParticipationToolbar.class).getView()));
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

        register(ApplicationComponentGroup.class, new Factory<NoHomePage>(NoHomePage.class) {
            @Override
            public NoHomePage create() {
                final NoHomePagePresenter presenter = new NoHomePagePresenter($(StateManager.class),
                        $$(EntityHeader.class));
                final NoHomePagePanel panel = new NoHomePagePanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(ApplicationComponentGroup.class, new Factory<EntityOptions>(EntityOptions.class) {
            @Override
            public EntityOptions create() {
                final EntityOptionsPresenter presenter = new EntityOptionsPresenter($(StateManager.class));
                final EntityOptionsPanel panel = new EntityOptionsPanel(presenter, $(EntityHeader.class),
                        $(I18nTranslationService.class), $(Images.class), $(EntityOptionsGroup.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(EntityOptionsGroup.class, new Factory<EntityOptionsToolsConf>(EntityOptionsToolsConf.class) {
            @Override
            public EntityOptionsToolsConf create() {
                final EntityOptionsToolsConfPresenter presenter = new EntityOptionsToolsConfPresenter(
                        $(StateManager.class), $(Session.class), $(I18nTranslationService.class),
                        $(EntityOptions.class), $$(GroupServiceAsync.class));
                final EntityOptionsToolsConfPanel panel = new EntityOptionsToolsConfPanel(presenter,
                        $(WorkspaceSkeleton.class), $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(EntityOptionsGroup.class, new Factory<EntityOptionsLogo>(EntityOptionsLogo.class) {
            @Override
            public EntityOptionsLogo create() {
                final EntityOptionsLogoPresenter presenter = new EntityOptionsLogoPresenter($(Session.class),
                        $(EntityHeader.class), $(EntityOptions.class), $(StateManager.class),
                        $$(UserServiceAsync.class), $$(ChatEngine.class));
                final EntityOptionsLogoPanel panel = new EntityOptionsLogoPanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(EntityOptionsGroup.class, new Factory<EntityOptionsDefLicense>(EntityOptionsDefLicense.class) {
            @Override
            public EntityOptionsDefLicense create() {
                final EntityOptionsDefLicensePresenter presenter = new EntityOptionsDefLicensePresenter(
                        $(EntityOptions.class), $(StateManager.class), $(Session.class), $$(LicenseWizard.class),
                        $$(LicenseChangeAction.class));
                final EntityOptionsDefLicensePanel panel = new EntityOptionsDefLicensePanel(presenter,
                        $(WorkspaceSkeleton.class), $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(EntityOptionsGroup.class,
                new Factory<EntityOptionsPublicSpaceConf>(EntityOptionsPublicSpaceConf.class) {
                    @Override
                    public EntityOptionsPublicSpaceConf create() {
                        final EntityOptionsPublicSpaceConfPresenter presenter = new EntityOptionsPublicSpaceConfPresenter(
                                $(EntityOptions.class));
                        final EntityOptionsPublicSpaceConfPanel panel = new EntityOptionsPublicSpaceConfPanel(
                                presenter, $(WorkspaceSkeleton.class), $(I18nTranslationService.class),
                                $(WsThemePresenter.class));
                        presenter.init(panel);
                        return presenter;
                    }
                });

        register(Singleton.class, new Factory<LicenseChangeAction>(LicenseChangeAction.class) {
            @Override
            public LicenseChangeAction create() {
                return new LicenseChangeAction($$(GroupServiceAsync.class), $(Session.class),
                        $(I18nTranslationService.class), $(StateManager.class));
            }
        });

        register(Singleton.class, new Factory<RenameAction>(RenameAction.class) {
            @Override
            public RenameAction create() {
                return new RenameAction($(I18nTranslationService.class), $(Session.class),
                        $$(ContentServiceAsync.class));
            }
        });

        register(Singleton.class, new Factory<LicenseWizard>(LicenseWizard.class) {
            @Override
            public LicenseWizard create() {
                final LicenseWizardPresenter presenter = new LicenseWizardPresenter(
                        $(LicenseWizardFirstFormView.class), $(LicenseWizardSndFormView.class),
                        $(LicenseWizardTrdFormView.class), $(LicenseWizardFrdFormView.class), $(Session.class));
                final LicenseWizardPanel panel = new LicenseWizardPanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<LicenseWizardFirstFormView>(LicenseWizardFirstFormView.class) {
            @Override
            public LicenseWizardFirstFormView create() {
                return new LicenseWizardFirstForm($(I18nTranslationService.class));
            }
        });

        register(Singleton.class, new Factory<LicenseWizardSndFormView>(LicenseWizardSndFormView.class) {
            @Override
            public LicenseWizardSndFormView create() {
                return new LicenseWizardSndForm($(I18nTranslationService.class));
            }
        });

        register(Singleton.class, new Factory<LicenseWizardTrdFormView>(LicenseWizardTrdFormView.class) {
            @Override
            public LicenseWizardTrdFormView create() {
                return new LicenseWizardTrdForm($(Images.class), $(I18nTranslationService.class));
            }
        });

        register(Singleton.class, new Factory<LicenseWizardFrdFormView>(LicenseWizardFrdFormView.class) {
            @Override
            public LicenseWizardFrdFormView create() {
                return new LicenseWizardFrdForm($(I18nTranslationService.class), $(Session.class));
            }
        });

        register(Singleton.class, new Factory<FileUploader>(FileUploader.class) {
            @Override
            public FileUploader create() {
                final FileUploaderPresenter presenter = new FileUploaderPresenter($(Session.class));
                final FileUploaderDialog panel = new FileUploaderDialog(presenter, $(I18nUITranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(panel);
                return presenter;
            }

            @Override
            public void onAfterCreated(FileUploader uploader) {
                $(ContextNavigator.class).addFileUploaderListener(uploader);
            }
        });

        register(Singleton.class, new Factory<SiteSearcher>(SiteSearcher.class) {
            @Override
            public SiteSearcher create() {
                final SiteSearcherPresenter presenter = new SiteSearcherPresenter($$(StateManager.class));
                final SiteSearcherView view = new SiteSearcherPanel(presenter, $(I18nTranslationService.class),
                        $(WorkspaceSkeleton.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<I18nTranslator>(I18nTranslator.class) {
            @Override
            public I18nTranslator create() {
                final I18nTranslatorPresenter presenter = new I18nTranslatorPresenter($(Session.class),
                        $(I18nServiceAsync.class), $(I18nUITranslationService.class));
                final I18nTranslatorView view = new I18nTranslatorPanel(presenter, $(I18nTranslationService.class),
                        $(LanguageSelector.class), $(WorkspaceSkeleton.class), $(Images.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<SignIn>(SignIn.class) {
            @Override
            public SignIn create() {
                final SignInPresenter presenter = new SignInPresenter($(Session.class), $(StateManager.class),
                        $(I18nUITranslationService.class), $$(UserServiceAsync.class), $$(Register.class));
                final SignInView panel = new SignInPanel(presenter, $(I18nTranslationService.class),
                        $(WorkspaceSkeleton.class), $(Images.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<Register>(Register.class) {
            @Override
            public Register create() {
                final RegisterPresenter presenter = new RegisterPresenter($(Session.class), $(StateManager.class),
                        $(I18nUITranslationService.class), $$(UserServiceAsync.class), $$(SignIn.class));
                final RegisterView panel = new RegisterPanel(presenter, $(I18nTranslationService.class),
                        $(WorkspaceSkeleton.class), $(Session.class), $(Images.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<NewGroup>(NewGroup.class) {
            @Override
            public NewGroup create() {
                final NewGroupPresenter presenter = new NewGroupPresenter($(I18nTranslationService.class),
                        $(Session.class), $(StateManager.class), $$(GroupServiceAsync.class));
                final NewGroupPanel view = new NewGroupPanel(presenter, $(I18nTranslationService.class),
                        $$(LicenseWizard.class), $(Images.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<UserLiveSearcher>(UserLiveSearcher.class) {
            @Override
            public UserLiveSearcher create() {
                final UserLiveSearcherPresenter presenter = new UserLiveSearcherPresenter();
                final EntityLiveSearcherView view = new UserLiveSearcherPanel(presenter,
                        $(I18nTranslationService.class), $(FileDownloadUtils.class));
                presenter.init(view);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<GroupLiveSearcher>(GroupLiveSearcher.class) {
            @Override
            public GroupLiveSearcher create() {
                final GroupLiveSearcherPresenter presenter = new GroupLiveSearcherPresenter();
                final EntityLiveSearcherView view = new GroupLiveSearchPanel(presenter,
                        $(I18nTranslationService.class), $(FileDownloadUtils.class));
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

        register(Singleton.class, new Factory<TextEditorInsertElement>(TextEditorInsertElement.class) {
            @Override
            public TextEditorInsertElement create() {
                final TextEditorInsertElementPresenter presenter = new TextEditorInsertElementPresenter();
                final TextEditorInsertElementPanel panel = new TextEditorInsertElementPanel(presenter,
                        $(WorkspaceSkeleton.class), $(Images.class), $(I18nTranslationService.class),
                        $(TextEditorInsertElementGroup.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(TextEditorInsertElementGroup.class, new Factory<TextEditorInsertLinkLocal>(
                TextEditorInsertLinkLocal.class) {
            @Override
            public TextEditorInsertLinkLocal create() {
                final TextEditorInsertLinkLocalPresenter presenter = new TextEditorInsertLinkLocalPresenter(
                        $(TextEditorInsertElement.class));
                final TextEditorInsertLinkLocalPanel panel = new TextEditorInsertLinkLocalPanel(presenter,
                        $(WorkspaceSkeleton.class), $(I18nTranslationService.class), $(FileDownloadUtils.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(TextEditorInsertElementGroup.class,
                new Factory<TextEditorInsertLinkExt>(TextEditorInsertLinkExt.class) {
                    @Override
                    public TextEditorInsertLinkExt create() {
                        final TextEditorInsertLinkExtPresenter presenter = new TextEditorInsertLinkExtPresenter(
                                $(TextEditorInsertElement.class));
                        final TextEditorInsertLinkExtView panel = new TextEditorInsertLinkExtPanel(presenter,
                                $(I18nTranslationService.class));
                        presenter.init(panel);
                        return presenter;
                    }
                });

        register(TextEditorInsertElementGroup.class, new Factory<TextEditorInsertLinkEmail>(
                TextEditorInsertLinkEmail.class) {
            @Override
            public TextEditorInsertLinkEmail create() {
                final TextEditorInsertLinkEmailPresenter presenter = new TextEditorInsertLinkEmailPresenter(
                        $(TextEditorInsertElement.class));
                final TextEditorInsertLinkEmailPanel panel = new TextEditorInsertLinkEmailPanel(presenter,
                        $(I18nTranslationService.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<TextEditor>(TextEditor.class) {
            @Override
            public TextEditor create() {
                final ActionCntCtxToolbarPanel<StateToken> contentNavigatorToolbar = new ActionCntCtxToolbarPanel<StateToken>(
                        ActionCntCtxToolbarPanel.Position.content, $$(ActionManager.class), $(WorkspaceSkeleton.class));
                final ActionToolbar<StateToken> toolbar = new ActionToolbarPresenter<StateToken>(
                        contentNavigatorToolbar);

                final TextEditorPresenter presenter = new TextEditorPresenter(true, toolbar,
                        $(I18nUITranslationService.class), $(StateManager.class), $(SiteSignOutLink.class),
                        $(DeferredCommandWrapper.class));
                final TextEditorPanel panel = new TextEditorPanel(presenter, $(I18nTranslationService.class),
                        $(WorkspaceSkeleton.class), $(ColorWebSafePalette.class), $(TextEditorInsertElement.class),
                        false);
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<LanguageSelector>(LanguageSelector.class) {
            @Override
            public LanguageSelector create() {
                final LanguageSelectorPresenter presenter = new LanguageSelectorPresenter($(Session.class));
                final LanguageSelectorView view = new LanguageSelectorPanel(presenter, $(I18nTranslationService.class));
                presenter.init(view);
                return presenter;
            }
        });

        // Register of tokens like "signin", "newgroup", "translate" etcetera
        $(StateManager.class).addSiteToken(SiteToken.signin.toString(), new Listener0() {
            public void onEvent() {
                $(SignIn.class).doSignIn();
            }
        });

        $(StateManager.class).addSiteToken(SiteToken.register.toString(), new Listener0() {
            public void onEvent() {
                $(Register.class).doRegister();
            }
        });

        $(StateManager.class).addSiteToken(SiteToken.newgroup.toString(), new Listener0() {
            public void onEvent() {
                $(NewGroup.class).doNewGroup();
            }
        });

        $(StateManager.class).addSiteToken(SiteToken.translate.toString(), new Listener0() {
            public void onEvent() {
                $(I18nTranslator.class).doShowTranslator();
            }
        });

        register(NoDecoration.class, new Factory<ActionContentToolbar>(ActionContentToolbar.class) {
            @Override
            public ActionContentToolbar create() {
                final ActionCntCtxToolbarPanel<StateToken> contentNavigatorToolbar = new ActionCntCtxToolbarPanel<StateToken>(
                        ActionCntCtxToolbarPanel.Position.content, $$(ActionManager.class), $(WorkspaceSkeleton.class));
                final ActionContentToolbar toolbar = new ActionContentToolbarPresenter(contentNavigatorToolbar);
                return toolbar;
            }
        });

        register(NoDecoration.class, new Factory<ActionContextToolbar>(ActionContextToolbar.class) {
            @Override
            public ActionContextToolbar create() {
                final ActionCntCtxToolbarPanel<StateToken> contentNavigatorToolbar = new ActionCntCtxToolbarPanel<StateToken>(
                        ActionCntCtxToolbarPanel.Position.context, $$(ActionManager.class), $(WorkspaceSkeleton.class));
                final ActionContextToolbar toolbar = new ActionContextToolbarPresenter(contentNavigatorToolbar);
                return toolbar;
            }
        });

        register(Singleton.class, new Factory<ContextNavigator>(ContextNavigator.class) {
            @Override
            public ContextNavigator create() {
                final ContextNavigatorPresenter presenter = new ContextNavigatorPresenter($(StateManager.class),
                        $(Session.class), $(I18nTranslationService.class), $(ContentIconsRegistry.class),
                        $(ContentCapabilitiesRegistry.class), $(ActionContextToolbar.class),
                        $(ContextActionRegistry.class), $$(FileDownloadUtils.class), true, $(RenameAction.class));
                final ContextNavigatorPanel panel = new ContextNavigatorPanel(presenter,
                        $(I18nTranslationService.class), $(WorkspaceSkeleton.class), $(ActionManager.class));
                presenter.init(panel);
                return presenter;
            }
        });

        register(Singleton.class, new Factory<ContextPropEditor>(ContextPropEditor.class) {
            @Override
            public ContextPropEditor create() {
                final ContextPropEditorPresenter presenter = new ContextPropEditorPresenter($(Session.class),
                        $(StateManager.class), $(ContentCapabilitiesRegistry.class), $$(TagsSummary.class),
                        $$(ContentServiceAsync.class), $(EntitySubTitle.class));
                final ContextPropEditorView view = new ContextPropEditorPanel(presenter,
                        $(I18nUITranslationService.class), $(WorkspaceSkeleton.class), $$(LanguageSelector.class),
                        $(Images.class));
                presenter.init(view);
                return presenter;
            }
        });
    }
}
