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

import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

public class EditorOfContentShareItemDescriptor extends GroupShareItemDescriptor {
  public static class ChangeToAdminAction extends AbstractSetRoleOnContentAction {
    @Inject
    ChangeToAdminAction(final Provider<ContentServiceHelper> helper) {
      super(AccessRolDTO.Administrator, helper);
    }
  }

  @Singleton
  public static class ChangeToAdminMenuItem extends AbstractRoleShareMenuItem {
    @Inject
    public ChangeToAdminMenuItem(final ChangeToAdminAction action, final IconicResources icons) {
      super(action);
      withIcon(icons.upArrow()).withText(I18n.t("Change to administrator"));
    }
  }

  public static class ChangeToViewerAction extends AbstractSetRoleOnContentAction {
    @Inject
    ChangeToViewerAction(final Provider<ContentServiceHelper> helper) {
      super(AccessRolDTO.Viewer, helper);
    }
  }

  @Singleton
  public static class ChangeToViewerMenuItem extends AbstractRoleShareMenuItem {
    @Inject
    public ChangeToViewerMenuItem(final ChangeToViewerAction action, final IconicResources icons) {
      super(action);
      withIcon(icons.downArrow()).withText(I18n.t("Change to viewer"));
    }
  }

  @Inject
  public EditorOfContentShareItemDescriptor(final IconicResources icons,
      final ClientFileDownloadUtils downloadUtils, final ChangeToAdminMenuItem changeToAdmin,
      final ChangeToViewerMenuItem changeToViewer, final RemoveShareItemMenuItem remove) {
    super(downloadUtils, I18n.tWithNT("is editor", "someone is editor"), changeToAdmin, changeToViewer,
        remove);
  }

}