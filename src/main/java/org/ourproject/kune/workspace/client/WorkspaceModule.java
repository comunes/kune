/*
 *
// * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

import org.ourproject.kune.platf.client.services.AbstractExtendedModule;

public class WorkspaceModule extends AbstractExtendedModule {
    @Override
    protected void onInstall() {
        //
        // register(Singleton.class, new
        // Factory<SocialNetworkServiceAsync>(SocialNetworkServiceAsync.class) {
        // @Override
        // public SocialNetworkServiceAsync create() {
        // final SocialNetworkServiceAsync snServiceAsync =
        // (SocialNetworkServiceAsync) GWT.create(SocialNetworkService.class);
        // ((ServiceDefTarget)
        // snServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL()
        // + "SocialNetworkService");
        // return snServiceAsync;
        // }
        // }, new Factory<GroupServiceAsync>(GroupServiceAsync.class) {
        // @Override
        // public GroupServiceAsync create() {
        // final GroupServiceAsync groupServiceAsync = (GroupServiceAsync)
        // GWT.create(GroupService.class);
        // ((ServiceDefTarget)
        // groupServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL() +
        // "GroupService");
        // return groupServiceAsync;
        // }
        // }, new Factory<ContentServiceAsync>(ContentServiceAsync.class) {
        // @Override
        // public ContentServiceAsync create() {
        // final ContentServiceAsync contentServiceAsync = (ContentServiceAsync)
        // GWT.create(ContentService.class);
        // ((ServiceDefTarget)
        // contentServiceAsync).setServiceEntryPoint(GWT.getModuleBaseURL() +
        // "ContentService");
        // return contentServiceAsync;
        // }
        // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<WorkspaceSkeleton>(WorkspaceSkeleton.class) {
        // @Override
        // public WorkspaceSkeleton create() {
        // return new WorkspaceSkeleton();
        // }
        // });
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<SiteProgress>(SiteProgress.class) {
        // // @Override
        // // public SiteProgress create() {
        // // final SiteProgressPresenter presenter = new
        // SiteProgressPresenter();
        // // final SiteProgressPanel panel = new SiteProgressPanel(presenter,
        // // p(SitePublicSpaceLink.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // // register(ApplicationComponentGroup.class, new
        // Factory<WorkspaceNotifyUser>(WorkspaceNotifyUser.class) {
        // // @Override
        // // public WorkspaceNotifyUser create() {
        // // return new WorkspaceNotifyUser(i(NotifyUser.class),
        // i(I18nUITranslationService.class),
        // // i(SiteProgress.class), p(ToastMessage.class),
        // p(WorkspaceSkeleton.class));
        // // }
        // // });
        // //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<SitePublicSpaceLink>(SitePublicSpaceLink.class) {
        // // @Override
        // // public SitePublicSpaceLink create() {
        // // final SitePublicSpaceLinkPresenter presenter = new
        // // SitePublicSpaceLinkPresenter(i(StateManager.class),
        // // i(StateTokenUtils.class));
        // // final SitePublicSpaceLinkPanel panel = new
        // // SitePublicSpaceLinkPanel(presenter,
        // // i(WorkspaceSkeleton.class), i(I18nUITranslationService.class),
        // // i(Images.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // register(Singleton.class, new
        // Factory<StateTokenUtils>(StateTokenUtils.class) {
        // @Override
        // public StateTokenUtils create() {
        // return new StateTokenUtils(i(Session.class));
        // }
        // });
        //
        // register(NoDecoration.class, new
        // Factory<ToastMessage>(ToastMessage.class) {
        // @Override
        // public ToastMessage create() {
        // final ToastMessagePresenter presenter = new ToastMessagePresenter();
        // final ToastMessagePanel panel = new ToastMessagePanel();
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<SiteUserOptions>(SiteUserOptions.class) {
        // // @Override
        // // public SiteUserOptions create() {
        // // final SiteUserOptionsPresenter presenter = new
        // // SiteUserOptionsPresenter(i(Session.class),
        // // i(StateManager.class), p(FileDownloadUtils.class),
        // // $(I18nTranslationService.class),
        // // $(IconResources.class));
        // // final SiteUserOptionsPanel panel = new
        // // SiteUserOptionsPanel(presenter, i(WorkspaceSkeleton.class),
        // // $(GuiBindingsRegister.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        // //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<SiteSignInLink>(SiteSignInLink.class) {
        // // @Override
        // // public SiteSignInLink create() {
        // // final SiteSignInLinkPresenter presenter = new
        // // SiteSignInLinkPresenter(i(Session.class));
        // // final SiteSignInLinkPanel panel = new
        // SiteSignInLinkPanel(presenter,
        // // i(I18nUITranslationService.class),
        // // i(WorkspaceSkeleton.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<SiteSignOutLink>(SiteSignOutLink.class) {
        // // @Override
        // // public SiteSignOutLink create() {
        // // final SiteSignOutLinkPresenter presenter = new
        // // SiteSignOutLinkPresenter(i(Session.class),
        // // p(UserServiceAsync.class), p(ErrorHandler.class));
        // // final SiteSignOutLinkPanel panel = new
        // // SiteSignOutLinkPanel(presenter,
        // // i(I18nUITranslationService.class), i(WorkspaceSkeleton.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        // //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<SiteOptions>(SiteOptions.class) {
        // // @Override
        // // public SiteOptions create() {
        // // final SiteOptionsPresenter presenter = new
        // // SiteOptionsPresenter(i(I18nUITranslationService.class),
        // // i(IconResources.class));
        // // final SiteOptionsPanel panel = new
        // // SiteOptionsPanel(i(WorkspaceSkeleton.class),
        // // i(I18nUITranslationService.class), i(GuiBindingsRegister.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<SiteSearch>(SiteSearch.class) {
        // @Override
        // public SiteSearch create() {
        // final SiteSearchPresenter presenter = new
        // SiteSearchPresenter(p(SiteSearcher.class),
        // i(I18nTranslationService.class));
        // final SiteSearchPanel panel = new SiteSearchPanel(presenter,
        // i(WorkspaceSkeleton.class),
        // i(Images.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<SiteLogo>(SiteLogo.class) {
        // @Override
        // public SiteLogo create() {
        // // final SiteLogoPresenter presenter = new
        // // SiteLogoPresenter(i(Session.class));
        // // final SiteLogoPanel panel = new SiteLogoPanel(presenter,
        // // i(WorkspaceSkeleton.class));
        // // presenter.init(panel);
        // return null;
        // }
        // });
        //
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
        //
        // register(Singleton.class, new
        // Factory<WsBackManager>(WsBackManager.class) {
        // @Override
        // public WsBackManager create() {
        // return new WsBackManagerImpl(i(FileDownloadUtils.class));
        // }
        // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<WsThemeManager>(WsThemeManager.class) {
        // @Override
        // public WsThemeManager create() {
        // final WsThemeManager presenter = new WsThemeManager(i(Session.class),
        // p(GroupServiceAsync.class),
        // i(StateManager.class), i(WsBackManager.class));
        // new WsThemeManagerPanel(presenter, i(WorkspaceSkeleton.class));
        // return presenter;
        // }
        // });
        //
        // register(NoDecoration.class, new
        // Factory<WsThemeSelector>(WsThemeSelector.class) {
        // @Override
        // public WsThemeSelector create() {
        // final WsThemeSelectorPresenter presenter = new
        // WsThemeSelectorPresenter(i(Session.class),
        // i(I18nTranslationService.class));
        // presenter.init(new SimpleGuiItem(i(GuiBindingsRegister.class)));
        // return presenter;
        // }
        // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<EntityTitle>(EntityTitle.class) {
        // @Override
        // public EntityTitle create() {
        // final EntityTitlePresenter presenter = new
        // EntityTitlePresenter(i(StateManager.class),
        // i(Session.class), i(ContentIconsRegistry.class),
        // i(RenamableRegistry.class),
        // i(RenameAction.class));
        // final EntityTitlePanel panel = new
        // EntityTitlePanel(i(WorkspaceSkeleton.class), presenter);
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<EntitySubTitle>(EntitySubTitle.class) {
        // @Override
        // public EntitySubTitle create() {
        // final EntitySubTitlePresenter presenter = new
        // EntitySubTitlePresenter(
        // i(I18nUITranslationService.class), i(StateManager.class), false,
        // i(AuthorableRegistry.class));
        // final EntitySubTitlePanel panel = new EntitySubTitlePanel(presenter,
        // i(WorkspaceSkeleton.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<EntityLicensePresenter>(EntityLicensePresenter.class) {
        // // @Override
        // // public EntityLicensePresenter create() {
        // // final EntityLicensePresenter presenter = new
        // // EntityLicensePresenter(i(StateManager.class));
        // // final EntityLicensePanel panel = new EntityLicensePanel(presenter,
        // // i(I18nUITranslationService.class),
        // // i(WorkspaceSkeleton.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<RateIt>(RateIt.class) {
        // @Override
        // public RateIt create() {
        // final RateItPresenter presenter = new
        // RateItPresenter(i(I18nUITranslationService.class),
        // i(Session.class), p(ContentServiceAsync.class),
        // i(StateManager.class), p(RatePresenter.class),
        // i(ContentCapabilitiesRegistry.class));
        // final RateItPanel panel = new RateItPanel(presenter,
        // i(I18nUITranslationService.class),
        // i(WorkspaceSkeleton.class), i(Images.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<RatePresenter>(RatePresenter.class) {
        // @Override
        // public RatePresenter create() {
        // final RatePresenter presenter = new
        // RatePresenter(i(StateManager.class),
        // i(ContentCapabilitiesRegistry.class));
        // final RatePanel panel = new RatePanel(null, null,
        // i(I18nUITranslationService.class),
        // i(WorkspaceSkeleton.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<ActionManager>(ActionManager.class) {
        // @Override
        // public ActionManager create() {
        // return new ActionManager();
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<ActionGroupSummaryToolbar>(ActionGroupSummaryToolbar.class) {
        // @Override
        // public ActionGroupSummaryToolbar create() {
        // final ActionToolbarPanel<StateToken> panel = new
        // ActionToolbarPanel<StateToken>(p(ActionManager.class));
        // final ActionGroupSummaryToolbarPresenter toolbar = new
        // ActionGroupSummaryToolbarPresenter(panel);
        // return toolbar;
        // }
        // }, new
        // Factory<ActionParticipationToolbar>(ActionParticipationToolbar.class)
        // {
        // @Override
        // public ActionParticipationToolbar create() {
        // final ActionToolbarPanel<StateToken> panel = new
        // ActionToolbarPanel<StateToken>(p(ActionManager.class));
        // final ActionParticipationSummaryToolbarPresenter toolbar = new
        // ActionParticipationSummaryToolbarPresenter(
        // panel);
        // return toolbar;
        // }
        // }, new
        // Factory<ActionBuddiesSummaryToolbar>(ActionBuddiesSummaryToolbar.class)
        // {
        // @Override
        // public ActionBuddiesSummaryToolbar create() {
        // final ActionToolbarPanel<UserSimpleDTO> panel = new
        // ActionToolbarPanel<UserSimpleDTO>(
        // p(ActionManager.class));
        // final ActionBuddiesSummaryToolbarPresenter toolbar = new
        // ActionBuddiesSummaryToolbarPresenter(panel);
        // return toolbar;
        // }
        // });
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<GroupMembersSummary>(GroupMembersSummary.class) {
        // // @Override
        // // public GroupMembersSummary create() {
        // // final GroupMembersSummaryPresenter presenter = new
        // // GroupMembersSummaryPresenter(
        // // i(I18nUITranslationService.class), i(StateManager.class),
        // // i(ImageUtils.class),
        // // i(Session.class), p(SocialNetworkServiceAsync.class),
        // // p(GroupServiceAsync.class),
        // // p(GroupLiveSearcher.class), p(ChatEngine.class),
        // // i(GroupActionRegistry.class),
        // // i(ActionGroupSummaryToolbar.class), p(FileDownloadUtils.class),
        // // i(AccessRightsClientManager.class), i(IconResources.class));
        // // final GroupMembersSummaryView view = new
        // // GroupMembersSummaryPanel(presenter,
        // // i(I18nUITranslationService.class), i(WorkspaceSkeleton.class), i(
        // // ActionGroupSummaryToolbar.class).getView());
        // // presenter.init(view);
        // // return presenter;
        // // }
        // // });
        //
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
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<BuddiesSummary>(BuddiesSummary.class) {
        // // @Override
        // // public BuddiesSummary create() {
        // // final BuddiesSummaryPresenter presenter = new
        // // BuddiesSummaryPresenter(i(StateManager.class),
        // // i(Session.class), p(UserServiceAsync.class),
        // // i(UserActionRegistry.class),
        // // i(I18nTranslationService.class), p(ChatEngine.class),
        // // i(ActionBuddiesSummaryToolbar.class),
        // // p(FileDownloadUtils.class), i(ImageUtils.class),
        // // p(SocialNetworkServiceAsync.class),
        // // i(GroupActionRegistry.class), i(AccessRightsClientManager.class),
        // // i(IconResources.class));
        // // final BuddiesSummaryPanel panel = new
        // BuddiesSummaryPanel(presenter,
        // // i(WorkspaceSkeleton.class),
        // // i(I18nTranslationService.class), i(ActionManager.class),
        // // i(ActionBuddiesSummaryToolbar.class).getView());
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<ParticipationSummary>(ParticipationSummary.class) {
        // // @Override
        // // public ParticipationSummary create() {
        // // final ParticipationSummaryPresenter presenter = new
        // // ParticipationSummaryPresenter(
        // // i(I18nUITranslationService.class), i(StateManager.class),
        // // i(ImageUtils.class),
        // // i(Session.class), p(SocialNetworkServiceAsync.class),
        // // i(GroupActionRegistry.class),
        // // i(ActionParticipationToolbar.class), p(FileDownloadUtils.class),
        // // i(AccessRightsClientManager.class), i(IconResources.class));
        // // final ParticipationSummaryView view = new
        // // ParticipationSummaryPanel(presenter,
        // // i(I18nUITranslationService.class), i(WorkspaceSkeleton.class), i(
        // // ActionParticipationToolbar.class).getView());
        // // presenter.init(view);
        // // return presenter;
        // // }
        // // });
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<TagsSummary>(TagsSummary.class) {
        // // @Override
        // // public TagsSummary create() {
        // // final TagsSummaryPresenter presenter = new
        // // TagsSummaryPresenter(i(Session.class),
        // // p(SiteSearcher.class), i(StateManager.class));
        // // final TagsSummaryPanel panel = new TagsSummaryPanel(presenter,
        // // i(I18nUITranslationService.class),
        // // i(WorkspaceSkeleton.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<NoHomePage>(NoHomePage.class) {
        // @Override
        // public NoHomePage create() {
        // final NoHomePagePresenter presenter = new
        // NoHomePagePresenter(i(StateManager.class),
        // p(EntityHeader.class));
        // final NoHomePagePanel panel = new NoHomePagePanel(presenter,
        // i(WorkspaceSkeleton.class),
        // i(I18nTranslationService.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(ApplicationComponentGroup.class, new
        // Factory<GroupOptions>(GroupOptions.class) {
        // @Override
        // public GroupOptions create() {
        // final GroupOptionsPresenter presenter = new
        // GroupOptionsPresenter(i(StateManager.class),
        // i(I18nTranslationService.class), i(IconResources.class));
        // final GroupOptionsPanel panel = new GroupOptionsPanel(presenter,
        // i(EntityHeader.class),
        // i(I18nTranslationService.class), i(NotifyLevelImages.class),
        // i(GroupOptionsCollection.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        // //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<UserOptions>(UserOptions.class) {
        // // @Override
        // // public UserOptions create() {
        // // final UserOptionsPresenter presenter = new
        // // UserOptionsPresenter(i(Session.class),
        // // i(StateManager.class), i(I18nTranslationService.class),
        // // i(IconResources.class),
        // // i(SiteUserOptions.class));
        // // final UserOptionsPanel panel = new UserOptionsPanel(presenter,
        // // i(EntityHeader.class),
        // // i(I18nTranslationService.class), i(NotifyLevelImages.class),
        // // i(UserOptionsCollection.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // register(GroupOptionsCollection.class, new
        // Factory<EntityOptionsToolsConf>(EntityOptionsToolsConf.class) {
        // @Override
        // public EntityOptionsToolsConf create() {
        // final GroupOptionsToolsConfPresenter presenter = new
        // GroupOptionsToolsConfPresenter(
        // i(StateManager.class), i(Session.class),
        // i(I18nTranslationService.class),
        // i(GroupOptions.class), p(GroupServiceAsync.class));
        // final EntityOptionsToolsConfPanel panel = new
        // EntityOptionsToolsConfPanel(presenter,
        // i(WorkspaceSkeleton.class), i(I18nTranslationService.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(UserOptionsCollection.class, new
        // Factory<UserOptionsToolsConf>(UserOptionsToolsConf.class) {
        // @Override
        // public UserOptionsToolsConf create() {
        // final UserOptionsToolsConfPresenter presenter = new
        // UserOptionsToolsConfPresenter(i(Session.class),
        // i(StateManager.class), i(I18nTranslationService.class),
        // i(UserOptions.class),
        // p(GroupServiceAsync.class));
        // final EntityOptionsToolsConfPanel panel = new
        // EntityOptionsToolsConfPanel(presenter,
        // i(WorkspaceSkeleton.class), i(I18nTranslationService.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(GroupOptionsCollection.class, new
        // Factory<GroupOptionsLogo>(GroupOptionsLogo.class) {
        // @Override
        // public GroupOptionsLogo create() {
        // final EntityOptionsLogoPresenter presenter = new
        // GroupOptionsLogoPresenter(i(Session.class),
        // i(EntityHeader.class), i(GroupOptions.class), i(StateManager.class),
        // p(UserServiceAsync.class),
        // p(ChatEngine.class));
        // final GroupOptionsLogoPanel panel = new
        // GroupOptionsLogoPanel(presenter, i(WorkspaceSkeleton.class),
        // i(I18nTranslationService.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(UserOptionsCollection.class, new
        // Factory<UserOptionsLogo>(UserOptionsLogo.class) {
        // @Override
        // public UserOptionsLogo create() {
        // final EntityOptionsLogoPresenter presenter = new
        // UserOptionsLogoPresenter(i(Session.class),
        // i(EntityHeader.class), i(UserOptions.class), i(StateManager.class),
        // p(UserServiceAsync.class),
        // p(ChatEngine.class));
        // final UserOptionsLogoPanel panel = new
        // UserOptionsLogoPanel(presenter, i(WorkspaceSkeleton.class),
        // i(I18nTranslationService.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(GroupOptionsCollection.class, new
        // Factory<GroupOptionsDefLicense>(GroupOptionsDefLicense.class) {
        // @Override
        // public GroupOptionsDefLicense create() {
        // final GroupOptionsDefLicensePresenter presenter = new
        // GroupOptionsDefLicensePresenter(
        // i(GroupOptions.class), i(StateManager.class), i(Session.class),
        // p(LicenseWizard.class),
        // p(LicenseChangeAction.class));
        // final EntityOptionsDefLicensePanel panel = new
        // EntityOptionsDefLicensePanel(presenter,
        // i(WorkspaceSkeleton.class), i(I18nTranslationService.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(UserOptionsCollection.class, new
        // Factory<UserOptionsDefLicense>(UserOptionsDefLicense.class) {
        // @Override
        // public UserOptionsDefLicense create() {
        // final UserOptionsDefLicensePresenter presenter = new
        // UserOptionsDefLicensePresenter(
        // i(UserOptions.class), i(Session.class), p(LicenseWizard.class),
        // p(LicenseChangeAction.class));
        // final EntityOptionsDefLicensePanel panel = new
        // EntityOptionsDefLicensePanel(presenter,
        // i(WorkspaceSkeleton.class), i(I18nTranslationService.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(GroupOptionsCollection.class, new
        // Factory<GroupOptionsPublicSpaceConf>(
        // GroupOptionsPublicSpaceConf.class) {
        // @Override
        // public GroupOptionsPublicSpaceConf create() {
        // final WsThemeSelector themeSelector = i(WsThemeSelector.class);
        // final GroupOptionsPublicSpaceConfPresenter presenter = new
        // GroupOptionsPublicSpaceConfPresenter(
        // i(Session.class), i(StateManager.class), i(GroupOptions.class),
        // i(WsThemeManager.class),
        // themeSelector, p(GroupServiceAsync.class), i(WsBackManager.class));
        // final EntityOptionsPublicSpaceConfPanel panel = new
        // EntityOptionsPublicSpaceConfPanel(presenter,
        // i(WorkspaceSkeleton.class), i(I18nTranslationService.class),
        // themeSelector,
        // i(FileDownloadUtils.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(UserOptionsCollection.class,
        // new
        // Factory<UserOptionsPublicSpaceConf>(UserOptionsPublicSpaceConf.class)
        // {
        // @Override
        // public UserOptionsPublicSpaceConf create() {
        // final WsThemeSelector themeSelector = i(WsThemeSelector.class);
        // final UserOptionsPublicSpaceConfPresenter presenter = new
        // UserOptionsPublicSpaceConfPresenter(
        // i(Session.class), i(StateManager.class), i(UserOptions.class),
        // i(WsThemeManager.class),
        // themeSelector, p(GroupServiceAsync.class), i(WsBackManager.class));
        // final EntityOptionsPublicSpaceConfPanel panel = new
        // EntityOptionsPublicSpaceConfPanel(
        // presenter, i(WorkspaceSkeleton.class),
        // i(I18nTranslationService.class), themeSelector,
        // i(FileDownloadUtils.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<LicenseChangeAction>(LicenseChangeAction.class) {
        // @Override
        // public LicenseChangeAction create() {
        // return new LicenseChangeAction(p(GroupServiceAsync.class),
        // i(Session.class),
        // i(I18nTranslationService.class), i(StateManager.class));
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<RenameAction>(RenameAction.class) {
        // @Override
        // public RenameAction create() {
        // return new RenameAction(i(I18nTranslationService.class),
        // i(Session.class), p(ContentServiceAsync.class));
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<LicenseWizard>(LicenseWizard.class) {
        // @Override
        // public LicenseWizard create() {
        // final LicenseWizardPresenter presenter = new LicenseWizardPresenter(
        // i(LicenseWizardFirstFormView.class),
        // i(LicenseWizardSndFormView.class),
        // i(LicenseWizardTrdFormView.class), i(LicenseWizardFrdFormView.class),
        // i(Session.class));
        // final LicenseWizardPanel panel = new LicenseWizardPanel(presenter,
        // i(WorkspaceSkeleton.class),
        // i(I18nTranslationService.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<LicenseWizardFirstFormView>(LicenseWizardFirstFormView.class)
        // {
        // @Override
        // public LicenseWizardFirstFormView create() {
        // return new LicenseWizardFirstForm(i(I18nTranslationService.class));
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<LicenseWizardSndFormView>(LicenseWizardSndFormView.class) {
        // @Override
        // public LicenseWizardSndFormView create() {
        // return new LicenseWizardSndForm(i(I18nTranslationService.class));
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<LicenseWizardTrdFormView>(LicenseWizardTrdFormView.class) {
        // @Override
        // public LicenseWizardTrdFormView create() {
        // return new LicenseWizardTrdForm(i(Images.class),
        // i(I18nTranslationService.class));
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<LicenseWizardFrdFormView>(LicenseWizardFrdFormView.class) {
        // @Override
        // public LicenseWizardFrdFormView create() {
        // return new LicenseWizardFrdForm(i(I18nTranslationService.class),
        // i(Session.class));
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<FileUploader>(FileUploader.class) {
        // @Override
        // public FileUploader create() {
        // final FileUploaderPresenter presenter = new
        // FileUploaderPresenter(i(Session.class));
        // final FileUploaderDialog panel = new FileUploaderDialog(presenter,
        // i(I18nUITranslationService.class),
        // i(WorkspaceSkeleton.class));
        // presenter.init(panel);
        // return presenter;
        // }
        //
        // @Override
        // public void onAfterCreated(final FileUploader uploader) {
        // i(ContextNavigator.class).addFileUploaderListener(uploader);
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<SiteSearcher>(SiteSearcher.class) {
        // @Override
        // public SiteSearcher create() {
        // final SiteSearcherPresenter presenter = new
        // SiteSearcherPresenter(p(StateManager.class));
        // final SiteSearcherView view = new SiteSearcherPanel(presenter,
        // i(I18nTranslationService.class),
        // i(WorkspaceSkeleton.class));
        // presenter.init(view);
        // return presenter;
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<I18nTranslator>(I18nTranslator.class) {
        // @Override
        // public I18nTranslator create() {
        // final I18nTranslatorPresenter presenter = new
        // I18nTranslatorPresenter(i(Session.class),
        // i(I18nServiceAsync.class), i(I18nUITranslationService.class));
        // final I18nTranslatorView view = new I18nTranslatorPanel(presenter,
        // i(I18nTranslationService.class),
        // i(LanguageSelector.class), i(WorkspaceSkeleton.class),
        // i(Images.class));
        // presenter.init(view);
        // return presenter;
        // }
        // });
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<SiteOptionsI18nTranslatorAction>(
        // // SiteOptionsI18nTranslatorAction.class) {
        // // @Override
        // // public SiteOptionsI18nTranslatorAction create() {
        // // return new SiteOptionsI18nTranslatorAction(i(SiteOptions.class),
        // // i(I18nTranslationService.class),
        // // i(IconResources.class), p(I18nTranslator.class));
        // // }
        // // });
        //
        // // register(Singleton.class, new Factory<SignIn>(SignIn.class) {
        // // @Override
        // // public SignIn create() {
        // // final SignInPresenter presenter = new
        // // SignInPresenter(i(Session.class), i(StateManager.class),
        // // i(I18nUITranslationService.class), p(UserServiceAsync.class),
        // // p(Register.class));
        // // final SignInView panel = new SignInPanel(presenter,
        // // i(I18nTranslationService.class),
        // // i(WorkspaceSkeleton.class), i(NotifyLevelImages.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        // //
        // // register(Singleton.class, new Factory<Register>(Register.class) {
        // // @Override
        // // public Register create() {
        // // final RegisterPresenter presenter = new
        // // RegisterPresenter(i(Session.class), i(StateManager.class),
        // // i(I18nUITranslationService.class), p(UserServiceAsync.class),
        // // p(SignIn.class));
        // // final RegisterView panel = new RegisterPanel(presenter,
        // // i(I18nTranslationService.class),
        // // i(WorkspaceSkeleton.class), i(Session.class),
        // // i(NotifyLevelImages.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // // register(Singleton.class, new Factory<NewGroup>(NewGroup.class) {
        // // @Override
        // // public NewGroup create() {
        // // final NewGroupPresenter presenter = new
        // // NewGroupPresenter(i(I18nTranslationService.class),
        // // i(Session.class), i(StateManager.class),
        // // p(GroupServiceAsync.class));
        // // final NewGroupPanel view = new NewGroupPanel(presenter,
        // // i(I18nTranslationService.class),
        // // p(LicenseWizard.class), i(NotifyLevelImages.class));
        // // presenter.init(view);
        // // return presenter;
        // // }
        // // });
        //
        // register(Singleton.class, new
        // Factory<UserLiveSearcher>(UserLiveSearcher.class) {
        // @Override
        // public UserLiveSearcher create() {
        // final UserLiveSearcherPresenter presenter = new
        // UserLiveSearcherPresenter();
        // final EntityLiveSearcherView view = new
        // UserLiveSearcherPanel(presenter,
        // i(I18nTranslationService.class), i(FileDownloadUtils.class));
        // presenter.init(view);
        // return presenter;
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<GroupLiveSearcher>(GroupLiveSearcher.class) {
        // @Override
        // public GroupLiveSearcher create() {
        // final GroupLiveSearcherPresenter presenter = new
        // GroupLiveSearcherPresenter();
        // final EntityLiveSearcherView view = new
        // GroupLiveSearchPanel(presenter,
        // i(I18nTranslationService.class), i(FileDownloadUtils.class));
        // presenter.init(view);
        // return presenter;
        // }
        // });
        //
        // // register(Singleton.class, new
        // // Factory<ToolSelector>(ToolSelector.class) {
        // // @Override
        // // public ToolSelector create() {
        // // final ToolSelectorPresenter presenter = new
        // // ToolSelectorPresenter(i(StateManager.class),
        // // i(WsThemeManager.class));
        // // return presenter;
        // // }
        // // });
        //
        // register(Singleton.class, new
        // Factory<LanguageSelector>(LanguageSelector.class) {
        // @Override
        // public LanguageSelector create() {
        // final LanguageSelectorPresenter presenter = new
        // LanguageSelectorPresenter(i(Session.class));
        // final LanguageSelectorView view = new
        // LanguageSelectorPanel(presenter, i(I18nTranslationService.class));
        // presenter.init(view);
        // return presenter;
        // }
        // });
        //
        // // Register of tokens like "signin", "newgroup", "translate" etcetera
        // i(StateManager.class).addSiteToken(SiteTokens.SIGNIN, new
        // HistoryTokenCallback() {
        //
        // @Override
        // public void onHistoryToken() {
        // i(SignIn.class).showSignInDialog();
        // }
        // });
        //
        // i(StateManager.class).addSiteToken(SiteTokens.REGISTER, new
        // HistoryTokenCallback() {
        //
        // @Override
        // public void onHistoryToken() {
        // i(Register.class).doRegister();
        // }
        // });
        //
        // i(StateManager.class).addSiteToken(SiteTokens.NEWGROUP, new
        // HistoryTokenCallback() {
        //
        // @Override
        // public void onHistoryToken() {
        // i(NewGroup.class).doNewGroup();
        // }
        // });
        //
        // i(StateManager.class).addSiteToken(SiteTokens.TRANSLATE, new
        // HistoryTokenCallback() {
        //
        // @Override
        // public void onHistoryToken() {
        // i(I18nTranslator.class).doShowTranslator();
        // }
        // });
        //
        // // register(NoDecoration.class, new
        // // Factory<ActionContentToolbar>(ActionContentToolbar.class) {
        // // @Override
        // // public ActionContentToolbar create() {
        // // final ActionCntCtxToolbarPanel<StateToken> tbar = new
        // // ActionCntCtxToolbarPanel<StateToken>(
        // // AbstractFoldableContentActions.CONTENT_TOPBAR,
        // // p(ActionManager.class),
        // // i(WorkspaceSkeleton.class));
        // // final ActionContentToolbar toolbar = new
        // // ActionContentToolbarPresenter(tbar);
        // // return toolbar;
        // // }
        // // });
        //
        // // register(Singleton.class, new
        // // Factory<ContentEditor>(ContentEditor.class) {
        // // @Override
        // // public ContentEditor create() {
        // // final ContentEditorPresenter presenter = new
        // // ContentEditorPresenter(i(I18nTranslationService.class),
        // // i(Session.class), i(RTEImgResources.class),
        // // p(InsertLinkDialog.class),
        // // p(ColorWebSafePalette.class), p(EditHtmlDialog.class),
        // // p(InsertImageDialog.class),
        // // p(InsertMediaDialog.class), p(InsertTableDialog.class),
        // // p(InsertSpecialCharDialog.class),
        // // i(SchedulerManager.class), true, i(StateManager.class),
        // // i(SiteSignOutLink.class),
        // // i(WorkspaceSkeleton.class), i(TimerWrapper.class),
        // // i(EntityTitle.class));
        // // final ContentEditorPanel panel = new ContentEditorPanel(presenter,
        // // i(I18nUITranslationService.class),
        // // i(GlobalShortcutRegister.class), i(GuiBindingsRegister.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        // register(NoDecoration.class, new
        // Factory<ActionContextTopToolbar>(ActionContextTopToolbar.class) {
        // @Override
        // public ActionContextTopToolbar create() {
        // final ActionCntCtxToolbarPanel<StateToken> panel = new
        // ActionCntCtxToolbarPanel<StateToken>(
        // OldAbstractFoldableContentActions.CONTEXT_TOPBAR,
        // p(ActionManager.class),
        // i(WorkspaceSkeleton.class));
        // final ActionContextTopToolbar toolbar = new
        // ActionContextTopToolbar(panel);
        // return toolbar;
        // }
        // });
        //
        // register(NoDecoration.class, new
        // Factory<ActionContextBottomToolbar>(ActionContextBottomToolbar.class)
        // {
        // @Override
        // public ActionContextBottomToolbar create() {
        // final ActionCntCtxToolbarPanel<StateToken> panel = new
        // ActionCntCtxToolbarPanel<StateToken>(
        // OldAbstractFoldableContentActions.CONTEXT_BOTTOMBAR,
        // p(ActionManager.class),
        // i(WorkspaceSkeleton.class));
        // final ActionContextBottomToolbar toolbar = new
        // ActionContextBottomToolbar(panel);
        // return toolbar;
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<ContextNavigator>(ContextNavigator.class) {
        // @Override
        // public ContextNavigator create() {
        // final ContextNavigatorPresenter presenter = new
        // ContextNavigatorPresenter(i(StateManager.class),
        // i(Session.class), i(I18nTranslationService.class),
        // i(ContentIconsRegistry.class),
        // i(ContentCapabilitiesRegistry.class),
        // i(ActionContextTopToolbar.class),
        // i(ActionContextBottomToolbar.class), i(ContextActionRegistry.class),
        // p(FileDownloadUtils.class), true, i(RenameAction.class));
        // final ContextNavigatorPanel panel = new
        // ContextNavigatorPanel(presenter,
        // i(I18nTranslationService.class), i(WorkspaceSkeleton.class),
        // i(ActionManager.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(Singleton.class, new
        // Factory<ContextPropEditor>(ContextPropEditor.class) {
        // @Override
        // public ContextPropEditor create() {
        // final ContextPropEditorPresenter presenter = new
        // ContextPropEditorPresenter(i(Session.class),
        // i(StateManager.class), i(ContentCapabilitiesRegistry.class),
        // p(TagsSummary.class),
        // p(ContentServiceAsync.class), i(EntitySubTitle.class));
        // final ContextPropEditorView view = new
        // ContextPropEditorPanel(presenter,
        // i(I18nUITranslationService.class), i(WorkspaceSkeleton.class),
        // p(LanguageSelector.class),
        // i(Images.class));
        // presenter.init(view);
        // return presenter;
        // }
        // });
        //
        // register(InsertImageGroup.class, new
        // Factory<InsertImageLocal>(InsertImageLocal.class) {
        // @Override
        // public InsertImageLocal create() {
        // final InsertImageLocalPresenter presenter = new
        // InsertImageLocalPresenter(i(InsertImageDialog.class),
        // i(Session.class));
        // final InsertImageLocalPanel panel = new
        // InsertImageLocalPanel(presenter,
        // i(I18nTranslationService.class), i(FileDownloadUtils.class),
        // i(Session.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // // register(ApplicationComponentGroup.class, new
        // // Factory<MaxMinWorkspace>(MaxMinWorkspace.class) {
        // // @Override
        // // public MaxMinWorkspace create() {
        // // final MaxMinWorkspacePresenter presenter = new
        // // MaxMinWorkspacePresenter(
        // // i(GlobalShortcutRegister.class), i(IconResources.class),
        // // i(I18nTranslationService.class),
        // // i(SiteOptions.class));
        // // final MaxMinWorkspacePanel panel = new
        // // MaxMinWorkspacePanel(i(WorkspaceSkeleton.class));
        // // presenter.init(panel);
        // // return presenter;
        // // }
        // // });
        //
        // register(InsertLinkGroup.class, new
        // Factory<InsertLinkLocal>(InsertLinkLocal.class) {
        // @Override
        // public InsertLinkLocal create() {
        // final InsertLinkLocalPresenter presenter = new
        // InsertLinkLocalPresenter(i(InsertLinkDialog.class));
        // final InsertLinkLocalPanel panel = new
        // InsertLinkLocalPanel(presenter, i(WorkspaceSkeleton.class),
        // i(I18nTranslationService.class), i(FileDownloadUtils.class),
        // i(StateTokenUtils.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        //
        // register(InsertMediaGroup.class, new
        // Factory<InsertMediaLocal>(InsertMediaLocal.class) {
        // @Override
        // public InsertMediaLocal create() {
        // final InsertMediaLocalPresenter presenter = new
        // InsertMediaLocalPresenter(i(InsertMediaDialog.class),
        // i(Session.class), p(MediaUtils.class));
        // final InsertMediaLocalPanel panel = new
        // InsertMediaLocalPanel(presenter,
        // i(I18nTranslationService.class), i(FileDownloadUtils.class));
        // presenter.init(panel);
        // return presenter;
        // }
        // });
        // i(GlobalShortcutRegister.class).enable();
    }

}
