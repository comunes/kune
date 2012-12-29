/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.gspace.client.options;

import cc.kune.common.client.ProvidersCollection;
import cc.kune.gspace.client.options.general.UserOptGeneral;
import cc.kune.gspace.client.options.general.UserOptPass;
import cc.kune.gspace.client.options.license.UserOptDefLicense;
import cc.kune.gspace.client.options.logo.UserOptLogo;
import cc.kune.gspace.client.options.style.UserOptStyle;
import cc.kune.gspace.client.options.tools.UserOptTools;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class UserOptionsCollection extends ProvidersCollection {

  @Inject
  public UserOptionsCollection(final Provider<UserOptGeneral> ug, final Provider<UserOptPass> up,
      final Provider<UserOptTools> utc, final Provider<UserOptLogo> ul,
      final Provider<UserOptStyle> ups, final Provider<UserOptDefLicense> udl) {
    add(ug);
    add(up);
    add(ul);
    add(utc);
    add(ups);
    add(udl);
  }
}
