package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;

public abstract class AbstractParentGuiActionDescrip extends AbstractGuiActionDescrip implements
    HasChilds {

  private final GuiActionDescCollection childs;

  public AbstractParentGuiActionDescrip(final AbstractAction action) {
    super(action);
    childs = new GuiActionDescCollection();
  }

  public void add(final GuiActionDescrip... descriptors) {
    childs.add(descriptors);
  }

  public void clear() {
    childs.clear();
  }

  public GuiActionDescCollection getChilds() {
    return childs;
  }

}
