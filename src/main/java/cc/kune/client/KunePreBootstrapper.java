package cc.kune.client;

import javax.validation.constraints.NotNull;

import cc.kune.common.client.notify.NotifyUser;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Timer;
import com.gwtplatform.mvp.client.PreBootstrapper;

public class KunePreBootstrapper implements PreBootstrapper {

  private static void ensureNotUmbrellaError(@NotNull final Throwable e) {
    // https://stackoverflow.com/questions/11029002/gwt-client-umbrellaexception-get-full-error-message-in-java/11033699#11033699
    if (e instanceof UmbrellaException) {
      for (final Throwable th : ((UmbrellaException) e).getCauses()) {
        ensureNotUmbrellaError(th);
      }
    } else {
      log(e);
    }
  }

  private static String format(String ret, final Throwable thr2) {
    if (ret != "") {
      ret += "\nCaused by: ";
    }
    ret += thr2.toString();
    ret += "\n  at " + getMessage(thr2);
    return ret;
  }

  private static String getMessage(Throwable throwable) {
    // https://stackoverflow.com/questions/13663753/turn-a-stack-trace-into-a-string
    String ret = "";
    while (throwable != null) {
      if (throwable instanceof com.google.gwt.event.shared.UmbrellaException) {
        for (final Throwable thr2 : ((com.google.gwt.event.shared.UmbrellaException) throwable).getCauses()) {
          ret = format(ret, thr2);
        }
      } else if (throwable instanceof com.google.web.bindery.event.shared.UmbrellaException) {
        for (final Throwable thr2 : ((com.google.web.bindery.event.shared.UmbrellaException) throwable).getCauses()) {
          ret = format(ret, thr2);
        }
      } else {
        if (ret != "") {
          ret += "\nCaused by: ";
        }
        ret += throwable.toString();
        for (final StackTraceElement sTE : throwable.getStackTrace()) {
          ret += "\n  at " + sTE;
        }
      }
      throwable = throwable.getCause();
    }

    return ret;
  }

  private static void log(final Throwable th) {
    NotifyUser.logError(getMessage(th));
    NotifyUser.showProgress("Error");
    new Timer() {
      @Override
      public void run() {
        NotifyUser.hideProgress();
      }
    }.schedule(5000);
  }

  @Override
  public void onPreBootstrap() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(@NotNull final Throwable e) {
        ensureNotUmbrellaError(e);
      }
    });
  }
}
