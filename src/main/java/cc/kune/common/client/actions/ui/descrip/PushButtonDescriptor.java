package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;

public class PushButtonDescriptor extends ButtonDescriptor {

    public static final String PUSHED = "pushed";

    public PushButtonDescriptor(final AbstractAction action) {
        super(action);
        setPushedImpl(false);
    }

    public PushButtonDescriptor(final PushButtonDescriptor button) {
        this(button.getAction());
    }

    @Override
    public Class<?> getType() {
        return PushButtonDescriptor.class;
    }

    public boolean isPushed() {
        return (Boolean) getValue(PUSHED);
    }

    public void setPushed(final boolean pushed) {
        setPushedImpl(pushed);
    }

    private void setPushedImpl(final boolean pushed) {
        putValue(PUSHED, pushed);
    }
}
