package org.ourproject.kune.platf.client.actions.ui;

import java.util.List;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.InputMap;
import org.ourproject.kune.platf.client.actions.KeyStroke;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractComplexGuiItem extends Composite {

    private GuiActionDescCollection guiItems;
    private InputMap inputMap;

    public void add(final GuiActionDescrip... descriptors) {
        for (final GuiActionDescrip descriptor : descriptors) {
            getGuiItems().add(descriptor);
        }
    }

    public void addAll(final GuiActionDescCollection items) {
        getGuiItems().addAll(items);
    }

    public void addAll(final List<GuiActionDescrip> items) {
        getGuiItems().addAll(items);
    }

    public void addShortcut(final KeyStroke key, final AbstractAction action) {
        getInputMap().put(key, action);
    }

    public AbstractAction getAction(final Event event) {
        return getInputMap().get(KeyStroke.getKeyStrokeForEvent(event));
    }

    public GuiActionDescCollection getGuiItems() {
        if (guiItems == null) {
            guiItems = new GuiActionDescCollection();
        }
        return guiItems;
    }

    public void setInputMap(final InputMap inputMap) {
        this.inputMap = inputMap;
    }

    private InputMap getInputMap() {
        if (inputMap == null) {
            inputMap = new InputMap();
        }
        return inputMap;
    }
}
