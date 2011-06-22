package cc.kune.docs.client.actions;

import cc.kune.gspace.client.actions.AbstractNewMenu;
import cc.kune.gspace.client.actions.NewMenusProvider;

import com.google.inject.Inject;

public class DocsFolderNewMenu extends NewMenusProvider {

  @Inject
  public DocsFolderNewMenu(final AbstractNewMenu menu) {
    super(menu);
  }

}
