package org.ourproject.kune.platf.client.ui.rte.insertspecialchar.utf8;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialogView;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.core.EventCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.TextField;

public class InsertSpecialUTF8CharPanel extends Panel implements InsertSpecialUTF8CharView {
    private static final int DEF_CHAR = 215;
    private final DefaultForm form;
    private final TextField inputUnicodeField;
    private final TextField previewField;

    public InsertSpecialUTF8CharPanel(final I18nTranslationService i18n, final InsertSpecialCharDialog charDialog) {
        super(i18n.t("Advanced"));
        setAutoWidth(true);
        setHeight(InsertSpecialCharDialogView.HEIGHT - 10);
        form = new DefaultForm(Position.LEFT);
        form.setAutoWidth(true);
        form.setHeight(InsertSpecialCharDialogView.HEIGHT - 10);

        Label label = new Label();
        label.setHtml(i18n.t("If you know a special character's [%s] value, enter it below.",
                TextUtils.generateHtmlLink("http://unicode.org/charts/", "Unicode"))
                + "<br/><br/>");

        inputUnicodeField = new TextField();
        inputUnicodeField.setTabIndex(4);
        inputUnicodeField.setFieldLabel(i18n.t("Unicode"));
        inputUnicodeField.setWidth(DefaultForm.DEF_XSMALL_FIELD_WIDTH);
        inputUnicodeField.setRegex(TextUtils.NUM_REGEXP);
        inputUnicodeField.setRegexText(i18n.t("This must be a number"));
        inputUnicodeField.setAllowBlank(false);
        inputUnicodeField.setValidationEvent(true);
        inputUnicodeField.setValue("" + DEF_CHAR);

        previewField = new TextField();
        previewField.setTabIndex(5);
        previewField.setFieldLabel(i18n.t("Preview"));
        previewField.setWidth(DefaultForm.DEF_XSMALL_FIELD_WIDTH);
        previewField.setValue("" + ((char) DEF_CHAR));
        previewField.setDisabled(true);
        previewField.setStyle("background-color:#FFF;background-image:none;color:#000;");

        inputUnicodeField.addKeyPressListener(new EventCallback() {
            public void execute(final EventObject e) {
                refreshPreview();
            }
        });

        form.add(label);
        form.add(inputUnicodeField);
        form.add(previewField);

        Button insert = new Button(i18n.t("Insert character"));

        insert.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                if (form.getFormPanel().getForm().isValid()) {
                    charDialog.onInsert(getCharEntered());
                }
            }
        });
        form.addButton(insert);

        super.add(form.getFormPanel());
        charDialog.addTab(this);
    }

    private char getCharEntered() {
        try {
            return (char) (new Integer(inputUnicodeField.getRawValue())).intValue();
        } catch (Exception except) {
            Log.debug("Not possible to cast utf8 int to char");
        }
        return '?';
    }

    private void refreshPreview() {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                previewField.setValue("" + getCharEntered());
            }
        });
    }

}
