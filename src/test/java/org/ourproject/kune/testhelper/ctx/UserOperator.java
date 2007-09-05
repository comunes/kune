package org.ourproject.kune.testhelper.ctx;

import org.ourproject.kune.platf.server.domain.User;

public class UserOperator {
    private final DomainContext ctx;
    private final User user;

    public UserOperator(final DomainContext ctx, final User user) {
	this.ctx = ctx;
	this.user = user;
    }

}
