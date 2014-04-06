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

import java.io.Closeable;
import java.io.IOException;

import org.waveprotocol.box.server.waveserver.PerUserWaveViewHandler;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.id.WaveletName;
import org.waveprotocol.wave.model.wave.ParticipantId;

import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CustomPerUserWaveViewHandler implements PerUserWaveViewHandler, Closeable {

  @Inject
  private static CustomPerUserWaveViewHandlerImpl searcher;

  @Override
  public void close() throws IOException {
    searcher.close();
  }

  @Override
  public ListenableFuture<Void> onParticipantAdded(final WaveletName wavelet, final ParticipantId partId) {
    return searcher.onParticipantAdded(wavelet, partId);
  }

  @Override
  public ListenableFuture<Void> onParticipantRemoved(final WaveletName wavelet,
      final ParticipantId partId) {
    return searcher.onParticipantRemoved(wavelet, partId);
  }

  @Override
  public ListenableFuture<Void> onWaveInit(final WaveletName wavelet) {
    return searcher.onWaveInit(wavelet);
  }

  @Override
  public Multimap<WaveId, WaveletId> retrievePerUserWaveView(final ParticipantId partId) {
    return searcher.retrievePerUserWaveView(partId);
  }

}
