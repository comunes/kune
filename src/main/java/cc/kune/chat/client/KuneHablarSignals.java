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

import cc.kune.chat.client.ChatClientDefault.ChatClientAction;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.services.ClientFileDownloadUtils;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.xep.storage.client.PrivateStorageManager;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.browser.BrowserFocusHandler;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.signals.client.SignalMessages;
import com.calclab.hablar.signals.client.SignalPreferences;
import com.calclab.hablar.signals.client.notifications.NotificationManager;
import com.calclab.hablar.signals.client.preferences.SignalsPreferencesPresenter;
import com.calclab.hablar.signals.client.preferences.SignalsPreferencesWidget;
import com.calclab.hablar.signals.client.unattended.UnattendedPagesManager;
import com.calclab.hablar.signals.client.unattended.UnattendedPresenter;
import com.calclab.hablar.user.client.UserContainer;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasText;

// TODO: Auto-generated Javadoc
/**
 * Install the signals module into Hablar.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneHablarSignals {

  /** The signal messages. */
  public static SignalMessages signalMessages;

  /**
   * Gets the {@link SignalMessages} object containing the internationalised
   * messages.
   * 
   * @return the SignalMessages object containing the internationalised messages
   */
  public static SignalMessages i18n() {
    return signalMessages;
  }

  /**
   * Sets the {@link SignalMessages} object containing the internationalised
   * messages.
   * 
   * @param t
   *          the messages object
   */
  public static void setMessages(final SignalMessages t) {
    KuneHablarSignals.signalMessages = t;
  }

  // FIXME: move to gin
  /**
   * Instantiates a new kune hablar signals.
   * 
   * @param kuneEventBus
   *          the kune event bus
   * @param session
   *          the session
   * @param hablar
   *          the hablar
   * @param action
   *          the action
   * @param privateStorageManager
   *          the private storage manager
   * @param i18n
   *          the i18n
   * @param downUtils
   *          the down utils
   */
  public KuneHablarSignals(final EventBus kuneEventBus, final XmppSession session, final Hablar hablar,
      final ChatClientAction action, final PrivateStorageManager privateStorageManager,
      final I18nTranslationService i18n, final ClientFileDownloadUtils downUtils) {
    final HablarEventBus hablarEventBus = hablar.getEventBus();
    final PrivateStorageManager storageManager = privateStorageManager;

    final HasText titleDisplay = new HasText() {
      @Override
      public String getText() {
        return Window.getTitle();
      }

      @Override
      public void setText(final String text) {
        Window.setTitle(text);
      }
    };
    final SignalPreferences preferences = new SignalPreferences();

    final UnattendedPagesManager manager = new UnattendedPagesManager(hablarEventBus,
        BrowserFocusHandler.getInstance());
    new KuneBrowserFocusManager(kuneEventBus, hablarEventBus, manager, BrowserFocusHandler.getInstance());
    new UnattendedPresenter(hablarEventBus, preferences, manager, titleDisplay);
    new KuneUnattendedPresenter(kuneEventBus, hablarEventBus, preferences, manager, action);
    final NotificationManager notificationManager = new NotificationManager(hablarEventBus, preferences);

    // notificationManager.addNotifier((BrowserPopupHablarNotifier)
    // GWT.create(BrowserPopupHablarNotifier.class),
    // true);
    notificationManager.addNotifier(new KuneChatNotifier(i18n, downUtils, kuneEventBus), true);

    final SignalsPreferencesPresenter preferencesPage = new SignalsPreferencesPresenter(session,
        storageManager, hablarEventBus, preferences, new SignalsPreferencesWidget(), notificationManager);
    hablar.addPage(preferencesPage, UserContainer.ROL);
  }
}
