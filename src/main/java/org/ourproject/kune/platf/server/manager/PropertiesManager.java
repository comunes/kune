package org.ourproject.kune.platf.server.manager;

import java.util.HashMap;

import cc.kune.domain.Properties;
import cc.kune.domain.PropertySetted;

public interface PropertiesManager extends Manager<Properties, Long> {

    HashMap<String, PropertySetted> get(Properties properties);

    PropertySetted get(Properties properties, String key);

    void set(Properties properties, String key, String value);

}
