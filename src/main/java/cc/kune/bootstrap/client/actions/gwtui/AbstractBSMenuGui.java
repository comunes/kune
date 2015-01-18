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
package cc.kune.bootstrap.client.actions.gwtui;

import java.util.Arrays;
import java.util.List;

import org.gwtbootstrap3.client.shared.event.HideEvent;
import org.gwtbootstrap3.client.shared.event.HideHandler;
import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Divider;
import org.gwtbootstrap3.client.ui.DropDown;
import org.gwtbootstrap3.client.ui.base.HasIcon;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Styles;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.gwtui.HasMenuItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.Position;
import cc.kune.common.shared.i18n.I18n;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * The Class AbstractBSMenuGui.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractBSMenuGui extends AbstractBSChildGuiItem implements ParentWidget {

  /** The menu. */
  protected DropDown dropDown;

  protected CustomDropDownMenu menu;
  /** The popup. */
  private PopupPanel popup;

  /**
   * Instantiates a new abstract gwt menu gui.
   */
  public AbstractBSMenuGui() {

  }

  /**
   * Instantiates a new abstract gwt menu gui.
   *
   * @param descriptor
   *          the descriptor
   */
  public AbstractBSMenuGui(final GuiActionDescrip descriptor) {
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
    menu.add((IsWidget) item);
  }

  /**
   * Adds the separator.
   *
   * @return the menu item separator
   */
  public Divider addSeparator() {
    final Divider divider = new Divider();
    menu.add(divider);
    return divider;
  }

  private void clearCurrentActiveItem() {

    final int count = menu.getWidgetCount();
    for (int cur = 0; cur < count; cur++) {
      final Widget item = menu.getWidget(cur);
      final List<String> styles = Arrays.asList(item.getStyleName().split(" "));
      if (styles.contains(Styles.ACTIVE)) {
        item.removeStyleName(Styles.ACTIVE);
      }
    }
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
          menu.clear();
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
    super.create(descriptor);

    dropDown = new DropDown();

    final Anchor anchor = new Anchor();
    anchor.setDataToggle(Toggle.DROPDOWN);
    anchor.setText(I18n.t("Click to toggle dropdown"));
    dropDown.add(anchor);
    menu = new CustomDropDownMenu();

    dropDown.add(menu);

    menu.addHideHandler(new HideHandler() {
      @Override
      public void onHide(final HideEvent hideEvent) {
        descriptor.putValue(MenuDescriptor.MENU_ONHIDE, menu);
      }
    });

    final Boolean inline = (Boolean) descriptor.getValue(MenuDescriptor.MENU_VERTICAL);
    menu.setInline(inline);

    // menu.setAutoOpen(true);
    // menu.setFocusOnHoverEnabled(true);
    // menu.setAnimationEnabled(true);

    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      private void activeNextItem(final int increment) {
        final int count = menu.getWidgetCount();
        for (int cur = 0; cur < count && cur >= 0; cur = cur + increment) {
          final Widget item = menu.getWidget(cur);
          final List<String> styles = Arrays.asList(item.getStyleName().split(" "));
          if (styles.contains(Styles.ACTIVE)) {
            item.removeStyleName(Styles.ACTIVE);
            final int proposed = cur + increment;
            final int next = proposed >= count ? 0 : (proposed < 0) ? count - 1 : proposed;
            menu.getWidget(next).addStyleName(Styles.ACTIVE);
            break;
          }
        }
      }

      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
          if (popup != null && popup.isShowing()) {
            popup.hide();
          }
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SHOW)) {
          show();
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SELECTION_DOWN)) {
          activeNextItem(1);
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SELECTION_UP)) {
          activeNextItem(-1);
        } else if (event.getPropertyName().equals(MenuDescriptor.MENU_SELECT_ITEM)) {
          final HasMenuItem item = (HasMenuItem) ((MenuItemDescriptor) descriptor.getValue(MenuDescriptor.MENU_SELECT_ITEM)).getValue(MenuItemDescriptor.UI);
          clearCurrentActiveItem();
          ((Widget) item).addStyleName(Styles.ACTIVE);
        }
      }
    });
    return this;
  }

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

  public void hide() {
    if (popup != null) {
      // if menu (not submenu)
      popup.hide();
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.common.client.actions.ui.ParentWidget#insert(int,
   * com.google.gwt.user.client.ui.UIObject)
   */
  @Override
  public void insert(final int position, final UIObject item) {
    menu.insert((Widget) item, position);
  }

  @Override
  protected void setIcon(final Object icon) {
    if (icon instanceof IconType) {
      final UIObject widget = descriptor.isChild() ? child : getWidget();
      if (widget instanceof HasIcon) {
        ((HasIcon) widget).setIcon((IconType) icon);
      }
    } else {
      super.setIcon(icon);
    }
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
   */
  protected abstract void show();

  /**
   * Show relative to.
   *
   * @param relative
   *          the relative
   */
  public void showRelativeTo(final Object relative) {
    // TODO
    // Maybe: http://jsbin.com/iGaHAtu/2/edit?html,css,js,output
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
    descriptor.putValue(MenuDescriptor.MENU_ONSHOW, menu);
    if (tooltip != null) {
      tooltip.hide();
    }
  }

}
