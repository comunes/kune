package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupType;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.UserBuddiesDataDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;

import com.calclab.suco.client.listener.Listener2;

public class BuddiesSummaryPresenter implements BuddiesSummary {

    private BuddiesSummaryView view;
    private final StateManager stateManager;

    public BuddiesSummaryPresenter(StateManager stateManager, final Session session) {
        this.stateManager = stateManager;
        stateManager.onGroupChanged(new Listener2<GroupDTO, GroupDTO>() {
            public void onEvent(GroupDTO param1, GroupDTO param2) {
                setState(session.getCurrentState());
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(BuddiesSummaryView view) {
        this.view = view;
    }

    public void onClick(UserSimpleDTO user) {
        stateManager.gotoToken(user.getShortName());
    }

    protected void setState(StateDTO state) {
        if (state.getGroup().getType().equals(GroupType.PERSONAL)) {
            view.clear();
            UserBuddiesDataDTO userBuddies = state.getUserBuddies();
            for (UserSimpleDTO user : userBuddies.getBuddies()) {
                view.addBuddie(user);
            }
            view.setOtherUsers(userBuddies.getOtherExternalBuddies());
            view.show();
        } else {
            view.hide();
        }
    }

}
