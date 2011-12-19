package cc.kune.wave.client;

import org.waveprotocol.wave.client.widget.common.ImplPanel;

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.log.Log;
import cc.kune.core.client.state.StateManager;

import com.google.gwt.user.client.ui.HasWidgets;

public class WaveClientUtils {
  public static void addListener(final StateManager stateManager, final KuneStagesProvider wave,
      final ImplPanel waveHolder, final HasWidgets parent) {
    stateManager.addBeforeStateChangeListener(new BeforeActionListener() {
      @Override
      public boolean beforeAction() {
        // This fix lot of problems when you are editing and move to other
        // location (without stop editing)
        clear(wave, waveHolder, parent);
        return true;
      }
    });
  }

  public static void clear(KuneStagesProvider wave, final ImplPanel waveHolder, final HasWidgets parent) {
    if (wave != null) {
      try {
        wave.destroy();
      } catch (final RuntimeException e) {
        // When editing: java.lang.RuntimeException: Component not found: MENU
        Log.error("Error clearing wave panel", e);
      }
      wave = null;
    }
    if (waveHolder != null && waveHolder.isAttached()) {
      waveHolder.removeFromParent();
      parent.remove(waveHolder);
    }
  }
}
