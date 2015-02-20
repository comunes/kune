package cc.kune.client;

import com.google.gwt.core.client.GWT;
import com.gwtplatform.mvp.client.PreBootstrapper;

public class KunePreBootstrapper implements PreBootstrapper {

  @Override
  public void onPreBootstrap() {
    GWT.setUncaughtExceptionHandler(GWT.isProdMode() ? new DefaultUncaughtExceptionHandler()
    : new SuperDevModeUncaughtExceptionHandler());
  }
}
