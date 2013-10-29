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

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.lists.client.rpc.ListsServiceHelper;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ShareItemVisibleByAnyone extends AbstractShareItemEveryoneWithMenu {

  private final Provider<ListsServiceHelper> listsHelper;

  @Inject
  public ShareItemVisibleByAnyone(final ActionSimplePanel actionsPanel,
      final Provider<ListsServiceHelper> listsHelper, final ClientFileDownloadUtils downloadUtils,
      final ContentServiceHelper contentService, final CommonResources res, final IconicResources icons) {
    super(icons.world(), I18n.tWithNT("Anyone", "with initial uppercase"), I18n.t("can view"),
        icons.del(), "", actionsPanel, downloadUtils, contentService, res);
    this.listsHelper = listsHelper;
  }

  @Override
  protected void doAction() {
    final String typeId = (String) menuItem.getValue(ShareItemNotVisibleByOthers.TYPE_ID);
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      listsHelper.get().setPublic(false, SimpleCallback.DO_NOTHING);
    } else {
    }
  }

  @Override
  public AbstractShareItem with(final String typeId) {
    String menuItemText;
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      menu.withText(I18n.t("can be member"));
      menuItemText = I18n.t("Make this list not public");
    } else {
      menu.withText(I18n.t("can view"));
      menuItemText = I18n.t("Don't do this public");
    }
    menuItem.withText(menuItemText);
    menuItem.putValue(ShareItemNotVisibleByOthers.TYPE_ID, typeId);
    return this;
  }

}
