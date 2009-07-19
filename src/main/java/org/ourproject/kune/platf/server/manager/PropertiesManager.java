package org.ourproject.kune.platf.server.manager;

import java.util.HashMap;

import org.ourproject.kune.platf.server.domain.Properties;
import org.ourproject.kune.platf.server.domain.PropertySetted;

public interface PropertiesManager extends Manager<Properties, Long> {

    HashMap<String, PropertySetted> get(Properties properties);

    PropertySetted get(Properties properties, String key);

    void set(Properties properties, String key, String value);

}
