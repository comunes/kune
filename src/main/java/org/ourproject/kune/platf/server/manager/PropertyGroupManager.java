package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.PropertyGroup;

public interface PropertyGroupManager extends Manager<PropertyGroup, Long> {

    PropertyGroup find(String groupName);

}
