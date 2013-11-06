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
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.share.ShareToListOnItemRemoved;

import com.google.inject.Inject;

public class ShareItemOfViewer extends AbstractShareItemWithMenu {

  private final IconicResources res;

  @Inject
  public ShareItemOfViewer(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final IconicResources res,
      final CommonResources commonResources) {
    super(I18n.tWithNT("can read", "someone can read a doc"), actionsPanel, downloadUtils,
        commonResources);
    this.res = res;
  }

  public AbstractShareItemUi of(final GroupDTO group, final ShareToListOnItemRemoved onRemoved) {
    setGroupName(group);
    final MenuItemDescriptor toEditor = new MenuItemDescriptor(menu, new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        // TODO
        NotifyUser.info("In development");
      }
    });
    toEditor.withText(I18n.t("Change to editor")).withIcon(res.upArrow());
    final MenuItemDescriptor remove = new MenuItemDescriptor(menu, new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        // TODO
        NotifyUser.info("In development");
      }
    });
    remove.withText(I18n.t("Remove")).withIcon(res.del());
    // super.add(toEditor);
    super.add(remove);
    return this;
  }

}
