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
package org.ourproject.kune.workspace.client.licensefoot;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.skel.SimpleToolbar;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;

public class EntityLicensePanel implements EntityLicenseView {
    private final Label copyright;
    private final Image licenseImage;
    private final Label licenseLabel;
    private final I18nTranslationService i18n;
    private final SimpleToolbar licenseBar;
    private final WorkspaceSkeleton ws;

    public EntityLicensePanel(final EntityLicensePresenter presenter, final I18nTranslationService i18n,
            final WorkspaceSkeleton ws) {
        this.i18n = i18n;
        this.ws = ws;
        copyright = new Label();
        licenseImage = new Image();
        licenseLabel = new Label();

        licenseBar = new SimpleToolbar();
        licenseBar.add(copyright);
        licenseBar.add(licenseLabel);
        licenseBar.addSpacer();
        licenseBar.addSpacer();
        licenseBar.add(licenseImage);

        final ClickListener clickListener = new ClickListener() {
            public void onClick(Widget arg0) {
                presenter.onLicenseClick();
            }
        };

        licenseLabel.addClickListener(clickListener);
        licenseImage.addClickListener(clickListener);
        copyright.setVisible(false);
        licenseLabel.setVisible(false);

        MouseListenerAdapter mouseListenerAdapter = new MouseListenerAdapter() {
            @Override
            public void onMouseEnter(Widget sender) {
                copyright.setVisible(true);
                licenseLabel.setVisible(true);
            }

            @Override
            public void onMouseLeave(Widget sender) {
            }
        };
        licenseImage.addMouseListener(mouseListenerAdapter);

        copyright.addStyleName("kune-Margin-Large-l");
        licenseLabel.setStyleName("k-entitylicensepanel-licensetext");
        licenseImage.setStyleName("k-elp-limg");
    }

    public void attach() {
        if (!licenseBar.isAttached()) {
            ws.getEntityWorkspace().getBottomTitle().add(licenseBar);
        }
    }

    public void detach() {
        if (licenseBar.isAttached()) {
            ws.getEntityWorkspace().getBottomTitle().remove(licenseBar);
        }
    }

    public void openWindow(final String url) {
        Window.open(url, "_blank", "");
    }

    public void showLicense(final String groupName, final LicenseDTO licenseDTO) {
        copyright.setText(i18n.t("© [%s], under license: ", groupName));
        licenseLabel.setText(licenseDTO.getLongName());
        copyright.setVisible(false);
        licenseLabel.setVisible(false);
        licenseImage.setUrl(licenseDTO.getImageUrl());
    }
}
