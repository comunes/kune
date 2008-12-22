package org.ourproject.kune.workspace.client.editor.insert.linkext;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.workspace.client.editor.insert.TextEditorInsertElementView;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.VType;

public class TextEditorInsertLinkExtPanel extends DefaultForm implements TextEditorInsertLinkExtView {

    public static final String LINK_FIELD = "k-teilep-link-f";
    private final TextField linkField;

    public TextEditorInsertLinkExtPanel(final TextEditorInsertLinkExtPresenter presenter,
            final I18nTranslationService i18n) {
        super(i18n.t("External link"));
        super.setAutoWidth(true);
        super.setHeight(TextEditorInsertElementView.HEIGHT);
        linkField = new TextField();
        linkField.setTabIndex(1);
        linkField.setFieldLabel(i18n.t("External link (URL)"));
        linkField.setVtype(VType.URL);
        linkField.setName(LINK_FIELD);
        linkField.setWidth(DEF_FIELD_WIDTH);
        linkField.setAllowBlank(false);
        linkField.setMinLength(3);
        linkField.setMaxLength(250);
        linkField.setValidationEvent(false);
        linkField.setId(LINK_FIELD);
        add(linkField);
        Button insert = new Button(i18n.t("Insert"));
        insert.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                if (getFormPanel().getForm().isValid()) {
                    presenter.onInsert("", linkField.getRawValue());
                }
            }
        });
        addButton(insert);
    }

    public void clear() {
        super.reset();
    }
}
