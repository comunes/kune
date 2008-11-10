package org.ourproject.kune.workspace.client.cnt;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class FoldableContentPanel extends AbstractContentPanel {

    private final I18nTranslationService i18n;

    public FoldableContentPanel(final WorkspaceSkeleton ws, I18nTranslationService i18n) {
        super(ws);
        this.i18n = i18n;
    }

    public void setContent(final String content) {
        final HTML html = new HTML(content);
        setDefStyle(html);
        setWidget(html);
        attach();
    }

    public void setLabel(String text) {
        Label label = new Label(text);
        setDefStyle(label);
        setWidget(label);
    }

    public void showImage(String imageUrl, String imageResizedUrl) {
        final Image imgOrig = new Image(imageUrl);
        final Image imgResized = new Image(imageResizedUrl);
        KuneUiUtils.setQuickTip(imgOrig, i18n.t("Click to zoom out"));
        KuneUiUtils.setQuickTip(imgResized, i18n.t("Click to zoom in"));
        setDefStyle(imgOrig);
        setDefStyle(imgResized);
        imgOrig.addStyleName("kune-pointer");
        imgResized.addStyleName("kune-pointer");
        imgResized.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                detach();
                setWidget(imgOrig);
                attach();
            }
        });
        imgOrig.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                detach();
                setWidget(imgResized);
                attach();
            }
        });
        setWidget(imgResized);
        attach();
    }

    private void setDefStyle(final Widget widget) {
        widget.setStyleName("kune-Content-Main");
        widget.addStyleName("kune-Margin-7-trbl");
    }
}
