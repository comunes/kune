package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.resources.client.ImageResource;

public class SubMenuDescriptor extends MenuDescriptor {

    public SubMenuDescriptor() {
        this(new BaseAction(null, null));
    }

    public SubMenuDescriptor(final AbstractAction action) {
        this(NO_PARENT, action);
    }

    public SubMenuDescriptor(final AbstractGuiActionDescrip parent, final AbstractAction action) {
        super(action);
        setParent(parent);
        putValue(MENU_HIDE, false);
        putValue(MENU_SHOW, false);
        putValue(MENU_CLEAR, false);
        putValue(MENU_STANDALONE, false);
    }

    public SubMenuDescriptor(final String text) {
        this(new BaseAction(text, null));
    }

    public SubMenuDescriptor(final String text, final ImageResource icon) {
        this(new BaseAction(text, null, icon));
    }

    public SubMenuDescriptor(final String text, final String tooltip) {
        this(new BaseAction(text, tooltip));
    }

    public SubMenuDescriptor(final String text, final String tooltip, final ImageResource icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    public SubMenuDescriptor(final String text, final String tooltip, final String icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    @Override
    public Class<?> getType() {
        return SubMenuDescriptor.class;
    }

}
