package cc.kune.common.client.actions.ui;

import com.google.inject.Inject;

public class GuiProviderInstance {

  @Inject
  private static GuiProvider guiProvider;

  public static GuiProvider get() {
    return guiProvider;
  }
}
