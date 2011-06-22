package cc.kune.blogs.client.actions;

import cc.kune.gspace.client.actions.AbstractNewMenu;
import cc.kune.gspace.client.actions.NewMenusProvider;

import com.google.inject.Inject;

public class BlogsNewMenu extends NewMenusProvider {

  @Inject
  public BlogsNewMenu(final AbstractNewMenu menu) {
    super(menu);
  }

}
