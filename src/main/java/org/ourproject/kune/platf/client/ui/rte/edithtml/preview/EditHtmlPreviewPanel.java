package org.ourproject.kune.platf.client.ui.rte.edithtml.preview;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlView;

import com.google.gwt.user.client.ui.HTML;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.PanelListenerAdapter;

public class EditHtmlPreviewPanel extends Panel implements EditHtmlPreviewView {

    public EditHtmlPreviewPanel(I18nTranslationService i18n, final EditHtmlPreviewPresenter presenter) {
        setTitle(i18n.t("Preview"));
        super.setCls("kune-Content-Main");
        setHeight(EditHtmlView.HEIGHT - 40);
        setAutoScroll(true);
        setAutoWidth(true);
        super.addListener(new PanelListenerAdapter() {
            @Override
            public void onActivate(Panel panel) {
                clear();
                add(new HTML(presenter.getHtml()));
                if (isRendered()) {
                    doLayout();
                }
            }
        });
    }
}
