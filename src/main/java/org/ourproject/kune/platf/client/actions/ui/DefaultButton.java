package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.Action;

import com.google.gwt.libideas.resources.client.ImageResource;

public class DefaultButton extends AbstractButton {
    public DefaultButton() {
    }

    public DefaultButton(final Action action) {
        configurePropertiesFromAction(action);
    }

    public DefaultButton(final ImageResource icon) {
        setIcon(icon);
    }

    public DefaultButton(final String text) {
        setText(text);
    }

    public DefaultButton(final String text, final ImageResource icon) {
        setText(text);
        setIcon(icon);
    }

}
