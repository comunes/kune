package org.ourproject.kune.platf.client.actions.ui;

public class PushButtonBinding extends GuiBindingAdapter {

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        return new ButtonGui((ButtonDescriptor) descriptor, true);
    }

}
