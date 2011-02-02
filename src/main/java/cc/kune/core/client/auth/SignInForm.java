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
 \*/
package cc.kune.core.client.auth;

import cc.kune.core.client.ui.DefaultForm;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;

public class SignInForm extends DefaultForm {
    public static final String NICKOREMAIL_FIELD = "kune-sif-nkf";
    public static final String PASSWORD_FIELD = "kune-sif-psf";

    private final TextField<String> loginNickOrEmailField;
    private final TextField<String> loginPassField;

    public SignInForm(final I18nTranslationService i18n) {
        super.addStyleName("kune-Margin-Large-trbl");

        loginNickOrEmailField = new TextField<String>();
        loginNickOrEmailField.setFieldLabel(i18n.t("Nickname or email"));
        loginNickOrEmailField.setName(NICKOREMAIL_FIELD);
        loginNickOrEmailField.setWidth(DEF_SMALL_FIELD_WIDTH);
        loginNickOrEmailField.setAllowBlank(false);
        loginNickOrEmailField.setValidationDelay(1000);
        loginNickOrEmailField.setId(NICKOREMAIL_FIELD);
        loginNickOrEmailField.setTabIndex(100);
        super.add(loginNickOrEmailField);

        loginPassField = new TextField<String>();
        loginPassField.setFieldLabel(i18n.t("Password"));
        loginPassField.setName(PASSWORD_FIELD);
        loginPassField.setWidth(DEF_MEDIUM_FIELD_WIDTH);
        loginPassField.setPassword(true);
        loginPassField.setAllowBlank(false);
        loginPassField.setValidationDelay(1000);
        loginPassField.setId(PASSWORD_FIELD);
        loginPassField.setTabIndex(101);
        loginPassField.addListener(Events.OnKeyPress, new Listener<FieldEvent>() {
            @Override
            public void handleEvent(final FieldEvent fe) {
                if (fe.getEvent().getKeyCode() == 13) {
                    // Window.alert("SignInForm");
                    // presenter.onFormSignIn();
                }
            }
        });
        super.add(loginPassField);
    }

    public void focusLogin() {
        loginNickOrEmailField.focus();
    }

    public void focusOnPassword() {
        loginPassField.focus();
    }

    public String getLoginPassword() {
        return loginPassField.getValue();
    }

    public String getNickOrEmail() {
        return loginNickOrEmailField.getValue();
    }

    public Field<String> getNickOrEmailField() {
        return loginNickOrEmailField;
    }

    public void setLoginPassword(final String password) {
        loginPassField.setValue(password);
    }

    public void setNickOrEmail(final String nickOrEmail) {
        loginNickOrEmailField.setValue(nickOrEmail);
    }
}
