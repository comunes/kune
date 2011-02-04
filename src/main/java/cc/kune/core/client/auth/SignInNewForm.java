package cc.kune.core.client.auth;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SignInNewForm extends Composite {

    interface SignInNewFormUiBinder extends UiBinder<Widget, SignInNewForm> {
    }
    private static SignInNewFormUiBinder uiBinder = GWT.create(SignInNewFormUiBinder.class);

    @UiField
    FormPanel form;
    @UiField
    TextField<String> nick;
    @UiField
    TextField<String> password;

    public SignInNewForm(final I18nTranslationService i18n) {
        initWidget(uiBinder.createAndBindUi(this));
        nick.setFieldLabel(i18n.t("Nickname or email"));
        password.setFieldLabel(i18n.t("Password"));
    }

}
