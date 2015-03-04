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

import org.gwtbootstrap3.client.ui.DropDown;
import org.gwtbootstrap3.client.ui.constants.Pull;

import cc.kune.bootstrap.client.ui.ComplexDropDownMenu;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.WidgetMenuDescriptor;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class BSMenuGui extends AbstractBSChildGuiItem implements AbstractBSMenuGui {

  private ComplexDropDownMenu<DropDown> menu;
  private PopupBSMenuGui popup;

  @Override
  public void add(final UIObject uiObject) {
    final Widget childWidget = (Widget) uiObject;
    menu.add(childWidget);
  }

  @Override
  protected void addStyle(final String style) {
    menu.addStyleName(style);
  }

  @Override
  public void configureItemFromProperties() {
    super.configureItemFromProperties();
    descriptor.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(WidgetMenuDescriptor.MENU_CLEAR)) {
          menu.clear();
        }
      }
    });
  }

  @Override
  public AbstractGuiItem create(final GuiActionDescrip descriptor) {
    this.descriptor = descriptor;
    final DropDown dropDown = new DropDown();
    dropDown.addStyleName("btn-group");
    menu = new ComplexDropDownMenu<DropDown>(dropDown);
    final String id = HTMLPanel.createUniqueId();
    dropDown.getElement().setId(id);
    menu.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        // Note: in smartmenus is setted to showOnClick: true,
        event.stopPropagation();
        clickHandlerDefault.onClick(event);
        menu.getList().getElement().getStyle().setDisplay(Display.BLOCK);
        show();
      }
    });

    popup = new PopupBSMenuGui(menu.getList(), menu.getWidget(), descriptor);

    descriptor.putValue(ParentWidget.PARENT_UI, this);
    initWidget(dropDown);
    configureItemFromProperties();

    if ((Boolean) descriptor.getValue(MenuDescriptor.MENU_ATRIGHT)) {
      menu.getList().setPull(Pull.RIGHT);
    }
    return this;
  }

  @Override
  public void hide() {
    popup.hide();
  }

  @Override
  public void insert(final int position, final UIObject uiObject) {
    menu.insert((Widget) uiObject, position);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.common.client.actions.ui.AbstractGuiItem#setEnabled(boolean)
   */
  @Override
  public void setEnabled(final boolean enabled) {
    menu.setEnabled(enabled);
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
    menu.setIcon(icon);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setIconBackground(java
   * .lang.String)
   */
  @Override
  public void setIconBackColor(final String backgroundColor) {
    menu.setIconBackColor(backgroundColor);
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
    menu.setIconResource(icon);
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
    menu.setIconStyle(style);
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
    menu.setIconUrl(url);
  }

  /**
   * Sets the pressed.
   *
   * @param pressed
   *          the new pressed
   */
  public void setPressed(final boolean pressed) {
    menu.setActive(pressed);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    menu.setText(text);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.common.client.actions.ui.AbstractGuiItem#setToolTipText(java.lang
   * .String)
   */
  @Override
  public void setToolTipText(final String tooltipText) {
    // Tooltip.to(menu.getWidget(), tooltipText);
    Tooltip.to(menu.getAnchor(), tooltipText);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    menu.setVisible(visible);
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

  @Override
  public void show() {
    popup.show();
  }
}
