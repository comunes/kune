package org.ourproject.kune.workspace.client.sitebar.login;

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;

import com.gwtext.client.widgets.form.TextField;

public class LoginForm extends DefaultForm {
    private static final String NICKOREMAIL_FIELD = "nickOrEmail";
    private static final String PASSWORD_FIELD = "password";

    private final TextField loginNickOrEmailField;
    private final TextField loginPassField;

    public LoginForm() {
	loginNickOrEmailField = new TextField();
	loginNickOrEmailField.setFieldLabel(Kune.I18N.t("Nickname or email"));
	loginNickOrEmailField.setName(NICKOREMAIL_FIELD);
	loginNickOrEmailField.setWidth(DEF_FIELD_WIDTH);
	loginNickOrEmailField.setAllowBlank(false);
	loginNickOrEmailField.setValidationEvent(false);
	super.add(loginNickOrEmailField);

	loginPassField = new TextField();
	loginPassField.setFieldLabel(Kune.I18N.t("Password"));
	loginPassField.setName(PASSWORD_FIELD);
	loginPassField.setWidth(DEF_FIELD_WIDTH);
	loginPassField.setPassword(true);
	loginPassField.setAllowBlank(false);
	loginPassField.setValidationEvent(false);
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
