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

import cc.kune.gspace.client.tags.TagsSummaryPanel;
import cc.kune.gspace.client.tags.TagsSummaryPresenter;
import cc.kune.gspace.client.tool.selector.ToolSelector;
import cc.kune.gspace.client.tool.selector.ToolSelectorPanel;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePanel;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class GSpaceGinModule extends AbstractPresenterModule {

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
    }

}