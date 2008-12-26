/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

import com.calclab.suco.client.listener.Event2;
import com.calclab.suco.client.listener.Listener2;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class IconLabelEditable extends Composite {

    private boolean useDoubleClick;
    private ClickListener listener;
    private String currentText;
    private String oldText;
    private MouseListenerAdapter mouseOverListener;
    private final AbstractLabel label;
    private String doubleClickToRenameLabel;
    private String clickToRenameLabel;
    private Event2<String, String> onEdit;
    private TextBox editor;
    private HorizontalPanel hp;

    public IconLabelEditable() {
        this("");
    }

    public IconLabelEditable(final AbstractImagePrototype icon, final String text, final boolean useDoubleClick) {
        label = new IconLabel(icon, text);
        init(text, useDoubleClick);
    }

    public IconLabelEditable(final AbstractImagePrototype icon, final String text, final String targetHistoryToken,
            final boolean useDoubleClick) {
        label = new IconHyperlink(icon, text, targetHistoryToken);
        init(text, useDoubleClick);
    }

    public IconLabelEditable(final String text) {
        this(text, false);
    }

    public IconLabelEditable(final String text, final boolean useDoubleClick) {
        this(text, false, false);
    }

    public IconLabelEditable(final String text, final boolean wordWrap, final boolean useDoubleClick) {
        label = new LabelWrapper(text, wordWrap);
        init(text, useDoubleClick);
    }

    public String getText() {
        return label.getText();
    }

    public void onEdit(final Listener2<String, String> slot) {
        onEdit.add(slot);
    }

    public void restoreOldText() {
        label.setText(oldText);
        this.currentText = this.oldText;
    }

    public void setClickToRenameLabel(final String clickToRenameLabel) {
        this.clickToRenameLabel = clickToRenameLabel;
    }

    public void setDoubleClickToRenameLabel(final String doubleClickToRenameLabel) {
        this.doubleClickToRenameLabel = doubleClickToRenameLabel;
    }

    public void setEditable(final boolean editable) {
        reset();
        if (editable) {
            if (useDoubleClick) {
                label.setTitle(doubleClickToRenameLabel);
                label.addDoubleClickListener(listener);
            } else {
                label.setTitle(clickToRenameLabel);
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

    public void setText(final String text) {
        this.oldText = this.currentText;
        this.currentText = text;
        label.setText(text);
    }

    private void afterEdit() {
        String text = editor.getText();
        onEdit.fire(currentText, text);
        editor.setVisible(false);
        editor.setReadOnly(true);
        label.setVisible(true);
        label.removeStyleDependentName("high");
    }

    private void init(final String text, final boolean useDoubleClick) {
        this.onEdit = new Event2<String, String>("onEdit");
        doubleClickToRenameLabel = "Double click to rename";
        clickToRenameLabel = "Click to rename";
        hp = new HorizontalPanel();
        hp.add((Widget) label);
        initWidget(hp);
        this.currentText = text;
        this.oldText = text;
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
        setEditable(false);
    }

    private void reset() {
        label.removeStyleDependentName("noneditable");
        label.removeStyleDependentName("editable");
        label.removeClickListener(listener);
        label.removeDoubleClickListener(listener);
        label.removeMouseListener(mouseOverListener);
    }

    private void showEditor() {
        int hpWidth = hp.getParent().getOffsetWidth();
        label.setVisible(false);
        if (editor == null) {
            editor = new TextBox();
            editor.setStyleName("k-eil-edit");
            hp.add(editor);
            editor.addFocusListener(new FocusListener() {
                public void onFocus(Widget sender) {
                }

                public void onLostFocus(Widget sender) {
                    afterEdit();
                }
            });
            editor.addChangeListener(new ChangeListener() {
                public void onChange(Widget sender) {
                    editor.setFocus(false);
                }
            });
        }
        editor.setReadOnly(false);
        editor.setPixelSize(hpWidth < 100 ? 100 : hpWidth, 21);
        editor.setText(currentText);
        editor.setVisible(true);
        editor.setFocus(true);
    }

}
