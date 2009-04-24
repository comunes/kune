package org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia;

import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialogView;

import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FormPanelListenerAdapter;

public class InsertMediaAbstractPanel extends DefaultForm implements InsertMediaAbstractView {

    protected TextField hrefField;
    private final Label intro;

    public InsertMediaAbstractPanel(final String title, final InsertMediaAbstractPresenter presenter) {
        super(title);
        super.setAutoWidth(true);
        super.setHeight(InsertMediaDialogView.HEIGHT);

        intro = new Label();

        hrefField = new TextField();
        hrefField.setTabIndex(1);
        hrefField.setLabel(Resources.i18n.t("Link"));
        hrefField.setWidth(DEF_FIELD_WIDTH);
        hrefField.setAllowBlank(false);
        hrefField.setMinLength(3);
        hrefField.setMaxLength(250);
        hrefField.setValidationEvent(false);

        super.addListener(new FormPanelListenerAdapter() {
            @Override
            public void onActivate(final Panel panel) {
                presenter.onActivate();
            }
        });

        add(intro);
        add(hrefField);
    }

    public String getSrc() {
        return hrefField.getRawValue();
    }

    @Override
    public void insert(final int index, final Component component) {
        super.insert(index, component);
    }

    public void setIntro(final String text) {
        intro.setHtml(text + "<br/>");
    }
}
