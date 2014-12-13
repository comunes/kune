package cc.kune.gspace.client.viewers;

import cc.kune.wave.client.CustomSavedStateIndicator;
import cc.kune.wave.client.kspecific.AurorisColorPicker;
import cc.kune.wave.client.kspecific.WaveClientProvider;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.View;

public class WaveViewerPanel extends AbstractWaveViewerPanel implements View {
  private Widget widget;

  public WaveViewerPanel(final WaveClientProvider waveClient, final EventBus eventBus,
      final CustomSavedStateIndicator waveUnsavedIndicator,
      final Provider<AurorisColorPicker> colorPicker) {
    super(waveClient, eventBus, waveUnsavedIndicator, colorPicker);
  }

  @Override
  public void addToSlot(final Object slot, final IsWidget content) {
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  protected void initWidget(final Widget widget) {
    this.widget = widget;
  }

  @Override
  public void removeFromSlot(final Object slot, final IsWidget content) {
  }

  @Override
  public void setInSlot(final Object slot, final IsWidget content) {
  }
}
