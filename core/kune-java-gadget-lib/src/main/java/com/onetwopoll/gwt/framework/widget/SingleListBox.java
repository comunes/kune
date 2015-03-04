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
package com.onetwopoll.gwt.framework.widget;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

public class SingleListBox extends ListBox implements HasValue<String> {

  public SingleListBox() {
    super(false);
    setVisibleItemCount(1);

    addChangeHandler(new ChangeHandler() {

      @Override
      public void onChange(ChangeEvent event) {
        ValueChangeEvent.fire(SingleListBox.this, getValue());
      }
    });
  }

  @Override
  public String getValue() {
    return getValue(getSelectedIndex());
  }

  @Override
  public void setValue(String text) {
    for (int i = 0; i < getItemCount(); i++) {
      if (text.equals(getValue(i)))
        setSelectedIndex(i);
    }
  }

  @Override
  public void setValue(String value, boolean fireEvents) {
    if (fireEvents)
      ValueChangeEvent.fireIfNotEqual(this, getValue(), value);
    setValue(value);
  }

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

}