package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;

import com.google.gwt.libideas.resources.client.ImageResource;

public class MenuDescriptor extends GuiActionDescrip {

    public static final String MENU_HIDE = "hidemenu";

    private boolean standalone;

    public MenuDescriptor() {
        this(new BaseAction(null, null, null));
    }

    public MenuDescriptor(final AbstractAction action) {
        this(NO_PARENT, action);
    }

    public MenuDescriptor(final GuiActionDescrip parent, final AbstractAction action) {
        super(action);
        setParent(parent);
        action.putValue(MENU_HIDE, false);
        standalone = false;
    }

    public MenuDescriptor(final String text) {
        this(new BaseAction(text, null, null));
    }

    public MenuDescriptor(final String text, final ImageResource icon) {
        this(new BaseAction(text, null, icon));
    }

    public MenuDescriptor(final String text, final String tooltip) {
        this(new BaseAction(text, tooltip, null));
    }

    public MenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    @Override
    public Class<?> getType() {
        return MenuDescriptor.class;
    }

    public void hide() {
        action.putValue(MENU_HIDE, !((Boolean) action.getValue(MENU_HIDE)));
    }

    public boolean isStandalone() {
        return standalone;
    }

    /**
     * Sets the standalone property (if the menu should have button (for a
     * toolbar) or is a menu independent.
     * 
     * @param standalone
     *            the new standalone
     */
    public void setStandalone(final boolean standalone) {
        this.standalone = standalone;
    }

    public void setText(final String text) {
        action.putValue(Action.NAME, text);
    }

}
