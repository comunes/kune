package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractComposedGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.bind.GuiProvider;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class GwtSimpleGuiItem extends AbstractComposedGuiItem {

    private final HorizontalPanel bar;

    public GwtSimpleGuiItem(final GuiProvider provider) {
        super(provider);
        bar = new HorizontalPanel();
        initWidget(bar);
    }

    @Override
    protected void addWidget(final AbstractGuiItem item) {
        bar.add(item);
    }

    @Override
    protected void insertWidget(final AbstractGuiItem item, final int position) {
        int count = bar.getWidgetCount();
        bar.insert(item, count < position ? count : position);
    }

}
