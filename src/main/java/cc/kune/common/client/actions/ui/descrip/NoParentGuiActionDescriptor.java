package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;

/**
 * The Class NoParentGuiActionDescriptor used to describe items with no parents.
 */
public class NoParentGuiActionDescriptor extends AbstractGuiActionDescrip {

    public NoParentGuiActionDescriptor() {
        super(AbstractAction.NO_ACTION);
    }

    @Override
    public Class<?> getType() {
        return NoParentGuiActionDescriptor.class;
    }
}
