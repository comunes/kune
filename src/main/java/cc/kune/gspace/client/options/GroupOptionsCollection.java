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
package cc.kune.gspace.client.options;

import cc.kune.common.client.ProvidersCollection;
import cc.kune.gspace.client.options.general.GroupOptGeneral;
import cc.kune.gspace.client.options.license.GroupOptDefLicense;
import cc.kune.gspace.client.options.logo.GroupOptLogo;
import cc.kune.gspace.client.options.style.GroupOptStyle;
import cc.kune.gspace.client.options.tools.GroupOptTools;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@SuppressWarnings("serial")
@Singleton
public class GroupOptionsCollection extends ProvidersCollection {
  @Inject
  public GroupOptionsCollection(final Provider<GroupOptGeneral> gg, final Provider<GroupOptTools> gtc,
      final Provider<GroupOptLogo> gl, final Provider<GroupOptStyle> gps,
      final Provider<GroupOptDefLicense> gdl) {
    add(gg);
    add(gtc);
    add(gl);
    add(gps);
    add(gdl);
  }
}
