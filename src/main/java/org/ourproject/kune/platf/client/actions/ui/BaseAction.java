package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;

import com.google.gwt.libideas.resources.client.ImageResource;

public class BaseAction extends AbstractAction {
    public BaseAction(final String text, final String tooltip, final ImageResource icon) {
        super();
        super.putValue(Action.NAME, text);
        super.putValue(Action.SHORT_DESCRIPTION, tooltip);
        super.putValue(Action.SMALL_ICON, icon);
    }

    public void actionPerformed(final ActionEvent actionEvent) {
        // Nothing to do
    }
}
