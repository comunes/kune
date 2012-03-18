package cc.kune.trash.server;

import com.google.inject.Binder;
import com.google.inject.Module;

public class TrashServerModule implements Module {
  @Override
  public void configure(final Binder binder) {
    binder.bind(TrashServerTool.class).asEagerSingleton();
  }
}
