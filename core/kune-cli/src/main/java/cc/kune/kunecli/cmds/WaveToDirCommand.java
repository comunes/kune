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

package cc.kune.kunecli.cmds;

import org.naturalcli.Command;
import org.naturalcli.ExecutionException;
import org.naturalcli.ICommandExecutor;
import org.naturalcli.InvalidSyntaxException;
import org.naturalcli.ParseResult;
import org.waveprotocol.box.server.persistence.file.FileUtils;
import org.waveprotocol.wave.model.id.WaveletName;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

public class WaveToDirCommand extends Command {

  public static class WaveToDirICommand implements ICommandExecutor {

    @Override
    public void execute(final ParseResult result) throws ExecutionException {
      final String path = result.getParameterValue(0).toString();
      try {
        final WaveRef waveRef = JavaWaverefEncoder.decodeWaveRefFromPath(path);
        final WaveletName waveName = WaveletName.of(waveRef.getWaveId(), waveRef.getWaveletId());
        System.out.println(
            "Wavelet of " + path + " to path " + FileUtils.waveletNameToPathSegment(waveName));
      } catch (final InvalidWaveRefException e) {
        e.printStackTrace();
      }

    }
  }

  public WaveToDirCommand() throws InvalidSyntaxException {
    super("waveletToDir [<waveletName:string>]",
        "Converts a wavelet like 'example.com/w+cbghmi0fsmxjIS/example.com/user+test1@example.com' to his filesystem directory name.",
        new WaveToDirICommand());
  }
}
