package org.ourproject.kune.platf.client.actions.ui;

public class ToolbarSeparator extends AbstractSeparator {

    public enum Type {
        spacer, separator, fill
    }

    private transient final Type type;

    public ToolbarSeparator(final Type type) {
        super();
        this.type = type;
    }

    public Type getType() {
        return type;
    }

}
