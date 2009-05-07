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
package org.ourproject.kune.workspace.client.entityheader;

import java.util.Date;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.download.FileConstants;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.themes.WsTheme;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EntityHeaderPanel extends HorizontalPanel implements EntityHeaderView {

    private final Provider<FileDownloadUtils> downloadProvider;
    private final EntityTextLogo entityTextLogo;
    private final Images images;
    private final VerticalPanel vp;

    public EntityHeaderPanel(final WorkspaceSkeleton ws, final Provider<FileDownloadUtils> downloadProvider,
            final Images images) {
        super.setWidth("100%");
        this.downloadProvider = downloadProvider;
        this.images = images;
        vp = new VerticalPanel();
        vp.setWidth("100%");
        vp.setHorizontalAlignment(ALIGN_RIGHT);
        entityTextLogo = new EntityTextLogo();
        add(entityTextLogo);
        add(vp);
        ws.addToEntityMainHeader(this);
    }

    public void addWidget(final View view) {
        Widget widget = (Widget) view;
        vp.add(widget);
    }

    public void reloadImage(final GroupDTO group) {
        entityTextLogo.setLogoImage(downloadProvider.get().getLogoImageUrl(group.getStateToken()) + "&nocache="
                + new Date().getTime());
    }

    public void setFullLogo(final StateToken stateToken, final boolean clipped) {
        clear();
        final String imageUrl = downloadProvider.get().getImageUrl(stateToken);
        Image logo;
        if (clipped) {
            logo = new Image(imageUrl, 0, 0, FileConstants.LOGO_ICON_DEFAULT_WIDTH,
                    FileConstants.LOGO_ICON_DEFAULT_HEIGHT);
        } else {
            logo = new Image(imageUrl);
            logo.setWidth("" + FileConstants.LOGO_ICON_DEFAULT_WIDTH);
            logo.setHeight("" + FileConstants.LOGO_ICON_DEFAULT_HEIGHT);
        }
        add(logo);
    }

    public void setLargeFont() {
        entityTextLogo.setLargeFont();
    }

    public void setLogoImage(final StateToken stateToken) {
        entityTextLogo.setLogoImage(downloadProvider.get().getLogoImageUrl(stateToken));
    }

    public void setLogoImageVisible(final boolean visible) {
        entityTextLogo.setLogoVisible(visible);
    }

    public void setLogoText(final String groupName) {
        entityTextLogo.setLogoText(groupName);
    }

    public void setMediumFont() {
        entityTextLogo.setMediumFont();
    }

    public void setSmallFont() {
        entityTextLogo.setSmallFont();
    }

    public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
        if (oldTheme != null) {
            entityTextLogo.removeStyleDependentName(oldTheme.toString());
        }
        entityTextLogo.addStyleDependentName(newTheme.toString());
    }

    public void showDefUserLogo() {
        entityTextLogo.setLogoImage(images.personAvatarDef());
    }
}
