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

// TODO: Auto-generated Javadoc
/**
 * The Class EntityUploaderForm.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class EntityUploaderForm extends Composite {

  /** The btn. */
  private final EntityOptUploadButton btn;

  /** The dialog info label. */
  private final Label dialogInfoLabel;

  /** The token field. */
  private final Hidden tokenField;

  /** The uploader. */
  private final MultiUploader uploader;

  /** The userhash field. */
  private final Hidden userhashField;

  /**
   * Instantiates a new entity uploader form.
   * 
   * @param servlet
   *          the servlet
   * @param btnText
   *          the btn text
   */
  public EntityUploaderForm(final String servlet, final String btnText) {
    btn = new EntityOptUploadButton(btnText);
    uploader = new MultiUploader(FileInputType.CUSTOM.with(btn, true));
    uploader.setServletPath(servlet);
    dialogInfoLabel = new Label();
    dialogInfoLabel.setWordWrap(true);
    dialogInfoLabel.addStyleName("kune-Margin-20-tb");
    userhashField = new Hidden(FileConstants.HASH, FileConstants.HASH);
    tokenField = new Hidden(FileConstants.TOKEN, FileConstants.TOKEN);
    final FlowPanel holder = new FlowPanel();
    initUploader();
    holder.add(dialogInfoLabel);
    initUploader();
    holder.add(uploader);
    initWidget(holder);
  }

  /**
   * Adds the on cancel upload handler.
   * 
   * @param handler
   *          the handler
   * @return the handler registration
   */
  public HandlerRegistration addOnCancelUploadHandler(final OnCancelUploaderHandler handler) {
    return uploader.addOnCancelUploadHandler(handler);
  }

  /**
   * Adds the on change upload handler.
   * 
   * @param handler
   *          the handler
   * @return the handler registration
   */
  public HandlerRegistration addOnChangeUploadHandler(final OnChangeUploaderHandler handler) {
    return uploader.addOnChangeUploadHandler(handler);
  }

  /**
   * Adds the on finish upload handler.
   * 
   * @param handler
   *          the handler
   * @return the handler registration
   */
  public HandlerRegistration addOnFinishUploadHandler(final OnFinishUploaderHandler handler) {
    return uploader.addOnFinishUploadHandler(handler);
  }

  /**
   * Adds the on start upload handler.
   * 
   * @param handler
   *          the handler
   * @return the handler registration
   */
  public HandlerRegistration addOnStartUploadHandler(final OnStartUploaderHandler handler) {
    return uploader.addOnStartUploadHandler(handler);
  }

  /**
   * Gets the btn.
   * 
   * @return the btn
   */
  public HasText getBtn() {
    return btn.hasText();
  }

  /**
   * Inits the uploader.
   */
  private void initUploader() {
    uploader.add(userhashField);
    uploader.add(tokenField);
    // This not allow to change the logo several times:
    // uploader.setMaximumFiles(1);
    uploader.setValidExtensions("png", "jpg", "gif", "jpeg", "bmp");
  }

  /**
   * Reset.
   */
  public void reset() {
    uploader.reset();
    initUploader();
  }

  /**
   * Sets the label text.
   * 
   * @param text
   *          the new label text
   */
  public void setLabelText(final String text) {
    dialogInfoLabel.setText(text);
  }

  /**
   * Sets the upload params.
   * 
   * @param userHash
   *          the user hash
   * @param token
   *          the token
   */
  public void setUploadParams(final String userHash, final String token) {
    userhashField.setValue(userHash);
    tokenField.setValue(token);
  }

}
