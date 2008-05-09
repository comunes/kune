package org.ourproject.kune.platf.client.extend;

import com.calclab.emite.client.components.Container;

public class HelloWorldUIModule {

    public static final String COMPONENT_HELLOWORLD = "helloworld:helloworld";

    public static HelloWorldUI getHelloWorldUI(final Container container) {
        return (HelloWorldUI) container.get(COMPONENT_HELLOWORLD);
    }

    public static void load(final Container container) {
        // final Emite emite = CoreModule.getEmite(container);
        HelloWorldUI hello = new HelloWorldUI();
        container.register(COMPONENT_HELLOWORLD, hello);
    }
}
