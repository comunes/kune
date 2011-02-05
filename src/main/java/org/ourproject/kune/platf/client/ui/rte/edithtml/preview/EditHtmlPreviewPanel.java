/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
