/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.colorpicker.client;

import net.auroris.ColorPicker.client.ColorPicker;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ComplexColorPicker extends DeckPanel {

  public ComplexColorPicker(final OnColorSelectedListener listener) {
    VerticalPanel vp = new VerticalPanel();
    SimpleColorPicker simplePicker = new SimpleColorPicker(listener);

    FlowPanel auPickerPanel = new FlowPanel();
    auPickerPanel.setWidth("155px");
    final ColorPicker auPicker = new ColorPicker();
    Button okBtn = new Button("Ok");
    Button cancelBtn = new Button("Cancel");
    okBtn.addStyleName("k-fl");
    okBtn.addStyleName("k-color-toolbar");
    cancelBtn.addStyleName("k-fr");
    cancelBtn.addStyleName("k-color-toolbar");
    auPickerPanel.add(auPicker);
    auPickerPanel.add(okBtn);
    auPickerPanel.add(cancelBtn);
    okBtn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        listener.onColorChoose("#" + auPicker.getHexColor());
        ComplexColorPicker.this.showWidget(0);
      }
    });
    cancelBtn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        ComplexColorPicker.this.showWidget(0);
      }
    });
    Label custom = new Label("Custom...");
    custom.addStyleName("k-color-toolbar");
    custom.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        ComplexColorPicker.this.showWidget(1);
      }
    });
    vp.add(simplePicker);
    vp.add(custom);
    super.add(vp);
    super.add(auPickerPanel);
    this.showWidget(0);
  }

}
