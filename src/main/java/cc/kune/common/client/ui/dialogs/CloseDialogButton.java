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
package cc.kune.common.client.ui.dialogs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class CloseDialogButton.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class CloseDialogButton extends Composite implements HasClickHandlers {

  /**
   * The Interface CloseDialogButtonUiBinder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface CloseDialogButtonUiBinder extends UiBinder<Widget, CloseDialogButton> {
  }

  /** The ui binder. */
  private static CloseDialogButtonUiBinder uiBinder = GWT.create(CloseDialogButtonUiBinder.class);

  /** The close btn. */
  @UiField
  PushButton closeBtn;

  /**
   * Instantiates a new close dialog button.
   */
  public CloseDialogButton() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  /* (non-Javadoc)
   * @see com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google.gwt.event.dom.client.ClickHandler)
   */
  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    return closeBtn.addClickHandler(handler);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.UIObject#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    closeBtn.setVisible(visible);
  }

}
