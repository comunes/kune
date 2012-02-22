package cc.kune.events.client.actions;

import cc.kune.gspace.client.actions.AbstractStandaloneMenu;
import cc.kune.gspace.client.actions.StandaloneMenuProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CalendarOnOverMenu extends StandaloneMenuProvider {

  @Inject
  public CalendarOnOverMenu(final AbstractStandaloneMenu menu) {
    super(menu);
  }
}
