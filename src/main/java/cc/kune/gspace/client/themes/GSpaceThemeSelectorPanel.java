package cc.kune.gspace.client.themes;

import cc.kune.common.client.actions.ui.ActionExtensibleView;
import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.bind.GuiProvider;

import com.google.inject.Inject;

public class GSpaceThemeSelectorPanel extends ActionFlowPanel implements ActionExtensibleView {

  @Inject
  public GSpaceThemeSelectorPanel(final GuiProvider guiProvider) {
    super(guiProvider);
  }

}
