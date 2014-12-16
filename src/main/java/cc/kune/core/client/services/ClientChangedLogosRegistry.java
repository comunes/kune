package cc.kune.core.client.services;

import java.util.ArrayList;

import cc.kune.core.shared.utils.ChangedLogosRegistry;

import com.google.inject.Singleton;

@Singleton
public class ClientChangedLogosRegistry implements ChangedLogosRegistry {
  private final ArrayList<String> recentlyChanged;

  public ClientChangedLogosRegistry() {
    recentlyChanged = new ArrayList<String>();
  }

  @Override
  public void add(final String token) {
    recentlyChanged.add(token);
  }

  @Override
  public boolean isRecentlyChanged(final String token) {
    return recentlyChanged.contains(token);
  }
}
