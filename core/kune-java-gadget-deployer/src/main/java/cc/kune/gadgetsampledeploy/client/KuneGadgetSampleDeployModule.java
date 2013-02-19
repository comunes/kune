package cc.kune.gadgetsampledeploy.client;

import cc.kune.gadgetsample.client.KuneGadgetSampleGinModule;

import com.google.inject.Singleton;
import com.thezukunft.wave.connector.Wave;
import com.thezukunft.wave.connectorimpl.WaveGINWrapper;

public class KuneGadgetSampleDeployModule extends KuneGadgetSampleGinModule {

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
