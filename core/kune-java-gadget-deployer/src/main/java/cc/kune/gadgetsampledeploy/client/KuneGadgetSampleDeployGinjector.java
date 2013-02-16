package cc.kune.gadgetsampledeploy.client;

import cc.kune.gadget.client.KuneGadgetGinInjector;
import cc.kune.gadgetsample.client.KuneGadgetSampleMainPanel;

import com.google.gwt.inject.client.GinModules;

@GinModules(KuneGadgetSampleDeployModule.class)
public interface KuneGadgetSampleDeployGinjector extends KuneGadgetGinInjector {
  KuneGadgetSampleMainPanel getMainPanel();
}
