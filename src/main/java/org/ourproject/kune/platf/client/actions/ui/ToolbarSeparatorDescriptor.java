package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;

public class ToolbarSeparatorDescriptor extends AbstractSeparatorDescriptor {

    public enum Type {
        spacer, separator, fill
    }

    private final Type type;
    private final View toolbar;

    public ToolbarSeparatorDescriptor(final Type type, final View toolbar) {
        super();
        this.type = type;
        this.toolbar = toolbar;
    }

    public Type getSeparatorType() {
        return type;
    }

    public View getToolbar() {
        return toolbar;
    }

    @Override
    public Class<?> getType() {
        return ToolbarSeparatorDescriptor.class;
    }

}
