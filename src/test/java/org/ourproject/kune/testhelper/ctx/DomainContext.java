package org.ourproject.kune.testhelper.ctx;

import java.util.HashMap;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;

public class DomainContext {
    private final HashMap<String, User> users;
    private final HashMap<String, Group> groups;

    public DomainContext() {
	this.users = new HashMap<String, User>();
	this.groups = new HashMap<String, Group>();
    }

    public void createUsers(final String... userNames) {
	User user;
	for (String name : userNames) {
	    user = new User(name, "long" + name, name + "@email.com", "password" + name);
	    user.setUserGroup(new Group(name, "groupLong" + name));
	    users.put(name, user);
	}
    }

    public void createGroups(final String... groupNames) {
	Group group;
	for (String name : groupNames) {
	    group = new Group("name", "Some group: " + name);
	    groups.put(name, group);
	}
    }

    public SocialNetworkOperator inSocialNetworkOf(final String userName) {
	return new SocialNetworkOperator(this, getSocialNetworkOf(userName));
    }

    private SocialNetwork getSocialNetworkOf(final String userName) {
	Group userGroup = getGroupOf(userName);
	SocialNetwork socialNetwork = userGroup.getSocialNetwork();
	return socialNetwork;
    }

    public User getUser(final String userName) {
	return users.get(userName);
    }

    public UserOperator user(final String userName) {
	return new UserOperator(this, getUser(userName));
    }

    public AccessLists getDefaultAccesListOf(final String userName) {
	return getSocialNetworkOf(userName).getAccessLists();
    }

    public Group getGroupOf(final String userName) {
	User user = getUser(userName);
	Group userGroup = user.getUserGroup();
	return userGroup;
    }

    public Group getGroup(String groupName) {
	return groups.get(groupName);
    }

}
