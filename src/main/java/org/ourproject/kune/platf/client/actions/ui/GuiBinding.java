package org.ourproject.kune.platf.client.actions.ui;

public interface GuiBinding {

    AbstractGuiItem create(AbstractGuiActionDescrip descriptor);

    boolean isAttachable();

}
