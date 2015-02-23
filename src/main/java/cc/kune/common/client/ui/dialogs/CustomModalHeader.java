/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under 
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

package cc.kune.common.client.ui.dialogs;

import org.gwtbootstrap3.client.ui.ModalHeader;
import org.gwtbootstrap3.client.ui.base.button.CloseButton;
import org.gwtbootstrap3.client.ui.constants.ButtonDismiss;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Styles;

import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;

public class CustomModalHeader extends ModalHeader implements HasCloseHandlers {
  private final CloseButton closeButton = new CloseButton();
  private IconLabel heading;

  public CustomModalHeader() {
    super();
    // We remove and override the default closable button
    super.setClosable(false);
    closeButton.setDataDismiss(ButtonDismiss.MODAL);
    add(closeButton);
    closeButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent evt) {
        CloseEvent.fire(CustomModalHeader.this, CustomModalHeader.this);
      }
    });
    heading = new IconLabel();
    add(heading);

    // TODO
    // private final Heading heading = new Heading(HeadingSize.H4);
    
    setStyleName(Styles.MODAL_HEADER);
    heading.setStyleName(Styles.MODAL_TITLE);
    heading.addStyleName("k-modal-header");
  }

  public CloseButton getCloseButton() {
    return closeButton;
  }

  @Override
  public void setTitle(String title) {
    heading.setText(title);
  }

  @Override
  public void setClosable(final boolean closable) {
    if (closable) {
      insert(closeButton, (Element) getElement(), 0, true);
    } else {
      closeButton.removeFromParent();
    }
  }

  public HandlerRegistration addCloseHandler(final CloseHandler handler) {
    return addHandler(handler, CloseEvent.getType());
  }

  @Override
  public boolean isClosable() {
    return closeButton.getParent() != null;
  }

  public void setIconResource(ImageResource img) {
    heading.setLeftIconResource(img);
  }

  public void setIconUrl(String url) {
    heading.setLeftIconUrl(url);
  }

  public void setIconStyle(String icon) {
    heading.setLeftIcon(icon);
  }



}
