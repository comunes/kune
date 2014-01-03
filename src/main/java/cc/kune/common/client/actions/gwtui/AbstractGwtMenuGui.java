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
package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.Position;
import cc.kune.common.client.resources.SubMenuResources;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGwtMenuGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractGwtMenuGui extends AbstractChildGuiItem implements ParentWidget {

  /** The menu. */
  protected MenuBar menu;
  
  /** The popup. */
  private PopupPanel popup;

  /**
   * Instantiates a new abstract gwt menu gui.
   */
  public AbstractGwtMenuGui() {
  }

  /**
   * Instantiates a new abstract gwt menu gui.
   *
   * @param descriptor the descriptor
   */
  public AbstractGwtMenuGui(final GuiActionDescrip descriptor) {
    super(descriptor);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.ParentWidget#add(com.google.gwt.user.client.ui.UIObject)
   */
  @Override
  public void add(final UIObject item) {
    menu.addItem((MenuItem) item);
  }

  /**
   * Adds the separator.
   *
   * @return the menu item separator
   */
  public MenuItemSeparator addSeparator() {
    return menu.addSeparator();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#configureItemFromProperties()
   */
  @Override
  public void configureItemFromProperties() {
    super.configureItemFromProperties();
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
          menu.clearItems();
        }
      }
    });
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractChildGuiItem#create(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.create(descriptor);
    menu = new MenuBar((Boolean) descriptor.getValue(MenuDescriptor.MENU_VERTICAL),
        SubMenuResources.INSTANCE);
    menu.setAutoOpen(true);
    menu.setFocusOnHoverEnabled(true);
    menu.setAnimationEnabled(true);
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
          if (popup != null && popup.isShowing()) {
            popup.hide();
          }
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SHOW)) {
          show();
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SELECTION_DOWN)) {
          menu.moveSelectionDown();
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SELECTION_UP)) {
          menu.moveSelectionUp();
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SELECT_ITEM)) {
          final HasMenuItem item = (HasMenuItem) ((MenuItemDescriptor) descriptor.getValue(MenuDescriptor.MENU_SELECT_ITEM)).getValue(MenuItemDescriptor.UI);
          menu.selectItem(item.getMenuItem());
        }
      }
    });
    return this;
  }

  /**
   * Creates the popup.
   *
   * @return the popup panel
   */
  private PopupPanel createPopup() {
    popup = new PopupPanel(true);
    popup.setStyleName("oc-menu");
    popup.add(menu);
    popup.addCloseHandler(new CloseHandler<PopupPanel>() {
      @Override
      public void onClose(final CloseEvent<PopupPanel> event) {
        descriptor.putValue(MenuDescriptor.MENU_ONHIDE, popup);
      }
    });
    return popup;
  }

  /**
   * Hide.
   */
  public void hide() {
    if (popup != null) {
      // if menu (not submenu)
      popup.hide();
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.ParentWidget#insert(int, com.google.gwt.user.client.ui.UIObject)
   */
  @Override
  public void insert(final int position, final UIObject item) {
    menu.insertItem((MenuItem) item, position);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() {
    return !descriptor.isChild();
  }

  /**
   * Show.
   */
  protected abstract void show();

  /**
   * Show relative to.
   *
   * @param relative the relative
   */
  public void showRelativeTo(final Object relative) {
    if (popup == null) {
      createPopup();
    }
    if (relative instanceof String) {
      popup.showRelativeTo(RootPanel.get((String) relative));
    } else if (relative instanceof UIObject) {
      popup.showRelativeTo((UIObject) relative);
    } else if (relative instanceof Position) {
      popup.setPopupPositionAndShow(new PositionCallback() {
        @Override
        public void setPosition(final int offsetWidth, final int offsetHeight) {
          final Position position = (Position) relative;
          popup.setPopupPosition(position.getX(), position.getY());
        }
      });
    }
    descriptor.putValue(MenuDescriptor.MENU_ONSHOW, popup);
    if (tooltip != null) {
      tooltip.hide();
    }
  }

}
