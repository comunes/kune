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

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;

import com.google.gwt.resources.client.ImageResource;

public abstract class AbstractShareItemEveryoneWithMenu extends AbstractShareItemWithMenu {

  protected MenuItemDescriptor menuItem;

  public AbstractShareItemEveryoneWithMenu(final ImageResource itemIcon, final String itemText,
      final String menuTitle, final ImageResource menuItemIcon, final String menuItemText,
      final ActionSimplePanel actionsPanel, final ClientFileDownloadUtils downloadUtils,
      final CommonResources res) {
    super(menuTitle, actionsPanel, downloadUtils, res);
    withText(itemText).withIcon(itemIcon);
    menuItem = new MenuItemDescriptor(menu, new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        doAction();
      }
    });
    menuItem.withText(menuItemText).withIcon(menuItemIcon);
    add(menuItem);
  }

  protected void doAction() {
    NotifyUser.info("In development");
  }
}
