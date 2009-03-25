package org.ourproject.kune.platf.client.ui.rte.insertimg.ext;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertImageAbstractPanel;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.FitLayout;

public class InsertImageExtPanel extends InsertImageAbstractPanel implements InsertImageExtView {

    private static final String LINK_FIELD = "iiep-img-field";
    private final TextField srcField;
    private final Panel previewPanel;

    public InsertImageExtPanel(final InsertImageExtPresenter presenter, I18nTranslationService i18n) {
        super(i18n.t("External image"), presenter);

        srcField = new TextField();
        srcField.setTabIndex(1);
        srcField.setFieldLabel(i18n.t("External image link (URL)"));
        srcField.setRegex(TextUtils.URL_REGEXP);
        srcField.setRegexText(i18n.t("The link should be a URL in the format 'http://www.domain.com'"));
        srcField.setName(LINK_FIELD);
        srcField.setWidth(DEF_FIELD_WIDTH);
        srcField.setAllowBlank(false);
        srcField.setMinLength(3);
        srcField.setMaxLength(250);
        srcField.setValidationEvent(false);
        srcField.setId(LINK_FIELD);
        insert(0, srcField);
        previewPanel = new Panel();
        previewPanel.setLayout(new FitLayout());
        previewPanel.setHeight(125);
        add(new PaddedPanel(previewPanel, 0));
        Button preview = new Button(i18n.t("Preview"));
        preview.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                // presenter.onPreview();
            }
        });
        addButton(preview);
    }

    @Override
    public String getSrc() {
        return srcField.getValueAsString();
    }
}
