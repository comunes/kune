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
package cc.kune.gspace.client.options.style;

import gwtupload.client.IUploader.OnCancelUploaderHandler;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.ui.dialogs.tabbed.TabTitleGenerator;
import cc.kune.gspace.client.options.EntityOptionsView;
import cc.kune.gspace.client.options.logo.EntityUploaderForm;
import cc.kune.gspace.client.themes.GSpaceThemeSelectorPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityOptStylePanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityOptStylePanel extends FlowPanel implements EntityOptStyleView {

  /** The Constant ICON_UPLD_SERVLET. */
  public static final String ICON_UPLD_SERVLET = GWT.getModuleBaseURL()
      + "servlets/EntityBackgroundUploadManager";

  /** The Constant TAB_ID. */
  public static final String TAB_ID = "k-eodlp-style-id";

  /** The background label. */
  private final IconLabel backgroundLabel;

  /** The back image. */
  private final Image backImage;

  /** The change image. */
  private final String changeImage;

  /** The choose image. */
  private final String chooseImage;

  /** The clear btn. */
  private final Button clearBtn;

  /** The has back label. */
  private final String hasBackLabel;

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The no has backlabel. */
  private final String noHasBacklabel;

  /** The tab title. */
  private final IconLabel tabTitle;

  /** The uploader. */
  private final EntityUploaderForm uploader;

  /** The ws theme info. */
  private final Label wsThemeInfo;

  /**
   * Instantiates a new entity opt style panel.
   * 
   * @param i18n
   *          the i18n
   * @param res
   *          the res
   * @param styleSelector
   *          the style selector
   */
  public EntityOptStylePanel(final I18nTranslationService i18n, final IconicResources res,
      final GSpaceThemeSelectorPanel styleSelector) {
    this.i18n = i18n;
    tabTitle = TabTitleGenerator.generate(res.styleWhite(), i18n.t("Style"), MAX_TABTITLE_LENGTH, TAB_ID);
    // super.setHeight(String.valueOf(EntityOptionsView.HEIGHT) + "px");
    super.setWidth(String.valueOf(EntityOptionsView.WIDTH_WOUT_MARGIN) + "px");

    final FlowPanel flow = new FlowPanel();
    wsThemeInfo = new Label(i18n.t("Change the group workspace theme:"));
    flow.addStyleName("kune-Margin-20-tb");
    wsThemeInfo.addStyleName("k-fl");
    styleSelector.addStyleName("k-fl");
    styleSelector.addStyleName("kune-Margin-10-lr");
    flow.add(wsThemeInfo);
    flow.add(styleSelector);
    hasBackLabel = i18n.t("Current background image: ");
    noHasBacklabel = i18n.t("You can also upload a background:");
    backgroundLabel = new IconLabel(res.pictureGrey(), hasBackLabel);
    backgroundLabel.addStyleName("k-clear");
    chooseImage = i18n.t("Choose");
    changeImage = i18n.t("Change");
    uploader = new EntityUploaderForm(ICON_UPLD_SERVLET, chooseImage);
    uploader.addStyleName("k-clean");
    backImage = new Image();
    backImage.addStyleName("kune-Margin-Medium-trbl");
    clearBtn = new Button(i18n.t("Clear"));
    clearBtn.setStyleName("k-button");
    clearBtn.addStyleName("k-fl");
    clearBtn.addStyleName("kune-Margin-20-lr");
    Tooltip.to(clearBtn, i18n.t("Remove current background image"));
    flow.add(backgroundLabel);
    flow.add(backImage);
    flow.add(clearBtn);
    flow.add(uploader);
    flow.addStyleName("oc-clean");
    backImage.addStyleName("k-fl");
    add(flow);
    final Label wsInfo = new Label(i18n.t("Select and configure the public space theme of this group:"));
    wsInfo.addStyleName("kune-Margin-Medium-tb");
    // add(wsInfo);

    final VerticalPanel stylesPanel = new VerticalPanel();
    // final ClickHandler clickHandler = new ClickHandler() {
    //
    // @Override
    // public void onClick(final ClickEvent event) {
    // NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
    // }
    // };
    // for (int i = 1; i <= 6; i++) {
    // final BasicThumb thumb = new BasicThumb(
    // GWT.getModuleBaseURL() + "images/styles/styl" + i + ".png", "Style " + i,
    // clickHandler);
    // thumb.setTooltip(i18n.t("Click to select and configure this theme"));
    // // add(thumb);
    // }
    stylesPanel.addStyleName("oc-clean");
    add(stylesPanel);
    setBackImageVisibleImpl(false);
    super.addStyleName("k-overflow-y-auto");
    super.addStyleName("k-tab-panel");
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.EntityOptionsUploaderView#
   * addOnCancelUploadHandler
   * (gwtupload.client.IUploader.OnCancelUploaderHandler)
   */
  @Override
  public HandlerRegistration addOnCancelUploadHandler(final OnCancelUploaderHandler handler) {
    return uploader.addOnCancelUploadHandler(handler);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.EntityOptionsUploaderView#
   * addOnChangeUploadHandler
   * (gwtupload.client.IUploader.OnChangeUploaderHandler)
   */
  @Override
  public HandlerRegistration addOnChangeUploadHandler(final OnChangeUploaderHandler handler) {
    return uploader.addOnChangeUploadHandler(handler);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.EntityOptionsUploaderView#
   * addOnFinishUploadHandler
   * (gwtupload.client.IUploader.OnFinishUploaderHandler)
   */
  @Override
  public HandlerRegistration addOnFinishUploadHandler(final OnFinishUploaderHandler handler) {
    return uploader.addOnFinishUploadHandler(handler);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.EntityOptionsUploaderView#addOnStartUploadHandler
   * (gwtupload.client.IUploader.OnStartUploaderHandler)
   */
  @Override
  public HandlerRegistration addOnStartUploadHandler(final OnStartUploaderHandler handler) {
    return uploader.addOnStartUploadHandler(handler);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.style.EntityOptStyleView#clearBackImage()
   */
  @Override
  public void clearBackImage() {
    setBackImageVisibleImpl(false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.style.EntityOptStyleView#getClearBtn()
   */
  @Override
  public HasClickHandlers getClearBtn() {
    return clearBtn;
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

  /**
   * Gets the ws theme info.
   * 
   * @return the ws theme info
   */
  public Label getWsThemeInfo() {
    return wsThemeInfo;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gspace.client.options.style.EntityOptStyleView#reset()
   */
  @Override
  public void reset() {
    uploader.reset();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.style.EntityOptStyleView#setBackImage(java
   * .lang.String)
   */
  @Override
  public void setBackImage(final String url) {
    backImage.setUrl(url);
    setBackImageVisibleImpl(true);
  }

  /**
   * Sets the back image visible impl.
   * 
   * @param visible
   *          the new back image visible impl
   */
  private void setBackImageVisibleImpl(final boolean visible) {
    backImage.setVisible(visible);
    clearBtn.setVisible(visible);
    backgroundLabel.setText(visible ? hasBackLabel : noHasBacklabel, i18n.getDirection());
    uploader.getBtn().setText(visible ? changeImage : chooseImage);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.options.style.EntityOptStyleView#setUploadParams(
   * java.lang.String, java.lang.String)
   */
  @Override
  public void setUploadParams(final String userHash, final String token) {
    uploader.setUploadParams(userHash, token);
    Log.info("Background uploader params: " + userHash + ", " + token);
  }
}
