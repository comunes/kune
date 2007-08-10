package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.domain.User;

public class TestDomainHelper {

    public static User createUser(final int number) {
	User user = new User("name" + number, "shortName" + number, "email" + number, "password" + number);
	return user;
    }

}
