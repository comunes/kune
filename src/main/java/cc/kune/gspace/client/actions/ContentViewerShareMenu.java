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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreResources;

import com.google.inject.Inject;

public class ContentViewerShareMenu extends MenuDescriptor {

  private static final String ID = "k-cnt-viewer-share-menu";

  @Inject
  public ContentViewerShareMenu(final CoreResources res, final I18nTranslationService i18n) {
    super();
    this.withText(i18n.t("Share")).withToolTip(i18n.t("Share this with group members, etc")).withIcon(
        res.world16()).withStyles(ActionStyles.MENU_BTN_STYLE_RIGHT).withId(ID);
  }

}
