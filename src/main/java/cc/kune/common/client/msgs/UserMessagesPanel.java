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
package cc.kune.common.client.msgs;

import cc.kune.common.client.msgs.UserMessagesPresenter.UserMessagesView;
import cc.kune.common.client.notify.NotifyLevel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class UserMessagesPanel.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserMessagesPanel extends Composite implements UserMessagesView {

  /**
   * The Interface MessagesPanelUiBinder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface MessagesPanelUiBinder extends UiBinder<Widget, UserMessagesPanel> {
  }
  
  /** The ui binder. */
  private static MessagesPanelUiBinder uiBinder = GWT.create(MessagesPanelUiBinder.class);

  /** The bottom. */
  @UiField
  FlowPanel bottom;

  /** The panel. */
  @UiField
  VerticalPanel panel;

  /**
   * Instantiates a new user messages panel.
   */
  public UserMessagesPanel() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.msgs.UserMessagesPresenter.UserMessagesView#add(cc.kune.common.client.notify.NotifyLevel, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, cc.kune.common.client.msgs.CloseCallback)
   */
  @Override
  public UserMessage add(final NotifyLevel level, final String title, final String message,
      final String id, final Boolean closeable, final CloseCallback closeCallback) {

    final UserMessageWidget msg = new UserMessageWidget(level, title, message, id, closeable,
        closeCallback);

    // msg.getText().

    panel.add(msg);
    if (panel.getWidgetCount() == 1) {
      bottom.setVisible(true);
    }
    msg.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached() && panel.getWidgetCount() == 1) {
          bottom.setVisible(false);
        }
      }
    });
    return msg;
  }

  /**
   * Gets the current msg count.
   *
   * @return the current msg count
   */
  public int getCurrentMsgCount() {
    return panel.getWidgetCount();
  }
}
