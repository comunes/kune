/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

public class GroupShareItemDescriptor extends ShareItemDescriptor {

  private final ClientFileDownloadUtils downloadUtils;

  public GroupShareItemDescriptor(final ClientFileDownloadUtils downloadUtils, final String menuText,
      final MenuItemDescriptor... menuItems) {
    super(menuText, menuItems);
    this.downloadUtils = downloadUtils;
  }

  public void setGroup(final GroupDTO group) {
    final boolean isAnUser = group.isPersonal();
    setItemText((isAnUser ? "" : I18n.t("Group") + ": ") + group.getLongName());
    setItemIcon(downloadUtils.getGroupLogo(group));
    super.setGroup(group.getShortName());
  }
}
