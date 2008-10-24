/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.docs.client.cnt.reader.ui;

import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderView;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class DocumentReaderPanel implements DocumentReaderView {

    private final WorkspaceSkeleton ws;
    private final I18nTranslationService i18n;

    public DocumentReaderPanel(final WorkspaceSkeleton ws, I18nTranslationService i18n) {
        this.ws = ws;
        this.i18n = i18n;
    }

    public void setContent(final String content) {
        final HTML html = new HTML(content);
        setDefStyle(html);
        ws.getEntityWorkspace().setContent(html);
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
                ws.getEntityWorkspace().setContent(imgOrig);
            }
        });
        imgOrig.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                ws.getEntityWorkspace().setContent(imgResized);
            }
        });
        ws.getEntityWorkspace().setContent(imgResized);
    }

    private void setDefStyle(final Widget widget) {
        widget.setStyleName("kune-Content-Main");
        widget.addStyleName("kune-Margin-7-trbl");
    }
}
