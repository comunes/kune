package org.ourproject.kune.platf.server.manager;

import javax.persistence.EntityManager;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.SocialNetwork;
import org.ourproject.kune.platf.server.domain.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class SocialNetworkManagerDefault extends DefaultManager<SocialNetwork, Long> implements SocialNetworkManager {

    @Inject
    public SocialNetworkManagerDefault(final Provider<EntityManager> provider) {
	super(provider, SocialNetwork.class);
    }

    public void addAdmin(final Group group, final User user) {
	SocialNetwork sn = group.getSocialNetwork();
	sn.addAdmin(user.getUserGroup());
    }

}
