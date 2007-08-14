package org.ourproject.kune.platf.server.manager;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GroupManagerDefault extends DefaultManager<Group, Long> implements GroupManager {

    private final Group finder;
    private final ToolRegistry registry;

    @Inject
    public GroupManagerDefault(final Provider<EntityManager> provider, final Group finder, final ToolRegistry registry) {
	super(provider, Group.class);
	this.finder = finder;
	this.registry = registry;
    }

    public void initGroup(final User user, final Group group) {
	for (ServerTool tool : registry.all()) {
	    tool.initGroup(user, group);
	}
	persist(group);
    }

    public Group get(final String shortName) {
	return finder.findByShortName(shortName);
    }

    public void create(final User user, final Group group) throws SerializableException {
	try {
	    initGroup(user, group);
	} catch (javax.persistence.EntityExistsException e) {
	    throw new SerializableException("Already exist a group with this name");
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new SerializableException("Cannot create group");
	}

    }

}
