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
package cc.kune.common.client.ui.dialogs;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyLevelImages;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageToolbar.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MessageToolbar extends Composite {
  
  /** The error icon. */
  private final Image errorIcon;
  
  /** The error label. */
  private final Label errorLabel;
  
  /** The images. */
  private final NotifyLevelImages images;
  
  /** The toolbar. */
  private final FlowPanel toolbar;

  /**
   * Instantiates a new message toolbar.
   *
   * @param images the images
   * @param errorLabelId the error label id
   */
  public MessageToolbar(final NotifyLevelImages images, final String errorLabelId) {
    this.images = images;
    toolbar = new FlowPanel();
    errorLabel = new Label("");
    errorLabel.setWordWrap(true);
    errorLabel.ensureDebugId(errorLabelId);
    errorIcon = new Image();
    errorIcon.setResource(images.getImage(NotifyLevel.error));

    toolbar.add(errorIcon);
    toolbar.setStyleName("k-error-tb");

    toolbar.add(errorLabel);
    errorIcon.setVisible(false);
    toolbar.setVisible(false);
    initWidget(toolbar);
  }

  /**
   * Gets the toolbar.
   *
   * @return the toolbar
   */
  public FlowPanel getToolbar() {
    return toolbar;
  }

  /**
   * Hide error message.
   */
  public void hideErrorMessage() {
    errorIcon.setVisible(false);
    errorLabel.setText("");
    toolbar.setVisible(false);
  }

  /**
   * Sets the error message.
   *
   * @param message the message
   * @param level the level
   */
  public void setErrorMessage(final String message, final NotifyLevel level) {
    errorLabel.setText(message);
    final ImageResource icon = images.getImage(level);
    errorIcon.setResource(icon);
    errorIcon.setVisible(true);
    toolbar.setVisible(true);
  }
}
