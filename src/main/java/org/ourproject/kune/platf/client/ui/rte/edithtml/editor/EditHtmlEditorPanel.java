/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.rte.edithtml.editor;

import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialogView;

import cc.kune.core.client.i18n.I18nUITranslationService;

import com.gwtext.client.widgets.form.TextArea;

public class EditHtmlEditorPanel extends DefaultForm implements EditHtmlEditorView {

    public static final String HTML_FIELD = "ehtp-html-f";
    private final TextArea editorField;

    public EditHtmlEditorPanel(I18nUITranslationService i18n, final EditHtmlEditorPresenter presenter) {
        super("HTML");
        super.setAutoWidth(true);
        super.setHideLabels(true);
        super.setHeight(EditHtmlDialogView.HEIGHT - 20);
        editorField = new TextArea();
        editorField.setHeight(EditHtmlDialogView.HEIGHT - 70);
        editorField.setWidth("98%");
        editorField.setTabIndex(4);
        editorField.setName(HTML_FIELD);
        editorField.setId(HTML_FIELD);
        add(editorField);
    }

    public String getHtml() {
        return editorField.getRawValue();
    }

    public void setHtml(String html) {
        editorField.setValue(html);
    }
}
