package cc.kune.docs.client.actions;

import javax.annotation.Nonnull;

import cc.kune.gspace.client.actions.AbstractNewMenu;
import cc.kune.gspace.client.actions.NewMenuProvider;

import com.google.inject.Inject;

public class DocsFolderNewMenu extends NewMenuProvider {

  @Inject
  public DocsFolderNewMenu(final @Nonnull AbstractNewMenu menu) {
    super(menu);
  }

}
