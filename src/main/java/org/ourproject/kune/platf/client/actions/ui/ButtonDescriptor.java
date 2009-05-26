package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;

public class ButtonDescriptor extends AbstractUIActionDescriptor {

    public ButtonDescriptor(final AbstractAction action) {
        super(action);
    }

    @Override
    public View getView() {
        if (view == null) {
            final DefaultButton defaultButton = new DefaultButton(this);
            view = defaultButton;
        }
        return view;
    }
}
