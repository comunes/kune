package org.ourproject.kune.platf.client.actions.ui;

import java.util.List;

import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.InputMap;
import org.ourproject.kune.platf.client.actions.KeyStroke;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractComplexGuiItem extends Composite {

    private GuiActionCollection guiItems;
    private InputMap inputMap;

    public void add(final AbstractGuiActionDescrip... descriptors) {
        for (final AbstractGuiActionDescrip descriptor : descriptors) {
            getGuiItems().add(descriptor);
        }
    }

    public void addAction(final KeyStroke key, final Action action) {
        getInputMap().put(key, action);
    }

    public void addAll(final GuiActionCollection actions) {
        getGuiItems().addAll(actions);
    }

    public void addAll(final List<AbstractGuiActionDescrip> descriptors) {
        getGuiItems().addAll(descriptors);
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
