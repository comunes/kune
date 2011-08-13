package cc.kune.gspace.client.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.state.StateManager;

import com.google.inject.Inject;

public class RefreshCurrentStateAction extends AbstractExtendedAction {

  private final StateManager stateManager;

  @Inject
  public RefreshCurrentStateAction(final StateManager stateManager) {
    this.stateManager = stateManager;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    stateManager.refreshCurrentStateWithoutCache();
  }

}