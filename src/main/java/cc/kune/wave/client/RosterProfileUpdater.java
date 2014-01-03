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

package cc.kune.wave.client;

import cc.kune.chat.client.ChatOptions;
import cc.kune.gspace.client.events.CurrentEntityChangedEvent;
import cc.kune.gspace.client.events.CurrentEntityChangedEvent.CurrentEntityChangedHandler;

import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.im.client.roster.events.RosterItemChangedEvent;
import com.calclab.emite.im.client.roster.events.RosterItemChangedHandler;
import com.calclab.emite.im.client.roster.events.RosterRetrievedEvent;
import com.calclab.emite.im.client.roster.events.RosterRetrievedHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The Class RosterProfileUpdater listen to xmpp roster updates and refresh wave
 * profile manager
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class RosterProfileUpdater {
  @Inject
  public RosterProfileUpdater(final EventBus eventBus, final ChatOptions chatOptions,
      final XmppRoster roster, final KuneWaveProfileManager profileManager) {
    roster.addRosterRetrievedHandler(new RosterRetrievedHandler() {
      @Override
      public void onRosterRetrieved(final RosterRetrievedEvent event) {
        for (final RosterItem item : event.getRosterItems()) {
          profileManager.refreshAddress(item.getJID().toString(), false);
        }
      }
    });
    roster.addRosterItemChangedHandler(new RosterItemChangedHandler() {
      @Override
      public void onRosterItemChanged(final RosterItemChangedEvent event) {
        profileManager.refreshAddress(event.getRosterItem().getJID().toString(), false);
      }
    });
    eventBus.addHandler(CurrentEntityChangedEvent.getType(), new CurrentEntityChangedHandler() {
      @Override
      public void onCurrentLogoChanged(final CurrentEntityChangedEvent event) {
        profileManager.refreshAddress(chatOptions.uriFrom(event.getShortName()).toString(), true);
      }
    });
  }

}
