/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.gadgetsample.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import com.onetwopoll.gwt.framework.EventBus;
import com.thezukunft.wave.connector.ModeChangeEvent;
import com.thezukunft.wave.connector.ModeChangeEventHandler;
import com.thezukunft.wave.connector.StateUpdateEvent;
import com.thezukunft.wave.connector.StateUpdateEventHandler;
import com.thezukunft.wave.connector.Wave;

/**
 * The Class KuneGadgetSampleMainPanel is a simple panel with a button that
 * update its text when you click in the button
 */
public class KuneGadgetSampleMainPanel extends Composite {

  private static final String AVATAR_SIZE = "50px";
  private static final String LOCK = "LOCK_KEY";
  private final KuneGadgetSampleMessages messages;

  /**
   * Instantiates a new kune gadget sample main panel. We use gin to inject the
   * dependencies.
   *
   * @param eventBus
   *          the event bus
   * @param wave
   *          the wave
   */
  @Inject
  public KuneGadgetSampleMainPanel(final EventBus eventBus, final Wave wave, KuneGadgetSampleMessages gadgetMessages) {
    this.messages = gadgetMessages;
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      // We run this deferred, at the end of the gadget load
      @Override
      public void execute() {

        final Image avatar = new Image();
        avatar.setSize(AVATAR_SIZE, AVATAR_SIZE);
        avatar.setTitle(wave.getViewer().getDisplayName());
        avatar.setUrl(wave.getViewer().getThumbnailUrl());

        boolean lockStatus = getLockStatus(wave);
        final Button btn = new Button(getBtnText(lockStatus));

        btn.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
            Boolean nextStatus = !getLockStatus(wave);
            // We update the status of key LOCK to the inverse of the current
            // status
            wave.getState().submitValue(LOCK, nextStatus.toString());
          }
        });
        final VerticalPanel vp = new VerticalPanel();
        vp.add(avatar);
        vp.add(btn);
        initWidget(vp);
        eventBus.addHandler(StateUpdateEvent.TYPE, new StateUpdateEventHandler() {
          @Override
          public void onUpdate(final StateUpdateEvent event) {
            // When the status changes we just update the text of the button
            btn.setText(getBtnText(Boolean.valueOf(event.getState().get(LOCK))));
          }
        });
        eventBus.addHandler(ModeChangeEvent.TYPE, new ModeChangeEventHandler() {
          @Override
          public void onUpdate(final ModeChangeEvent event) {
            // See the modes in
            // http://www.waveprotocol.org/wave-apis/google-wave-gadgets-api/reference
            // EDIT, VIEW, PLAYBACK, etc
            if (wave.isPlayback()) {
              // Do something
            } else {
              // Do other thing
            }
          }
        });

      }

      private Boolean getLockStatus(final Wave wave) {
        return Boolean.parseBoolean(wave.getState().get(LOCK));
      }
    });
  }

  /**
   * Calculate the button text.
   *
   * @param lockStatus
   *          the lock status
   * @return the btn text
   */
  protected String getBtnText(Boolean lockStatus) {
    return lockStatus ? messages.unlock() : messages.lock();
  }
}
