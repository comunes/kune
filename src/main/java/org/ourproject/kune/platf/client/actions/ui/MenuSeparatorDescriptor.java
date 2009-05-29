package org.ourproject.kune.platf.client.actions.ui;

public class MenuSeparatorDescriptor extends AbstractSeparatorDescriptor {

    public MenuSeparatorDescriptor(final MenuDescriptor parent) {
        super();
        setParent(parent);
    }

    @Override
    public Class<?> getType() {
        return MenuSeparatorDescriptor.class;
    }
}