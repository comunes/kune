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
package org.ourproject.kune.platf.client.ui.rte.insertlink.email;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.rte.insertlink.TextEditorInsertElementView;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.VType;

public class TextEditorInsertLinkEmailPanel extends DefaultForm implements TextEditorInsertLinkEmailView {

    public static final String EMAIL_FIELD = "k-teilep-email-field";
    private final TextField emailField;

    public TextEditorInsertLinkEmailPanel(final TextEditorInsertLinkEmailPresenter presenter,
            I18nTranslationService i18n) {
        super(i18n.t("Email link"));
        super.setAutoWidth(true);
        super.setHeight(TextEditorInsertElementView.HEIGHT);
        emailField = new TextField();
        emailField.setTabIndex(4);
        emailField.setFieldLabel(i18n.t("Email"));
        emailField.setName(EMAIL_FIELD);
        emailField.setVtype(VType.EMAIL);
        emailField.setWidth(DEF_MEDIUM_FIELD_WIDTH);
        emailField.setAllowBlank(false);
        emailField.setValidationEvent(false);
        emailField.setId(EMAIL_FIELD);
        add(emailField);
        Button insert = new Button(i18n.t("Insert"));
        insert.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                if (getFormPanel().getForm().isValid()) {
                    presenter.onInsert("", "mailto://" + emailField.getRawValue());
                }
            }
        });
        addButton(insert);
    }

    public void clear() {
        super.reset();
    }
}
