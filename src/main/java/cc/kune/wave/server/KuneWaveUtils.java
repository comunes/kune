package cc.kune.wave.server;

import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;
import org.waveprotocol.wave.model.waveref.WaveRef;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.core.client.errors.DefaultException;
import cc.kune.domain.Content;

public class KuneWaveUtils {
  public static String getUrl(final WaveRef waveref) {
    return JavaWaverefEncoder.encodeToUriPathSegment(waveref);
  }

  public static WaveRef getWaveRef(final Content content) {
    try {
      return JavaWaverefEncoder.decodeWaveRefFromPath(String.valueOf(content.getWaveId()));
    } catch (final InvalidWaveRefException e) {
      throw new DefaultException("Error getting the wave");
    }
  }
}
