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
package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.ActionStyles;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.core.client.events.AccessRightsChangedEvent;
import cc.kune.core.client.events.AccessRightsChangedEvent.AccessRightsChangedHandler;
import cc.kune.core.client.state.AccessRightsClientManager;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractEditorsMenu.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AbstractEditorsMenu extends MenuDescriptor {

  /**
   * Instantiates a new abstract editors menu.
   * 
   * @param rightsManager
   *          the rights manager
   */
  public AbstractEditorsMenu(final AccessRightsClientManager rightsManager) {
    this.withStyles(ActionStyles.MENU_BTN_STYLE_LEFT);
    rightsManager.onRightsChanged(true, new AccessRightsChangedHandler() {
      @Override
      public void onAccessRightsChanged(final AccessRightsChangedEvent event) {
        AbstractEditorsMenu.this.setVisible(event.getCurrentRights().isEditable());
      }
    });
  }
}
