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
package cc.kune.chat.client;

import cc.kune.common.client.ui.AbstractDecorator;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.avatar.AvatarDecorator;

import com.calclab.emite.core.client.events.StateChangedEvent;
import com.calclab.emite.core.client.events.StateChangedHandler;
import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.core.client.xmpp.stanzas.Presence;
import com.calclab.emite.core.client.xmpp.stanzas.Presence.Show;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.presence.PresenceManager;
import com.calclab.emite.im.client.presence.events.OwnPresenceChangedEvent;
import com.calclab.emite.im.client.presence.events.OwnPresenceChangedHandler;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.im.client.roster.events.RosterItemChangedEvent;
import com.calclab.emite.im.client.roster.events.RosterItemChangedHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.IsWidget;

public class AvatarDecoratorImpl extends AbstractDecorator implements AvatarDecorator {

  private HandlerRegistration attachHandler;
  private final ChatClient chatClient;
  private final ImageResource chatDotAvailable;
  private final ImageResource chatDotAway;
  private final ImageResource chatDotBusy;
  private final ImageResource chatDotExtendedAway;
  private final ImageResource chatDotXA;
  private final I18nTranslationService i18n;
  private final HandlerRegistration presenceHandler;
  private final PresenceManager presenceManager;
  private final XmppRoster roster;
  private final HandlerRegistration rosterHandler;
  private final XmppSession session;
  private final HandlerRegistration sessionStateChangedHandler;
  private XmppURI uri;
  private final XmppSession xmppSession;

  public AvatarDecoratorImpl(final I18nTranslationService i18n, final ChatInstances chatInstances,
      final ChatClient chatClient, final ImageResource chatDotBusy, final ImageResource chatDotXA,
      final ImageResource chatDotAway, final ImageResource chatDotExtendedAway,
      final ImageResource chatDotAvailable) {
    this.chatClient = chatClient;
    this.i18n = i18n;
    this.chatDotBusy = chatDotBusy;
    this.chatDotXA = chatDotXA;
    this.chatDotAway = chatDotAway;
    this.chatDotExtendedAway = chatDotExtendedAway;
    this.chatDotAvailable = chatDotAvailable;
    session = chatInstances.xmppSession;
    sessionStateChangedHandler = session.addSessionStateChangedHandler(false, new StateChangedHandler() {
      @Override
      public void onStateChanged(final StateChangedEvent event) {
        refresh();
      }
    });
    roster = chatInstances.roster;
    xmppSession = chatInstances.xmppSession;
    presenceManager = chatInstances.presenceManager;
    rosterHandler = roster.addRosterItemChangedHandler(new RosterItemChangedHandler() {
      @Override
      public void onRosterItemChanged(final RosterItemChangedEvent event) {
        final RosterItem item = event.getRosterItem();
        if (item.getJID().equals(uri)) {
          setIcon(item.isAvailable(), item.getShow(), item.getStatus());
        }
      }
    });
    presenceHandler = presenceManager.addOwnPresenceChangedHandler(new OwnPresenceChangedHandler() {
      @Override
      public void onOwnPresenceChanged(final OwnPresenceChangedEvent event) {
        refresh();
      }
    });
  }

  private void clearDecorator() {
    AvatarDecoratorImpl.this.clearImage();
  }

  protected boolean isMe() {
    final XmppURI currentUserURI = xmppSession.getCurrentUserURI();
    return currentUserURI != null && currentUserURI.getJID().equals(uri);
  }

  private void refresh() {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        if (uri != null) {
          if (isMe()) {
            final Presence ownPresence = presenceManager.getOwnPresence();
            setIcon(xmppSession.isReady(), ownPresence.getShow(), ownPresence.getStatus());
          } else {
            final RosterItem item = roster.getItemByJID(uri.getJID());
            if (session.isReady() && item != null) {
              setIcon(item.isAvailable(), item.getShow(), item.getStatus());
            } else {
              clearDecorator();
            }
          }
        } else {
          clearDecorator();
        }
      }
    });
  }

  private void setIcon(final boolean available, final Show show, final String status) {
    String finalStatus = "";
    if (show == Show.dnd) {
      finalStatus = i18n.t("Busy");
      super.setImage(chatDotBusy);
    } else if (show == Show.xa) {
      finalStatus = i18n.t("Away");
      super.setImage(chatDotXA);
    } else if (show == Show.away) {
      finalStatus = i18n.t("Away");
      super.setImage(chatDotAway);
    } else if (show == Show.chat) {
      finalStatus = i18n.t("Available for chat");
      super.setImage(chatDotExtendedAway);
    } else if (available) {
      finalStatus = i18n.t("Available");
      super.setImage(chatDotAvailable);
    } else {
      clearDecorator();
    }
    super.setImageTooltip(TextUtils.empty(finalStatus) ? status : finalStatus
        + (TextUtils.empty(status) ? "" : ": " + status));
  }

  @Override
  public void setItem(final String name) {
    this.uri = (name != null ? chatClient.uriFrom(name) : null);
    refresh();
  }

  @Override
  public void setWidget(final IsWidget widget) {
    super.setWidget(widget);
    attachHandler = widget.asWidget().addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached()) {
          rosterHandler.removeHandler();
          sessionStateChangedHandler.removeHandler();
          attachHandler.removeHandler();
          presenceHandler.removeHandler();
        }
      }
    });
  }

}
