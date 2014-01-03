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
package cc.kune.gspace.client.options.license;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.KuneWindowUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.dialogs.tabbed.TabTitleGenerator;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.gspace.client.options.EntityOptionsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityOptDefLicensePanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityOptDefLicensePanel extends Composite implements EntityOptDefLicenseView {

  /** The Constant TAB_ID. */
  public static final String TAB_ID = "k-eodlp-lic-id";

  /** The change. */
  private final Button change;

  /** The intro. */
  private final Label intro;

  /** The license image. */
  private final Image licenseImage;

  /** The tab title. */
  private final IconLabel tabTitle;

  /**
   * Instantiates a new entity opt def license panel.
   * 
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   */
  public EntityOptDefLicensePanel(final I18nTranslationService i18n, final IconicResources res) {
    tabTitle = TabTitleGenerator.generate(res.copyleftWhite(), i18n.t("License"), MAX_TABTITLE_LENGTH,
        TAB_ID);
    final FlowPanel flow = new FlowPanel();
    intro = new Label();
    intro.setWordWrap(true);
    intro.setText(i18n.t("This is the default license for all the contents of this group (although you can choose a different license for specific contents):"));
    intro.addStyleName("kune-Margin-20-tb");
    flow.add(intro);
    licenseImage = new Image();
    flow.add(licenseImage);
    licenseImage.addStyleName("kune-pointer");
    licenseImage.addStyleName("kune-Margin-10-r");
    change = new Button(i18n.t("Change"));
    change.addStyleName("k-button");
    flow.add(change);
    initWidget(flow);
    // flow.setHeight(String.valueOf(EntityOptionsView.HEIGHT) + "px");
    flow.setWidth(String.valueOf(EntityOptionsView.WIDTH_WOUT_MARGIN) + "px");
    flow.addStyleName("k-overflow-y-auto");
    flow.addStyleName("k-tab-panel");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.license.EntityOptDefLicenseView#getChange()
   */
  @Override
  public HasClickHandlers getChange() {
    return change;
  }

  /**
   * Gets the intro.
   * 
   * @return the intro
   */
  public Label getIntro() {
    return intro;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.license.EntityOptDefLicenseView#getLicenseImage
   * ()
   */
  @Override
  public HasClickHandlers getLicenseImage() {
    return licenseImage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.EntityOptionsTabView#getTabTitle()
   */
  @Override
  public IsWidget getTabTitle() {
    return tabTitle;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.license.EntityOptDefLicenseView#openWindow
   * (java.lang.String)
   */
  @Override
  public void openWindow(final String url) {
    KuneWindowUtils.open(url);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.license.EntityOptDefLicenseView#setLicense
   * (cc.kune.core.shared.dto.LicenseDTO)
   */
  @Override
  public void setLicense(final LicenseDTO defaultLicense) {
    licenseImage.setUrl(GWT.getModuleBaseURL() + defaultLicense.getImageUrl());
    Tooltip.to(licenseImage, defaultLicense.getLongName());
  }
}
