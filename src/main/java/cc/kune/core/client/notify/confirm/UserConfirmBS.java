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
package cc.kune.core.client.notify.confirm;

import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;

import cc.kune.common.client.notify.ConfirmAskEvent;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class UserConfirmBS { // extends ViewImpl implements UserConfirmView {

  @Inject
  UserConfirmBS(final EventBus eventbus) {
    eventbus.addHandler(ConfirmAskEvent.getType(), new ConfirmAskEvent.ConfirmAskHandler() {
      @Override
      public void onConfirmAsk(final ConfirmAskEvent event) {
        confirmAsk(event);
      }
    });
  }

  /**
   * Displays a message in a modal dialog box, along with the standard 'OK' and
   * 'Cancel' buttons.
   *
   * @param msg
   *          the message to be displayed.
   * @param callback
   *          the callback handler.
   */
  public native void confirm(String title, String msg, String acceptMsg, String cancelMsg,
      ConfirmCallback callback) /*-{
		$wnd.bootbox
				.dialog({
					message : msg,
					title : title,
					buttons : {
						success : {
							label : acceptMsg,
							//className : "btn-success",
							callback : function() {
								callback.@org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback::callback(Z)(true);
							}
						},
						main : {
							label : cancelMsg,
							//							className : "btn-primary",
							callback : function() {
								callback.@org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback::callback(Z)(false);
							}
						}
					}
				});
  }-*/;

  public void confirmAsk(final ConfirmAskEvent event) {
    confirm(event.getTitle(), event.getMessage(), event.getAcceptBtnMsg(), event.getCancelBtnMsg(),
        new ConfirmCallback() {
          @Override
          public void callback(final boolean result) {
            if (result) {
              event.getCallback().onSuccess();
            } else {
              event.getCallback().onCancel();
            }
          }
        });
  }
}
