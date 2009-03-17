package org.ourproject.kune.platf.client.ui.rte.insertspecialchar;

import com.calclab.suco.client.ioc.Container;
import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.ioc.decorator.ProviderCollection;
import com.calclab.suco.client.ioc.decorator.Singleton;

public class InsertSpecialCharGroup extends ProviderCollection {

    public InsertSpecialCharGroup(final Container container) {
        super(container, Singleton.instance);
    }

    public void createAll() {
        for (final Provider<?> p : getProviders()) {
            p.get();
        }
    }
}