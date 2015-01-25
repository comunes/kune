package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;

public abstract class GenericMenuDescriptor extends AbstractParentGuiActionDescrip {

  public static final String MENU_CLEAR = "menuclear";

  public GenericMenuDescriptor(final AbstractAction action) {
    super(action);
  }

}
