/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
 */
package cc.kune.gspace.client;

import cc.kune.core.client.ExtendedGinModule;
import cc.kune.core.client.sitebar.search.SitebarSearchPanel;
import cc.kune.core.client.sitebar.search.SitebarSearchPresenter;
import cc.kune.core.client.sn.AdminsGroupSNDropController;
import cc.kune.core.client.sn.AllMembersGroupSNDropController;
import cc.kune.core.client.sn.CollabsGroupSNDropController;
import cc.kune.core.client.sn.actions.BuddyLastConnectedHeaderLabel;
import cc.kune.core.client.sn.actions.WriteToBuddyHeaderButton;
import cc.kune.gspace.client.actions.ActionUtils;
import cc.kune.gspace.client.actions.ContentViewerOptionsMenu;
import cc.kune.gspace.client.actions.RenameAction;
import cc.kune.gspace.client.actions.share.ContentViewerShareMenu;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.armor.GSpaceArmorDefault;
import cc.kune.gspace.client.feedback.FeedbackBottomPanel;
import cc.kune.gspace.client.i18n.I18nTranslator;
import cc.kune.gspace.client.i18n.I18nTranslatorPanel;
import cc.kune.gspace.client.i18n.I18nTranslatorPresenter;
import cc.kune.gspace.client.i18n.I18nTranslatorPresenter.I18nTranslatorView;
import cc.kune.gspace.client.i18n.I18nTranslatorTabsCollection;
import cc.kune.gspace.client.i18n.SiteOptionsI18nTranslatorAction;
import cc.kune.gspace.client.licensewizard.LicenseChangeAction;
import cc.kune.gspace.client.licensewizard.LicenseWizard;
import cc.kune.gspace.client.licensewizard.LicenseWizardPanel;
import cc.kune.gspace.client.licensewizard.LicenseWizardPresenter;
import cc.kune.gspace.client.licensewizard.LicenseWizardView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardFirstForm;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdForm;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdFormView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardSndForm;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardSndFormView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardTrdForm;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardTrdFormView;
import cc.kune.gspace.client.maxmin.MaxMinWorkspace;
import cc.kune.gspace.client.maxmin.MaxMinWorkspacePanel;
import cc.kune.gspace.client.maxmin.MaxMinWorkspacePresenter;
import cc.kune.gspace.client.options.GroupOptions;
import cc.kune.gspace.client.options.GroupOptionsCollection;
import cc.kune.gspace.client.options.GroupOptionsPanel;
import cc.kune.gspace.client.options.GroupOptionsPresenter;
import cc.kune.gspace.client.options.GroupOptionsPresenter.GroupOptionsView;
import cc.kune.gspace.client.options.UserOptions;
import cc.kune.gspace.client.options.UserOptionsCollection;
import cc.kune.gspace.client.options.UserOptionsPanel;
import cc.kune.gspace.client.options.UserOptionsPresenter;
import cc.kune.gspace.client.options.UserOptionsPresenter.UserOptionsView;
import cc.kune.gspace.client.options.general.GroupOptGeneral;
import cc.kune.gspace.client.options.general.GroupOptGeneralPanel;
import cc.kune.gspace.client.options.general.GroupOptGeneralPresenter;
import cc.kune.gspace.client.options.general.GroupOptGeneralView;
import cc.kune.gspace.client.options.general.UserOptGeneral;
import cc.kune.gspace.client.options.general.UserOptGeneralPanel;
import cc.kune.gspace.client.options.general.UserOptGeneralPresenter;
import cc.kune.gspace.client.options.general.UserOptGeneralView;
import cc.kune.gspace.client.options.general.UserOptPass;
import cc.kune.gspace.client.options.general.UserOptPassPanel;
import cc.kune.gspace.client.options.general.UserOptPassPresenter;
import cc.kune.gspace.client.options.general.UserOptPassView;
import cc.kune.gspace.client.options.license.GroupOptDefLicense;
import cc.kune.gspace.client.options.license.GroupOptDefLicensePanel;
import cc.kune.gspace.client.options.license.GroupOptDefLicensePresenter;
import cc.kune.gspace.client.options.license.GroupOptDefLicenseView;
import cc.kune.gspace.client.options.license.UserOptDefLicense;
import cc.kune.gspace.client.options.license.UserOptDefLicensePanel;
import cc.kune.gspace.client.options.license.UserOptDefLicensePresenter;
import cc.kune.gspace.client.options.license.UserOptDefLicenseView;
import cc.kune.gspace.client.options.logo.GroupOptLogo;
import cc.kune.gspace.client.options.logo.GroupOptLogoPanel;
import cc.kune.gspace.client.options.logo.GroupOptLogoPresenter;
import cc.kune.gspace.client.options.logo.GroupOptLogoView;
import cc.kune.gspace.client.options.logo.UserOptLogo;
import cc.kune.gspace.client.options.logo.UserOptLogoPanel;
import cc.kune.gspace.client.options.logo.UserOptLogoPresenter;
import cc.kune.gspace.client.options.logo.UserOptLogoView;
import cc.kune.gspace.client.options.style.GroupOptStyle;
import cc.kune.gspace.client.options.style.GroupOptStylePanel;
import cc.kune.gspace.client.options.style.GroupOptStylePresenter;
import cc.kune.gspace.client.options.style.GroupOptStyleView;
import cc.kune.gspace.client.options.style.UserOptStyle;
import cc.kune.gspace.client.options.style.UserOptStylePanel;
import cc.kune.gspace.client.options.style.UserOptStylePresenter;
import cc.kune.gspace.client.options.style.UserOptStyleView;
import cc.kune.gspace.client.options.tools.GroupOptTools;
import cc.kune.gspace.client.options.tools.GroupOptToolsPanel;
import cc.kune.gspace.client.options.tools.GroupOptToolsPresenter;
import cc.kune.gspace.client.options.tools.GroupOptToolsView;
import cc.kune.gspace.client.options.tools.UserOptTools;
import cc.kune.gspace.client.options.tools.UserOptToolsPanel;
import cc.kune.gspace.client.options.tools.UserOptToolsPresenter;
import cc.kune.gspace.client.options.tools.UserOptToolsView;
import cc.kune.gspace.client.share.ShareDialog;
import cc.kune.gspace.client.share.ShareDialogPanel;
import cc.kune.gspace.client.share.ShareDialogPresenter;
import cc.kune.gspace.client.share.ShareToListPanel;
import cc.kune.gspace.client.share.ShareToListView;
import cc.kune.gspace.client.share.ShareToOthersPanel;
import cc.kune.gspace.client.share.ShareToOthersView;
import cc.kune.gspace.client.share.ShareToTheNetPanel;
import cc.kune.gspace.client.share.ShareToTheNetView;
import cc.kune.gspace.client.share.items.ShareItemFactory;
import cc.kune.gspace.client.style.GSpaceBackgroundManager;
import cc.kune.gspace.client.style.GSpaceBackgroundManagerImpl;
import cc.kune.gspace.client.tags.TagsSummaryPanel;
import cc.kune.gspace.client.tags.TagsSummaryPresenter;
import cc.kune.gspace.client.themes.GSpaceThemeSelectorPanel;
import cc.kune.gspace.client.themes.GSpaceThemeSelectorPresenter;
import cc.kune.gspace.client.tool.selector.ToolSelector;
import cc.kune.gspace.client.tool.selector.ToolSelectorPanel;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePanel;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter;
import cc.kune.gspace.client.viewers.ContentDropController;
import cc.kune.gspace.client.viewers.ContentViewerPanel;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerAsTablePanel;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerUtils;
import cc.kune.gspace.client.viewers.NoHomePageViewer;
import cc.kune.gspace.client.viewers.PathToolbarUtils;
import cc.kune.gspace.client.viewers.TutorialViewer;

