/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
 *
 */
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.AbstractContentSimpleDTO;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;

public class DelContainerForEditorsMenuItem extends MenuItemDescriptor {

  public static class DelContainerForEditorAction extends RolAction {

    private final ContentServiceHelper contentService;

    @Inject
    public DelContainerForEditorAction(final ContentServiceHelper contentService) {
      super(AccessRolDTO.Editor, true);
      this.contentService = contentService;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      final StateToken token = ((AbstractContentSimpleDTO) event.getTarget()).getStateToken();
      contentService.delContent(token);
    }

  }

  public DelContainerForEditorsMenuItem(final I18nTranslationService i18n,
      final DelContainerForEditorAction action, final IconicResources res) {
    super(action);
    this.withText(i18n.t("Delete")).withIcon(res.trashGrey());
  }

}
