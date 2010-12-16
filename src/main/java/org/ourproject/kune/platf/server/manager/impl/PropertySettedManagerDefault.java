package org.ourproject.kune.platf.server.manager.impl;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.manager.PropertySettedManager;

import cc.kune.domain.PropertySetted;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class PropertySettedManagerDefault extends DefaultManager<PropertySetted, Long> implements PropertySettedManager {

    @Inject
    public PropertySettedManagerDefault(final Provider<EntityManager> provider) {
        super(provider, PropertySetted.class);
    }

}
