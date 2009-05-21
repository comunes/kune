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

import com.calclab.suco.client.events.Event2;
import com.calclab.suco.client.events.Listener2;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author vjrj
 * 
 */
public class IconLabelEditable extends Composite {

    private transient boolean useDoubleClick;
    private transient ClickListener listener;
    private transient String currentText;
    private transient MouseListenerAdapter mouseOverListener;
    private transient final AbstractLabel label;
    private transient String dblClickLabel;
    private transient String clickLabel;
    private transient Event2<String, String> onEditEvent;
    private transient TextBox editor;
    private transient HorizontalPanel hpanel;

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

    public void edit() {
        showEditor();
        editor.setFocus(true);
    }

    public String getText() {
        return label.getText();
    }

    public void onEdit(final Listener2<String, String> slot) {
        onEditEvent.add(slot);
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

    public void setText(final String text) {
        setTextImpl(text);
    }

    private void afterEdit() {
        final String text = editor.getText();
        onEditEvent.fire(currentText, text);
        setTextImpl(text);
        editor.setVisible(false);
        editor.setReadOnly(true);
        label.setVisible(true);
        label.removeStyleDependentName("high");
    }

    private void init(final String text, final boolean useDoubleClick) {
        this.onEditEvent = new Event2<String, String>("onLabelEdit");
        dblClickLabel = "Double click to rename";
        clickLabel = "Click to rename";
        hpanel = new HorizontalPanel();
        hpanel.add((Widget) label);
        initWidget(hpanel);
        this.currentText = text;
        this.useDoubleClick = useDoubleClick;
        this.listener = new ClickListener() {
            public void onClick(final Widget sender) {
                showEditor();
            }
        };

        mouseOverListener = new MouseListenerAdapter() {
            @Override
            public void onMouseEnter(final Widget sender) {
                label.addStyleDependentName("high");
            }

            @Override
            public void onMouseLeave(final Widget sender) {
                label.removeStyleDependentName("high");
            }
        };
        label.setStylePrimaryName("kune-EditableLabel");
        label.addMouseListener(mouseOverListener);
        setEditableImpl(false);
    }

    private void reset() {
        label.removeStyleDependentName("noneditable");
        label.removeStyleDependentName("editable");
        label.removeClickListener(listener);
        label.removeDoubleClickListener(listener);
        label.removeMouseListener(mouseOverListener);
    }

    private void setEditableImpl(final boolean editable) {
        reset();
        if (editable) {
            if (useDoubleClick) {
                label.setTitle(dblClickLabel);
                label.addDoubleClickListener(listener);
            } else {
                label.setTitle(clickLabel);
                label.addClickListener(listener);
            }
            label.addStyleDependentName("editable");
            // label.addDoubleClickListener(listener);
            label.addMouseListener(mouseOverListener);
        } else {
            label.setTitle(null);
            label.addStyleDependentName("noneditable");
        }
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
            editor.addFocusListener(new FocusListener() {
                public void onFocus(final Widget sender) {
                    // doNothing
                }

                public void onLostFocus(final Widget sender) {
                    afterEdit();
                }
            });
            editor.addChangeListener(new ChangeListener() {
                public void onChange(final Widget sender) {
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
