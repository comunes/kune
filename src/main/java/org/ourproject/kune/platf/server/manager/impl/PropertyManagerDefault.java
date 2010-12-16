package org.ourproject.kune.platf.server.manager.impl;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.manager.PropertyManager;

import cc.kune.domain.Property;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class PropertyManagerDefault extends DefaultManager<Property, Long> implements PropertyManager {

    @Inject
    public PropertyManagerDefault(final Provider<EntityManager> provider) {
        super(provider, Property.class);
    }

}
