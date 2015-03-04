/*
 *
 * Copyright (C) 2007-2015 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.client.ui;

import java.util.Arrays;
import java.util.List;

import org.gwtbootstrap3.client.ui.base.button.CustomButton;
import org.vectomatic.dnd.DataTransferExt;
import org.vectomatic.dnd.DropPanel;
import org.vectomatic.file.File;
import org.vectomatic.file.FileList;
import org.vectomatic.file.FileReader;
import org.vectomatic.file.FileUploadExt;
import org.vectomatic.file.events.ErrorEvent;
import org.vectomatic.file.events.ErrorHandler;
import org.vectomatic.file.events.LoadEndEvent;
import org.vectomatic.file.events.LoadEndHandler;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.UploadFinishedEvent;
import cc.kune.common.client.ui.UploadFinishedEvent.UploadFinishedHandler;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.ui.UploadFile;
import cc.kune.core.shared.FileConstants;
import cc.kune.core.shared.dto.BasicMimeTypeDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UploaderPanel extends Composite {

  interface UploaderPanelUiBinder extends UiBinder<Widget, UploaderPanel> {
  }
  private static final String NORMAL_BORDER_COLOR = "#808080";

  private static final String ON_OVER_BORDER_COLOR = "orange";

  private static UploaderPanelUiBinder uiBinder = GWT.create(UploaderPanelUiBinder.class);

  private final List<String> acceptedMimes;
  @UiField
  CustomButton button;
  @UiField
  FileUploadExt customUpload;
  @UiField
  DeckPanel deck;
  @UiField
  Label dropIntro;
  @UiField
  Label dropOr;
  @UiField
  DropPanel dropPanel;
  private File file;
  private final FileReader fileReader;
  @UiField
  FlowPanel flow;
  @UiField
  FlowPanel innerFlow;
  private final InputElement inputElement;
  @UiField
  Label intro;
  @UiField
  Image preview;

  public UploaderPanel(final String btnText, final String accepted) {
    initWidget(uiBinder.createAndBindUi(this));

    // TODO: Right now, we only accept one file
    customUpload.setMultiple(false);
    customUpload.setName("custom");
    dropIntro.setText(I18n.t("Drag an image here"));
    dropOr.setText(I18n.t("or, if you prefer..."));
    button.setText(btnText);
    deck.showWidget(0);

    inputElement = getInputElement();
    inputElement.setAccept(accepted);
    acceptedMimes = Arrays.asList(accepted.split(","));

    preview.setHeight(FileConstants.LOGO_DEF_SIZE + "px");
    fileReader = new FileReader();

    fileReader.addErrorHandler(new ErrorHandler() {
      @Override
      public void onError(final ErrorEvent event) {
        Log.error("Error reading upload file " + event.getAssociatedType().getName());
      }
    });

    fileReader.addLoadEndHandler(new LoadEndHandler() {
      @Override
      public void onLoadEnd(final LoadEndEvent event) {
        fireEvent(new UploadFinishedEvent((UploadFile.build(file.getName(), file.getType(),
            file.getSize(), fileReader.getStringResult()))));
      }
    });

  }

  @UiHandler({ "button", "dropOr", "dropIntro", "dropPanel" })
  public void addDrapButtonHandler(final DragEnterEvent event) {
    setBorderColor(ON_OVER_BORDER_COLOR);
  }

  @UiHandler({ "button", "dropOr", "dropIntro", "dropPanel" })
  public void addDrapOverHandler(final DragOverEvent event) {
    setBorderColor(ON_OVER_BORDER_COLOR);
  }

  public HandlerRegistration addUploadFinishedHandler(final UploadFinishedHandler handler) {
    return super.addHandler(handler, UploadFinishedEvent.getType());
  }

  @UiHandler("button")
  public void browse(final ClickEvent event) {
    customUpload.click();
  }

  public void clearBackImage() {
    preview.setVisible(false);
  }

  public HasText getBtn() {
    return button;
  }

  private InputElement getInputElement() {
    return (InputElement) customUpload.getElement().cast();
  }

  @UiHandler("dropPanel")
  public void onDragEnter(final DragEnterEvent event) {
    event.stopPropagation();
    event.preventDefault();
  }

  @UiHandler("dropPanel")
  public void onDragLeave(final DragLeaveEvent event) {
    event.stopPropagation();
    event.preventDefault();
  }

  @UiHandler("dropPanel")
  public void onDragOver(final DragOverEvent event) {
    // Mandatory handler, otherwise the default behavior will kick in and
    // onDrop will never be called
    event.stopPropagation();
    event.preventDefault();
  }

  @UiHandler("dropPanel")
  public void onDrop(final DropEvent event) {
    processFile(event.getDataTransfer().<DataTransferExt> cast().getFiles());
    setBorderColor(NORMAL_BORDER_COLOR);
    event.stopPropagation();
    event.preventDefault();
  }

  /**
   * Adds a collection of file the queue and begin processing them
   *
   * @param files
   *          The file to process
   */
  private void processFile(final FileList files) {
    // We read only one file (not multiple)
    file = files.getItem(0);
    final BasicMimeTypeDTO type = new BasicMimeTypeDTO(file.getType());
    if (acceptedMimes.contains(type.getSubtype())) {
      deck.showWidget(1);
      fileReader.readAsDataURL(file);
    } else {
      NotifyUser.error("Upload of type " + type + " is not allowed");
    }
  }

  public void reset() {
    file = null;
    deck.showWidget(0);
  }

  public void setBackImage(final String logoImageUrl) {
    preview.setUrl(logoImageUrl);
    preview.setVisible(true);
  }

  private void setBorderColor(final String color) {
    dropPanel.getElement().getStyle().setBorderColor(color);
    dropIntro.getElement().getStyle().setColor(color);
    dropOr.getElement().getStyle().setColor(color);
  }

  public void setLabelText(final String text) {
    intro.setText(text);
  }

  @UiHandler("customUpload")
  public void uploadFile(final ChangeEvent event) {
    processFile(customUpload.getFiles());
  }
}
