package org.ourproject.kune.workspace.client.ui.newtmp.themes;

import org.ourproject.kune.platf.client.dto.InitDataDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.sitebar.Site;

import com.calclab.suco.client.provider.Provider;
import com.calclab.suco.client.signal.Signal2;
import com.calclab.suco.client.signal.Slot;
import com.calclab.suco.client.signal.Slot2;

public class WsThemePresenter {

    private WsThemeView view;
    private WsTheme previousTheme;
    private final Signal2<WsTheme, WsTheme> onThemeChanged;
    private final Provider<GroupServiceAsync> groupServiceProvider;
    private final Session session;

    public WsThemePresenter(final Session session, final Provider<GroupServiceAsync> groupServiceProvider,
	    final StateManager stateManager) {
	this.session = session;
	this.groupServiceProvider = groupServiceProvider;
	this.onThemeChanged = new Signal2<WsTheme, WsTheme>("onThemeChanged");
	session.onInitDataReceived(new Slot<InitDataDTO>() {
	    public void onEvent(final InitDataDTO initData) {
		view.setThemes(initData.getWsThemes());
		setTheme(new WsTheme(initData.getWsThemes()[0]));
	    }
	});
	stateManager.onStateChanged(new Slot<StateDTO>() {
	    public void onEvent(final StateDTO state) {
		setState(state);
	    }
	});
    }

    public void init(final WsThemeView view) {
	this.view = view;
    }

    public void onThemeChanged(final Slot2<WsTheme, WsTheme> slot) {
	onThemeChanged.add(slot);
    }

    protected void onChangeGroupWsTheme(final WsTheme newTheme) {
	Site.showProgressProcessing();
	groupServiceProvider.get().changeGroupWsTheme(session.getUserHash(),
		session.getCurrentState().getGroup().getShortName(), newTheme.getName(),
		new AsyncCallbackSimple<Object>() {
		    public void onSuccess(final Object result) {
			setTheme(newTheme);
			Site.hideProgress();
		    }
		});
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
