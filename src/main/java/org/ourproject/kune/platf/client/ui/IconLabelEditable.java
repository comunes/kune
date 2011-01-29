/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 *
 */
package org.ourproject.kune.platf.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.calclab.suco.client.events.Listener2;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author vjrj
 * 
 */
public class IconLabelEditable extends Composite {

    private ClickHandler clickHandler;
    private String clickLabel;
    private String currentText;
    private String dblClickLabel;
    private DoubleClickHandler doubleClickHandler;
    // private Event2<String, String> onEditEvent;
    private TextBox editor;
    private HorizontalPanel hpanel;
    private final AbstractLabel label;
    private MouseOutHandler mouseOutHandler;
    private MouseOverHandler mouseOverHandler;
    private List<HandlerRegistration> registrations;
    private boolean useDoubleClick;

    public IconLabelEditable() {
        this("");
    }

    public IconLabelEditable(final AbstractImagePrototype icon, final String text, final boolean useDoubleClick) { // NO_UCD
        super();
        label = new IconLabel(icon, text);
        init(text, useDoubleClick);
    }

    public IconLabelEditable(final AbstractImagePrototype icon, final String text, final String targetToken, // NO_UCD
            final boolean useDoubleClick) {
        super();
        label = new IconHyperlink(icon, text, targetToken);
        init(text, useDoubleClick);
    }

    public IconLabelEditable(final String text) {
        this(text, false);
    }

    public IconLabelEditable(final String text, final boolean useDoubleClick) { // NO_UCD
        this(text, false, useDoubleClick);
    }

    public IconLabelEditable(final String text, final boolean wordWrap, final boolean useDoubleClick) { // NO_UCD
        super();
        label = new LabelWrapper(text, wordWrap);
        init(text, useDoubleClick);
    }

    private void afterEdit() {
        final String text = editor.getText();
        // onEditEvent.fire(currentText, text);
        setTextImpl(text);
        editor.setVisible(false);
        editor.setReadOnly(true);
        label.setVisible(true);
        label.removeStyleDependentName("high");
    }

    public void edit() {
        showEditor();
        editor.setFocus(true);
    }

    public String getText() {
        return label.getText();
    }

    private void init(final String text, final boolean useDoubleClick) {
        // this.onEditEvent = new Event2<String, String>("onLabelEdit");
        registrations = new ArrayList<HandlerRegistration>();
        dblClickLabel = "Double click to rename";
        clickLabel = "Click to rename";
        hpanel = new HorizontalPanel();
        hpanel.add((Widget) label);
        initWidget(hpanel);
        this.currentText = text;
        this.useDoubleClick = useDoubleClick;
        label.setStylePrimaryName("kune-EditableLabel");
        doubleClickHandler = new DoubleClickHandler() {
            @Override
            public void onDoubleClick(final DoubleClickEvent event) {
                showEditor();
            }
        };
        clickHandler = new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                showEditor();
            }
        };
        mouseOverHandler = new MouseOverHandler() {
            @Override
            public void onMouseOver(final MouseOverEvent event) {
                label.addStyleDependentName("high");
            }
        };
        mouseOutHandler = new MouseOutHandler() {
            @Override
            public void onMouseOut(final MouseOutEvent event) {
                label.removeStyleDependentName("high");
            }
        };
        setEditableImpl(false);
    }

    public void onEdit(final Listener2<String, String> slot) {
        // onEditEvent.add(slot);
    }

    private void reset() {
        label.removeStyleDependentName("noneditable");
        label.removeStyleDependentName("editable");
        for (final HandlerRegistration reg : registrations) {
            reg.removeHandler();
        }
        registrations.clear();
    }

    /**
     * Sets the text tooltip showed to indicate "click to rename".
     * 
     * @param text
     *            the new click to rename label
     */
    public void setClickToRenameLabel(final String text) {
        this.clickLabel = text;
    }

    /**
     * Sets the text tooltip showed to indicate "double click to rename".
     * 
     * @param text
     *            the new double click to rename label
     */
    public void setDoubleClickToRenameLabel(final String text) {
        this.dblClickLabel = text;
    }

    public void setEditable(final boolean editable) {
        setEditableImpl(editable);
    }

    private void setEditableImpl(final boolean editable) {
        reset();
        if (editable) {
            if (useDoubleClick) {
                label.setTitle(dblClickLabel);
                registrations.add(label.addDoubleClickHandler(doubleClickHandler));
            } else {
                label.setTitle(clickLabel);
                registrations.add(label.addClickHandler(clickHandler));
            }
            registrations.add(label.addMouseOverHandler(mouseOverHandler));
            registrations.add(label.addMouseOutHandler(mouseOutHandler));
            label.addStyleDependentName("editable");
        } else {
            label.setTitle(null);
            label.addStyleDependentName("noneditable");
        }
    }

    public void setText(final String text) {
        setTextImpl(text);
    }

    private void setTextImpl(final String text) {
        this.currentText = text;
        label.setText(text);
    }

    private void showEditor() {
        final int hpWidth = hpanel.getParent().getOffsetWidth();
        label.setVisible(false);
        if (editor == null) {
            editor = new TextBox();
            editor.setStyleName("k-eil-edit");
            hpanel.add(editor);
            editor.addBlurHandler(new BlurHandler() {
                @Override
                public void onBlur(final BlurEvent event) {
                    afterEdit();
                }
            });
            editor.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(final ChangeEvent event) {
                    editor.setFocus(false);
                }
            });
        }
        editor.setReadOnly(false);
        editor.setPixelSize(hpWidth < 100 ? 100 : hpWidth - 3, 21);
        editor.setText(currentText);
        editor.setVisible(true);
        editor.setFocus(true);
    }

}
