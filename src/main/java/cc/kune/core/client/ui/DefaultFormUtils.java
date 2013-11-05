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
package cc.kune.core.client.ui;

import cc.kune.common.client.tooltip.Tooltip;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.Radio;

public class DefaultFormUtils {

  public static final String BIG_FIELD_SIZE = "310px";

  public static FieldSet createFieldSet(final String heading) {
    return createFieldSet(heading, BIG_FIELD_SIZE);
  }

  public static FieldSet createFieldSet(final String heading, final String width) {
    final FieldSet fieldSet = new FieldSet();
    fieldSet.setHeadingHtml(heading);
    fieldSet.addStyleName("k-form-fieldset");
    fieldSet.setCollapsible(false);
    fieldSet.setWidth(width);
    fieldSet.setAutoHeight(true);
    return fieldSet;
  }

  public static Radio createRadio(final FieldSet fieldSet, final String radioLabel,
      final String radioFieldName, final String radioTip, final String id) {
    final Radio radio = new Radio();
    radio.setName(radioFieldName);
    radio.setHideLabel(true);
    radio.setId(id);
    fieldSet.add(radio);

    radio.addListener(Events.OnMouseOver, new Listener<BaseEvent>() {
      private Tooltip tooltip;

      @Override
      public void handleEvent(final BaseEvent be) {
        if (radioTip != null && tooltip == null) {
          tooltip = Tooltip.to(radio, radioTip);
          tooltip.setWidth(300);
        }
      }
    });
    radio.setBoxLabel(radioLabel);
    return radio;
  }
}
