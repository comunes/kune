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
    private final TextField linkField;
    private final Panel previewPanel;

    public InsertImageExtPanel(final InsertImageExtPresenter presenter, I18nTranslationService i18n) {
        super(i18n.t("External image"), presenter);

        linkField = new TextField();
        linkField.setTabIndex(1);
        linkField.setFieldLabel(i18n.t("External image link (URL)"));
        linkField.setRegex(TextUtils.URL_REGEXP);
        linkField.setRegexText(i18n.t("The link should be a URL in the format 'http://www.domain.com'"));
        linkField.setName(LINK_FIELD);
        linkField.setWidth(DEF_FIELD_WIDTH);
        linkField.setAllowBlank(false);
        linkField.setMinLength(3);
        linkField.setMaxLength(250);
        linkField.setValidationEvent(false);
        linkField.setId(LINK_FIELD);
        add(linkField);
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
}
