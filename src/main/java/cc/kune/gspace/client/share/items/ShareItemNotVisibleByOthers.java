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
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.lists.client.rpc.ListsServiceHelper;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ShareItemNotVisibleByOthers extends AbstractShareItemEveryoneWithMenu {

  public static final String TYPE_ID = "type-id";
  private final Provider<ListsServiceHelper> listsHelper;

  @Inject
  public ShareItemNotVisibleByOthers(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final Provider<ListsServiceHelper> listsHelper,
      final CommonResources res, final IconicResources icons) {
    super(icons.del(), I18n.t("Nobody else"), I18n.t("can't view"), icons.world(),
        I18n.t("Do this public to anyone"), actionsPanel, downloadUtils, res);
    this.listsHelper = listsHelper;

  }

  @Override
  protected void doAction() {
    final String typeId = (String) menuItem.getValue(TYPE_ID);
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      listsHelper.get().setPublic(true, SimpleCallback.DO_NOTHING);
    } else {
    }
  }

  public AbstractShareItemUi with(final boolean visible, final String typeId) {
    String menuItemText;
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      menuItemText = I18n.t("Make this list public");
    } else {
      menuItemText = I18n.t("Do this public to anyone");
    }
    menuItem.withText(menuItemText);
    menuItem.putValue(TYPE_ID, typeId);
    return this;
  }
}
