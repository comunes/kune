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

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;

public class EditableIconLabel extends Composite {

    private boolean useDoubleClick;
    private ClickListener listener;
    private String currentText;
    private String oldText;
    private EditableClickListener editableListener;
    private MouseListenerAdapter mouseOverListener;
    private final AbstractLabel label;
    private String renameDialogTitle;
    private String renameDialogLabel;
    private String doubleClickToRenameLabel;
    private String clickToRenameLabel;

    public EditableIconLabel(final AbstractImagePrototype icon, final String text, final boolean useDoubleClick,
	    final EditableClickListener editableListenerOrig) {
	label = new IconLabel(icon, text);
	init(text, useDoubleClick, editableListenerOrig);
    }

    public EditableIconLabel(final AbstractImagePrototype icon, final String text, final String targetHistoryToken,
	    final boolean useDoubleClick, final EditableClickListener editableListenerOrig) {
	label = new IconHyperlink(icon, text, targetHistoryToken);
	init(text, useDoubleClick, editableListenerOrig);
    }

    public EditableIconLabel(final EditableClickListener editableListener) {
	this("", editableListener);
    }

    public EditableIconLabel(final String text, final boolean wordWrap, final boolean useDoubleClick,
	    final EditableClickListener editableListenerOrig) {
	label = new LabelWrapper(text, wordWrap);
	init(text, useDoubleClick, editableListenerOrig);
    }

    public EditableIconLabel(final String text, final boolean useDoubleClick,
	    final EditableClickListener editableListener) {
	this(text, false, false, editableListener);
    }

    public EditableIconLabel(final String text, final EditableClickListener editableListener) {
	this(text, false, editableListener);
    }

    public EditableClickListener getEditableListener() {
	return editableListener;
    }

    public String getText() {
	return label.getText();
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
	    label.setTitle("");
	    label.addStyleDependentName("noneditable");
	}
    }

    public void setEditableListener(final EditableClickListener editableListener) {
	this.editableListener = editableListener;
    }

    public void setRenameDialogLabel(final String renameDialogLabel) {
	this.renameDialogLabel = renameDialogLabel;
    }

    public void setRenameDialogTitle(final String renameDialogTitle) {
	this.renameDialogTitle = renameDialogTitle;
    }

    public void setText(final String text) {
	this.oldText = this.currentText;
	this.currentText = text;
	label.setText(text);
    }

    private void init(final String text, final boolean useDoubleClick, final EditableClickListener editableListenerOrig) {
	doubleClickToRenameLabel = "Double click to rename";
	clickToRenameLabel = "Click to rename";
	renameDialogLabel = "Write a new name:";
	renameDialogTitle = "Rename";
	initWidget((Widget) label);
	this.currentText = text;
	this.oldText = text;
	this.useDoubleClick = useDoubleClick;
	this.editableListener = editableListenerOrig;
	this.listener = new ClickListener() {
	    public void onClick(final Widget sender) {
		showEditableDialog();
	    }
	};
	mouseOverListener = new MouseListenerAdapter() {
	    public void onMouseEnter(final Widget sender) {
		label.addStyleDependentName("high");
	    }

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

    private void showEditableDialog() {
	MessageBox.show(new MessageBoxConfig() {
	    {
		// i18n
		setClosable(true);
		setModal(true);
		setWidth(300);
		setDefaultTextHeight(2);
		setButtons(MessageBox.OKCANCEL);
		setTitle(renameDialogTitle);
		setMsg(renameDialogLabel);
		setCallback(new MessageBox.PromptCallback() {
		    public void execute(final String btnID, final String text) {
			if (btnID.equals("ok") && text != null) {
			    editableListener.onEdited(text);
			} else {
			    // Do nothing
			}
		    }
		});
		setMultiline(true);
		setValue(currentText);
	    }
	});
    }

}
