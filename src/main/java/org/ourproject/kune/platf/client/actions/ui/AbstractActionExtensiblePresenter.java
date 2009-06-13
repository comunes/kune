package org.ourproject.kune.platf.client.actions.ui;

public abstract class AbstractActionExtensiblePresenter implements IsActionExtensible {

    public abstract void addAction(final GuiActionDescrip descriptor);

    public void addActionCollection(final GuiActionDescCollection descriptors) {
        for (final GuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

    public void addActions(final GuiActionDescrip... descriptors) {
        for (final GuiActionDescrip descriptor : descriptors) {
            addAction(descriptor);
        }
    }

}
