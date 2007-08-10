package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.domain.Folder;
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

}
