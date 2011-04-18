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
package cc.kune.gspace.client.tags;

import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.gspace.client.tags.TagsSummaryPresenter.TagsSummaryView;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class TagsSummaryPanel extends ViewImpl implements TagsSummaryView {

    private final FlowPanel flowPanel;
    private final I18nTranslationService i18n;
    private FlowPanel mainPanel;

    @Inject
    public TagsSummaryPanel(final I18nTranslationService i18n, final GSpaceArmor ws) {
        this.i18n = i18n;
        mainPanel = new FlowPanel();
        flowPanel = new FlowPanel();
        flowPanel.setWidth("100%");
        Label header = new Label(i18n.t("Tags"));
        header.setTitle(i18n.t("Keywords or terms associated with this group"));
        // super.setBorderStylePrimaryName("k-dropdownouter-tags");
        flowPanel.addStyleName("kune-Margin-Small-trbl");
        flowPanel.addStyleName("k-tsp-cloud");
        header.addStyleName("k-sn-maintitle");
        mainPanel.addStyleName("k-sn-mainpanel");
        mainPanel.add(header);
        mainPanel.add(flowPanel);
        ws.getEntityToolsSouth().add(mainPanel);
    }

    public void addTag(final String name, final Long count, final String style, ClickHandler clickHandler) {
        final Label label = new Label(name);
        // i18n pluralization
        if (count > 1) {
            label.setTitle(i18n.t("There are [%d] items with this tag", count));
        } else {
            label.setTitle(i18n.t("There are [%d] item with this tag", count));
        }
        label.addClickHandler(clickHandler);
        label.addStyleName("k-tsp-tag");
        label.addStyleName(style);
        flowPanel.add(label);
    }

    @Override
    public void clear() {
        flowPanel.clear();
    }

    @Override
    public void setVisible(boolean visible) {
        mainPanel.setVisible(visible);
    }

    @Override
    public Widget asWidget() {
        return mainPanel;
    }
}
