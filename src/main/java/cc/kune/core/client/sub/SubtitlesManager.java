package cc.kune.core.client.sub;

import cc.kune.common.client.utils.Base64Utils;
import cc.kune.common.client.utils.SimpleCallback;
import cc.kune.core.client.state.StateManager;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class SubtitlesManager extends
    Presenter<SubtitlesManager.SubtitlesView, SubtitlesManager.SubtitlesProxy> {

  @ProxyCodeSplit
  public interface SubtitlesProxy extends Proxy<SubtitlesManager> {
  }

  public interface SubtitlesView extends View {

    void setDescription(String descr);

    void setTitleText(String text);

    void show(final SimpleCallback atShowEnd);

  }
  private final StateManager stateManager;

  @Inject
  public SubtitlesManager(final EventBus eventBus, final SubtitlesView view, final SubtitlesProxy proxy,
      final StateManager stateManager) {
    super(eventBus, view, proxy);
    this.stateManager = stateManager;
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  public void show(final String token) {
    final String[] params = token.split("\\|");
    getView().setTitleText(new String(Base64Utils.fromBase64(params[0])));
    getView().setDescription(new String(Base64Utils.fromBase64(params[1])));
    getView().show(new SimpleCallback() {
      @Override
      public void onCallback() {
        stateManager.gotoHistoryToken(params[2]);
      }
    });
  }
}