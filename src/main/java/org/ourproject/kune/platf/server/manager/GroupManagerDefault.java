package org.ourproject.kune.platf.server.manager;

import javax.persistence.EntityExistsException;
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

    public Group findByShortName(final String shortName) {
	return finder.findByShortName(shortName);
    }

    public Group createGroup(final String shortName, final String longName, final User user)
	    throws SerializableException {
	Group group = new Group(shortName, longName);
	return createGroup(group, user);
    }

    public Group createUserGroup(final User user) {
	Group group = new Group(user.getShortName(), user.getName());
	user.setUserGroup(group);
	initSocialNetwork(group, group);
	initGroup(user, group);
	return group;
    }

    public Group createGroup(final Group group, final User user) throws SerializableException {
	try {
	    initSocialNetwork(group, user.getUserGroup());
	    initGroup(user, group);
	    return group;
	} catch (EntityExistsException e) {
	    throw new SerializableException("Already exist a group with this name");
	}
    }

    private void initSocialNetwork(final Group group, final Group userGroup) {
	group.getSocialNetwork().addAdmin(userGroup);
    }

    private void initGroup(final User user, final Group group) {
	for (ServerTool tool : registry.all()) {
	    tool.initGroup(user, group);
	}
	persist(group);
    }
}
