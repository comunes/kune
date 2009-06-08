package org.ourproject.kune.platf.client.actions.ui;


public class ToolbarSeparatorDescriptor extends AbstractSeparatorDescriptor {

    public enum Type {
        spacer, separator, fill
    }

    private final Type type;

    public ToolbarSeparatorDescriptor(final Type type) {
        super();
        // setParent(parent);
        this.type = type;
    }

    public Type getSeparatorType() {
        return type;
    }

    @Override
    public Class<?> getType() {
        return ToolbarSeparatorDescriptor.class;
    }

}
