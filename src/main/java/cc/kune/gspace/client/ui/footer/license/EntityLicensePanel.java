/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter.EntityLicenseView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityLicensePanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityLicensePanel extends ViewImpl implements EntityLicenseView {

  /** The Constant LICENSE_LABEL. */
  public static final String LICENSE_LABEL = "k-elp-lic-lab";

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The license bar. */
  private final FlowPanel licenseBar;

  /** The license image. */
  private final Image licenseImage;

  /** The tooltip. */
  private final Tooltip tooltip;

  /**
   * Instantiates a new entity license panel.
   * 
   * @param i18n
   *          the i18n
   * @param armor
   *          the armor
   */
  @Inject
  public EntityLicensePanel(final I18nTranslationService i18n, final GSpaceArmor armor) {
    this.i18n = i18n;
    licenseImage = new Image();

    licenseBar = new FlowPanel();
    licenseBar.add(licenseImage);
    licenseImage.addStyleName("k-footer-license-img");
    armor.getEntityFooter().add(licenseBar);
    tooltip = Tooltip.to(licenseImage, ".");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter.
   * EntityLicenseView#attach()
   */
  @Override
  public void attach() {
    licenseBar.setVisible(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter.
   * EntityLicenseView#detach()
   */
  @Override
  public void detach() {
    licenseBar.setVisible(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter.
   * EntityLicenseView#getImage()
   */
  @Override
  public HasClickHandlers getImage() {
    return licenseImage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter.
   * EntityLicenseView#openWindow(java.lang.String)
   */
  @Override
  public void openWindow(final String url) {
    KuneWindowUtils.open(url);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter.
   * EntityLicenseView#showLicense(java.lang.String,
   * cc.kune.core.shared.dto.LicenseDTO)
   */
  @Override
  public void showLicense(final String groupName, final LicenseDTO licenseDTO) {
    final String licenseText = i18n.t("© [%s], under license: [%s]", groupName, licenseDTO.getLongName());
    // KuneUiUtils.setQuickTip(licenseLabel, licenseText);
    licenseImage.setUrl(GWT.getModuleBaseURL() + licenseDTO.getImageUrl());
    tooltip.setText(licenseText);
  }
}
