package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;

public class ToolbarSeparatorDescriptor extends AbstractSeparatorDescriptor {

    public enum Type {
        spacer, separator, fill
    }

    private transient final Type type;

    public ToolbarSeparatorDescriptor(final Type type) {
        super();
        // setParent(parent);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public View getView() {
        return null;
    }

}
