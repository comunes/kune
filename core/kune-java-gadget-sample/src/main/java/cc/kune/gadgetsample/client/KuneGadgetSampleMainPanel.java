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
import com.google.inject.Inject;
import com.onetwopoll.gwt.framework.EventBus;
import com.thezukunft.wave.connector.ModeChangeEvent;
import com.thezukunft.wave.connector.ModeChangeEventHandler;
import com.thezukunft.wave.connector.StateUpdateEvent;
import com.thezukunft.wave.connector.StateUpdateEventHandler;
import com.thezukunft.wave.connector.Wave;

public class KuneGadgetSampleMainPanel extends Composite {

  private static final String LOCK = "LOCK_KEY";

  @Inject
  public KuneGadgetSampleMainPanel(final EventBus eventBus, final Wave wave) {
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        boolean lockStatus = getLockStatus(wave);
        final Button btn = new Button(getBtnText(lockStatus));
        btn.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(ClickEvent event) {
            Boolean nextStatus = !getLockStatus(wave);
            wave.getState().submitValue(LOCK, nextStatus.toString());
          }
        });
        initWidget(btn);
        eventBus.addHandler(StateUpdateEvent.TYPE, new StateUpdateEventHandler() {
          @Override
          public void onUpdate(final StateUpdateEvent event) {
            btn.setText(getBtnText(Boolean.valueOf(event.getState().get(LOCK))));
          }
        });
        eventBus.addHandler(ModeChangeEvent.TYPE, new ModeChangeEventHandler() {
          @Override
          public void onUpdate(final ModeChangeEvent event) {
            // FIXME
          }
        });

      }

      private Boolean getLockStatus(final Wave wave) {
        return Boolean.parseBoolean(wave.getState().get(LOCK));
      }
    });
  }

  protected String getBtnText(Boolean lockStatus) {
    return lockStatus? "Unlock": "Lock";
  }
}
