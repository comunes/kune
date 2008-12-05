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
package org.ourproject.kune.workspace.client.tags;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.workspace.client.skel.SummaryPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TagsSummaryPanel extends SummaryPanel implements TagsSummaryView {

    private final FlowPanel flowPanel;
    private final TagsSummaryPresenter presenter;
    private final I18nTranslationService i18n;

    public TagsSummaryPanel(final TagsSummaryPresenter presenter, final I18nTranslationService i18n,
            final WorkspaceSkeleton ws) {
        super(i18n.t("Tags"), i18n.t("Keywords or terms associated with this group"), ws);
        this.i18n = i18n;
        this.presenter = presenter;
        flowPanel = new FlowPanel();
        final VerticalPanel vp = new VerticalPanel();
        vp.add(flowPanel);
        vp.setWidth("100%");
        vp.setCellWidth(flowPanel, "100%");
        super.add(vp);
        // super.setBorderStylePrimaryName("k-dropdownouter-tags");
        flowPanel.addStyleName("kune-Margin-Small-trbl");
        flowPanel.addStyleName("k-tsp-cloud");
        addInSummary();
    }

    public void addTag(final String name, Long count, String style) {
        final Label label = new Label(name);
        // i18n pluralization
        if (count > 1) {
            KuneUiUtils.setQuickTip(label, i18n.t("There are [%d] items with this tag", count));
        } else {
            KuneUiUtils.setQuickTip(label, i18n.t("There are [%d] item with this tag", count));
        }
        label.addClickListener(new ClickListener() {
            public void onClick(final Widget sender) {
                presenter.doSearchTag(name);
            }
        });
        label.addStyleName("k-tsp-tag");
        label.addStyleName(style);
        flowPanel.add(label);
    }

    @Override
    public void clear() {
        flowPanel.clear();
    }

    public void setTheme(WsTheme oldTheme, WsTheme newTheme) {
        // TODO Auto-generated method stub
    }
}
