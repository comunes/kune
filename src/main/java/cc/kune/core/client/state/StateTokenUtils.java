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
package cc.kune.core.client.state;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;

import com.google.inject.Inject;

public class StateTokenUtils {

  private static final String SEPARATOR = ".";
  private final Session session;

  @Inject
  public StateTokenUtils(final Session session) {
    this.session = session;
  }

  public String getPublicUrl(final StateToken token) {
    String publicUrl = "";

    final String group = token.getGroup();
    final String tool = token.getTool();
    final String folder = token.getFolder();
    final String document = token.getDocument();

    publicUrl += session.getSiteUrl() + "/public";

    if (group != null) {
      publicUrl += "/" + group;
    }
    if (tool != null) {
      publicUrl += SEPARATOR + tool;
    }
    if (folder != null) {
      publicUrl += SEPARATOR + folder;
    }
    if (document != null) {
      publicUrl += SEPARATOR + document;
    }

    return publicUrl;
  }
}
