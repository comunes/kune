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
 \*/
package cc.kune.gspace.client.options.logo;

import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.ui.UploadFinishedEvent.UploadFinishedHandler;
import cc.kune.common.client.ui.UploaderPanel;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.dialogs.tabbed.TabTitleGenerator;
import cc.kune.core.shared.FileConstants;
import cc.kune.gspace.client.options.EntityOptionsView;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The Class EntityOptLogoPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityOptLogoPanel extends Composite implements EntityOptLogoView {

  /** The Constant TAB_ID. */
  public static final String TAB_ID = "k-eodlp-logo-id";

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The tab title. */
  private final IconLabel tabTitle;

  private final UploaderPanel uploader;

  /**
   * Instantiates a new entity opt logo panel.
   *
   * @param eventBus
   *          the event bus
   * @param i18n
   *          the i18n
   * @param panelId
   *          the panel id
   * @param buttonId
   *          the button id
   * @param inputId
   *          the input id
   * @param res
   *          the res
   */
  public EntityOptLogoPanel(final EventBus eventBus, final I18nTranslationService i18n,
      final String panelId, final String buttonId, final String inputId, final IconicResources res) {
    super();
    this.i18n = i18n;
    tabTitle = TabTitleGenerator.generate(res.pictureWhite(), "", TAB_ID);
    uploader = new UploaderPanel(i18n.t("choose an image"), FileConstants.ACCEPTED_IMAGES);

    initWidget(uploader);

    // Better autoadjust
    // setHeight(String.valueOf(EntityOptionsView.HEIGHT) + "px");
    setWidth(String.valueOf(EntityOptionsView.WIDTH_WOUT_MARGIN) + "px");

    addStyleName("k-overflow-y-auto");
    addStyleName("k-tab-panel");
  }

  @Override
  public HandlerRegistration addUploadFinishedHandler(final UploadFinishedHandler handler) {
    return uploader.addUploadFinishedHandler(handler);
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
   * @see cc.kune.gspace.client.options.logo.EntityOptLogoView#reset()
   */
  @Override
  public void reset() {
    uploader.reset();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.logo.EntityOptLogoView#setNormalGroupsLabels
   * ()
   */
  @Override
  public void setNormalGroupsLabels() {
    uploader.setLabelText(i18n.t("Select an image from your computer as the logo for this group. "
        + "For best results use a [%d]x[%d] pixel image. Bigger images will be automatically resized.",
        FileConstants.LOGO_DEF_SIZE, FileConstants.LOGO_DEF_SIZE));
    TabTitleGenerator.setText(tabTitle, i18n.t(CoreMessages.ENT_LOGO_SELECTOR_NORMAL_TITLE),
        MAX_TABTITLE_LENGTH, i18n.getDirection());
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.logo.EntityOptLogoView#setPersonalGroupsLabels
   * ()
   */
  @Override
  public void setPersonalGroupsLabels() {
    uploader.setLabelText(i18n.t("Select an image from your computer as your avatar. "
        + "For best results use a [%d]x[%d] pixel image. Bigger images will be automatically resized.",
        FileConstants.LOGO_DEF_SIZE, FileConstants.LOGO_DEF_SIZE));
    TabTitleGenerator.setText(tabTitle, i18n.t(CoreMessages.ENT_LOGO_SELECTOR_PERSON_TITLE),
        MAX_TABTITLE_LENGTH, i18n.getDirection());
  }

}
