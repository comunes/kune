package org.ourproject.kune.workspace.client.editor.insert.linkemail;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;

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
        super.setAutoWidth(true);
        super.setAutoHeight(true);
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
