package org.ourproject.kune.sitebar.client.login;

import org.ourproject.kune.sitebar.client.bar.SiteBarTrans;
import org.ourproject.kune.sitebar.client.services.Translate;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.TextFieldConfig;

public class LoginFormPanel extends Composite implements LoginView {
    private static final Translate t = SiteBarTrans.getInstance().t;
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private final Form signInForm;

    public LoginFormPanel(final Login initialPresenter) {
	final VerticalPanel generalVP = new VerticalPanel();

	initWidget(generalVP);

	signInForm = createSignInForm();
	generalVP.add(signInForm);

    }

    public void clearData() {
	signInForm.findField(USERNAME_FIELD).clearInvalid();
	signInForm.findField(PASSWORD_FIELD).clearInvalid();
    }

    public String getUsername() {
	return signInForm.findField(USERNAME_FIELD).getRawValue();
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
		setName(USERNAME_FIELD);
		setWidth(175);
		setAllowBlank(false);
	    }
	}));

	form.add(new TextField(new TextFieldConfig() {
	    {
		setFieldLabel(t.Password());
		setName(PASSWORD_FIELD);
		setWidth(175);
		setPassword(true);
		setAllowBlank(false);
	    }
	}));
	form.end();
	form.render();
	return form;
    }
}
