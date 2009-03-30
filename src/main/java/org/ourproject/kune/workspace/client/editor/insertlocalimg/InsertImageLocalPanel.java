package org.ourproject.kune.workspace.client.editor.insertlocalimg;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertImageAbstractPanel;

import com.gwtext.client.widgets.form.Label;

public class InsertImageLocalPanel extends InsertImageAbstractPanel implements InsertImageLocalView {

    public InsertImageLocalPanel(final InsertImageLocalPresenter presenter, final I18nTranslationService i18n) {
        super(i18n.t("Local"), presenter);
        Label label = new Label();
        label.setHtml(TextUtils.IN_DEVELOPMENT + "<br/><br/>");
        insert(0, label);
    }
}
