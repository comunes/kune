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
package cc.kune.wave.server.search;

import org.waveprotocol.box.server.waveserver.AbstractWaveIndexer;
import org.waveprotocol.box.server.waveserver.IndexException;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewBus;
import org.waveprotocol.box.server.waveserver.PerUserWaveViewBus.Listener;
import org.waveprotocol.box.server.waveserver.WaveMap;
import org.waveprotocol.box.server.waveserver.WaveletProvider;
import org.waveprotocol.box.server.waveserver.WaveletStateException;
import org.waveprotocol.wave.model.id.WaveletName;

import com.google.inject.Inject;

public class CustomWaveIndexerImpl extends AbstractWaveIndexer {

  private final PerUserWaveViewBus.Listener listener;

  @Inject
  public CustomWaveIndexerImpl(final WaveMap waveMap, final WaveletProvider waveletProvider,
      final Listener listener) {
    super(waveMap, waveletProvider);
    this.listener = listener;
  }

  @Override
  protected void postIndexHook() {
    try {
      getWaveMap().unloadAllWavelets();
    } catch (final WaveletStateException e) {
      throw new IndexException("Problem encountered while cleaning up", e);
    }
  }

  @Override
  protected void processWavelet(final WaveletName waveletName) {
    listener.onWaveInit(waveletName);
  }
}
