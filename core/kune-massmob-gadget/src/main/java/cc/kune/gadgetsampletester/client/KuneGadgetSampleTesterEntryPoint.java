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
package cc.kune.gadgetsampletester.client;

import org.ourproject.massmob.client.ui.MassmobMainPanel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.RootPanel;
import com.thezukunft.wave.connector.ModeChangeEvent;
import com.thezukunft.wave.connectormock.WaveMock;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneGadgetSampleTesterEntryPoint.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneGadgetSampleTesterEntryPoint implements EntryPoint {

  /*
   * (non-Javadoc)
   *
   * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
   */
  @Override
  public void onModuleLoad() {
    final KuneGadgetSampleGinInjector gin = GWT.create(KuneGadgetSampleGinInjector.class);

    final WaveMock waveMock = (WaveMock) gin.getWave();

    // We initialize some participants
    waveMock.initRandomParticipants();

    // We have to create the gadget using gin so it can use injection of its
    // dependencies (evenBus, etc)

    final MassmobMainPanel gadget = gin.getWaveGadget();
    final MassmobMainPanel gadget2 = gin.getWaveGadget();

    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        RootPanel.get().add(gadget);
        RootPanel.get().add(gadget2);
        gin.getEventBus().fireEvent(new ModeChangeEvent(0));
      }
    });
  }

}
