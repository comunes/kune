package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;

public abstract class AbstractActionExtensiblePresenter implements IsActionExtensible {

    @Override
    public abstract void addAction(final AbstractGuiActionDescrip descriptor);

    public void addActionCollection(final GuiActionDescCollection descriptors) {
        for (final AbstractGuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    @Override
    public void addActions(final AbstractGuiActionDescrip... descriptors) {
        for (final AbstractGuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

}
