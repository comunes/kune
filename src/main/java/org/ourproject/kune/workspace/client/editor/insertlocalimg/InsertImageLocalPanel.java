package org.ourproject.kune.workspace.client.editor.insertlocalimg;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageView;

import com.google.gwt.user.client.ui.Label;
import com.gwtext.client.widgets.Panel;

public class InsertImageLocalPanel extends Panel implements InsertImageLocalView {

    public InsertImageLocalPanel(final InsertImageLocalPresenter presenter, I18nTranslationService i18n) {
        super(i18n.t("Local image"));
        setAutoWidth(true);
        setHeight(InsertImageView.HEIGHT);
        setPaddings(20);
        add(new Label(TextUtils.IN_DEVELOPMENT));
    }
}
