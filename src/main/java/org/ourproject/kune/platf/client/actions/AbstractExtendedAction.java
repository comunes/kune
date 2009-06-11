package org.ourproject.kune.platf.client.actions;


import com.google.gwt.libideas.resources.client.ImageResource;

public abstract class AbstractExtendedAction extends AbstractAction {
    public static final String NO_TEXT = null;
    public static final ImageResource NO_ICON = null;

    public AbstractExtendedAction() {
        super();
    }

    public AbstractExtendedAction(final String text) {
        this(text, null, null);
    }

    public AbstractExtendedAction(final String text, final ImageResource icon) {
        this(text, null, icon);
    }

    public AbstractExtendedAction(final String text, final String tooltip, final ImageResource icon) {
        super();
        super.putValue(Action.NAME, text);
        super.putValue(Action.SHORT_DESCRIPTION, tooltip);
        super.putValue(Action.SMALL_ICON, icon);
    }
}