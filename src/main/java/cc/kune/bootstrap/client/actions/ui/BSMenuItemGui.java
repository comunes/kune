/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.bootstrap.client.actions.ui;

import cc.kune.bootstrap.client.ui.ComplexAnchorListItem;
import cc.kune.common.client.log.Log;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * The Class BSMenuItemGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BSMenuItemGui extends AbstractBSMenuItemGui {

  @Override
  protected void configureClickListener() {
    item.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        // It's this necessary?
        try {
          getParentMenu(descriptor).hide();
        } catch (final ClassCastException e) {
          Log.error("Failed to close parent widget" + descriptor.getParent());
        }
        clickHandlerChildDefault.onClick(event);
      }
    });

  }

  @Override
  protected ComplexAnchorListItem createMenuItem() {
    return new ComplexAnchorListItem();
  }

}
