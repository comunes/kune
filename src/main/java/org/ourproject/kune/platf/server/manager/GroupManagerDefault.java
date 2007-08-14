package org.ourproject.kune.platf.server.manager;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.client.dto.GroupDTO;
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

    public void create(final User user, final GroupDTO groupDTO) throws SerializableException {
	Group group = new Group(groupDTO.getShortName(), groupDTO.getLongName());
	initGroup(user, group);
	throw new SerializableException("Only for test");
    }

}
