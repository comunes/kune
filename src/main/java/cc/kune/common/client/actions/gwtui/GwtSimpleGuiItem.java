package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractComposedGuiItem;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.bind.GuiProvider;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class GwtSimpleGuiItem extends AbstractComposedGuiItem {

    private final HorizontalPanel hpanel;

    public GwtSimpleGuiItem(final GuiProvider provider) {
        super(provider);
        hpanel = new HorizontalPanel();
        initWidget(hpanel);
    }

    @Override
    protected void add(final AbstractGuiItem item) {
        hpanel.add(item);
    }

    @Override
    protected void insert(final AbstractGuiItem item, final int position) {
        hpanel.insert(item, position);
    }

}
