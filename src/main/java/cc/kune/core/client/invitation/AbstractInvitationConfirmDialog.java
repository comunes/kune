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

package cc.kune.core.client.invitation;

import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.shared.i18n.I18n;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractInvitationConfirmDialog.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class AbstractInvitationConfirmDialog extends BasicTopDialog {

  /**
   * The Class Builder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class Builder extends BasicTopDialog.Builder {

    /** The message. */
    private final String message;

    /**
     * Instantiates a new builder.
     * 
     * @param dialogId
     *          the dialog id
     * @param title
     *          the title
     * @param message
     *          the message
     */
    public Builder(final String dialogId, final String title, final String message) {
      super(dialogId, false, false, I18n.getDirection());
      super.title(title);
      this.message = message;
      this.sndButtonTitle(I18n.t("Cancel"));
      width(300);
    }

    /*
     * (non-Javadoc)
     * 
     * @see cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder#build()
     */
    @Override
    public AbstractInvitationConfirmDialog build() {
      final AbstractInvitationConfirmDialog dialog = new AbstractInvitationConfirmDialog(this);
      return dialog;
    }

    /**
     * Gets the message.
     * 
     * @return the message
     */
    public String getMessage() {
      return message;
    }

  }

  /**
   * Instantiates a new generic invitation confirm dialog.
   * 
   * @param builder
   *          the builder
   */
  protected AbstractInvitationConfirmDialog(final AbstractInvitationConfirmDialog.Builder builder) {
    super(builder);
    final FocusPanel focus = new FocusPanel();
    final HTMLPanel label = new HTMLPanel(builder.getMessage());
    focus.add(label);
    focus.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        hide();
      }
    });
    super.getInnerPanel().add(focus);
    super.setFirstBtnVisible(false);
    this.getSecondBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        hide();
      }
    });
  }

}
