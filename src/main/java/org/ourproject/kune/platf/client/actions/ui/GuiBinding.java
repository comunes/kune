package org.ourproject.kune.platf.client.actions.ui;

public interface GuiBinding {

    AbstractGuiItem create(GuiActionDescrip descriptor);

    boolean isAttachable();

}
