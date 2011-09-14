package cc.kune.wave.client;

import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;
import org.waveprotocol.wave.client.widget.common.ImplPanel;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;

public interface WaveClientView extends IsWidget {

  void clear();

  RemoteViewServiceMultiplexer getChannel();

  Element getLoading();

  ProfileManager getProfiles();

  void getStackTraceAsync(Throwable caught, Accessor<SafeHtml> accessor);

  ImplPanel getWaveHolder();

  WaveWebSocketClient getWebSocket();

  void login();

  void logout();

}
