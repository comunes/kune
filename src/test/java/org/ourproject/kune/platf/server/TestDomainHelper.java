package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
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

}
