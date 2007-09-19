/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.editor;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TextEditorPanel extends Composite implements TextEditorView {
    private static final String BACKCOLOR_ENABLED = "#FFF";
    private static final String BACKCOLOR_DISABLED = "#CCC";
    private final RichTextArea gwtRTarea;
    private final TextEditorToolbar textEditorToolbar;

    public TextEditorPanel(final TextEditorPresenter panelListener) {

	gwtRTarea = new RichTextArea();
	textEditorToolbar = new TextEditorToolbar(gwtRTarea, panelListener);
	final VerticalPanel areaVP = new VerticalPanel();
	areaVP.add(textEditorToolbar);
	areaVP.add(gwtRTarea);
	initWidget(areaVP);

	gwtRTarea.setWidth("97%");
	gwtRTarea.addStyleName("kune-TexEditorPanel-TextArea");
	areaVP.setWidth("100%");
	adjustSize("" + (Window.getClientHeight() - 212));
	Window.addWindowResizeListener(new WindowResizeListener() {
	    public void onWindowResized(final int arg0, final int arg1) {
		adjustSize("" + (Window.getClientHeight() - 212));
	    }
	});
    }

    public void setEnabled(final boolean enabled) {
	final String bgColor = enabled ? BACKCOLOR_ENABLED : BACKCOLOR_DISABLED;
	DOM.setStyleAttribute(gwtRTarea.getElement(), "backgroundColor", bgColor);
	gwtRTarea.setEnabled(enabled);
    }

    public String getHTML() {
	return gwtRTarea.getHTML();
    }

    public String getText() {
	return gwtRTarea.getText();
    }

    public void setHTML(final String html) {
	gwtRTarea.setHTML(html);
    }

    public void setText(final String text) {
	gwtRTarea.setText(text);
    }

    public void setHeight(final String height) {
	gwtRTarea.setHeight(height);
    }

    public void setEnabledSaveButton(final boolean enabled) {
	textEditorToolbar.setEnabledSaveButton(enabled);
    }

    public void setEnabledCancelButton(final boolean enabled) {
	textEditorToolbar.setEnabledCloseButton(enabled);
    }

    public void setTextSaveButton(final String text) {
	textEditorToolbar.setTextSaveButton(text);
    }

    public void editHTML(final boolean edit) {
	textEditorToolbar.editHTML(edit);
    }

    private void adjustSize(final String height) {
	gwtRTarea.setHeight(height);
    }

}
