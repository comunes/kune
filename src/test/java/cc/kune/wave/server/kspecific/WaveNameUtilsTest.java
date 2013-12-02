package cc.kune.wave.server.kspecific;

import org.junit.Test;
import org.waveprotocol.wave.model.waveref.InvalidWaveRefException;

public class WaveNameUtilsTest {

  private void p(final String output) {
    System.out.println(output);
  }

  @Test
  public void test1() throws InvalidWaveRefException {
    p(WaveNameUtils.decode("example.com/w+cbghmi0fsmxjIS/example.com/user+test1@example.com"));
    p(WaveNameUtils.decode("example.com/w+11vlhvi417bimY/example.com/user+test1@example.com"));
    p(WaveNameUtils.decode("example.com/w+cbghmi0fsmxjIS/example.com/user+test2@example.com"));
    p(WaveNameUtils.decode("example.com/w+11vlhvi417bimY/example.com/user+test2@example.com"));
  }

}
