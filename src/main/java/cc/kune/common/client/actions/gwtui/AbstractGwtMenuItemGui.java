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

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MenuItem;

public abstract class AbstractGwtMenuItemGui extends AbstractGuiItem implements HasMenuItem {

  private IconLabel iconLabel;

  private GwtBaseMenuItem item;
  private final CommonResources res = CommonResources.INSTANCE;

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
    item.setCommand(new Command() {
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

  private GwtCheckItem createCheckItem(final MenuItemDescriptor descriptor) {
    final GwtCheckItem checkItem = new GwtCheckItem();
    checkItem.setChecked(((MenuCheckItemDescriptor) descriptor).isChecked());
    return checkItem;
  }

  public GwtBaseMenuItem getItem() {
    return item;
  }

  @Override
  public MenuItem getMenuItem() {
    return item;
  }

  private AbstractGwtMenuGui getParentMenu(final GuiActionDescrip descriptor) {
    return ((AbstractGwtMenuGui) descriptor.getParent().getValue(ParentWidget.PARENT_UI));
  }

  private void layout() {
    item.setHTML(iconLabel.toString());
  }

  private void setCheckedIcon(final Boolean checked) {
    iconLabel.setLeftIconResource(checked ? res.checked() : res.unChecked());
  }

  @Override
  protected void setEnabled(final boolean enabled) {
    item.setEnabled(enabled);
    // setVisible(enabled);
  }

  @Override
  public void setIconBackground(final String backgroundColor) {
    iconLabel.setLeftIconBackground(backgroundColor);
    layout();
  }

  @Override
  public void setIconResource(final ImageResource icon) {
    iconLabel.setRightIconResource(icon);
    // iconLabel.addRightIconStyle("k-fl");
    layout();
  }

  @Override
  protected void setIconStyle(final String style) {
    iconLabel.setRightIcon(style);
    layout();
  }

  @Override
  public void setIconUrl(final String url) {
    iconLabel.setRightIconUrl(url);
    layout();
  }

  private void setRadioChecked(final Boolean checked) {
    iconLabel.setLeftIconResource(checked ? res.radioChecked() : res.radioUnChecked());
  }

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

  @Override
  public void setVisible(final boolean visible) {
    item.setVisible(visible);
    iconLabel.setVisible(visible);
    layout();
  }

  @Override
  public boolean shouldBeAdded() { // NOPMD by vjrj on 18/01/11 0:48
    return false;
  }
}
