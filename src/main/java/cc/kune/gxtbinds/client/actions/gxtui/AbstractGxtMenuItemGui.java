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

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractChildGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.shared.res.KuneIcon;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.CheckMenuItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGxtMenuItemGui.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractGxtMenuItemGui extends AbstractChildGuiItem {

  /** The item. */
  private MenuItem item;

  /**
   * Instantiates a new abstract gxt menu item gui.
   */
  public AbstractGxtMenuItemGui() {
    super();
  }

  /**
   * Instantiates a new abstract gxt menu item gui.
   * 
   * @param descriptor
   *          the descriptor
   */
  public AbstractGxtMenuItemGui(final MenuItemDescriptor descriptor) {
    super(descriptor);

  }

  /**
   * Conf check listener.
   * 
   * @param descriptor
   *          the descriptor
   * @param checkItem
   *          the check item
   */
  private void confCheckListener(final MenuItemDescriptor descriptor, final CheckMenuItem checkItem) {
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuCheckItemDescriptor.CHECKED)) {
          checkItem.setChecked((Boolean) event.getNewValue());
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
    if (descriptor instanceof MenuRadioItemDescriptor) {
      final CheckMenuItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
      checkItem.setGroup(((MenuRadioItemDescriptor) descriptor).getGroup());
      confCheckListener((MenuItemDescriptor) descriptor, checkItem);
      item = checkItem;
    } else if (descriptor instanceof MenuCheckItemDescriptor) {
      final CheckMenuItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
      confCheckListener((MenuItemDescriptor) descriptor, checkItem);
      item = checkItem;
    } else {
      item = new MenuItem("");
    }

    final String id = descriptor.getId();
    if (id != null) {
      item.ensureDebugId(id);
    }
    item.addSelectionListener(new SelectionListener<MenuEvent>() {
      @Override
      public void componentSelected(final MenuEvent ce) {
        final AbstractAction action = descriptor.getAction();
        if (action != null) {
          action.actionPerformed(new ActionEvent(item, getTargetObjectOfAction(descriptor),
              Event.getCurrentEvent()));
        }
      }
    });
    child = item;
    super.create(descriptor);
    configureItemFromProperties();
    return this;
  }

  /**
   * Creates the check item.
   * 
   * @param descriptor
   *          the descriptor
   * @return the check menu item
   */
  private CheckMenuItem createCheckItem(final MenuItemDescriptor descriptor) {
    final CheckMenuItem checkItem = new CheckMenuItem();
    checkItem.setChecked(((MenuCheckItemDescriptor) descriptor).isChecked());
    return checkItem;
  }

  /**
   * Creates the short cut.
   * 
   * @param key
   *          the key
   * @param style
   *          the style
   * @return the string
   */
  private String createShortCut(final KeyStroke key, final String style) {
    // See: https://yui-ext.com/forum/showthread.php?t=5762
    final Element keyLabel = DOM.createSpan();
    keyLabel.setId(style);
    keyLabel.setInnerText(key.toString());
    return keyLabel.getString();
  }

  /**
   * Gets the item.
   * 
   * @return the item
   */
  public MenuItem getItem() {
    return item;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  protected void setEnabled(final boolean enabled) {
    item.setVisible(enabled);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(cc.kune.common
   * .shared.res.KuneIcon)
   */
  @Override
  public void setIcon(final KuneIcon icon) {
    // TODO Auto-generated method stub
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
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconResource(com.google
   * .gwt.resources.client.ImageResource)
   */
  @Override
  public void setIconResource(final ImageResource icon) {
    item.setIcon(AbstractImagePrototype.create(icon));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconStyle(java.lang
   * .String)
   */
  @Override
  protected void setIconStyle(final String style) {
    item.setIconStyle(style);
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
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  protected void setText(final String text) {
    if (text != null) {
      final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
      if (key == null) {
        item.setText(text);
      } else {
        item.setText(text + createShortCut(key, "oc-mshortcut-hidden")
            + createShortCut(key, "oc-mshortcut"));
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    item.setVisible(visible);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() { // NOPMD by vjrj on 18/01/11 0:48
    return false;
  }
}
