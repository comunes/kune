package cc.kune.wiki.client.actions;

import cc.kune.gspace.client.actions.AbstractNewMenu;
import cc.kune.gspace.client.actions.NewMenuProvider;

import com.google.inject.Inject;

public class WikiFolderNewMenu extends NewMenuProvider {

  @Inject
  public WikiFolderNewMenu(final AbstractNewMenu menu) {
    super(menu);
  }

}