import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class GSpaceGinModule.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class GSpaceGinModule extends ExtendedGinModule {

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.inject.client.AbstractGinModule#configure()
   */
  @Override
  protected void configure() {
    requestStaticInjection(ActionUtils.class);
    s(PathToolbarUtils.class);
    s(FolderViewerUtils.class);
    s(ContentDropController.class);
    s(AdminsGroupSNDropController.class);
    s(CollabsGroupSNDropController.class);
    s(AllMembersGroupSNDropController.class);
    bindPresenter(EntityLicensePresenter.class, EntityLicensePresenter.EntityLicenseView.class,
        EntityLicensePanel.class, EntityLicensePresenter.EntityLicenseProxy.class);
    bindPresenter(TagsSummaryPresenter.class, TagsSummaryPresenter.TagsSummaryView.class,
        TagsSummaryPanel.class, TagsSummaryPresenter.TagsSummaryProxy.class);
    bind(GSpaceArmorDefault.class).in(Singleton.class);
    bind(GSpaceArmor.class).to(GSpaceArmorDefault.class).in(Singleton.class);
    bind(GSpaceParts.class).asEagerSingleton();
    bindPresenter(ToolSelectorPresenter.class, ToolSelectorPresenter.ToolSelectorView.class,
        ToolSelectorPanel.class, ToolSelectorPresenter.ToolSelectorProxy.class);
    bind(ToolSelector.class).to(ToolSelectorPresenter.class).in(Singleton.class);
    bindPresenter(ContentViewerPresenter.class, ContentViewerPresenter.ContentViewerView.class,
        ContentViewerPanel.class, ContentViewerPresenter.ContentViewerProxy.class);
    bindPresenter(FolderViewerPresenter.class, FolderViewerPresenter.FolderViewerView.class,
        FolderViewerAsTablePanel.class, FolderViewerPresenter.FolderViewerProxy.class);
    bindPresenter(SitebarSearchPresenter.class, SitebarSearchPresenter.SitebarSearchView.class,
        SitebarSearchPanel.class, SitebarSearchPresenter.SitebarSearchProxy.class);
    bindPresenter(MaxMinWorkspacePresenter.class, MaxMinWorkspacePresenter.MaxMinWorkspaceView.class,
        MaxMinWorkspacePanel.class, MaxMinWorkspacePresenter.MaxMinWorkspaceProxy.class);
    bind(MaxMinWorkspace.class).to(MaxMinWorkspacePresenter.class).in(Singleton.class);

    s(NoHomePageViewer.class);
    s(TutorialViewer.class);

    bindPresenter(GroupOptionsPresenter.class, GroupOptionsView.class, GroupOptionsPanel.class,
        GroupOptionsPresenter.GroupOptionsProxy.class);
    s(GroupOptions.class, GroupOptionsPresenter.class);

    bindPresenter(UserOptionsPresenter.class, UserOptionsView.class, UserOptionsPanel.class,
        UserOptionsPresenter.UserOptionsProxy.class);
    s(UserOptions.class, UserOptionsPresenter.class);

    s(UserOptionsCollection.class);
    s(GroupOptionsCollection.class);
    s(I18nTranslatorTabsCollection.class);

    s(GroupOptGeneral.class, GroupOptGeneralPresenter.class);
    s(GroupOptGeneralView.class, GroupOptGeneralPanel.class);
    s(GroupOptDefLicense.class, GroupOptDefLicensePresenter.class);
    s(GroupOptDefLicenseView.class, GroupOptDefLicensePanel.class);
    s(GroupOptStyle.class, GroupOptStylePresenter.class);
    s(GroupOptStyleView.class, GroupOptStylePanel.class);
    s(GroupOptLogo.class, GroupOptLogoPresenter.class);
    s(GroupOptLogoView.class, GroupOptLogoPanel.class);
    s(GroupOptTools.class, GroupOptToolsPresenter.class);
    s(GroupOptToolsView.class, GroupOptToolsPanel.class);

    s(UserOptGeneral.class, UserOptGeneralPresenter.class);
    s(UserOptGeneralView.class, UserOptGeneralPanel.class);
    s(UserOptPass.class, UserOptPassPresenter.class);
    s(UserOptPassView.class, UserOptPassPanel.class);
    s(UserOptDefLicense.class, UserOptDefLicensePresenter.class);
    s(UserOptDefLicenseView.class, UserOptDefLicensePanel.class);
    s(UserOptStyle.class, UserOptStylePresenter.class);
    s(UserOptStyleView.class, UserOptStylePanel.class);
    s(UserOptLogo.class, UserOptLogoPresenter.class);
    s(UserOptLogoView.class, UserOptLogoPanel.class);
    s(UserOptTools.class, UserOptToolsPresenter.class);
    s(UserOptToolsView.class, UserOptToolsPanel.class);

    s(GSpaceBackgroundManager.class, GSpaceBackgroundManagerImpl.class);
    s(GSpaceThemeSelectorPresenter.class);
    s(GSpaceThemeSelectorPanel.class);

    // Actions
    s(ContentViewerOptionsMenu.class);
    s(ContentViewerShareMenu.class);
    s(RenameAction.class);
    s(FeedbackBottomPanel.class);
    s(WriteToBuddyHeaderButton.class);
    s(BuddyLastConnectedHeaderLabel.class);

    // LicenseWizard
    bindPresenter(LicenseWizardPresenter.class, LicenseWizardView.class, LicenseWizardPanel.class,
        LicenseWizardPresenter.LicenseWizardProxy.class);
    s(LicenseWizard.class, LicenseWizardPresenter.class);
    s(LicenseWizardFirstFormView.class, LicenseWizardFirstForm.class);
    s(LicenseWizardSndFormView.class, LicenseWizardSndForm.class);
    s(LicenseWizardTrdFormView.class, LicenseWizardTrdForm.class);
    s(LicenseWizardFrdFormView.class, LicenseWizardFrdForm.class);
    s(LicenseChangeAction.class);

    // I18nTranslator
    bindPresenter(I18nTranslatorPresenter.class, I18nTranslatorView.class, I18nTranslatorPanel.class,
        I18nTranslatorPresenter.I18nTranslatorProxy.class);
    s(I18nTranslator.class, I18nTranslatorPresenter.class);
    s(SiteOptionsI18nTranslatorAction.class);

    // ShareDialog
    bindPresenter(ShareDialogPresenter.class, ShareDialogPresenter.ShareDialogView.class,
        ShareDialogPanel.class, ShareDialogPresenter.ShareDialogProxy.class);
    s(ShareDialog.class, ShareDialogPresenter.class);
    s(ShareToListView.class, ShareToListPanel.class);
    s(ShareToOthersView.class, ShareToOthersPanel.class);
    s(ShareToTheNetView.class, ShareToTheNetPanel.class);
    s(ShareItemFactory.class);
    s(ShareToTheNetPanel.class);
    s(ShareToOthersPanel.class);

    requestStaticInjection(ShareItemFactory.class);
  }
}
