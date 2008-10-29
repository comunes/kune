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

import java.util.Date;

import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EntityLogoPanel extends SimplePanel implements EntityLogoView {
    class EntityTextLogo extends VerticalPanel {

        private static final String _100 = "100%";
        private static final String LOGO_MEDIUM_FONT_STYLE = "k-elogo-l-m";
        private static final String LOGO_SMALL_FONT_STYLE = "k-elogo-l-s";
        private static final String LOGO_LARGE_FONT_STYLE = "k-elogo-l-l";
        private final Label logoLabel;
        private final Label putYourLogoHL;
        private final HorizontalPanel putYourLogoHP;
        private final Image logoImage;

        public EntityTextLogo(final Provider<EntityLogoSelector> entityLogoSelectorProvider) {
            // Initialize
            super();
            HorizontalPanel generalHP = new HorizontalPanel();
            VerticalPanel logoTextVP = new VerticalPanel();
            logoImage = new Image();
            logoLabel = new Label();
            final Label expandCell = new Label("");
            putYourLogoHP = new HorizontalPanel();
            putYourLogoHL = new Label();

            logoImage.ensureDebugId(LOGO_IMAGE);
            logoLabel.ensureDebugId(LOGO_NAME);
            putYourLogoHL.ensureDebugId(PUT_YOUR_LOGO_LINK);

            // Layout
            add(generalHP);
            generalHP.add(logoImage);
            generalHP.add(logoTextVP);
            logoTextVP.add(logoLabel);
            logoTextVP.add(putYourLogoHP);
            putYourLogoHP.add(expandCell);
            putYourLogoHP.add(putYourLogoHL);

            // Set properties

            setPutYourLogo();
            expandCell.setStyleName("k-elogop-expand");
            putYourLogoHL.setStyleName("k-elogo-plink");
            putYourLogoHL.addStyleName("kune-pointer");
            putYourLogoHL.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    entityLogoSelectorProvider.get().show();
                }
            });
            generalHP.setWidth(_100);
            generalHP.setHeight(_100);
            generalHP.setCellWidth(logoTextVP, _100);
            generalHP.setCellHeight(logoTextVP, _100);
            logoTextVP.setWidth(_100);
            logoTextVP.setCellWidth(logoLabel, _100);
            super.setVerticalAlignment(ALIGN_MIDDLE);
            logoTextVP.setVerticalAlignment(ALIGN_MIDDLE);
            generalHP.setVerticalAlignment(ALIGN_MIDDLE);
            logoTextVP.setCellWidth(putYourLogoHP, _100);
            logoTextVP.setHeight(_100);
            expandCell.setWidth(_100);
            putYourLogoHP.setCellWidth(expandCell, _100);
            setStylePrimaryName("k-entitytextlogo");
            addStyleName("k-entitytextlogo-no-border");
            logoImage.setVisible(false);
            setLogoText("");
        }

        public void setChangeYourLogo() {
            putYourLogoHL.setText(i18n.t("Change Your Logo"));
        }

        public void setLargeFont() {
            resetFontSize();
            logoLabel.addStyleName(LOGO_LARGE_FONT_STYLE);
        }

        public void setLogoImage(final String url) {
            logoImage.setUrl(url);
        }

        public void setLogoText(final String text) {
            logoLabel.setText(text);
        }

        public void setLogoVisible(final boolean visible) {
            logoImage.setVisible(visible);
        }

        public void setMediumFont() {
            resetFontSize();
            logoLabel.addStyleName(LOGO_MEDIUM_FONT_STYLE);
        }

        public void setPutYourLogo() {
            putYourLogoHL.setText(i18n.t("Put Your Logo Here"));
        }

        public void setPutYourLogoVisible(final boolean visible) {
            putYourLogoHP.setVisible(visible);
        }

        public void setSmallFont() {
            resetFontSize();
            logoLabel.addStyleName(LOGO_SMALL_FONT_STYLE);
        }

        private void resetFontSize() {
            logoLabel.removeStyleName(LOGO_LARGE_FONT_STYLE);
            logoLabel.removeStyleName(LOGO_SMALL_FONT_STYLE);
            logoLabel.removeStyleName(LOGO_MEDIUM_FONT_STYLE);
        }
    }
    public static final String LOGO_NAME = "k-elogop-ln";
    public static final String LOGO_IMAGE = "k-elogop-image";
    public static final String PUT_YOUR_LOGO_LINK = "k-elogop-pyll";

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

    public void reloadImage(GroupDTO group) {
        entityTextLogo.setLogoImage(downloadProvider.get().getLogoImageUrl(group.getStateToken()) + "?nocache="
                + new Date().getTime());
    }

    public void setChangeYourLogoText() {
        getEntityTextLogo().setChangeYourLogo();

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

    public void setLargeFont() {
        getEntityTextLogo().setLargeFont();
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

    public void setMediumFont() {
        getEntityTextLogo().setMediumFont();
    }

    public void setPutYourLogoText() {
        getEntityTextLogo().setPutYourLogo();
    }

    public void setSetYourLogoVisible(final boolean visible) {
        getEntityTextLogo().setPutYourLogoVisible(visible);
    }

    public void setSmallFont() {
        getEntityTextLogo().setSmallFont();
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
