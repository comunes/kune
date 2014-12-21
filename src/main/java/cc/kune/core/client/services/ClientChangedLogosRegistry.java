package cc.kune.core.client.services;

import java.util.Date;
import java.util.HashMap;

import cc.kune.core.shared.utils.ChangedLogosRegistry;

import com.google.inject.Singleton;

@Singleton
public class ClientChangedLogosRegistry implements ChangedLogosRegistry {
  private final HashMap<String, String> recentlyChanged;

  public ClientChangedLogosRegistry() {
    recentlyChanged = new HashMap<String, String>();
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
