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
package cc.kune.gspace.client.ui.footer.license;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.WsArmor;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter.EntityLicenseView;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class EntityLicensePanel extends ViewImpl implements EntityLicenseView {
    public static final String LICENSE_LABEL = "k-elp-lic-lab";
    private final I18nTranslationService i18n;
    private final FlowPanel licenseBar;
    private final Image licenseImage;

    @Inject
    public EntityLicensePanel(final I18nTranslationService i18n, final WsArmor armor) {
        this.i18n = i18n;
        licenseImage = new Image();

        licenseBar = new FlowPanel();
        licenseBar.add(licenseImage);
        licenseImage.addStyleName("k-footer-license-img");
        armor.getEntityFooter().add(licenseBar);
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public void attach() {
        licenseBar.setVisible(true);
    }

    @Override
    public void detach() {
        licenseBar.setVisible(false);
    }

    @Override
    public HasClickHandlers getImage() {
        return licenseImage;
    }

    @Override
    public void openWindow(final String url) {
        KuneWindowUtils.open(url);
    }

    @Override
    public void showLicense(final String groupName, final LicenseDTO licenseDTO) {
        final String licenseText = i18n.t("© [%s], under license: [%s]", groupName, licenseDTO.getLongName());
        // KuneUiUtils.setQuickTip(licenseLabel, licenseText);
        licenseImage.setUrl(licenseDTO.getImageUrl());
        Tooltip.to(licenseImage, licenseText);
    }
}
