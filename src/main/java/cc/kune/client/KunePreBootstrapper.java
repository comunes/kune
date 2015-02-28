package cc.kune.client;

import cc.kune.polymer.client.PolymerId;
import cc.kune.polymer.client.PolymerUtils;

import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.PreBootstrapper;

public class KunePreBootstrapper implements PreBootstrapper {

  @Override
  public void onPreBootstrap() {
    GWT.setUncaughtExceptionHandler(GWT.isProdMode() ? new DefaultUncaughtExceptionHandler()
    : new SuperDevModeUncaughtExceptionHandler());

    // Polymer preventing FOUC
    // https://www.polymer-project.org/docs/polymer/styling.html#fouc-prevention
    for (final PolymerId id : KuneBootstrapper.unresolvedIdList) {
      PolymerUtils.unresolved(id);
    }

  }

}