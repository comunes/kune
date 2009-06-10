package org.ourproject.kune.platf.client.actions.ui;

public class PushButtonBinding extends GuiBindingAdapter {

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        return new PushButtonGui((PushButtonDescriptor) descriptor);
    }

}
