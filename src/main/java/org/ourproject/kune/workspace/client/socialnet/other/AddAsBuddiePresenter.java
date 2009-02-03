package org.ourproject.kune.workspace.client.socialnet.other;

import org.ourproject.kune.chat.client.ChatEngine;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public class AddAsBuddiePresenter implements AddAsBuddie {

    private AddAsBuddieView view;
    private final Session session;
    private final Provider<ChatEngine> chatEngine;

    public AddAsBuddiePresenter(Provider<ChatEngine> chatEngine, StateManager stateManager, final Session session) {
        this.chatEngine = chatEngine;
        this.session = session;
        stateManager.onStateChanged(new Listener<StateAbstractDTO>() {
            public void onEvent(StateAbstractDTO state) {
                setState(state);
            }
        });
        chatEngine.get().addOnRosterChanged(new Listener0() {
            public void onEvent() {
                setState(session.getCurrentState());
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(AddAsBuddieView view) {
        this.view = view;
    }

    public void onAdd() {
        chatEngine.get().addNewBuddie(session.getCurrentState().getGroup().getShortName());
        Site.info("Added as buddie. Waiting buddie response");
        view.setVisible(false);
    }

    private void setState(StateAbstractDTO state) {
        String groupName = state.getGroup().getShortName();
        boolean isPersonal = state.getGroup().isPersonal();
        boolean isLogged = session.isLogged();
        if (isLogged && isPersonal && (!chatEngine.get().isBuddie(groupName))
                && (!session.getCurrentUser().getShortName().equals(groupName))) {
            view.setVisible(true);
        } else {
            view.setVisible(false);
        }
    }
}
