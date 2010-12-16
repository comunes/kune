package org.ourproject.kune.platf.server.manager;

import cc.kune.domain.PropertyGroup;

public interface PropertyGroupManager extends Manager<PropertyGroup, Long> {

    PropertyGroup find(String groupName);

}
