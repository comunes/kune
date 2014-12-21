package cc.kune.core.server.utils;

import java.util.Date;

import cc.kune.core.server.persist.CachedCollection;
import cc.kune.core.shared.utils.ChangedLogosRegistry;

import com.google.inject.Singleton;

@Singleton
public class ServerChangedLogosRegistry implements ChangedLogosRegistry {

  private final CachedCollection<String, String> recentlyChanged;

  public ServerChangedLogosRegistry() {
    recentlyChanged = new CachedCollection<String, String>(100);
  }

  @Override
  public void add(final String token) {
    recentlyChanged.put(token, ((Long) new Date().getTime()).toString());
  }

  @Override
  public String getLastModified(final String token) {
    return recentlyChanged.get(token);
  }

  @Override
  public boolean isRecentlyChanged(final String token) {
    return recentlyChanged.containsKey(token);
  }

}
