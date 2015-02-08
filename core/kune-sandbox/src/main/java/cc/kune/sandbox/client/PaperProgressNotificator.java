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

package cc.kune.sandbox.client;

import br.com.rpa.client._paperelements.PaperProgress;
import cc.kune.common.client.notify.ProgressHideEvent;
import cc.kune.common.client.notify.ProgressShowEvent;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class PaperProgressNotificator {

  private static final int DELAY_MILLIS = 1000;
  private final PaperProgress progress;
  private final Timer timer;

  @Inject
  public PaperProgressNotificator(final EventBus eventBus) {
    progress = PaperProgress.wrap("paper_progress");
    timer = new Timer() {
      @Override
      public void run() {
        progress.setVisible(false);
      }
    };
    eventBus.addHandler(ProgressHideEvent.getType(), new ProgressHideEvent.ProgressHideHandler() {

      @Override
      public void onProgressHide(final ProgressHideEvent event) {
        progress.setValue(100);
        progress.setIndeterminate(false);
        timer.schedule(DELAY_MILLIS);
      }
    });

    eventBus.addHandler(ProgressShowEvent.getType(), new ProgressShowEvent.ProgressShowHandler() {

      @Override
      public void onProgressShow(final ProgressShowEvent event) {
        progress.setVisible(true);
        progress.setValue(0);
        progress.setIndeterminate(true);
      }
    });
  }
}
