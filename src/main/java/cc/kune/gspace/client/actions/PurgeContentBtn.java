/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;

public class PurgeContentBtn extends ButtonDescriptor {

  public static class PurgeContentAction extends RolAction {

    private final ContentServiceHelper contentService;

    @Inject
    public PurgeContentAction(final ContentServiceHelper contentService, final IconicResources res) {
      super(AccessRolDTO.Administrator, true);
      this.contentService = contentService;
      this.withText(I18n.t("Delete permanently")).withIcon(res.trashGrey());
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      contentService.purgeContent(ActionUtils.getActionToken(event));
    }

  }

  @Inject
  public PurgeContentBtn(final PurgeContentAction action) {
    super(action);
  }

}
