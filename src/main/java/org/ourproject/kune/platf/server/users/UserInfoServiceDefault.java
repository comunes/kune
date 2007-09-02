package org.ourproject.kune.platf.server.users;

import java.util.ArrayList;
import java.util.List;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupList;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Singleton;

@Singleton
public class UserInfoServiceDefault implements UserInfoService {

    public UserInfo buildInfo(final User user) {
	UserInfo info = null;
	if (User.isAUser(user)) {
	    info = new UserInfo();

	    info.setName(user.getName());
	    info.setChatName(user.getShortName());
	    info.setChatPassword(user.getPassword());

	    GroupList listAdmin = user.getAdminInGroups();
	    GroupList listEditor = user.getEditorInGroups();
	    List<Link> groupsIsAdmin = new ArrayList();
	    List<Link> groupsIsEditor = new ArrayList();
	    for (Group g : listAdmin.getList()) {
		groupsIsAdmin.add(new Link(g.getShortName(), g.getDefaultContent().getStateToken()));
	    }
	    for (Group g : listEditor.getList()) {
		groupsIsEditor.add(new Link(g.getShortName(), g.getDefaultContent().getStateToken()));
	    }
	    info.setGroupsIsAdmin(groupsIsAdmin);
	    info.setGroupsIsEditor(groupsIsEditor);

	    Group userGroup = user.getUserGroup();
	    Content defaultContent = userGroup.getDefaultContent();
	    if (defaultContent != null)
		info.setHomePage(defaultContent.getStateToken());
	}
	return info;
    }

}
