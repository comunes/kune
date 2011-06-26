/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.state.AccessRightsChangedEvent;
import cc.kune.core.client.state.AccessRightsChangedEvent.AccessRightsChangedHandler;
import cc.kune.core.client.state.AccessRightsClientManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;

public class AbstractNewMenu extends MenuDescriptor {

  @Inject
  public AbstractNewMenu(final CoreResources res, final I18nTranslationService i18n,
      final AccessRightsClientManager rightsManager) {
    super();
    this.withIcon(res.addGreen()).withStyles("k-button, k-btn, k-5corners, k-def-docbtn, k-fl").withText(
        "New");

    rightsManager.onRightsChanged(true, new AccessRightsChangedHandler() {
      @Override
      public void onAccessRightsChanged(final AccessRightsChangedEvent event) {
        AbstractNewMenu.this.setVisible(event.getCurrentRights().isEditable());
      }
    });
  }

}
