/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.gspace.client.share.items;

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;

@Deprecated
public abstract class AbstractShareItemWithMenu extends AbstractShareItemUi {

  protected MenuDescriptor menu;

  public AbstractShareItemWithMenu(final String menuTitle, final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final CommonResources res) {
    super(actionsPanel, downloadUtils);
    menu = new MenuDescriptor(menuTitle);
    menu.withIcon(res.arrowdownsitebarSmall()).withStyles(
        ActionStyles.MENU_BTN_STYLE_NO_BORDER_RIGHT + ", k-share-item-actions");
    super.add(menu);
  }

}
