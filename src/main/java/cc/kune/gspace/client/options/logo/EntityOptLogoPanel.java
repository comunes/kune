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
 \*/
package cc.kune.gspace.client.options.logo;

import gwtupload.client.IUploader.OnCancelUploaderHandler;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.utils.OnAcceptCallback;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.options.EntityOptionsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;

public class EntityOptLogoPanel extends Composite implements EntityOptLogoView {

  public static final String ICON_UPLD_SERVLET = GWT.getModuleBaseURL()
      + "servlets/EntityLogoUploadManager";
  private final I18nTranslationService i18n;
  private final IconLabel tabTitle;
  private final EntityUploaderForm uploader;

  public EntityOptLogoPanel(final EventBus eventBus, final I18nTranslationService i18n,
      final String panelId, final String buttonId, final String inputId, final NavResources res) {
    super();
    this.i18n = i18n;
    tabTitle = new IconLabel(res.picture(), "");

    uploader = new EntityUploaderForm(ICON_UPLD_SERVLET, i18n.t("Choose"));

    initWidget(uploader);
    setHeight(String.valueOf(EntityOptionsView.HEIGHT) + "px");
    setWidth(String.valueOf(EntityOptionsView.WIDTH) + "px");

    addStyleName("k-overflow-y-auto");
    addStyleName("k-tab-panel");
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
  public OnAcceptCallback getOnSubmit() {
    return null;
  }

  @Override
  public IsWidget getTabTitle() {
    return tabTitle;
  }

  public void reset() {
    uploader.reset();
  }

  @Override
  public void setNormalGroupsLabels() {
    uploader.setLabelText(i18n.t("Select an image from your computer as the logo for this group. "
        + "For best results use a [%d]x[%d] pixel image. Bigger images will be automatically resized.",
        FileConstants.LOGO_DEF_HEIGHT, FileConstants.LOGO_DEF_HEIGHT));
    tabTitle.setText(CoreMessages.ENT_LOGO_SELECTOR_NORMAL_TITLE);
  }

  @Override
  public void setPersonalGroupsLabels() {
    uploader.setLabelText(i18n.t("Select an image from your computer as your avatar. "
        + "For best results use a [%d]x[%d] pixel image. Bigger images will be automatically resized.",
        FileConstants.LOGO_DEF_HEIGHT, FileConstants.LOGO_DEF_HEIGHT));
    tabTitle.setText(CoreMessages.ENT_LOGO_SELECTOR_PERSON_TITLE);
  }

  @Override
  public void setUploadParams(final String userHash, final String token) {
    uploader.setUploadParams(userHash, token);
  }

}
