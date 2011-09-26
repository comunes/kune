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
package cc.kune.gspace.client.options.logo;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploader.OnCancelUploaderHandler;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import gwtupload.client.MultiUploader;
import cc.kune.core.shared.FileConstants;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;

public class EntityUploaderForm extends Composite {

  private final EntityOptUploadButton btn;
  private final Label dialogInfoLabel;
  private final Hidden tokenField;
  private final MultiUploader uploader;
  private final Hidden userhashField;

  public EntityUploaderForm(final String servlet, final String btnText) {
    btn = new EntityOptUploadButton(btnText);
    uploader = new MultiUploader(FileInputType.CUSTOM.with(btn));
    uploader.setServletPath(servlet);
    uploader.setMaximumFiles(1);
    uploader.setValidExtensions("png", "jpg", "gif", "jpeg", "bmp");
    dialogInfoLabel = new Label();
    dialogInfoLabel.setWordWrap(true);
    dialogInfoLabel.addStyleName("kune-Margin-20-tb");
    userhashField = new Hidden(FileConstants.HASH, FileConstants.HASH);
    tokenField = new Hidden(FileConstants.TOKEN, FileConstants.TOKEN);
    final FlowPanel holder = new FlowPanel();
    uploader.add(userhashField);
    uploader.add(tokenField);
    holder.add(dialogInfoLabel);
    holder.add(uploader);
    initWidget(holder);
  }

  public HandlerRegistration addOnCancelUploadHandler(final OnCancelUploaderHandler handler) {
    return uploader.addOnCancelUploadHandler(handler);
  }

  public HandlerRegistration addOnChangeUploadHandler(final OnChangeUploaderHandler handler) {
    return uploader.addOnChangeUploadHandler(handler);
  }

  public HandlerRegistration addOnFinishUploadHandler(final OnFinishUploaderHandler handler) {
    return uploader.addOnFinishUploadHandler(handler);
  }

  public HandlerRegistration addOnStartUploadHandler(final OnStartUploaderHandler handler) {
    return uploader.addOnStartUploadHandler(handler);
  }

  public HasText getBtn() {
    return btn.getBtn();
  }

  public void reset() {
    uploader.reset();
  }

  public void setLabelText(final String text) {
    dialogInfoLabel.setText(text);
  }

  public void setUploadParams(final String userHash, final String token) {
    userhashField.setValue(userHash);
    tokenField.setValue(token);
  }

}
