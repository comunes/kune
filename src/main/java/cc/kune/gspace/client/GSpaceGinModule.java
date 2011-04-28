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
 */
package cc.kune.gspace.client;

import cc.kune.client.ExtendedGinModule;
import cc.kune.gspace.client.options.GroupOptions;
import cc.kune.gspace.client.options.GroupOptionsCollection;
import cc.kune.gspace.client.options.GroupOptionsPanel;
import cc.kune.gspace.client.options.GroupOptionsPresenter;
import cc.kune.gspace.client.options.GroupOptionsView;
import cc.kune.gspace.client.options.UserOptions;
import cc.kune.gspace.client.options.UserOptionsCollection;
import cc.kune.gspace.client.options.UserOptionsPanel;
import cc.kune.gspace.client.options.UserOptionsPresenter;
import cc.kune.gspace.client.options.UserOptionsView;
import cc.kune.gspace.client.options.license.GroupOptionsDefLicense;
import cc.kune.gspace.client.options.license.GroupOptionsDefLicensePanel;
import cc.kune.gspace.client.options.license.GroupOptionsDefLicensePresenter;
import cc.kune.gspace.client.options.license.GroupOptionsDefLicenseView;
import cc.kune.gspace.client.options.license.UserOptionsDefLicense;
import cc.kune.gspace.client.options.license.UserOptionsDefLicensePanel;
import cc.kune.gspace.client.options.license.UserOptionsDefLicensePresenter;
import cc.kune.gspace.client.options.license.UserOptionsDefLicenseView;
import cc.kune.gspace.client.options.logo.GroupOptionsLogo;
import cc.kune.gspace.client.options.logo.GroupOptionsLogoPanel;
import cc.kune.gspace.client.options.logo.GroupOptionsLogoPresenter;
import cc.kune.gspace.client.options.logo.GroupOptionsLogoView;
import cc.kune.gspace.client.options.logo.UserOptionsLogo;
import cc.kune.gspace.client.options.logo.UserOptionsLogoPanel;
import cc.kune.gspace.client.options.logo.UserOptionsLogoPresenter;
import cc.kune.gspace.client.options.logo.UserOptionsLogoView;
import cc.kune.gspace.client.options.pscape.GroupOptionsPublicSpaceConf;
import cc.kune.gspace.client.options.pscape.GroupOptionsPublicSpaceConfPanel;
import cc.kune.gspace.client.options.pscape.GroupOptionsPublicSpaceConfPresenter;
import cc.kune.gspace.client.options.pscape.GroupOptionsPublicSpaceConfView;
import cc.kune.gspace.client.options.pscape.UserOptionsPublicSpaceConf;
import cc.kune.gspace.client.options.pscape.UserOptionsPublicSpaceConfPanel;
import cc.kune.gspace.client.options.pscape.UserOptionsPublicSpaceConfPresenter;
import cc.kune.gspace.client.options.pscape.UserOptionsPublicSpaceConfView;
import cc.kune.gspace.client.options.tools.GroupOptionsToolsConf;
import cc.kune.gspace.client.options.tools.GroupOptionsToolsConfPanel;
import cc.kune.gspace.client.options.tools.GroupOptionsToolsConfPresenter;
import cc.kune.gspace.client.options.tools.GroupOptionsToolsConfView;
import cc.kune.gspace.client.options.tools.UserOptionsToolsConf;
import cc.kune.gspace.client.options.tools.UserOptionsToolsConfPanel;
import cc.kune.gspace.client.options.tools.UserOptionsToolsConfPresenter;
import cc.kune.gspace.client.options.tools.UserOptionsToolsConfView;
import cc.kune.gspace.client.style.GSpaceBackManager;
import cc.kune.gspace.client.style.GSpaceBackManagerImpl;
import cc.kune.gspace.client.tags.TagsSummaryPanel;
import cc.kune.gspace.client.tags.TagsSummaryPresenter;
import cc.kune.gspace.client.tool.selector.ToolSelector;
import cc.kune.gspace.client.tool.selector.ToolSelectorPanel;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePanel;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter;
import cc.kune.gspace.client.viewers.DocViewerPanel;
import cc.kune.gspace.client.viewers.DocViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerAsTablePanel;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.inject.Singleton;

