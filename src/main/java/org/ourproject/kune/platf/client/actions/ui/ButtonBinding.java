package org.ourproject.kune.platf.client.actions.ui;

public class ButtonBinding extends GuiBindingAdapter {

    @Override
    public AbstractGuiItem create(final GuiActionDescrip descriptor) {
        return new ButtonGui((ButtonDescriptor) descriptor);
    }

}
