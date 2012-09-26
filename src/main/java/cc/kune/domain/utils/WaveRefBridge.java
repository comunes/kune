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

package cc.kune.domain.utils;

import org.hibernate.search.bridge.TwoWayStringBridge;
import org.waveprotocol.wave.model.id.WaveId;
import org.waveprotocol.wave.model.id.WaveletId;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.domain.WaveRefKey;

public class WaveRefBridge implements TwoWayStringBridge {

  @Override
  public String objectToString(final Object object) {
    if (object == null) {
      return null;
    }
    final WaveRefKey key = (WaveRefKey) object;
    final WaveRef waveRef = WaveRef.of(WaveId.of(key.getDomain(), key.getWaveId()),
        WaveletId.of(key.getDomain(), key.getWaveletId()));
    return JavaWaverefEncoder.encodeToUriPathSegment(waveRef);
  }

  @Override
  public WaveRefKey stringToObject(final String stringValue) {
    if (stringValue == null || stringValue.isEmpty()) {
      return null;
    }
    WaveRef waveRef;
    try {
      waveRef = JavaWaverefEncoder.decodeWaveRefFromPath(stringValue);
      return WaveRefKey.of(waveRef.getWaveId().getDomain(), waveRef.getWaveId().serialise(),
          waveRef.getWaveletId().serialise());
    } catch (final InvalidWaveRefException e) {
      e.printStackTrace();
      return null;
    }
  }
}