public class GSpaceGinModule extends ExtendedGinModule {

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.inject.client.AbstractGinModule#configure()
     */
    @Override
    protected void configure() {
        bindPresenter(EntityLicensePresenter.class, EntityLicensePresenter.EntityLicenseView.class,
                EntityLicensePanel.class, EntityLicensePresenter.EntityLicenseProxy.class);
        bindPresenter(TagsSummaryPresenter.class, TagsSummaryPresenter.TagsSummaryView.class, TagsSummaryPanel.class,
                TagsSummaryPresenter.TagsSummaryProxy.class);
        bind(GSpaceArmorImpl.class).in(Singleton.class);
        bind(GSpaceArmor.class).to(GSpaceArmorImpl.class).in(Singleton.class);
        bind(GSpaceParts.class).asEagerSingleton();
        bindPresenter(ToolSelectorPresenter.class, ToolSelectorPresenter.ToolSelectorView.class,
                ToolSelectorPanel.class, ToolSelectorPresenter.ToolSelectorProxy.class);
        bind(ToolSelector.class).to(ToolSelectorPresenter.class).in(Singleton.class);
        bindPresenter(DocViewerPresenter.class, DocViewerPresenter.DocViewerView.class, DocViewerPanel.class,
                DocViewerPresenter.DocViewerProxy.class);
        bindPresenter(FolderViewerPresenter.class, FolderViewerPresenter.FolderViewerView.class,
                FolderViewerAsTablePanel.class, FolderViewerPresenter.FolderViewerProxy.class);

        s(GroupOptions.class, GroupOptionsPresenter.class);
        s(GroupOptionsView.class, GroupOptionsPanel.class);
        s(UserOptions.class, UserOptionsPresenter.class);
        s(UserOptionsView.class, UserOptionsPanel.class);

        s(UserOptionsCollection.class);
        s(GroupOptionsCollection.class);

        s(GroupOptionsDefLicense.class, GroupOptionsDefLicensePresenter.class);
        s(GroupOptionsDefLicenseView.class, GroupOptionsDefLicensePanel.class);
        s(GroupOptionsPublicSpaceConf.class, GroupOptionsPublicSpaceConfPresenter.class);
        s(GroupOptionsPublicSpaceConfView.class, GroupOptionsPublicSpaceConfPanel.class);
        s(GroupOptionsLogo.class, GroupOptionsLogoPresenter.class);
        s(GroupOptionsLogoView.class, GroupOptionsLogoPanel.class);
        s(GroupOptionsToolsConf.class, GroupOptionsToolsConfPresenter.class);
        s(GroupOptionsToolsConfView.class, GroupOptionsToolsConfPanel.class);

        s(UserOptionsDefLicense.class, UserOptionsDefLicensePresenter.class);
        s(UserOptionsDefLicenseView.class, UserOptionsDefLicensePanel.class);
        s(UserOptionsPublicSpaceConf.class, UserOptionsPublicSpaceConfPresenter.class);
        s(UserOptionsPublicSpaceConfView.class, UserOptionsPublicSpaceConfPanel.class);
        s(UserOptionsLogo.class, UserOptionsLogoPresenter.class);
        s(UserOptionsLogoView.class, UserOptionsLogoPanel.class);
        s(UserOptionsToolsConf.class, UserOptionsToolsConfPresenter.class);
        s(UserOptionsToolsConfView.class, UserOptionsToolsConfPanel.class);

        s(GSpaceBackManager.class, GSpaceBackManagerImpl.class);
    }
}