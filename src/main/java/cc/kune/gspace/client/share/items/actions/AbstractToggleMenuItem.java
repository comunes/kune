package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

import com.google.inject.Inject;

public abstract class AbstractToggleMenuItem extends MenuItemDescriptor {

  @Inject
  public AbstractToggleMenuItem(final AbstractAction action) {
    super(action);
  }

  public void setComplementary(final ShareItemDescriptor complementaryItem) {
    ((AbstractToggleAction) getAction()).init(complementaryItem);
  }
}