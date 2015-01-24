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
package cc.kune.bootstrap.client.actions.ui;

import cc.kune.bootstrap.client.ui.CheckListItem;
import cc.kune.bootstrap.client.ui.ComplexAnchorListItem;
import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;

/**
 * The Class BSCheckMenuItemGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BSCheckMenuItemGui extends AbstractBSMenuItemGui {

  private void confCheckListener(final MenuCheckItemDescriptor descriptor) {
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuCheckItemDescriptor.CHECKED)) {
          setChecked((Boolean) event.getNewValue());
        }
      }
    });
    setChecked(descriptor.isChecked());
  }

  @Override
  protected void configureClickListener() {
    item.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        final AbstractAction action = descriptor.getAction();
        if (action != null) {
          if (descriptor instanceof MenuCheckItemDescriptor) {
            final MenuCheckItemDescriptor checkItem = (MenuCheckItemDescriptor) descriptor;
            final boolean currentValue = checkItem.isChecked();
            checkItem.setChecked(!currentValue);
          }
          descriptor.getAction().actionPerformed(
              new ActionEvent(item, getTargetObjectOfAction(descriptor), Event.getCurrentEvent()));
        }
      }
    });
  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.create(descriptor);
    confCheckListener((MenuCheckItemDescriptor) descriptor);
    return this;
  }

  @Override
  protected ComplexAnchorListItem createMenuItem() {
    return new CheckListItem();
  }

  private void setChecked(final boolean checked) {
    ((CheckListItem) item).setChecked(checked);
  }

}