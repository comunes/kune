package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.ActionEvent;

public class AbstractSeparator extends AbstractUIActionDescriptor {

    public AbstractSeparator() {
        super(new AbstractAction() {
            public void actionPerformed(final ActionEvent event) {
                // No action
            }
        });
    }

}
