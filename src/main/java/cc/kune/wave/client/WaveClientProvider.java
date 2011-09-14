package cc.kune.wave.client;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class WaveClientProvider implements Provider<WaveClientView> {

  private final Provider<? extends WaveClientView> webClientProv;

  @Inject
  public WaveClientProvider(final Provider<WebClient> webClient,
      final Provider<WebClientMock> webClientMock) {
    webClientProv = webClient;
    // If you want not to load the Webclient Mock
    // webClientProv = webClientMock;
  }

  @Override
  public WaveClientView get() {
    return webClientProv.get();
  }

}
