package org.ourproject.kune.platf.server.manager.impl;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Property;
import org.ourproject.kune.platf.server.manager.PropertyManager;

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
