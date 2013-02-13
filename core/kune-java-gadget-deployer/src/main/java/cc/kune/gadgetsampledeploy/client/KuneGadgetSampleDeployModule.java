package cc.kune.gadgetsampledeploy.client;

import cc.kune.gadget.client.AbstractKuneGadgetGinModule;

import com.google.inject.Singleton;
import com.thezukunft.wave.connector.Wave;
import com.thezukunft.wave.connectorimpl.WaveGINWrapper;

public class KuneGadgetSampleDeployModule extends AbstractKuneGadgetGinModule {

  public KuneGadgetSampleDeployModule() {
  }

  @Override
  protected void configure() {
    bind(Wave.class).to(WaveGINWrapper.class).in(Singleton.class);
  }
}
