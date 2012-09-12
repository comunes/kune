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

package cc.kune.wave.server.kspecific;

import org.waveprotocol.box.server.persistence.file.FileUtils;
import org.waveprotocol.wave.model.id.WaveletName;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

public class WaveCommandLineUtils {

  public static void main(String[] args) {
    try {
      WaveRef waveRef = JavaWaverefEncoder.decodeWaveRefFromPath(args[1]);
      WaveletName waveName = WaveletName.of(waveRef.getWaveId(), waveRef.getWaveletId());
      String name = FileUtils.waveletNameToPathSegment(waveName);
      System.out.println(name);
    } catch (InvalidWaveRefException e) {
      e.printStackTrace();
    }
  }
}
