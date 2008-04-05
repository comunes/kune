package org.ourproject.kune.platf.server.users;

import org.ourproject.kune.platf.server.ParticipationData;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.I18nLanguage;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.SocialNetworkManager;

import com.google.gwt.user.client.rpc.SerializableException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserInfoServiceDefault implements UserInfoService {

    private final SocialNetworkManager socialNetworkManager;

    @Inject
    public UserInfoServiceDefault(final SocialNetworkManager socialNetwork) {
        this.socialNetworkManager = socialNetwork;
    }

    public UserInfo buildInfo(final User user, final String userHash) throws SerializableException {
        UserInfo info = null;
        if (User.isKnownUser(user)) {
            info = new UserInfo();

            info.setShortName(user.getShortName());
            info.setName(user.getName());
            info.setChatName(user.getShortName());
            info.setChatPassword(user.getPassword());
            I18nLanguage language = user.getLanguage();
            info.setLanguage(language);
            info.setCountry(user.getCountry());
            info.setUserHash(userHash);

            Group userGroup = user.getUserGroup();

            ParticipationData participation = socialNetworkManager.findParticipation(user, userGroup);
            info.setGroupsIsAdmin(participation.getGroupsIsAdmin());
            info.setGroupsIsCollab(participation.getGroupsIsCollab());

            Content defaultContent = userGroup.getDefaultContent();
            if (defaultContent != null) {
                info.setHomePage(defaultContent.getStateToken());
            }
        }
        return info;
    }
}
