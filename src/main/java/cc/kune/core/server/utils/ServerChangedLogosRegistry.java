package cc.kune.core.server.utils;

import cc.kune.core.server.persist.CachedCollection;
import cc.kune.core.shared.utils.ChangedLogosRegistry;

import com.google.inject.Singleton;

@Singleton
public class ServerChangedLogosRegistry implements ChangedLogosRegistry {

  private final CachedCollection<String, Boolean> recentlyChanged;

  public ServerChangedLogosRegistry() {
    recentlyChanged = new CachedCollection<String, Boolean>(100);
  }

  @Override
  public void add(final String token) {
    recentlyChanged.put(token, Boolean.TRUE);
  }

  @Override
  public boolean isRecentlyChanged(final String token) {
    return recentlyChanged.containsKey(token);
  }

}
