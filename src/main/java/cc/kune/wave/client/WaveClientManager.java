package cc.kune.wave.client;

import org.waveprotocol.box.webclient.client.ClientEvents;
import org.waveprotocol.box.webclient.client.events.NetworkStatusEvent;
import org.waveprotocol.box.webclient.client.events.NetworkStatusEventHandler;

import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.dto.WaveClientParams;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.inject.Inject;

public class WaveClientManager {
    @Inject
    public WaveClientManager(final Session session, final StateManager stateManager,
            final UserServiceAsync userService, final WsArmor wsArmor) {
        session.onUserSignIn(true, new UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                userService.getWaveClientParameters(session.getUserHash(), new AsyncCallbackSimple<WaveClientParams>() {
                    private WebClient webClient;

                    @Override
                    public void onSuccess(final WaveClientParams result) {
                        // NotifyUser.info(result.getSessionJSON(), true);
                        setUseSocketIO(result.useSocketIO());
                        setSessionJSON(JsonUtils.safeEval(result.getSessionJSON()));
                        setClientFlags(JsonUtils.safeEval(result.getClientFlags()));
                        // Only for testing:
                        final ForIsWidget userSpace = wsArmor.getUserSpace();
                        if (userSpace.getWidgetCount() > 0) {
                            userSpace.remove(0);
                        }
                        if (webClient ==  null) {
                            webClient = new WebClient();
                            userSpace.add(webClient);
                        }
                    }
                });
            }
        });
        session.onUserSignOut(true, new UserSignOutHandler() {
            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
            }
        });
        ClientEvents.get().addNetworkStatusEventHandler(new NetworkStatusEventHandler() {
            @Override
            public void onNetworkStatus(NetworkStatusEvent event) {
                switch (event.getStatus()) {
                  case CONNECTED:
                  case RECONNECTED:
                      NotifyUser.info("Online");
                    break;
                  case DISCONNECTED:
                    NotifyUser.info("Offline");
                    break;
                  case RECONNECTING:
                      NotifyUser.showProgress("Connecting");
                    break;
                }
            }
          });
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
