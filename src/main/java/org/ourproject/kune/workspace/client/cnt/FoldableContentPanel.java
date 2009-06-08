/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.workspace.client.cnt;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.platf.client.ui.RoundedPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class FoldableContentPanel extends AbstractContentPanel implements AbstractContentView {

    private static final String DEF_CONTENT_MARGINS_STYLE = "kune-Margin-7-trbl";
    private final I18nTranslationService i18n;
    private final RoundedPanel messagePanel;
    private final IconLabel messageLabel;

    public FoldableContentPanel(final WorkspaceSkeleton ws, final I18nTranslationService i18n) {
        super(ws);
        this.i18n = i18n;
        messageLabel = new IconLabel(Images.App.getInstance().info(), "");
        messageLabel.addStyleName("k-preview-msg-lab");
        messagePanel = new RoundedPanel(messageLabel, RoundedPanel.ALL, 2);
        messagePanel.setCornerStyleName("k-preview-msg");
        messagePanel.addStyleName("kune-Margin-7-b");
    }

    public void setContent(final String content, final boolean showPreviewMsg) {
        final VerticalPanel vp = createMessageVp(showPreviewMsg);
        final HTML html = new HTML(content);
        vp.add(html);
        setWidgetAsContent(vp, true);
    }

    public void setInfo(final String info) {
        setLabel(info);
    }

    public void setInfoMessage(final String text) {
        final VerticalPanel vp = createMessageVp(true);
        messageLabel.setText(text);
        setWidgetAsContent(vp, true);
    }

    public void setLabel(final String text) {
        final Label label = new Label(text);
        setDefStyle(label);
        setWidget(label);
    }

    public void setNoPreview() {
        final VerticalPanel vp = createMessageVp(true);
        setNoPreviewLabelMsg();
        setWidgetAsContent(vp, true);
    }

    public void setRawContent(final String content) {
        final HTML html = new HTML(content);
        setDefStyle(html);
        setContent(html);
    }

    public void setWidgetAsContent(final Widget widget, final boolean setDefMargins) {
        if (setDefMargins) {
            widget.addStyleName(DEF_CONTENT_MARGINS_STYLE);
        }
        setContent(widget);
    }

    public void showImage(final String imageUrl, final String imageResizedUrl, final boolean showPreviewMsg) {
        final VerticalPanel vp = createMessageVp(showPreviewMsg);
        final Image imgOrig = new Image(imageUrl);
        final Image imgResized = new Image(imageResizedUrl);
        KuneUiUtils.setQuickTip(imgOrig, i18n.t("Click to zoom out"));
        KuneUiUtils.setQuickTip(imgResized, i18n.t("Click to zoom in"));
        setDefStyle(imgOrig);
        setDefStyle(imgResized);
        imgOrig.addStyleName("kune-pointer");
        imgResized.addStyleName("kune-pointer");
        imgResized.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                imgResized.removeFromParent();
                vp.add(imgOrig);
            }
        });
        imgOrig.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                imgOrig.removeFromParent();
                vp.add(imgResized);
            }
        });
        vp.add(imgResized);
        setWidgetAsContent(vp, true);
        Image.prefetch(imageUrl);
    }

    private VerticalPanel createMessageVp(final boolean showMsg) {
        final VerticalPanel vp = new VerticalPanel();
        if (showMsg) {
            setDefPreviewMsg();
            vp.add(messagePanel);
        }
        return vp;
    }

    private void setContent(final Widget widget) {
        setWidget(widget);
        attach();
    }

    private void setDefPreviewMsg() {
        messageLabel.setText(i18n.t("This is only a preview, download it to get the complete file"));
    }

    private void setDefStyle(final Widget widget) {
        widget.setStyleName("kune-Content-Main");
        widget.addStyleName(DEF_CONTENT_MARGINS_STYLE);
    }

    private void setNoPreviewLabelMsg() {
        messageLabel.setText(i18n.t("Preview not available"));
    }
}
