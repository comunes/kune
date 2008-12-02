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
 */package org.ourproject.kune.workspace.client.signin;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class SignInForm extends DefaultForm {
    public static final String NICKOREMAIL_FIELD = "kune-sif-nkf";
    public static final String PASSWORD_FIELD = "kune-sif-psf";

    private final TextField loginNickOrEmailField;
    private final TextField loginPassField;

    public SignInForm(final SignInPresenter presenter, final I18nTranslationService i18n) {
        super.addStyleName("kune-Margin-Large-trbl");

        loginNickOrEmailField = new TextField();
        loginNickOrEmailField.setFieldLabel(i18n.t("Nickname or email"));
        loginNickOrEmailField.setName(NICKOREMAIL_FIELD);
        loginNickOrEmailField.setWidth(DEF_SMALL_FIELD_WIDTH);
        loginNickOrEmailField.setAllowBlank(false);
        loginNickOrEmailField.setValidationEvent(false);
        loginNickOrEmailField.setId(NICKOREMAIL_FIELD);
        loginNickOrEmailField.setTabIndex(100);
        super.add(loginNickOrEmailField);

        loginPassField = new TextField();
        loginPassField.setFieldLabel(i18n.t("Password"));
        loginPassField.setName(PASSWORD_FIELD);
        loginPassField.setWidth(DEF_MEDIUM_FIELD_WIDTH);
        loginPassField.setPassword(true);
        loginPassField.setAllowBlank(false);
        loginPassField.setValidationEvent(false);
        loginPassField.setId(PASSWORD_FIELD);
        loginPassField.setTabIndex(101);
        loginPassField.addListener(new FieldListenerAdapter() {
            @Override
            public void onSpecialKey(final Field field, final EventObject e) {
                if (e.getKey() == 13) {
                    presenter.onFormSignIn();
                }
            }
        });

        super.add(loginPassField);
    }

    public void focusLogin() {
        loginNickOrEmailField.focus();
    }

    public String getLoginPassword() {
        return loginPassField.getValueAsString();
    }

    public String getNickOrEmail() {
        return loginNickOrEmailField.getValueAsString();
    }
}
