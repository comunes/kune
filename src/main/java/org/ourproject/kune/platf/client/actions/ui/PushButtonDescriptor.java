package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;

public class PushButtonDescriptor extends ButtonDescriptor {

    public static final String PUSHED = "pushed";

    protected boolean pushed = false;

    public PushButtonDescriptor(final AbstractAction action) {
        super(action);
    }

    public PushButtonDescriptor(final PushButtonDescriptor button) {
        super(button.action);
        pushed = button.pushed;
    }

    @Override
    public Class<?> getType() {
        return PushButtonDescriptor.class;
    }

    public boolean isPushed() {
        return pushed;
    }

    public void setPushed(final boolean pushed) {
        if (pushed != this.pushed) {
            this.pushed = pushed;
            action.putValue(PUSHED, this.pushed);
        }
    }
}
