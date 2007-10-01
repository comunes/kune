package org.ourproject.kune.platf.server.users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserInfoServiceDefault implements UserInfoService {

    private final GroupManager groupFinder;

    @Inject
    public UserInfoServiceDefault(final GroupManager groupFinder) {
	this.groupFinder = groupFinder;
    }

    public UserInfo buildInfo(final User user) {
	UserInfo info = null;
	if (User.isKownUser(user)) {
	    info = new UserInfo();

	    info.setName(user.getName());
	    info.setChatName(user.getShortName());
	    info.setChatPassword(user.getPassword());

	    Long userGroupId = user.getUserGroup().getId();
	    List<Group> adminInGroups = groupFinder.findAdminInGroups(userGroupId);
	    List<Group> collabInGroups = groupFinder.findCollabInGroups(userGroupId);

	    List<Link> groupsIsAdmin = new ArrayList();
	    List<Link> groupsIsCollab = new ArrayList();
	    ;
	    Iterator iter = adminInGroups.iterator();
	    while (iter.hasNext()) {
		Group g = (Group) iter.next();
		groupsIsAdmin.add(new Link(g.getLongName(), g.getDefaultContent().getStateToken()));

	    }
	    iter = collabInGroups.iterator();
	    while (iter.hasNext()) {
		Group g = (Group) iter.next();
		groupsIsCollab.add(new Link(g.getLongName(), g.getDefaultContent().getStateToken()));
	    }
	    info.setGroupsIsAdmin(groupsIsAdmin);
	    info.setGroupsIsCollab(groupsIsCollab);

	    Group userGroup = user.getUserGroup();
	    Content defaultContent = userGroup.getDefaultContent();
	    if (defaultContent != null) {
		info.setHomePage(defaultContent.getStateToken());
	    }
	}
	return info;
    }
}
