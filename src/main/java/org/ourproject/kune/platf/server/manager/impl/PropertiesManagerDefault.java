package org.ourproject.kune.platf.server.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.client.errors.UnknownPropertyException;
import org.ourproject.kune.platf.server.domain.Properties;
import org.ourproject.kune.platf.server.domain.Property;
import org.ourproject.kune.platf.server.domain.PropertySetted;
import org.ourproject.kune.platf.server.manager.PropertiesManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class PropertiesManagerDefault extends DefaultManager<Properties, Long> implements PropertiesManager {

    private final Property propFinder;

    @Inject
    public PropertiesManagerDefault(final Provider<EntityManager> provider, final Property propFinder) {
        super(provider, Properties.class);
        this.propFinder = propFinder;
    }

    public HashMap<String, PropertySetted> get(final Properties properties) {
        final List<Property> defProperties = propFinder.find(properties.getPgroup());
        final Map<Property, PropertySetted> propSetted = properties.getList();
        final HashMap<String, PropertySetted> result = new HashMap<String, PropertySetted>();
        for (final Property prop : defProperties) {
            final PropertySetted setted = propSetted.get(prop);
            if (setted == null) {
                result.put(prop.getName(), createPropSettedFromDefault(prop));
            } else {
                result.put(prop.getName(), setted);
            }
        }
        return result;
    }

    public PropertySetted get(final Properties properties, final String key) {
        final Property prop = getDefProp(key);
        PropertySetted propSet = properties.getList().get(prop);
        if (propSet == null) {
            propSet = createPropSettedFromDefault(prop);
        }
        return propSet;
    }

    public void set(final Properties properties, final String key, final String value) {
        final PropertySetted propertySetted = get(properties, key);
        propertySetted.setValue(value);
        if (properties.getList().get(getDefProp(key)) == null) {
            properties.getList().put(propertySetted.getProperty(), propertySetted);
        }
        persist(properties);
    }

    private PropertySetted createPropSettedFromDefault(final Property prop) {
        return new PropertySetted(prop, prop.getDefaultValue());
    }

    private Property getDefProp(final String key) {
        final Property prop = propFinder.find(key);
        if (prop == null) {
            throw new UnknownPropertyException("Unknown property: " + key);
        }
        return prop;
    }

    // @Override
    // public void remove(final Properties properties) {
    // for (final Entry<Property, PropertySetted> prop :
    // properties.getList().entrySet()) {
    // }
    // super.remove(properties);
    // }

}
