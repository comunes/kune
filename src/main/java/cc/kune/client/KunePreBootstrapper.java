package cc.kune.client;

import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.wave.client.WebClient.ErrorHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Timer;
import com.gwtplatform.mvp.client.PreBootstrapper;

public class KunePreBootstrapper implements PreBootstrapper {

  @Override
  public void onPreBootstrap() {
    GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(final Throwable excep) {
        final Throwable unwrapped = unwrap(excep);

        Log.error("Error in 'onModuleLoad()' method", unwrapped);

        ErrorHandler.getStackTraceAsync(unwrapped, new Accessor<SafeHtml>() {
          @Override
          public void use(final SafeHtml stack) {
            final String message = stack.asString().replace("<br>", "\n");
            NotifyUser.logError(message);
            NotifyUser.showProgress("Error");
            new Timer() {
              @Override
              public void run() {
                NotifyUser.hideProgress();
              }
            }.schedule(5000);
          }
        });
      }

      public Throwable unwrap(final Throwable e) {
        if (e instanceof UmbrellaException) {
          final UmbrellaException ue = (UmbrellaException) e;
          if (ue.getCauses().size() == 1) {
            return unwrap(ue.getCauses().iterator().next());
          }
        }
        return e;
      }
    });
  }
}
