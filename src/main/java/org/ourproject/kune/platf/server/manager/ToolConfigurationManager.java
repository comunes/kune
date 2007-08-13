package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.ToolConfiguration;

public interface ToolConfigurationManager {
    ToolConfiguration persist(ToolConfiguration config);
}
