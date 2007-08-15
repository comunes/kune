package org.ourproject.kune.platf.server.tool;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;

/**
 * ATENCIÓN: las tools hay que registrarlas en el módulo correspondiente
 * marcándolas como asEagerSingleton!! y el método register tiene que tener la
 * anotacion de Inject
 * 
 */
public interface ServerTool {

    public void register(ToolRegistry registry);

    String getName();

    Group initGroup(User user, Group group);
}
