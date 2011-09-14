package cc.kune.wave.client;

import org.waveprotocol.box.webclient.client.RemoteViewServiceMultiplexer;
import org.waveprotocol.box.webclient.client.WaveWebSocketClient;
import org.waveprotocol.wave.client.account.ProfileManager;
import org.waveprotocol.wave.client.common.safehtml.SafeHtml;
import org.waveprotocol.wave.client.common.util.AsyncHolder.Accessor;
import org.waveprotocol.wave.client.widget.common.ImplPanel;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class WebClientMock extends Composite implements WaveClientView {

  private static final String MOCK_MSG = "This is only a mock of the wave client, only to make development testing not so heavy";
  private ImplPanel implPanel;

  public WebClientMock() {
    initWidget(new Label(MOCK_MSG));
  }

  @Override
  public void clear() {
  }

  @Override
  public RemoteViewServiceMultiplexer getChannel() {
    return null;
  }

  @Override
  public Element getLoading() {
    return null;
  }

  @Override
  public ProfileManager getProfiles() {
    return null;
  }

  @Override
  public void getStackTraceAsync(final Throwable caught, final Accessor<SafeHtml> accessor) {
  }

  @Override
  public ImplPanel getWaveHolder() {
    if (implPanel == null) {
      implPanel = new ImplPanel("<span style='padding:20px;'>" + MOCK_MSG + "</span>");
    }
    return implPanel;
  }

  @Override
  public WaveWebSocketClient getWebSocket() {
    return null;
  }

  @Override
  public void login() {
  }

  @Override
  public void logout() {
  }

}
