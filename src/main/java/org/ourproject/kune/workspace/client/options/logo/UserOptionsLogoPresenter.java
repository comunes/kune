package org.ourproject.kune.workspace.client.options.logo;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class UserOptionsLogoPresenter extends EntityOptionsLogoPresenter {

    public UserOptionsLogoPresenter(final Session session, final EntityHeader entityLogo,
            final EntityOptions entityOptions, final StateManager stateManager,
            final Provider<UserServiceAsync> userService, final Provider<ChatEngine> chatEngine) {
        super(session, entityLogo, entityOptions, userService, chatEngine);
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                setState();
            }
        });
    }

    @Override
    public void init(final EntityOptionsLogoView view) {
        super.init(view);
        view.setPersonalGroupsLabels();
    }

    @Override
    public void onSubmitComplete(final int httpStatus, final String photoBinary) {
        super.onSubmitComplete(httpStatus, photoBinary);
        final GroupDTO group = session.getCurrentState().getGroup();
        if (session.getCurrentUser().getShortName().equals(group.getShortName())) {
            userService.get().getUserAvatarBaser64(session.getUserHash(), group.getStateToken(),
                    new AsyncCallbackSimple<String>() {
                        public void onSuccess(final String photoBinary) {
                            chatEngine.get().setAvatar(photoBinary);
                        }
                    });
        }
    }

    @Override
    protected void setState() {
        view.setUploadParams(session.getUserHash(), session.getCurrentUser().getStateToken().toString());
    }
}
