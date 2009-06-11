package org.ourproject.kune.platf.client.services;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.ioc.module.AbstractModule;

public abstract class AbstractExtendedModule extends AbstractModule {

    /**
     * Get a instance of the specified component key
     * 
     * @param <T>
     *            The component key
     * @param componentType
     *            The component key
     * @return The component instance
     */
    protected <T> T i(final Class<T> componentType) { // NOPMD by vjrj on
                                                      // 11/06/09 18:34
        return $(componentType);
    }

    /**
     * Get a provider of the specified component key
     * 
     * @param <T>
     *            The component key
     * @param componentType
     *            The component key
     * @return The provider of that component key
     */
    protected <T> Provider<T> p(final Class<T> componentType) {// NOPMD by vjrj
                                                               // on 11/06/09
                                                               // 18:34
        return $$(componentType);
    }

}
