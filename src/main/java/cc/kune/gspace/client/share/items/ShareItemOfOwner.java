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
import cc.kune.common.client.actions.ui.descrip.LabelDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.dto.GroupDTO;

import com.google.inject.Inject;

public class ShareItemOfOwner extends AbstractShareItemUi {

  @Inject
  public ShareItemOfOwner(final ActionSimplePanel actionsPanel,
      final ClientFileDownloadUtils downloadUtils) {
    super(actionsPanel, downloadUtils);
  }

  public AbstractShareItemUi of(final GroupDTO group) {
    setGroupName(group);
    final LabelDescriptor isOwner = new LabelDescriptor(I18n.t("is owner"));
    isOwner.withStyles("k-share-item-noactions");
    super.add(isOwner);
    return this;
  }

}
