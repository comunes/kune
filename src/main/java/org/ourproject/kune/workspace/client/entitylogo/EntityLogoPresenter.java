package org.ourproject.kune.workspace.client.entitylogo;

import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.themes.WsTheme;
import org.ourproject.kune.workspace.client.themes.WsThemePresenter;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener2;

public class EntityLogoPresenter implements EntityLogo {
    private EntityLogoView view;
    private final Session session;
    private final Provider<GroupServiceAsync> groupServiceProvider;

    public EntityLogoPresenter(final StateManager stateManager, final WsThemePresenter theme, final Session session,
            Provider<GroupServiceAsync> groupServiceProvider) {
        this.session = session;
        this.groupServiceProvider = groupServiceProvider;

        stateManager.onGroupChanged(new Listener2<GroupDTO, GroupDTO>() {
            public void onEvent(final GroupDTO oldGroup, final GroupDTO newGroup) {
                setGroupLogo(newGroup);
            }
        });
        stateManager.onStateChanged(new Listener<StateDTO>() {
            public void onEvent(final StateDTO state) {
                final boolean isAdmin = state.getGroupRights().isAdministrable();
                view.setPutYourLogoVisible(isAdmin);
            }
        });
        theme.onThemeChanged(new Listener2<WsTheme, WsTheme>() {
            public void onEvent(final WsTheme oldTheme, final WsTheme newTheme) {
                view.setTheme(oldTheme, newTheme);
            }
        });
    }

    public void init(final EntityLogoView view) {
        this.view = view;
    }

    public void refreshGroupLogo() {
        setGroupLogo(session.getCurrentState().getGroup());
    }

    public void reloadGroupLogo() {
        groupServiceProvider.get().getGroup(session.getUserHash(), session.getCurrentStateToken(),
                                            new AsyncCallbackSimple<GroupDTO>() {
                                                public void onSuccess(GroupDTO group) {
                                                    StateDTO currentState = session.getCurrentState();
                                                    if (currentState.getGroup().getShortName().equals(
                                                                                                      group.getShortName())) {
                                                        // only if we are in the
                                                        // same group
                                                        currentState.setGroup(group);
                                                        setGroupLogo(group);
                                                    }
                                                }
                                            });
    }

    private void setGroupLogo(final GroupDTO group) {
        final ContentSimpleDTO groupFullLogo = group.getGroupFullLogo();
        if (groupFullLogo != null) {
            view.setFullLogo(groupFullLogo.getStateToken(), true);
        } else if (group.hasLogo()) {
            view.setLogoImage(new StateToken(group.getShortName()));
            view.setLogoText(group.getLongName());
            view.setLogoImageVisible(true);
        } else {
            view.setLogoText(group.getLongName());
            view.setLogoImageVisible(false);
        }
    }
}
