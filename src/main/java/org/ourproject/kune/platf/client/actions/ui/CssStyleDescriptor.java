package org.ourproject.kune.platf.client.actions.ui;

public class CssStyleDescriptor {

    public static CssStyleDescriptor create(final String name) {
        return new CssStyleDescriptor(name);
    }

    private final String name;

    public CssStyleDescriptor(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
