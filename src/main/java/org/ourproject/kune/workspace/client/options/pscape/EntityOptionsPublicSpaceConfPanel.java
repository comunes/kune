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

import org.ourproject.kune.platf.client.ui.BasicThumb;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.download.ImageSize;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.options.EntityOptionsView;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsThemeSelector;

import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class EntityOptionsPublicSpaceConfPanel extends Panel implements EntityOptionsPublicSpaceConfView {

    private final Image backImage;
    private final FileDownloadUtils downUtils;
    private final Label currentBackLabel;
    private final Label noBackLabel;
    private final Button clearBtn;

    public EntityOptionsPublicSpaceConfPanel(final EntityOptionsPublicSpaceConfPresenter presenter,
            final WorkspaceSkeleton ws, final I18nTranslationService i18n, final WsThemeSelector wsSelector,
            final FileDownloadUtils downUtils) {
        this.downUtils = downUtils;
        super.setTitle(i18n.t("Style"));
        super.setIconCls("k-colors-icon");
        super.setAutoScroll(true);
        super.setBorder(false);
        super.setHeight(EntityOptionsView.HEIGHT);
        super.setFrame(true);
        super.setPaddings(10);

        final HorizontalPanel wsHP = new HorizontalPanel();
        final Label wsThemeInfo = new Label(i18n.t("Change this workspace theme:"));
        final Widget toolbarWsChange = (Widget) wsSelector.getView();
        toolbarWsChange.addStyleName("kune-Margin-Medium-l");
        wsHP.add(wsThemeInfo);
        wsHP.add(toolbarWsChange);
        add(wsHP);
        final VerticalPanel backPanel = new VerticalPanel();
        currentBackLabel = new Label(i18n.t("Current background image: "));
        noBackLabel = new Label(i18n.t("Also you can upload any image and select it later as background."));
        backImage = new Image();
        backImage.addStyleName("kune-Margin-Medium-trbl");
        noBackLabel.addStyleName("kune-Margin-Medium-tb");
        clearBtn = new Button(i18n.t("Clear"));
        clearBtn.setTooltip(i18n.t("Remove current background image"));
        clearBtn.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject event) {
                presenter.clearBackImage();
            }
        });
        backPanel.add(noBackLabel);
        backPanel.add(currentBackLabel);
        backPanel.add(backImage);
        backPanel.add(clearBtn);
        add(backPanel);
        final Label wsInfo = new Label(i18n.t("Select and configure the public space theme of this group:"));
        wsInfo.addStyleName("kune-Margin-Medium-tb");
        add(wsInfo);

        final Panel stylesPanel = new Panel();
        final ClickHandler clickHandler = new ClickHandler() {
            public void onClick(final ClickEvent event) {
                NotifyUser.info(TextUtils.IN_DEVELOPMENT_P);
            }
        };
        for (int i = 1; i <= 6; i++) {
            final BasicThumb thumb = new BasicThumb("images/styles/styl" + i + ".png", "Style " + i, clickHandler);
            thumb.setTooltip(i18n.t("Click to select and configure this theme"));
            add(thumb);
        }
        add(stylesPanel);
        setBackImageVisibleImpl(false);
    }

    public void clearBackImage() {
        setBackImageVisibleImpl(false);
    }

    public void setBackImage(final StateToken token) {
        backImage.setUrl(downUtils.getImageResizedUrl(token, ImageSize.thumb));
        setBackImageVisibleImpl(true);
    }

    private void setBackImageVisibleImpl(final boolean visible) {
        backImage.setVisible(visible);
        currentBackLabel.setVisible(visible);
        clearBtn.setVisible(visible);
        noBackLabel.setVisible(!visible);
    }
}
