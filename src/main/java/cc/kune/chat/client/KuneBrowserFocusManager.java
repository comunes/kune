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
package cc.kune.chat.client;

import cc.kune.core.client.events.WindowFocusEvent;

import com.calclab.hablar.chat.client.ui.ChatPresenter;
import com.calclab.hablar.chat.client.ui.PairChatPresenter;
import com.calclab.hablar.core.client.browser.BrowserFocusHandler;
import com.calclab.hablar.core.client.browser.BrowserFocusHandler.BrowserFocusListener;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.core.client.page.Page;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.page.events.VisibilityChangedEvent;
import com.calclab.hablar.core.client.page.events.VisibilityChangedHandler;
import com.calclab.hablar.rooms.client.room.RoomPresenter;
import com.calclab.hablar.signals.client.unattended.UnattendedPagesManager;
import com.google.gwt.event.shared.EventBus;

// TODO: Auto-generated Javadoc
/**
 * This class is a workaround to clear the focus on the active chat page FIXME:
 * workaround to clear the focus <br/>
 * 
 * <br/>
 * TODO: change the page/header visibility system... quite a big job
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneBrowserFocusManager {

  /** The current focused. */
  protected ChatPresenter currentFocused;

  /**
   * Instantiates a new kune browser focus manager.
   *
   * @param kuneEventBus the kune event bus
   * @param eventBus the event bus
   * @param unattendedManager the unattended manager
   * @param handler the handler
   */
  public KuneBrowserFocusManager(final EventBus kuneEventBus, final HablarEventBus eventBus,
      final UnattendedPagesManager unattendedManager, final BrowserFocusHandler handler) {

    eventBus.addHandler(VisibilityChangedEvent.TYPE, new VisibilityChangedHandler() {
      @Override
      public void onVisibilityChanged(final VisibilityChangedEvent event) {
        if (event.getVisibility() == Visibility.focused) {
          final Page<?> page = event.getPage();
          if (PairChatPresenter.TYPE.equals(page.getType()) || RoomPresenter.TYPE.equals(page.getType())) {
            currentFocused = (ChatPresenter) page;
          }
        }
      }
    });

    handler.setFocusListener(new BrowserFocusListener() {
      @Override
      public void onBrowserFocusChanged(final boolean hasFocus) {
        kuneEventBus.fireEvent(new WindowFocusEvent(hasFocus));
        if (currentFocused != null) {
          if (hasFocus == false) {
            currentFocused.getDisplay().setTextBoxFocus(false);
          }
        }
      }
    });
  }
}
