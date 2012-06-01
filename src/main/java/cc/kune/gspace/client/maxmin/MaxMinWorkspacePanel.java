package cc.kune.gspace.client.maxmin;

import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.gspace.client.maxmin.MaxMinWorkspacePresenter.MaxMinWorkspaceView;
import cc.kune.wave.client.kspecific.WaveClientProvider;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class MaxMinWorkspacePanel implements MaxMinWorkspaceView {

  private final GSpaceArmor gsArmor;
  private final WaveClientProvider waveClient;

  @Inject
  public MaxMinWorkspacePanel(final GSpaceArmor gsArmor, final WaveClientProvider waveClient) {
    this.gsArmor = gsArmor;
    this.waveClient = waveClient;
  }

  @Override
  public void addToSlot(final Object slot, final Widget content) {
  }

  @Override
  public Widget asWidget() {
    return null;
  }

  @Override
  public void removeFromSlot(final Object slot, final Widget content) {
  }

  @Override
  public void setInSlot(final Object slot, final Widget content) {
  }

  @Override
  public void setMaximized(final boolean maximized) {
    gsArmor.setMaximized(maximized);
    waveClient.get().setMaximized(maximized);
  }
}
