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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ContentViewerOptionsMenu extends MenuDescriptor {

  private static final String ID = "k-cnt-viewer-opt-menu";

  @Inject
  public ContentViewerOptionsMenu(final CoreResources res, final Session session) {
    super();
    this.withIcon(res.arrowdownsitebar()).withId(ID);
    if (session.isNewbie()) {
      this.withText(I18n.t("More"));
      this.withStyles(ActionStyles.MENU_BTN_STYLE_RIGHT);
    } else {
      this.withStyles(ActionStyles.MENU_BTN_STYLE_NO_BORDER_RIGHT);
    }
  }

}
