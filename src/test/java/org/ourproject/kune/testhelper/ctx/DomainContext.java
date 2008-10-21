package org.ourproject.kune.testhelper.ctx;

import java.util.HashMap;
import java.util.TimeZone;

import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.I18nCountry;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;

public class DomainContext {
    private final HashMap<String, User> users;
    private final HashMap<String, Group> groups;

    public DomainContext() {
        this.users = new HashMap<String, User>();
        this.groups = new HashMap<String, Group>();
    }

    public void createGroups(final String... groupNames) {
        Group group;
        for (String name : groupNames) {
            group = new Group("name", "Some group: " + name);
            groups.put(name, group);
        }
    }

    public void createOrphanGroup(final String... groupNames) {
        Group group;
        for (String name : groupNames) {
            group = new Group("name", "Some group: " + name);
            group.setGroupType(GroupType.ORPHANED_PROJECT);
            groups.put(name, group);
        }
    }

    public void createUsers(final String... userNames) {
        User user;
        for (String name : userNames) {
            user = new User(name, "long" + name, name + "@email.com", "password" + name, new I18nLanguage(),
                    new I18nCountry(), TimeZone.getDefault());
            user.setUserGroup(new Group(name, "groupLong" + name));
            users.put(name, user);
        }
    }

    public AccessLists getDefaultAccessListOf(final String userName) {
        return getSocialNetworkOf(userName).getAccessLists();
    }

    public Group getGroup(final String groupName) {
        return groups.get(groupName);
    }

    public Group getGroupOf(final String userName) {
        User user = getUser(userName);
        Group userGroup = user.getUserGroup();
        return userGroup;
    }

    public User getUser(final String userName) {
        return users.get(userName);
    }

    public SocialNetworkOperator inSocialNetworkOf(final String userName) {
        return new SocialNetworkOperator(this, getSocialNetworkOf(userName));
    }

    private SocialNetwork getSocialNetworkOf(final String userName) {
        Group userGroup = getGroupOf(userName);
        SocialNetwork socialNetwork = userGroup.getSocialNetwork();
        return socialNetwork;
    }

}
