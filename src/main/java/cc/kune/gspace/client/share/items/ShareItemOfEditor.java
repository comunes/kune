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
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.share.ShareToListOnItemRemoved;
import cc.kune.lists.client.rpc.ListsServiceHelper;
import cc.kune.lists.shared.ListsToolConstants;

import com.google.inject.Inject;

public class ShareItemOfEditor extends AbstractShareItemWithMenu {

  private final ContentServiceHelper contentService;
  private final ListsServiceHelper listService;
  private final IconicResources res;

  @Inject
  public ShareItemOfEditor(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils, final IconicResources res,
      final CommonResources commonResources, final ContentServiceHelper contentService,
      final ListsServiceHelper listService) {
    super("", actionsPanel, downloadUtils, commonResources);
    this.res = res;
    this.contentService = contentService;
    this.listService = listService;
  }

  public AbstractShareItemUi of(final GroupDTO group, final String typeId,
      final ShareToListOnItemRemoved onItemRemoved) {
    setGroupName(group);
    final MenuItemDescriptor editorToAdmin = new MenuItemDescriptor(menu, new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        // TODO
        NotifyUser.info("In development");
      }
    });
    editorToAdmin.withText(I18n.t("Change to administrator")).withIcon(res.upArrow());
    final MenuItemDescriptor remove = new MenuItemDescriptor(menu, true, new AbstractExtendedAction() {
      @Override
      public void actionPerformed(final ActionEvent event) {
        final String participant = group.getShortName();
        final SimpleCallback onDel = new SimpleCallback() {
          @Override
          public void onCallback() {
            onItemRemoved.onRemove(ShareItemOfEditor.this);
          }
        };
        if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
          listService.subscribeAnUserToList(participant, false, onDel);
        } else {
          contentService.delParticipants(onDel, participant);
        }
      }
    });
    remove.withText(I18n.t("Remove")).withIcon(res.del());
    // super.add(editorToAdmin);
    super.add(remove);
    return this;
  }

  @Override
  public AbstractShareItemUi with(final String typeId) {
    if (typeId.equals(ListsToolConstants.TYPE_LIST)) {
      menu.setText(I18n.tWithNT("is member", "someone is member of a list"));
    } else {
      menu.setText(I18n.tWithNT("is editor", "someone is editor"));
    }
    return this;
  }

}
