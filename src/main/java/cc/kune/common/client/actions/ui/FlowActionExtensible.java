package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.ui.AbstractComposedGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ActionExtensibleView;
import cc.kune.common.client.actions.ui.GuiProviderInstance;
import cc.kune.common.client.log.Log;
import cc.kune.common.shared.i18n.I18n;

import com.google.gwt.user.client.ui.FlowPanel;

public class FlowActionExtensible extends AbstractComposedGuiItem implements ActionExtensibleView {

  private final FlowPanel bar;

  public FlowActionExtensible() {
    super(GuiProviderInstance.get(), I18n.get());
    bar = new FlowPanel();
    initWidget(bar);
  }

  @Override
  protected void addWidget(final AbstractGuiItem item) {
    try {
      Log.debug("Adding element" + item.getClass());
      bar.add(item);
    } catch (final AssertionError e) {
      Log.error("Error adding element" + item.getClass());
    }
  }

  @Override
  public void clear() {
    super.clear();
    bar.clear();
  }

  @Override
  protected void insertWidget(final AbstractGuiItem item, final int position) {
    final int count = bar.getWidgetCount();
    bar.insert(item, count < position ? count : position);
  }

}
