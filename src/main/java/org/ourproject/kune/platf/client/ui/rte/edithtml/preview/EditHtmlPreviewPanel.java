package org.ourproject.kune.platf.client.ui.rte.edithtml.preview;

import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialogView;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.user.client.ui.HTML;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.PanelListenerAdapter;

public class EditHtmlPreviewPanel extends Panel implements EditHtmlPreviewView {

    public EditHtmlPreviewPanel(I18nTranslationService i18n, final EditHtmlPreviewPresenter presenter) {
        setTitle(i18n.t("Preview"));
        setCls("kune-Content-Main");
        setHeight(EditHtmlDialogView.HEIGHT - 45);
        setPaddings(5);
        setAutoScroll(true);
        setAutoWidth(true);
        addListener(new PanelListenerAdapter() {
            @Override
            public void onActivate(Panel panel) {
                clear();
                setHeight(EditHtmlDialogView.HEIGHT - 45);
                add(new HTML(presenter.getHtml()));
                if (isRendered()) {
                    doLayout();
                }
            }
        });
    }
}
