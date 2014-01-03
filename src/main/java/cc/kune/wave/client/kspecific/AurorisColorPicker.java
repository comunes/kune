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
package cc.kune.wave.client.kspecific;

import net.auroris.ColorPicker.client.ColorPicker;

import org.waveprotocol.wave.client.wavepanel.impl.toolbar.color.AbstractColorPicker;
import org.waveprotocol.wave.client.wavepanel.impl.toolbar.color.ComplexColorPicker;

import cc.kune.common.shared.i18n.I18n;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class AurorisColorPicker enables the auroris color picker in wave editor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class AurorisColorPicker extends AbstractColorPicker {

  /**
   * Instantiates a new sample custom color picker.
   *
   */
  public AurorisColorPicker() {
    super(ComplexColorPicker.getInstance());

    final FlowPanel auPickerPanel = new FlowPanel();
    auPickerPanel.setWidth("155px");
    auPickerPanel.addStyleName("k-aurorisColorPicker");
    final ColorPicker auPicker = new ColorPicker();
    final Button okBtn = new Button("Ok");
    final Button cancelBtn = new Button("Cancel");
    okBtn.addStyleName("k-fl");
    okBtn.addStyleName("k-aurorisColorPicker-btn");
    cancelBtn.addStyleName("k-fr");
    cancelBtn.addStyleName("k-aurorisColorPicker-btn");
    auPickerPanel.add(auPicker);
    auPickerPanel.add(okBtn);
    auPickerPanel.add(cancelBtn);
    okBtn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        onColorChoose("#" + auPicker.getHexColor());
        colorPicker.showWidget(0);
      }
    });
    cancelBtn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        colorPicker.showWidget(0);
      }
    });

    final PushButton custom = new PushButton(I18n.tWithNT("Custom...",
        "Used in a button to choose a custom color"));
    custom.addStyleName(ComplexColorPicker.style.buttonsMargins());
    custom.setStylePrimaryName(ComplexColorPicker.style.customColorPushbutton());
    custom.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        // ComplexColorPicker is a DeckPanel, so we show our widget
        colorPicker.showWidget(1);
      }
    });

    initWidget(auPickerPanel);

    // We add the button and the panel to the ComplexColorPicker (the button
    // opens the panel)
    colorPicker.addToBottom(custom);
    colorPicker.addColorPicker(this);
  }

}
