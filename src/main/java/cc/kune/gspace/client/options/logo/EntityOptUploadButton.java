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
package cc.kune.gspace.client.options.logo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasText;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityOptUploadButton.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityOptUploadButton extends Composite implements HasClickHandlers {

  /** The btn. */
  private final Button btn;

  /**
   * Instantiates a new entity opt upload button.
   * 
   * @param text
   *          the text
   */
  public EntityOptUploadButton(final String text) {
    final DecoratorPanel decorator = new DecoratorPanel();
    btn = new Button(text);
    btn.addStyleName("k-button");
    initWidget(decorator);
    decorator.setWidget(btn);
    decorator.setHeight("50px");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.dom.client.HasClickHandlers#addClickHandler(com.google
   * .gwt.event.dom.client.ClickHandler)
   */
  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    return addDomHandler(handler, ClickEvent.getType());
  }

  /**
   * Checks for text.
   * 
   * @return the checks for text
   */
  public HasText hasText() {
    return btn;
  }
}
