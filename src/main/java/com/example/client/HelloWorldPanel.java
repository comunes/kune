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
package com.example.client;

import cc.kune.common.client.notify.NotifyUser;

import com.example.client.HelloWorldPresenter.HelloWorldView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class HelloWorldPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class HelloWorldPanel extends ViewImpl implements HasText, HelloWorldView {

  /**
   * The Interface HelloWorldPanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface HelloWorldPanelUiBinder extends UiBinder<Widget, HelloWorldPanel> {
  }

  /** The ui binder. */
  private static HelloWorldPanelUiBinder uiBinder = GWT.create(HelloWorldPanelUiBinder.class);

  /** The button. */
  @UiField
  Button button;

  /** The widget. */
  private final Widget widget;

  /**
   * Instantiates a new hello world panel.
   */
  public HelloWorldPanel() {
    widget = uiBinder.createAndBindUi(this);
  }

  /**
   * Instantiates a new hello world panel.
   * 
   * @param firstName
   *          the first name
   */
  public HelloWorldPanel(final String firstName) {
    widget = uiBinder.createAndBindUi(this);
    button.setText(firstName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return widget;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasText#getText()
   */
  @Override
  public String getText() {
    return button.getText();
  }

  /**
   * On click.
   * 
   * @param e
   *          the e
   */
  @UiHandler("button")
  void onClick(final ClickEvent e) {
    NotifyUser.info("Hello world!");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
   */
  @Override
  public void setText(final String text) {
    button.setText(text);
  }

}
