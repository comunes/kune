package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

import com.google.inject.Inject;

public abstract class AbstractToggleShareMenuItem extends MenuItemDescriptor {

  @Inject
  public AbstractToggleShareMenuItem(final AbstractAction action) {
    super(action);
  }

  public void onPerformNewDescriptor(final ShareItemDescriptor onPerformShareItem) {
    ((AbstractToggleShareItemAction) getAction()).init(onPerformShareItem);
  }
}