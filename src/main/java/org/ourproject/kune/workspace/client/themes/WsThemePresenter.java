package org.ourproject.kune.workspace.client.themes;

import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.event.Events;
import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Event2;
import com.calclab.suco.client.listener.Listener;
import com.calclab.suco.client.listener.Listener0;
import com.calclab.suco.client.listener.Listener2;

public class WsThemePresenter {

    private WsThemeView view;
    private WsTheme previousTheme;
    private final Event2<WsTheme, WsTheme> onThemeChanged;
    private final Provider<GroupServiceAsync> groupServiceProvider;
    private final Session session;
    private WsTheme defTheme;

    public WsThemePresenter(final Session session, final Provider<GroupServiceAsync> groupServiceProvider,
            final StateManager stateManager, final KuneErrorHandler errorHandler) {
        this.session = session;
        this.groupServiceProvider = groupServiceProvider;
        this.onThemeChanged = Events.create(WsTheme.class, WsTheme.class, "onThemeChanged");
        session.onInitDataReceived(new Listener<InitDataDTO>() {
            public void onEvent(final InitDataDTO initData) {
                view.setThemes(initData.getWsThemes());
                setDefTheme(initData);
                setTheme(defTheme);
            }
        });
        stateManager.onStateChanged(new Listener<StateDTO>() {
            public void onEvent(final StateDTO state) {
                setState(state);
            }
        });
        errorHandler.onNotDefaultContent(new Listener0() {
            public void onEvent() {
                setTheme(defTheme);
            }
        });
    }

    public void init(final WsThemeView view) {
        this.view = view;
    }

    public void onThemeChanged(final Listener2<WsTheme, WsTheme> listener) {
        onThemeChanged.add(listener);
    }

    protected void onChangeGroupWsTheme(final WsTheme newTheme) {
        Site.showProgressProcessing();
        groupServiceProvider.get().changeGroupWsTheme(session.getUserHash(), session.getCurrentState().getStateToken(),
                newTheme.getName(), new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        setTheme(newTheme);
                        Site.hideProgress();
                    }
                });
    }

    private void setDefTheme(final InitDataDTO initData) {
        defTheme = new WsTheme(initData.getWsThemes()[0]);
    }

    private void setState(final StateDTO state) {
        setTheme(new WsTheme(state.getGroup().getWorkspaceTheme()));
        if (state.getGroupRights().isAdministrable()) {
            view.setVisible(true);
        } else {
            view.setVisible(false);
        }
    }

    private void setTheme(final WsTheme newTheme) {
        if (previousTheme == null || !previousTheme.equals(newTheme)) {
            onThemeChanged.fire(previousTheme, newTheme);
        }
        previousTheme = newTheme;
    }

}
