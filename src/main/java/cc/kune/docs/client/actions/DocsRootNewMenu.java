package cc.kune.docs.client.actions;

import cc.kune.gspace.client.actions.AbstractNewMenu;
import cc.kune.gspace.client.actions.NewMenusProvider;

import com.google.inject.Inject;

public class DocsRootNewMenu extends NewMenusProvider {

  @Inject
  public DocsRootNewMenu(final AbstractNewMenu menu) {
    super(menu);
  }

}
