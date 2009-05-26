package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.ActionEvent;

public abstract class AbstractSeparatorDescriptor extends AbstractUIActionDescriptor {

    public AbstractSeparatorDescriptor() {
        super(new AbstractAction() {
            public void actionPerformed(final ActionEvent event) {
                // No action
            }
        });
    }

}
