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

import cc.kune.core.client.init.AppStartEvent;
import cc.kune.core.client.init.AppStartEvent.AppStartHandler;
import cc.kune.core.client.sitebar.search.SitebarSearchPresenter;
import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.i18n.SiteOptionsI18nTranslatorAction;
import cc.kune.gspace.client.options.GroupOptions;
import cc.kune.gspace.client.options.GroupOptionsCollection;
import cc.kune.gspace.client.options.UserOptions;
import cc.kune.gspace.client.options.UserOptionsCollection;
import cc.kune.gspace.client.options.license.GroupOptDefLicense;
import cc.kune.gspace.client.options.license.UserOptDefLicense;
import cc.kune.gspace.client.options.logo.GroupOptLogo;
import cc.kune.gspace.client.options.logo.UserOptLogo;
import cc.kune.gspace.client.options.style.GroupOptStyle;
import cc.kune.gspace.client.options.style.UserOptStyle;
import cc.kune.gspace.client.options.tools.GroupOptTools;
import cc.kune.gspace.client.options.tools.UserOptTools;
import cc.kune.gspace.client.tags.TagsSummaryPresenter;
import cc.kune.gspace.client.themes.GSpaceThemeManager;
import cc.kune.gspace.client.tool.selector.ToolSelector;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter;
import cc.kune.gspace.client.viewers.ContentViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;
import cc.kune.gspace.client.viewers.NoHomePageViewer;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GSpaceParts {

  @Inject
  public GSpaceParts(final Session session, final GSpaceThemeManager themeManager,
      final Provider<EntityLicensePresenter> licenseFooter,
      final Provider<TagsSummaryPresenter> tagsPresenter, final Provider<ToolSelector> toolSelector,
      final Provider<NoHomePageViewer> noHome, final Provider<ContentViewerPresenter> docsViewer,
      final Provider<FolderViewerPresenter> folderViewer, final Provider<GroupOptions> go,
      final Provider<UserOptions> uo, final Provider<GroupOptionsCollection> goc,
      final Provider<UserOptionsCollection> uoc, final Provider<GroupOptDefLicense> gdl,
      final Provider<GroupOptStyle> gps, final Provider<GroupOptLogo> gl,
      final Provider<GroupOptTools> gtc, final Provider<UserOptDefLicense> udl,
      final Provider<UserOptStyle> ups, final Provider<UserOptLogo> ul,
      final Provider<UserOptTools> utc, final Provider<SitebarSearchPresenter> siteSearch,
      final Provider<SiteOptionsI18nTranslatorAction> transAction) {

    session.onAppStart(true, new AppStartHandler() {
      @Override
      public void onAppStart(final AppStartEvent event) {
        licenseFooter.get();
        tagsPresenter.get();
        toolSelector.get();
        docsViewer.get();
        folderViewer.get();
        siteSearch.get();
        noHome.get();

        // Add User & Groups Options
        goc.get().add(gtc);
        goc.get().add(gl);
        goc.get().add(gps);
        goc.get().add(gdl);
        uoc.get().add(utc);
        uoc.get().add(ul);
        uoc.get().add(ups);
        uoc.get().add(udl);

        // Init
        go.get();
        uo.get();

        // i18n
        transAction.get();
      }
    });
  }
}
