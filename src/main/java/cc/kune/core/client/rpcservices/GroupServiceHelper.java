package cc.kune.core.client.rpcservices;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupServiceHelper {

  @SuppressWarnings("unused")
  private final Provider<GroupServiceAsync> groupService;

  @Inject
  public GroupServiceHelper(final Provider<GroupServiceAsync> groupService) {
    this.groupService = groupService;
  }
}
