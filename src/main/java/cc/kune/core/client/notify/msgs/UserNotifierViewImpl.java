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
package cc.kune.core.client.notify.msgs;

import cc.kune.common.client.notify.UserNotifyEvent;
import cc.kune.common.client.ui.PopupBottomPanel;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter.UserNotifierView;
import cc.kune.msgs.client.CloseCallback;
import cc.kune.msgs.client.UserMessage;
import cc.kune.msgs.client.UserMessagesPanel;
import cc.kune.msgs.client.UserMessagesPresenter;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class UserNotifierViewImpl extends PopupViewImpl implements UserNotifierView {
  private final UserMessagesPresenter msgs;
  private final PopupBottomPanel popup;

  @Inject
  public UserNotifierViewImpl(final EventBus eventBus, final UserMessagesPresenter msgs,
      final UserMessagesPanel panel) {
    super(eventBus);
    this.msgs = msgs;
    msgs.init(panel);
    panel.setWidth("370px");
    popup = new PopupBottomPanel(false, false);
    popup.add(panel);
    popup.show();
  }

  @Override
  public Widget asWidget() {
    return popup;
  }

  @Override
  public void notify(final UserNotifyEvent event) {
    final UserMessage msg = msgs.add(event.getLevel(), event.getTitle(), event.getMessage(),
        event.getId(), event.getCloseable(), new CloseCallback() {
          @Override
          public void onClose() {
            popup.setCenterPosition();
          }
        });
    final ClickHandler clickHandler = event.getClickHandler();
    if (clickHandler != null) {
      msg.addClickHandler(clickHandler);
    }
    event.setCloser(new UserNotifyEvent.UserNotifyCloser() {
      @Override
      public void close() {
        if (msg.isAttached()) {
          msg.close();
        }
      }
    });
    popup.setCenterPosition();
    DOM.setStyleAttribute(popup.getElement(), "zIndex", "100000");
  }

}
