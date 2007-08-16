package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.domain.AccessLists;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Data;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
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
	group.setId(new Long(number));
	return group;
    }

    public static SocialNetwork createSocialNetwork(final Group groupInAdmins, final Group groupInCollab,
	    final Group groupInViewer, final Group groupInPendings) {

	SocialNetwork socialNetwork = new SocialNetwork();
	socialNetwork.addAdmin(groupInAdmins);
	socialNetwork.addCollaborator(groupInCollab);
	socialNetwork.addViewer(groupInViewer);
	socialNetwork.addPendingCollaborator(groupInPendings);
	return socialNetwork;
    }

    public static AccessLists createAccessLists(final Group groupAdmins, final Group groupEdit, final Group groupView) {

	AccessLists lists = new AccessLists();
	lists.addAdmin(groupAdmins);
	lists.addEditor(groupEdit);
	lists.addViewer(groupView);
	return lists;
    }

    public static Content createDescriptor(final long id, final String title, final String content) {
	Content descriptor = new Content();
	descriptor.setId(id);
	Revision rev = new Revision();
	descriptor.addRevision(rev);
	Data data = rev.getData();
	data.setTitle(title);
	data.setContent(content.toCharArray());
	return descriptor;
    }
}
