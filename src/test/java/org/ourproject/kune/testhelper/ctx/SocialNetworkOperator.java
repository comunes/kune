package org.ourproject.kune.testhelper.ctx;

import org.ourproject.kune.platf.server.domain.SocialNetwork;

public class SocialNetworkOperator {
    private final DomainContext ctx;
    private final SocialNetwork socialNetwork;

    public SocialNetworkOperator(final DomainContext ctx, final SocialNetwork socialNetwork) {
        this.ctx = ctx;
        this.socialNetwork = socialNetwork;
    }

    public void addAsAdministrator(final String userName) {
        socialNetwork.addAdmin(ctx.getGroupOf(userName));
    }

    public void addAsCollaborator(final String userName) {
        socialNetwork.addCollaborator(ctx.getGroupOf(userName));
    }

}
