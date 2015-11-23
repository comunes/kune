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
package org.ourproject.massmob.client;

import org.ourproject.massmob.client.MassmobGinModule;

import com.google.inject.Singleton;
import com.thezukunft.wave.connector.Wave;
import com.thezukunft.wave.connectorimpl.WaveGINWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneGadgetSampleDeployModule.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class MassmobGadgetDeployModule extends MassmobGinModule {

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.gadgetsample.client.KuneGadgetSampleGinModule#configure()
   */
  @Override
  protected void configure() {
    super.configure();
    // As this is the real deployer, we use the real wave functionality so,
    // this can run in the wave infrastructure. KuneGadgetSampleTesterGinModule
    // is similar but with a mock that allow the testing without the wave
    // infrastructure
    bind(Wave.class).to(WaveGINWrapper.class).in(Singleton.class);
  }
}
