package cc.kune.wave.server.kspecific;

import org.waveprotocol.box.server.persistence.file.FileUtils;
import org.waveprotocol.wave.model.id.WaveletName;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

public class WaveNameUtils {

  public static String decode(final String path) throws InvalidWaveRefException {
    final WaveRef waveRef = JavaWaverefEncoder.decodeWaveRefFromPath(path);
    final WaveletName waveName = WaveletName.of(waveRef.getWaveId(), waveRef.getWaveletId());
    return FileUtils.waveletNameToPathSegment(waveName);
  }

}
