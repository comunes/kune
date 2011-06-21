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
package cc.kune.gspace.client.options.style;

import gwtupload.client.IUploader.OnCancelUploaderHandler;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.BasicThumb;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.EntityOptionsView;
import cc.kune.gspace.client.options.logo.EntityUploaderForm;
import cc.kune.gspace.client.themes.GSpaceThemeSelectorPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EntityOptionsStyleConfPanel extends FlowPanel implements EntityOptionsStyleConfView {

  public static final String ICON_UPLD_SERVLET = "servlets/EntityBackgroundUploadManager";
  private final Image backImage;
  private final Button clearBtn;
  private final Label currentBackLabel;
  private final Label noBackLabel;
  private final IconLabel tabTitle;
  private final EntityUploaderForm uploader;

  public EntityOptionsStyleConfPanel(final I18nTranslationService i18n, final CoreResources res,
      final GSpaceThemeSelectorPanel styleSelector) {
    ;
    tabTitle = new IconLabel(res.themeChoose(), i18n.t("Style"));
    super.setHeight(String.valueOf(EntityOptionsView.HEIGHT) + "px");
    super.setWidth(String.valueOf(EntityOptionsView.WIDTH) + "px");

    final FlowPanel flow = new FlowPanel();
    final Label wsThemeInfo = new Label(i18n.t("Change this workspace theme:"));
    flow.addStyleName("kune-Margin-20-tb");
    wsThemeInfo.addStyleName("k-fl");
    styleSelector.addStyleName("k-fl");
    styleSelector.addStyleName("kune-Margin-10-trbl");
    flow.add(wsThemeInfo);
    flow.add(styleSelector);
    currentBackLabel = new Label(i18n.t("Current background image: "));
    noBackLabel = new Label(i18n.t("Also you can upload a background:"));
    currentBackLabel.addStyleName("k-clear");
    noBackLabel.addStyleName("k-clear");
    uploader = new EntityUploaderForm(ICON_UPLD_SERVLET, i18n.t("Choose"));
    uploader.addStyleName("k-fl");
    backImage = new Image();
    backImage.addStyleName("kune-Margin-Medium-trbl");
    noBackLabel.addStyleName("kune-Margin-Medium-tb");
    clearBtn = new Button(i18n.t("Clear"));
    clearBtn.setStyleName("k-button");
    clearBtn.addStyleName("k-fl");
    Tooltip.to(clearBtn, i18n.t("Remove current background image"));
    flow.add(noBackLabel);
    flow.add(currentBackLabel);
    flow.add(backImage);
    flow.add(uploader);
    flow.add(clearBtn);
    flow.addStyleName("oc-clean");
    backImage.addStyleName("k-fr");
    add(flow);
    final Label wsInfo = new Label(i18n.t("Select and configure the public space theme of this group:"));
    wsInfo.addStyleName("kune-Margin-Medium-tb");
    // add(wsInfo);

    final VerticalPanel stylesPanel = new VerticalPanel();
    final ClickHandler clickHandler = new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        NotifyUser.info(TextUtils.IN_DEVELOPMENT);
      }
    };
    for (int i = 1; i <= 6; i++) {
      final BasicThumb thumb = new BasicThumb("images/styles/styl" + i + ".png", "Style " + i,
          clickHandler);
      thumb.setTooltip(i18n.t("Click to select and configure this theme"));
      // add(thumb);
    }
    stylesPanel.addStyleName("oc-clean");
    add(stylesPanel);
    setBackImageVisibleImpl(false);
    super.addStyleName("k-overflow-y-auto");
    super.addStyleName("k-tab-panel");
  }

  @Override
  public HandlerRegistration addOnCancelUploadHandler(final OnCancelUploaderHandler handler) {
    return uploader.addOnCancelUploadHandler(handler);
  }

  @Override
  public HandlerRegistration addOnChangeUploadHandler(final OnChangeUploaderHandler handler) {
    return uploader.addOnChangeUploadHandler(handler);
  }

  @Override
  public HandlerRegistration addOnFinishUploadHandler(final OnFinishUploaderHandler handler) {
    return uploader.addOnFinishUploadHandler(handler);
  }

  @Override
  public HandlerRegistration addOnStartUploadHandler(final OnStartUploaderHandler handler) {
    return uploader.addOnStartUploadHandler(handler);
  }

  @Override
  public void clearBackImage() {
    setBackImageVisibleImpl(false);
  }

  @Override
  public HasClickHandlers getClearBtn() {
    return clearBtn;
  }

  @Override
  public IsWidget getTabTitle() {
    return tabTitle;
  }

  @Override
  public void reset() {
    uploader.reset();
  }

  @Override
  public void setBackImage(final String url) {
    // backImage.setUrl(downUtils.getImageResizedUrl(token, ImageSize.thumb));
    backImage.setUrl(url);
    setBackImageVisibleImpl(true);
  }

  private void setBackImageVisibleImpl(final boolean visible) {
    backImage.setVisible(visible);
    currentBackLabel.setVisible(visible);
    clearBtn.setVisible(visible);
    noBackLabel.setVisible(!visible);
  }

  @Override
  public void setUploadParams(final String userHash, final String token) {
    uploader.setUploadParams(userHash, token);
  }
}
