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
package cc.kune.gxtbinds.client.actions.gxtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.Position;
import cc.kune.common.client.errors.NotImplementedException;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGxtMenuGui.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractGxtMenuGui extends AbstractChildGuiItem implements ParentWidget {

  /**
   * The Enum MenuPosition.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public enum MenuPosition {

    /** The b. */
    b, // The top left corner (default)
    /** The bl. */
    bl, // The center of the top edge
    /** The br. */
    br, // The bottom right corner ,// The top right corner
    /** The c. */
    c, // The center of the left edge
    /** The l. */
    l, // In the center of the element
    /** The r. */
    r, // The center of the right edge
    /** The t. */
    t, // The bottom left corner
    /** The tl. */
    tl, // The center of the bottom edge
    /** The tr. */
    tr
  }

  /** The Constant DEF_MENU_POSITION. */
  public static final String DEF_MENU_POSITION = "bl";

  /** The Constant MENU_POSITION. */
  public static final String MENU_POSITION = "menu-position";

  /** The menu. */
  protected Menu menu;

  /**
   * Instantiates a new abstract gxt menu gui.
   */
  public AbstractGxtMenuGui() {
  }

  /**
   * Instantiates a new abstract gxt menu gui.
   * 
   * @param descriptor
   *          the descriptor
   */
  public AbstractGxtMenuGui(final GuiActionDescrip descriptor) {
    super(descriptor);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.ParentWidget#add(com.google.gwt.user.client
   * .ui.UIObject)
   */
  @Override
  public void add(final UIObject item) {
    menu.add((MenuItem) item);
  }

  /**
   * Adds the separator.
   */
  public void addSeparator() {
    menu.add(new SeparatorMenuItem());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#configureItemFromProperties
   * ()
   */
  @Override
  public void configureItemFromProperties() {
    super.configureItemFromProperties();
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
          menu.removeAll();
        }
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractChildGuiItem#create(cc.kune.common
   * .client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    super.create(descriptor);
    menu = new Menu();
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
          menu.hide();
        }
      }
    });
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_SHOW)) {
          show(descriptor.getValue(MenuDescriptor.MENU_SHOW_NEAR_TO));
        }
      }
    });
    return this;
  }

  /**
   * Gets the menu position.
   * 
   * @return the menu position
   */
  protected String getMenuPosition() {
    final MenuPosition position = (MenuPosition) descriptor.getValue(MENU_POSITION);
    return position == null ? DEF_MENU_POSITION : position.name();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.ParentWidget#insert(int,
   * com.google.gwt.user.client.ui.UIObject)
   */
  @Override
  public void insert(final int position, final UIObject item) {
    menu.insert((MenuItem) item, position);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java
   * .lang.String)
   */
  @Override
  public void setIconBackground(final String back) {
    throw new NotImplementedException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconUrl(java.lang.String
   * )
   */
  @Override
  public void setIconUrl(final String url) {
    throw new NotImplementedException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() {
    return !descriptor.isChild();
  }

  /**
   * Show.
   * 
   * @param relative
   *          the relative
   */
  public void show(final Object relative) {
    if (relative instanceof String) {
      menu.show(RootPanel.get((String) relative).getElement(), getMenuPosition());
    } else if (relative instanceof UIObject) {
      menu.show(((UIObject) relative).getElement(), getMenuPosition());
    } else if (relative instanceof Position) {
      final Position position = (Position) relative;
      menu.showAt(position.getX(), position.getY());
    }
  }

}
