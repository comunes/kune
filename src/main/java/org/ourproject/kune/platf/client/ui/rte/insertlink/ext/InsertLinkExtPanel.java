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
package org.ourproject.kune.platf.client.ui.rte.insertlink.ext;

import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;
import org.ourproject.kune.platf.client.ui.rte.insertlink.abstractlink.InsertLinkAbstractPanel;

import com.google.gwt.user.client.ui.Frame;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

public class InsertLinkExtPanel extends InsertLinkAbstractPanel implements InsertLinkExtView {

    final Panel previewPanel;

    public InsertLinkExtPanel(final InsertLinkExtPresenter presenter, final I18nTranslationService i18n) {
        super(i18n.t("External link"), presenter);

        hrefField.setFieldLabel(Resources.i18n.t("External link (URL)"));
        hrefField.setRegex(TextUtils.URL_REGEXP);
        hrefField.setRegexText(Resources.i18n.t("The link should be a URL in the format 'http://www.domain.com'"));

        previewPanel = new Panel();
        previewPanel.setLayout(new FitLayout());
        previewPanel.setHeight(125);

        add(new PaddedPanel(previewPanel, 0));

        Button preview = new Button(i18n.t("Preview"));
        preview.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                presenter.onPreview();
            }
        });
        addButton(preview);
    }

    public String getUrl() {
        return hrefField.getRawValue();
    }

    @Override
    public void reset() {
        super.reset();
        previewPanel.clear();
    }

    public void setPreviewUrl(String url) {
        Frame previewFrame = new Frame(url);
        previewPanel.clear();
        previewPanel.add(previewFrame);
        previewPanel.doLayout();
    }

    public void setUrl(String url) {
        hrefField.setValue(url);
    }

    @Override
    protected void updateValues(LinkInfo linkInfo) {
        super.updateValues(linkInfo);
        String href = linkInfo.getHref();
        if (href != null && !href.equals("")) {
            String hrefValue = hrefField.getRawValue();
            if (hrefValue != null && hrefValue.length() == 0) {
                hrefField.setValue(href);
            }
        }
    }
}
