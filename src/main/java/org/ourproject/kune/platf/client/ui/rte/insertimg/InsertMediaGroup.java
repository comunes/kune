package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstract;

import com.calclab.suco.client.ioc.Container;
import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.ioc.decorator.ProviderCollection;
import com.calclab.suco.client.ioc.decorator.Singleton;

public class InsertMediaGroup extends ProviderCollection {

    public InsertMediaGroup(final Container container) {
        super(container, Singleton.instance);
    }

    public void createAll() {
        for (final Provider<?> p : getProviders()) {
            p.get();
        }
    }

    public void resetAll() {
        for (final Provider<?> p : getProviders()) {
            ((InsertMediaAbstract) p.get()).reset();
        }
    }
}
