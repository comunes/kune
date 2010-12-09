package org.ourproject.kune.platf.client.ui.rte.insertimg.ext;

import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertImageAbstractPanel;

import cc.kune.core.client.utils.TextUtils;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.core.EventCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.FitLayout;

public class InsertImageExtPanel extends InsertImageAbstractPanel implements InsertImageExtView {

    private static final String LINK_FIELD = "iiep-img-field";
    private final TextField srcField;
    private final Panel previewPanel;
    private final InsertImageExtPresenter presenter;
    private final Label previewLabel;

    public InsertImageExtPanel(final InsertImageExtPresenter presenter, final I18nTranslationService i18n) {
        super(i18n.t("External"), presenter);
        this.presenter = presenter;

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
        previewPanel.setHeight(75);

        add(new PaddedPanel(previewPanel, 0));

        srcField.addKeyPressListener(new EventCallback() {
            public void execute(final EventObject e) {
                refreshPreview();
            }
        });

        previewLabel = new Label(i18n.t("Image preview will be displayed here."));
        // previewLabel.setStyleName("kune-Margin-20-trbl");
        previewLabel.addStyleName("k-preview-panel");
        previewReset();
    }

    @Override
    public String getSrc() {
        return srcField.getValueAsString();
    }

    @Override
    public void reset() {
        super.reset();
        previewReset();
    }

    public void setPreviewUrl(final String url) {
        final Frame previewFrame = new Frame(url);
        previewPanel.clear();
        previewPanel.add(previewFrame);
        previewPanel.doLayout();
    }

    private void previewReset() {
        previewPanel.clear();
        previewPanel.add(previewLabel);
    }

    private void refreshPreview() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            public void execute() {
                presenter.onPreview();
            }
        });
    }

}
