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
package cc.kune.core.server.init;

import cc.kune.domain.finders.ContainerFinder;
import cc.kune.domain.finders.ContentFinder;
import cc.kune.domain.finders.ExtMediaDescripFinder;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.I18nCountryFinder;
import cc.kune.domain.finders.I18nLanguageFinder;
import cc.kune.domain.finders.I18nTranslationFinder;
import cc.kune.domain.finders.LicenseFinder;
import cc.kune.domain.finders.RateFinder;
import cc.kune.domain.finders.TagFinder;
import cc.kune.domain.finders.TagUserContentFinder;
import cc.kune.domain.finders.UserFinder;
import cc.kune.domain.finders.UserSignInLogFinder;

import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;

public class FinderRegistry {

  public static Module init(final JpaPersistModule jpaPersistModule) {
    jpaPersistModule.addFinder(ContainerFinder.class);
    jpaPersistModule.addFinder(ContentFinder.class);
    jpaPersistModule.addFinder(ExtMediaDescripFinder.class);
    jpaPersistModule.addFinder(GroupFinder.class);
    jpaPersistModule.addFinder(I18nCountryFinder.class);
    jpaPersistModule.addFinder(I18nLanguageFinder.class);
    jpaPersistModule.addFinder(I18nTranslationFinder.class);
    jpaPersistModule.addFinder(LicenseFinder.class);
    jpaPersistModule.addFinder(RateFinder.class);
    jpaPersistModule.addFinder(TagFinder.class);
    jpaPersistModule.addFinder(TagUserContentFinder.class);
    jpaPersistModule.addFinder(UserFinder.class);
    jpaPersistModule.addFinder(UserSignInLogFinder.class);
    return jpaPersistModule;
  }

}
