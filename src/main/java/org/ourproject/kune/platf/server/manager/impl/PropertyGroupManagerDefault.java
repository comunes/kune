package org.ourproject.kune.platf.server.manager.impl;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.manager.PropertiesManager;
import org.ourproject.kune.platf.server.manager.PropertyGroupManager;

import cc.kune.domain.Properties;
import cc.kune.domain.PropertyGroup;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class PropertyGroupManagerDefault extends DefaultManager<PropertyGroup, Long> implements PropertyGroupManager {

    private final PropertyGroup finder;
    private final PropertiesManager propertiesManager;

    @Inject
    public PropertyGroupManagerDefault(final Provider<EntityManager> provider, final PropertyGroup finder,
            final PropertiesManager propertiesManager) {
        super(provider, PropertyGroup.class);
        this.finder = finder;
        this.propertiesManager = propertiesManager;
    }

    public PropertyGroup find(final String groupName) {
        return finder.find(groupName);
    }

    @Override
    public void remove(final PropertyGroup pGroup) {
        for (final Properties properties : pGroup.getPropertiesList()) {
            propertiesManager.remove(properties);
        }
        super.remove(pGroup);
    }

}
