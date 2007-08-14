package org.ourproject.kune.platf.server;

import java.util.ArrayList;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Data;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupList;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;

public class TestDomainHelper {

    public static User createUser(final int number) {
	User user = new User("name" + number, "shortName" + number, "email" + number, "password" + number);
	return user;
    }

    public static Folder createFolderWithId(final long id) {
	Folder folder = new Folder();
	folder.setId(id);
	return folder;
    }

    public static Folder createFolderWithIdAndToolName(final int i, final String toolName) {
	Folder folder = createFolderWithId(i);
	folder.setToolName(toolName);
	return folder;
    }

    public static Folder createFolderWithIdAndGroupAndTool(final int i, final String groupShortName,
	    final String toolName) {
	Folder folder = createFolderWithIdAndToolName(i, toolName);
	Group owner = new Group();
	owner.setShortName(groupShortName);
	folder.setOwner(owner);
	return folder;

    }

    public static Group createGroup(final int number) {
	Group group = new Group("ysei" + number, "Yellow Submarine Environmental Initiative" + number);
	return group;
    }

    public static SocialNetwork createSocialNetwork(final Group groupInAdmins, final Group groupInCollab,
	    final Group groupInViewer, final Group groupInPendings) {
	ArrayList<Group> adminList = new ArrayList<Group>();
	ArrayList<Group> collabList = new ArrayList<Group>();
	ArrayList<Group> viewerList = new ArrayList<Group>();
	ArrayList<Group> pendingList = new ArrayList<Group>();
	adminList.add(groupInAdmins);
	collabList.add(groupInCollab);
	viewerList.add(groupInViewer);
	pendingList.add(groupInPendings);
	SocialNetwork socialNetwork = new SocialNetwork(new GroupList(adminList), new GroupList(collabList),
		new GroupList(viewerList), new GroupList(pendingList));
	return socialNetwork;
    }

    public static AccessLists createAccessLists(final Group groupAdmins, final Group groupEdit, final Group groupView) {
	ArrayList<Group> adminList = new ArrayList<Group>();
	ArrayList<Group> editList = new ArrayList<Group>();
	ArrayList<Group> viewerList = new ArrayList<Group>();
	adminList.add(groupAdmins);
	editList.add(groupEdit);
	viewerList.add(groupView);
	return new AccessLists(adminList, editList, viewerList);
    }

    public static ContentDescriptor createDescriptor(final long id, final String title, final String content) {
	ContentDescriptor descriptor = new ContentDescriptor();
	descriptor.setId(id);
	Revision rev = new Revision();
	descriptor.addRevision(rev);
	Data data = rev.getData();
	data.setTitle(title);
	data.setContent(content.toCharArray());
	return descriptor;
    }
}
