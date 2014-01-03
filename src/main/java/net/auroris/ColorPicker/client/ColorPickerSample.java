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
package net.auroris.ColorPicker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class ColorPickerSample.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ColorPickerSample implements EntryPoint, ClickHandler
{
    
    /**
     * The Class ColorPickerDialog.
     *
     * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
     */
    private static class ColorPickerDialog extends DialogBox
    {
        
        /** The picker. */
        private ColorPicker picker;

        /**
         * Instantiates a new color picker dialog.
         */
        public ColorPickerDialog()
        {
            setText("Choose a color");

            setWidth("435px");
            setHeight("350px");

            // Define the panels
            VerticalPanel panel = new VerticalPanel();
            FlowPanel okcancel = new FlowPanel();
            picker = new ColorPicker();

            // Define the buttons
            Button ok = new Button("Ok");   // ok button
            ok.addClickHandler(new ClickHandler()
            {
                public void onClick(ClickEvent event)
                {
                    Window.alert("You chose " + picker.getHexColor());
                    ColorPickerDialog.this.hide();
                }
            });

            Button cancel = new Button("Cancel");   // cancel button
            cancel.addClickHandler(new ClickHandler()
            {
                public void onClick(ClickEvent event)
                {
                    ColorPickerDialog.this.hide();
                }
            });
            okcancel.add(ok);
            okcancel.add(cancel);

            // Put it together
            panel.add(picker);
            panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
            panel.add(okcancel);

            setWidget(panel);
        }
    }

    /** The picker dialog. */
    private ColorPickerDialog pickerDialog;

    /* (non-Javadoc)
     * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
     */
    public void onClick(ClickEvent event) {
        // Show the dialog box.
        pickerDialog.show();
    }

    /* (non-Javadoc)
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    public void onModuleLoad()
    {
        pickerDialog = new ColorPickerDialog();

        Button b = new Button("Click me");
        b.addClickHandler(this);

        RootPanel.get().add(b);
    }
}
