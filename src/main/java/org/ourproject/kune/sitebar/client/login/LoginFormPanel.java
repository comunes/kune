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

package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.sitebar.client.bar.SiteBarTrans;
import org.ourproject.kune.sitebar.client.services.Translate;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;

public class LoginFormPanel extends Composite implements LoginFormView, View {
    private static final Translate t = SiteBarTrans.getInstance().t;
    private static final String NICKOREMAIL_FIELD = "nickOrEmail";
    private static final String PASSWORD_FIELD = "password";
    private final Form signInForm;

    public LoginFormPanel(final LoginForm initialPresenter) {
	final VerticalPanel generalVP = new VerticalPanel();
	initWidget(generalVP);

	signInForm = createSignInForm();
	generalVP.add(signInForm);
	generalVP.addStyleName("kune-Default-Form");
	generalVP.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
    }

    public void clearData() {
	signInForm.findField(NICKOREMAIL_FIELD).clearInvalid();
	signInForm.findField(PASSWORD_FIELD).clearInvalid();
    }

    public String getNickOrEmail() {
	return signInForm.findField(NICKOREMAIL_FIELD).getRawValue();
    }

    public String getPassword() {
	return signInForm.findField(PASSWORD_FIELD).getRawValue();
    }

    private Form createSignInForm() {
	Form form = new Form(new FormConfig() {
	    {
		setWidth(300);
		setLabelWidth(75);
	    }
	});
	form.fieldset(t.SignIn());
	form.add(new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.UserNameOrEmail());
		setName(NICKOREMAIL_FIELD);
		setWidth(175);
		setAllowBlank(false);
		setMsgTarget("side");
		// setRegex("\\d");
		// setRegexText("Numbers only");
	    }
	}));

	form.add(new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.Password());
		setName(PASSWORD_FIELD);
		setWidth(175);
		setPassword(true);
		setAllowBlank(false);
		setMsgTarget("side");
	    }
	}));
	form.end();
	form.render();
	return form;
    }
}
