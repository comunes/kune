package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;

import com.google.gwt.resources.client.ImageResource;

public class MenuDescriptor extends GuiActionDescrip {

    public static final String MENU_HIDE = "hidemenu";
    public static final String MENU_SHOW = "showmenu";
    public static final String MENU_SHOW_ID = "showmenuid";
    public static final String MENU_CLEAR = "menuclear";

    private boolean standalone;

    public MenuDescriptor() {
        this(new BaseAction(null, null));
    }

    public MenuDescriptor(final AbstractAction action) {
        this(NO_PARENT, action);
    }

    public MenuDescriptor(final GuiActionDescrip parent, final AbstractAction action) {
        super(action);
        setParent(parent);
        action.putValue(MENU_HIDE, false);
        action.putValue(MENU_SHOW, false);
        action.putValue(MENU_CLEAR, false);
        standalone = false;
    }

    public MenuDescriptor(final String text) {
        this(new BaseAction(text, null));
    }

    public MenuDescriptor(final String text, final ImageResource icon) {
        this(new BaseAction(text, null, icon));
    }

    public MenuDescriptor(final String text, final String tooltip) {
        this(new BaseAction(text, tooltip));
    }

    public MenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    public MenuDescriptor(final String text, final String tooltip, final String icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    public void clear() {
        // Action detects changes in values, then we fire a change (whatever) to
        // fire this method in the UI
        putValue(MENU_CLEAR, !((Boolean) getValue(MENU_CLEAR)));
    }

    @Override
    public Class<?> getType() {
        return MenuDescriptor.class;
    }

    public void hide() {
        putValue(MENU_HIDE, !((Boolean) getValue(MENU_HIDE)));
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
        putValue(Action.NAME, text);
    }

    /**
     * Show the menu near the Element id specified
     * 
     * @param id
     *            the element id to show menu near of it
     */
    public void show(final String id) {
        putValue(MENU_SHOW_ID, id);
        putValue(MENU_SHOW, !((Boolean) getValue(MENU_SHOW)));
    }
}
