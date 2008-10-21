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

package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EntityLogoPanel extends SimplePanel implements EntityLogoView {
    class EntityTextLogo extends VerticalPanel {

        private static final int GROUP_NAME_LIMIT_SIZE = 90;
        private static final String LOGO_DEFAULT_FONT_SIZE = "167%";
        private static final String LOGO_SMALL_FONT_SIZE = "108%";
        private final Label logoLabel;
        private final Hyperlink defTextPutYourLogoHL;
        private final HorizontalPanel putYourLogoHP;
        private final Image logoImage;

        public EntityTextLogo(final Provider<EntityLogoSelector> entityLogoSelectorProvider) {
            // Initialize
            super();
            HorizontalPanel logoHP = new HorizontalPanel();
            logoImage = new Image();
            logoLabel = new Label();
            logoHP.add(logoImage);
            logoHP.add(logoLabel);
            final HTML expandCell = new HTML("<b></b>");
            putYourLogoHP = new HorizontalPanel();
            defTextPutYourLogoHL = new Hyperlink();

            // Layout
            add(logoHP);
            add(putYourLogoHP);
            putYourLogoHP.add(expandCell);
            putYourLogoHP.add(defTextPutYourLogoHL);

            // Set properties
            defTextPutYourLogoHL.setText(i18n.t("Put Your Logo Here"));
            defTextPutYourLogoHL.addStyleName("kune-pointer");
            defTextPutYourLogoHL.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    entityLogoSelectorProvider.get().show();
                }
            });
            expandCell.setWidth("100%");
            putYourLogoHP.setCellWidth(expandCell, "100%");
            // TODO: link to configure the logo
            setStylePrimaryName("k-entitytextlogo");
            logoImage.setVisible(false);
            setLogoText("");
        }

        public void setLogoImage(final String url) {
            logoImage.setUrl(url);
        }

        public void setLogoText(final String text) {
            if (text.length() > GROUP_NAME_LIMIT_SIZE) {
                DOM.setStyleAttribute(logoLabel.getElement(), "fontSize", LOGO_SMALL_FONT_SIZE);
            } else {
                DOM.setStyleAttribute(logoLabel.getElement(), "fontSize", LOGO_DEFAULT_FONT_SIZE);
            }
            logoLabel.setText(text);
        }

        public void setLogoVisible(final boolean visible) {
            logoImage.setVisible(visible);
        }

        public void setPutYourLogoVisible(final boolean visible) {
            putYourLogoHP.setVisible(visible);
        }
    }

    private final Provider<FileDownloadUtils> downloadProvider;
    private final Provider<EntityLogoSelector> entityLogoSelectorProvider;
    private EntityTextLogo entityTextLogo;
    private final I18nTranslationService i18n;

    public EntityLogoPanel(final I18nTranslationService i18n, final WorkspaceSkeleton ws,
            final Provider<FileDownloadUtils> dowloadProvider,
            final Provider<EntityLogoSelector> entityLogoSelectorProvider) {
        this.i18n = i18n;
        this.downloadProvider = dowloadProvider;
        this.entityLogoSelectorProvider = entityLogoSelectorProvider;
        ws.addToEntityMainHeader(this);
    }

    public void setFullLogo(final StateToken stateToken, final boolean clipped) {
        clear();
        final String imageUrl = downloadProvider.get().getImageUrl(stateToken);
        Image logo;
        if (clipped) {
            logo = new Image(imageUrl, 0, 0, LOGO_ICON_DEFAULT_WIDTH, LOGO_ICON_DEFAULT_HEIGHT);
        } else {
            logo = new Image(imageUrl);
            logo.setWidth("" + LOGO_ICON_DEFAULT_WIDTH);
            logo.setHeight("" + LOGO_ICON_DEFAULT_HEIGHT);
        }
        add(logo);
    }

    public void setLogoImage(StateToken stateToken) {
        entityTextLogo.setLogoImage(downloadProvider.get().getLogoImageUrl(stateToken));
    }

    public void setLogoImageVisible(boolean visible) {
        entityTextLogo.setLogoVisible(visible);
    }

    public void setLogoText(final String groupName) {
        clear();
        add(getEntityTextLogo());
        entityTextLogo.setLogoText(groupName);
    }

    public void setPutYourLogoVisible(final boolean visible) {
        getEntityTextLogo().setPutYourLogoVisible(visible);
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
        if (oldTheme != null) {
            getEntityTextLogo().removeStyleDependentName(oldTheme.toString());
        }
        getEntityTextLogo().addStyleDependentName(newTheme.toString());
    }

    private EntityTextLogo getEntityTextLogo() {
        if (entityTextLogo == null) {
            this.entityTextLogo = new EntityTextLogo(entityLogoSelectorProvider);
        }
        return entityTextLogo;
    }
}
