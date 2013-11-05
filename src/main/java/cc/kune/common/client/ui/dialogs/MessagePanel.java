/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class MessagePanel extends Composite implements MessagePanelView {

  interface MessagePanelUiBinder extends UiBinder<Widget, MessagePanel> {
  }
  private static MessagePanelUiBinder uiBinder = GWT.create(MessagePanelUiBinder.class);
  @UiField
  Label description;
  @UiField
  FlowPanel flowPanel;
  @UiField
  Image icon;
  private final NotifyLevelImages images;
  @UiField
  DockLayoutPanel mainPanel;
  @UiField
  Label title;

  @Inject
  public MessagePanel(final NotifyLevelImages images, final String errorLabelId) {
    this.images = images;
    initWidget(uiBinder.createAndBindUi(this));
    description.ensureDebugId(errorLabelId);
  }

  @Override
  public IsWidget getPanel() {
    return this;
  }

  @Override
  public void hideErrorMessage() {
    icon.setVisible(false);
    title.setText("");
    description.setText("");
    this.setVisible(false);
  }

  @Override
  public void setErrorMessage(final String message, final NotifyLevel level) {
    description.setText(message);
    final ImageResource resource = images.getImage(level);
    icon.setResource(resource);
    icon.setVisible(true);
    this.setVisible(true);
  }
}
