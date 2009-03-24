package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg.InsertImageAbstract;

import com.calclab.suco.client.ioc.Container;
import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.ioc.decorator.ProviderCollection;
import com.calclab.suco.client.ioc.decorator.Singleton;

public class InsertImageGroup extends ProviderCollection {

    public InsertImageGroup(final Container container) {
        super(container, Singleton.instance);
    }

    public void createAll() {
        for (final Provider<?> p : getProviders()) {
            p.get();
        }
    }

    public void resetAll() {
        for (final Provider<?> p : getProviders()) {
            ((InsertImageAbstract) p.get()).reset();
        }
    }
}