package org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia;

import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ContentPosition;
import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertElementAbstractPanel;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialogView;

import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FormPanelListenerAdapter;

public class InsertMediaAbstractPanel extends InsertElementAbstractPanel implements InsertMediaAbstractView {

    protected TextField hrefField;

    public InsertMediaAbstractPanel(final String title, final InsertMediaAbstractPresenter presenter) {
        super(title, InsertMediaDialogView.HEIGHT);

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

        insertImpl(1, hrefField);
        defValues();
    }

    @Override
    public String getSrc() {
        return hrefField.getRawValue();
    }

    @Override
    public void insert(final int index, final Component component) {
        insertImpl(index, component);
    }

    @Override
    public void reset() {
        defValues();
        super.reset();
    }

    @Override
    public void setIntro(final String text) {
        intro.setHtml(text + "<br/>");
    }

    public String setPosition(final String embedElement) {
        return ContentPosition.setPosition(embedElement, getWrapText(), getPosition());
    }

    private void defValues() {
        wrapText.setVisible(true);
        wrapText.setChecked(true);
        positionCombo.setValue(ContentPosition.RIGHT);
    }

    private void insertImpl(final int index, final Component component) {
        super.insert(index, component);
    }
}
