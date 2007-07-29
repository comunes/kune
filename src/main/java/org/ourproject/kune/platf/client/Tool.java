package org.ourproject.kune.platf.client;

import org.ourproject.kune.platf.client.extend.ContentProviderAsync;
import org.ourproject.kune.platf.client.extend.ViewFactory;

public class Tool {
    public final String name;
    public final ContentProviderAsync provider;
    public final ViewFactory factory;

    public Tool(String name, ContentProviderAsync provider, ViewFactory factory) {
        this.name = name;
        this.provider = provider;
        this.factory = factory;
    }

}
