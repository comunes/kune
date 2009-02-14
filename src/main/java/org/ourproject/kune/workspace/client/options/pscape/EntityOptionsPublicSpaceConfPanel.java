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
package org.ourproject.kune.workspace.client.options.pscape;

import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.BasicThumb;
import org.ourproject.kune.workspace.client.options.EntityOptionsView;
import org.ourproject.kune.workspace.client.site.Site;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolbarButton;

public class EntityOptionsPublicSpaceConfPanel extends Panel implements EntityOptionsPublicSpaceConfView {

    public EntityOptionsPublicSpaceConfPanel(final EntityOptionsPublicSpaceConfPresenter presenter,
            final WorkspaceSkeleton ws, I18nTranslationService i18n, WsThemePresenter wsPresenter) {
        super.setTitle(i18n.t("Style"));
        super.setIconCls("k-colors-icon");
        super.setAutoScroll(true);
        super.setBorder(false);
        super.setHeight(EntityOptionsView.HEIGHT);
        super.setFrame(true);
        super.setPaddings(10);

        HorizontalPanel wsHP = new HorizontalPanel();
        Label wsThemeInfo = new Label(i18n.t("Change this workspace theme:"));
        ToolbarButton toolbarWsChange = (ToolbarButton) wsPresenter.getView();
        toolbarWsChange.addStyleName("kune-Margin-Medium-l");
        wsHP.add(wsThemeInfo);
        wsHP.add(toolbarWsChange);
        add(wsHP);
        Label wsInfo = new Label(i18n.t("Select and configure the public space theme of this group:"));
        wsInfo.addStyleName("kune-Margin-Medium-tb");
        add(wsInfo);

        FlowPanel stylesPanel = new FlowPanel();
        for (int i = 1; i <= 6; i++) {
            BasicThumb thumb = new BasicThumb("images/styles/styl" + i + ".png", "Style " + i, new ClickListener() {
                public void onClick(Widget sender) {
                    Site.info(Site.IN_DEVELOPMENT);
                }
            });
            thumb.setTooltip(i18n.t("Click to select and configure this theme"));
            add(thumb);
        }
        add(stylesPanel);
    }
}
