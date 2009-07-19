package org.ourproject.kune.platf.server.manager.impl;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.PropertySubgroup;
import org.ourproject.kune.platf.server.manager.PropertySubgroupManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class PropertySubgroupManagerDefault extends DefaultManager<PropertySubgroup, Long> implements
        PropertySubgroupManager {

    @Inject
    public PropertySubgroupManagerDefault(final Provider<EntityManager> provider) {
        super(provider, PropertySubgroup.class);
    }

}
