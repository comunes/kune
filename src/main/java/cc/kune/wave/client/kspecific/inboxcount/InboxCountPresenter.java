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
package cc.kune.wave.client.kspecific.inboxcount;

import java.util.HashMap;
import java.util.Map;

import org.waveprotocol.box.webclient.search.Digest;
import org.waveprotocol.box.webclient.search.Search;
import org.waveprotocol.box.webclient.search.Search.Listener;

import cc.kune.core.client.events.InboxUnreadUpdatedEvent;
import cc.kune.core.client.events.NewUserRegisteredEvent;
import cc.kune.core.client.events.SndClickEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent;
import cc.kune.core.client.events.UserSignInOrSignOutEvent.UserSignInOrSignOutHandler;
import cc.kune.core.client.state.Session;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class InboxCountPresenter.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class InboxCountPresenter {

  /**
   * The Interface InboxCountView.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface InboxCountView {

    /**
     * Blink.
     */
    void blink();

    /**
     * Sets the total.
     *
     * @param total the new total
     */
    void setTotal(int total);

    /**
     * Show count.
     *
     * @param show the show
     */
    void showCount(boolean show);

  }

  /** The current total. */
  private int currentTotal;
  
  /** The diggests. */
  private final Map<Integer, Integer> diggests;
  
  /** The event bus. */
  private final EventBus eventBus;
  // protected SimpleSearch search;
  /** The search listener. */
  private final Listener searchListener;
  
  /** The session. */
  private final Session session;
  // private final Session session;
  /** The update timer. */
  private final Timer updateTimer;
  
  /** The view. */
  private final InboxCountView view;

  /**
   * Instantiates a new inbox count presenter.
   *
   * @param view the view
   * @param session the session
   * @param eventBus the event bus
   */
  @Inject
  public InboxCountPresenter(final InboxCountView view, final Session session, final EventBus eventBus) {
    this.view = view;
    this.session = session;
    this.eventBus = eventBus;
    // this.session = session;
    diggests = new HashMap<Integer, Integer>();
    currentTotal = Search.UNKNOWN_SIZE;

    updateTimer = new Timer() {
      @Override
      public void run() {
        update();
      }
    };

    searchListener = new Listener() {
      private void log(final int index, final Digest diggest, final String msg) {
        // Log.info(msg + index + " - " + diggest.getTitle() + ": unread: " +
        // diggest.getUnreadCount());
      }

      @Override
      public void onDigestAdded(final int index, final Digest diggest) {
        log(index, diggest, "Diggest added: ");
        updateDigest(index, diggest);
        shouldUpdate();
      }

      @Override
      public void onDigestReady(final int index, final Digest diggest) {
        log(index, diggest, "Diggest ready: ");
        updateDigest(index, diggest);
        shouldUpdate();
      }

      @Override
      public void onDigestRemoved(final int index, final Digest diggest) {
        log(index, diggest, "Diggest removed: ");
        updateDigest(index, diggest);
        shouldUpdate();
      }

      @Override
      public void onStateChanged() {
        shouldUpdate();
        // diggests.clear();
      }

      @Override
      public void onTotalChanged(final int total) {
        shouldUpdate();
      }

      private void shouldUpdate() {
        updateTimer.schedule(3000);
      }

      private void updateDigest(final int index, final Digest diggest) {
        diggests.put(Integer.valueOf(index), Integer.valueOf(diggest.getUnreadCount()));
      }
    };

    session.onUserSignInOrSignOut(true, new UserSignInOrSignOutHandler() {
      @Override
      public void onUserSignInOrSignOut(final UserSignInOrSignOutEvent event) {
        view.showCount(event.isLogged());
      }
    });

    eventBus.addHandler(NewUserRegisteredEvent.getType(),
        new NewUserRegisteredEvent.NewUserRegisteredHandler() {
          @Override
          public void onNewUserRegistered(final NewUserRegisteredEvent event) {
            sendNoticeToUser();
          }
        });

  }

  /**
   * Gets the search listener.
   *
   * @return the search listener
   */
  public Listener getSearchListener() {
    return searchListener;
  }

  /**
   * Send notice to user.
   */
  private void sendNoticeToUser() {
    view.blink();
    SndClickEvent.fire(eventBus);
  }

  /**
   * Sets the total.
   *
   * @param total the new total
   */
  private void setTotal(final int total) {
    view.setTotal(total);
    final boolean show = session.isLogged() && total != Search.UNKNOWN_SIZE && total > 0;
    view.showCount(show);
    final boolean greater = total > currentTotal;
    if (show && greater) {
      sendNoticeToUser();
    }
    InboxUnreadUpdatedEvent.fire(eventBus, total, greater);
    currentTotal = total;
  }

  /**
   * Update.
   */
  private void update() {
    int total = 0;
    for (final Integer unread : diggests.values()) {
      total += unread;
    }
    setTotal(total);
  }
}
