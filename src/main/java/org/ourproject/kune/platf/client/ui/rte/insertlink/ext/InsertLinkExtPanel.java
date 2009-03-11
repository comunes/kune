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
import org.ourproject.kune.platf.client.ui.TextUtils;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialogView;

import com.google.gwt.user.client.ui.Frame;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.FitLayout;

public class InsertLinkExtPanel extends DefaultForm implements InsertLinkExtView {

    public static final String LINK_FIELD = "k-teilep-link-f";
    final TextField linkField;
    final Panel previewPanel;

    public InsertLinkExtPanel(final InsertLinkExtPresenter presenter,
            final I18nTranslationService i18n) {
        super(i18n.t("External link"));
        super.setAutoWidth(true);
        super.setHeight(InsertLinkDialogView.HEIGHT);
        linkField = new TextField();
        linkField.setTabIndex(1);
        linkField.setFieldLabel(i18n.t("External link (URL)"));
        linkField.setRegex(TextUtils.URL_REGEXP);
        linkField.setRegexText(i18n.t("The link should be a URL in the format 'http://www.domain.com'"));
        linkField.setName(LINK_FIELD);
        linkField.setWidth(DEF_FIELD_WIDTH);
        linkField.setAllowBlank(false);
        linkField.setMinLength(3);
        linkField.setMaxLength(250);
        linkField.setValidationEvent(false);
        linkField.setId(LINK_FIELD);
        add(linkField);
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

        Button insert = new Button(i18n.t("Insert"));
        insert.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                presenter.onInsert();
            }
        });
        addButton(insert);
    }

    public String getUrl() {
        return linkField.getRawValue();
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
        linkField.setValue(url);
    }
}
