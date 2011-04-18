package cc.kune.wave.client;

import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.dto.WaveClientParams;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.inject.Inject;

public class WaveClientManager {
    private WebClient webClient;

    @Inject
    public WaveClientManager(final Session session, final StateManager stateManager, final EventBus eventBus,
            final UserServiceAsync userService, final GSpaceArmor wsArmor) {
        session.onUserSignIn(true, new UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                userService.getWaveClientParameters(session.getUserHash(), new AsyncCallbackSimple<WaveClientParams>() {

                    @Override
                    public void onSuccess(final WaveClientParams result) {
                        // NotifyUser.info(result.getSessionJSON(), true);
                        setUseSocketIO(result.useSocketIO());
                        setSessionJSON(JsonUtils.safeEval(result.getSessionJSON()));
                        setClientFlags(JsonUtils.safeEval(result.getClientFlags()));
                        // Only for testing:
                        final ForIsWidget userSpace = wsArmor.getUserSpace();
                        if (webClient == null) {
                            if (userSpace.getWidgetCount() > 0) {
                                userSpace.remove(0);
                            }
                            webClient = new WebClient(eventBus);
                            userSpace.add(webClient);
                        } else {
                            webClient.setVisible(true);
                        }
                    }
                });
            }
        });
        session.onUserSignOut(true, new UserSignOutHandler() {
            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
                if (webClient != null) {
                    webClient.setVisible(false);
                }
                setUseSocketIO(false);
                setSessionJSON(JsonUtils.safeEval("{}"));
                setClientFlags(JsonUtils.safeEval("{}"));
            }
        });
    }

    public WebClient getWebClient() {
        return webClient;
    }

    private native void setClientFlags(JavaScriptObject object) /*-{
		$wnd.__client_flags = object;
    }-*/;

    private native void setSessionJSON(JavaScriptObject object) /*-{
		$wnd.__session = object;
    }-*/;

    private native void setUseSocketIO(boolean use) /*-{
		$wnd.__useSocketIO = use;
    }-*/;
}
