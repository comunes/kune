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
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;

import com.google.gwt.resources.client.ImageResource;

public abstract class AbstractShareItemWithMenuUi extends AbstractShareItemUi {

  protected final MenuDescriptor menu;

  public AbstractShareItemWithMenuUi(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final CommonResources res) {
    super(actionsPanel, downloadUtils);
    menu = new MenuDescriptor("");
    menu.withIcon(res.arrowdownsitebarSmall()).withStyles(
        ActionStyles.MENU_BTN_STYLE_NO_BORDER_RIGHT + ", k-share-item-actions");
    super.add(menu);
  }

  public void setValuesViaDescriptor(final ShareItemDescriptor descriptor) {
    final Object itemIcon = descriptor.getItemIcon();
    if (itemIcon instanceof ImageResource) {
      withIcon((ImageResource) itemIcon);
    } else if (itemIcon instanceof String) {
      withIcon((String) itemIcon);
    }
    withText(descriptor.getItemText());
    menu.withText(descriptor.getMenuText());
    // We remove the previous items
    menu.clear();
    for (final MenuItemDescriptor menuItem : descriptor.getMenuItems()) {
      menuItem.setParent(menu);
      super.add(menuItem);
    }
  }

}
