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

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuCheckItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.common.client.errors.UIException;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MenuItem;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGwtMenuItemGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractGwtMenuItemGui extends AbstractGuiItem implements HasMenuItem {

  /** The icon label. */
  private IconLabel iconLabel;

  /** The item. */
  private GwtBaseMenuItem item;
  
  /** The res. */
  private final CommonResources res = CommonResources.INSTANCE;

  /**
   * Conf check listener.
   *
   * @param descriptor the descriptor
   * @param checkItem the check item
   */
  private void confCheckListener(final MenuCheckItemDescriptor descriptor, final GwtCheckItem checkItem) {
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuCheckItemDescriptor.CHECKED)) {
          final Boolean checked = (Boolean) event.getNewValue();
          setCheckedIcon(checked);
          layout();
        }
      }
    });
    setCheckedIcon(descriptor.isChecked());
    iconLabel.setWidth("100%");
  }

  /**
   * Conf radio check listener.
   *
   * @param descriptor the descriptor
   * @param checkItem the check item
   */
  private void confRadioCheckListener(final MenuRadioItemDescriptor descriptor,
      final GwtCheckItem checkItem) {
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {

        if (event.getPropertyName().equals(MenuCheckItemDescriptor.CHECKED)) {
          final Boolean checked = (Boolean) event.getNewValue();
          setRadioChecked(checked);
          layout();
        }
      }
    });
    setRadioChecked(descriptor.isChecked());
    iconLabel.setWidth("100%");
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#create(cc.kune.common.client.actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    super.descriptor = descriptor;
    iconLabel = new IconLabel("");
    if (descriptor instanceof MenuRadioItemDescriptor) {
      final GwtCheckItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
      final MenuRadioItemDescriptor radioDescrip = (MenuRadioItemDescriptor) descriptor;
      confRadioCheckListener(radioDescrip, checkItem);
      item = checkItem;
    } else if (descriptor instanceof MenuCheckItemDescriptor) {
      final GwtCheckItem checkItem = createCheckItem((MenuItemDescriptor) descriptor);
      confCheckListener((MenuCheckItemDescriptor) descriptor, checkItem);
      item = checkItem;
    } else if (descriptor instanceof MenuTitleItemDescriptor) {
      item = new GwtBaseMenuItem("", true);
      item.setStyleName("k-menuimtem-title");
    } else {
      item = new GwtBaseMenuItem("", true);
    }
    descriptor.putValue(MenuItemDescriptor.UI, this);
    final String id = descriptor.getId();
    if (id != null) {
      item.ensureDebugId(id);
    }
    // initWidget(item);
    item.setScheduledCommand(new ScheduledCommand() {

      @Override
      public void execute() {
        getParentMenu(descriptor).hide();
        final AbstractAction action = descriptor.getAction();
        if (action != null) {
          if (descriptor instanceof MenuRadioItemDescriptor) {
            final MenuRadioItemDescriptor checkItem = (MenuRadioItemDescriptor) descriptor;
            if (!checkItem.isChecked()) {
              checkItem.setChecked(true);
            }
          } else if (descriptor instanceof MenuCheckItemDescriptor) {
            final MenuCheckItemDescriptor checkItem = (MenuCheckItemDescriptor) descriptor;
            final boolean currentValue = checkItem.isChecked();
            checkItem.setChecked(!currentValue);
          }
          descriptor.getAction().actionPerformed(
              new ActionEvent(item, getTargetObjectOfAction(descriptor), Event.getCurrentEvent()));
        }
      }
    });
    configureItemFromProperties();

    final int position = descriptor.getPosition();
    final AbstractGwtMenuGui menu = getParentMenu(descriptor);
    if (menu == null) {
      // Note: Be careful if you have two menus (not a singleton) and you add
      // one but
      // no the other that it associated with this menuitem
      throw new UIException("Warning: To add a menu item you need to add the menu before. Item: "
          + descriptor);
    }
    if (position == GuiActionDescrip.NO_POSITION) {
      menu.add(item);
    } else {
      menu.insert(position, item);
    }
    return this;
  }

  /**
   * Creates the check item.
   *
   * @param descriptor the descriptor
   * @return the gwt check item
   */
  private GwtCheckItem createCheckItem(final MenuItemDescriptor descriptor) {
    final GwtCheckItem checkItem = new GwtCheckItem();
    checkItem.setChecked(((MenuCheckItemDescriptor) descriptor).isChecked());
    return checkItem;
  }

  /**
   * Gets the item.
   *
   * @return the item
   */
  public GwtBaseMenuItem getItem() {
    return item;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.gwtui.HasMenuItem#getMenuItem()
   */
  @Override
  public MenuItem getMenuItem() {
    return item;
  }

  /**
   * Gets the parent menu.
   *
   * @param descriptor the descriptor
   * @return the parent menu
   */
  private AbstractGwtMenuGui getParentMenu(final GuiActionDescrip descriptor) {
    return ((AbstractGwtMenuGui) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
  }

  /**
   * Layout.
   */
  private void layout() {
    item.setHTML(iconLabel.toString());
  }

  /**
   * Sets the checked icon.
   *
   * @param checked the new checked icon
   */
  private void setCheckedIcon(final Boolean checked) {
    iconLabel.setLeftIconResource(checked ? res.checked() : res.unChecked());
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  protected void setEnabled(final boolean enabled) {
    item.setEnabled(enabled);
    // setVisible(enabled);
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIcon(cc.kune.common.shared.res.KuneIcon)
   */
  @Override
  public void setIcon(final KuneIcon icon) {
    iconLabel.setLeftIconFont(icon);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java.lang.String)
   */
  @Override
  public void setIconBackground(final String backgroundColor) {
    iconLabel.setLeftIconBackground(backgroundColor);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconResource(com.google.gwt.resources.client.ImageResource)
   */
  @Override
  public void setIconResource(final ImageResource icon) {
    iconLabel.setRightIconResource(icon);
    // iconLabel.addRightIconStyle("k-fl");
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconStyle(java.lang.String)
   */
  @Override
  protected void setIconStyle(final String style) {
    iconLabel.setRightIcon(style);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setIconUrl(java.lang.String)
   */
  @Override
  public void setIconUrl(final String url) {
    iconLabel.setRightIconUrl(url);
    layout();
  }

  /**
   * Sets the radio checked.
   *
   * @param checked the new radio checked
   */
  private void setRadioChecked(final Boolean checked) {
    iconLabel.setLeftIconResource(checked ? res.radioChecked() : res.radioUnChecked());
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  protected void setText(final String text) {
    if (text != null) {
      final KeyStroke key = (KeyStroke) descriptor.getValue(Action.ACCELERATOR_KEY);
      if (key == null) {
        iconLabel.setText(text, descriptor.getDirection());
      } else {
        iconLabel.setText(text + " " + key.toString(), descriptor.getDirection());
      }
    }
    layout();
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    item.setVisible(visible);
    iconLabel.setVisible(visible);
    layout();
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() { // NOPMD by vjrj on 18/01/11 0:48
    return false;
  }
}
