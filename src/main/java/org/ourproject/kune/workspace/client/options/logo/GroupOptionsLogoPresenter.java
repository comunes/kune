package org.ourproject.kune.workspace.client.options.logo;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.rpc.UserServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.entityheader.EntityHeader;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class GroupOptionsLogoPresenter extends EntityOptionsLogoPresenter {

    public GroupOptionsLogoPresenter(final Session session, final EntityHeader entityLogo,
            final EntityOptions entityOptions, final StateManager stateManager,
            final Provider<UserServiceAsync> userService, final Provider<ChatEngine> chatEngine) {
        super(session, entityLogo, entityOptions, userService, chatEngine);
        stateManager.onGroupChanged(new Listener2<String, String>() {
            public void onEvent(final String group1, final String group2) {
                setState();
            }
        });
    }

    @Override
    public void init(final EntityOptionsLogoView view) {
        super.init(view);
        view.setNormalGroupsLabels();
    }

    @Override
    protected void setState() {
        view.setUploadParams(session.getUserHash(), session.getCurrentStateToken().toString());
    }
}
