package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.extend.ContentProvider;
import org.ourproject.kune.platf.client.extend.ViewFactory;

public class Tool {
    public final String name;
    public final ContentProvider provider;
    public final ViewFactory factory;

    public Tool(String name, ContentProvider provider, ViewFactory factory) {
        this.name = name;
        this.provider = provider;
        this.factory = factory;
    }

}
