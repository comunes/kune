package org.ourproject.kune.workspace.client.nohomepage;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkResultDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.entitylogo.EntityLogo;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener0;

public class NoHomePagePresenter implements NoHomePage {

    private NoHomePageView view;

    public NoHomePagePresenter(final Session session, final StateManager stateManager, KuneErrorHandler errorHandler,
            EntityLogo entityLogo, final Provider<GroupServiceAsync> groupServiceProvider,
            final Provider<EntityLogo> entityLogoProvider, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final HistoryWrapper history) {

        errorHandler.onNotDefaultContent(new Listener0() {
            public void onEvent() {
                view.clearWs();
                StateToken groupToken = new StateToken(history.getToken());
                groupServiceProvider.get().getGroup(session.getUserHash(), groupToken,
                        new AsyncCallbackSimple<GroupDTO>() {
                            public void onSuccess(GroupDTO group) {
                                StateDTO currentState = new StateDTO();
                                currentState.setStateToken(group.getStateToken());
                                session.setCurrentState(currentState);
                                session.getCurrentState().setGroup(group);
                                entityLogoProvider.get().refreshGroupLogo();
                                snServiceProvider.get().getSocialNetwork(session.getUserHash(),
                                        session.getCurrentStateToken(),
                                        new AsyncCallbackSimple<SocialNetworkResultDTO>() {
                                            public void onSuccess(SocialNetworkResultDTO result) {
                                                session.getCurrentState().setGroupRights(result.getGroupRights());
                                                stateManager.setSocialNetwork(result);
                                            }
                                        });
                            }
                        });
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(NoHomePageView view) {
        this.view = view;
    }
}
