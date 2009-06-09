package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;

import com.google.gwt.libideas.resources.client.ImageResource;

public abstract class AbstractRTEAction extends AbstractAction {
    public static final String NO_TEXT = null;
    public static final ImageResource NO_ICON = null;

    public AbstractRTEAction() {
        super();
    }

    public AbstractRTEAction(final String text) {
        this(text, null, null);
    }

    public AbstractRTEAction(final String text, final ImageResource icon) {
        this(text, null, icon);
    }

    public AbstractRTEAction(final String text, final String tooltip, final ImageResource icon) {
        super();
        super.putValue(Action.NAME, text);
        super.putValue(Action.SHORT_DESCRIPTION, tooltip);
        super.putValue(Action.SMALL_ICON, icon);
    }
}