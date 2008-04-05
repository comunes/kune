
package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.ToolConfiguration;

public interface ToolConfigurationManager extends Manager<ToolConfiguration, Long> {
    ToolConfiguration persist(ToolConfiguration config);
}
