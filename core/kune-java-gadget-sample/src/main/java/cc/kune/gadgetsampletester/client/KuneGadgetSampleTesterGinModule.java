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

import cc.kune.gadgetsample.client.KuneGadgetSampleGinModule;
import cc.kune.gadgetsample.client.KuneGadgetSampleMainPanel;

import com.google.inject.Singleton;
import com.thezukunft.wave.connector.Wave;
import com.thezukunft.wave.connectormock.WaveMock;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneGadgetSampleTesterGinModule.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneGadgetSampleTesterGinModule extends KuneGadgetSampleGinModule {

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.gadgetsample.client.KuneGadgetSampleGinModule#configure()
   */
  @Override
  protected void configure() {
    // We use two gadgets for testing so is not a Singleton.class
    bind(KuneGadgetSampleMainPanel.class);
    // As this is just a tester, we use a mock for the wave functionality so,
    // this can run without the rest of the wave infrastructure. See
    // KuneGadgetSampleDeployModule to see how this looks like in real use
    bind(Wave.class).to(WaveMock.class).in(Singleton.class);
  };
}
