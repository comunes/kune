package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;

import com.google.gwt.libideas.resources.client.ImageResource;

public class MenuDescriptor extends AbstractGuiActionDescrip {

    public MenuDescriptor(final AbstractAction action) {
        this(NO_PARENT, action);
    }

    public MenuDescriptor(final AbstractGuiActionDescrip parent, final AbstractAction action) {
        super(action);
        setParent(parent);
    }

    public MenuDescriptor(final String text) {
        this(new BaseAction(text, null, null));
    }

    public MenuDescriptor(final String text, final ImageResource icon) {
        this(new BaseAction(text, null, icon));
    }

    public MenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    @Override
    public Class<?> getType() {
        return MenuDescriptor.class;
    }

    public void setText(final String text) {
        action.putValue(Action.NAME, text);
    }

}
