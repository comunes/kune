package org.ourproject.kune.platf.client.actions.ui;

import java.util.List;

import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.InputMap;
import org.ourproject.kune.platf.client.actions.KeyStroke;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractUIElement extends Composite {

    private transient GuiActionCollection guiItems;
    private transient InputMap inputMap;

    public void add(final AbstractUIActionDescriptor descriptor) {
        getGuiItems().add(descriptor);
    }

    public void addAction(final KeyStroke key, final Action action) {
        getInputMap().put(key, action);
    }

    public void addAll(final List<AbstractUIActionDescriptor> moreDescriptor) {
        getGuiItems().addAll(moreDescriptor);
    }

    public GuiActionCollection getGuiItems() {
        if (guiItems == null) {
            guiItems = new GuiActionCollection();
        }
        return guiItems;
    }

    private InputMap getInputMap() {
        if (inputMap == null) {
            inputMap = new InputMap();
        }
        return inputMap;
    }

}
