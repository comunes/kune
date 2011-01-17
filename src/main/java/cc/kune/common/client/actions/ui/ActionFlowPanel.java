package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.ui.bind.GuiProvider;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

public class ActionFlowPanel extends AbstractComposedGuiItem implements ActionExtensibleView {

    private final FlowPanel bar;

    @Inject
    public ActionFlowPanel(final GuiProvider guiProvider) {
        super(guiProvider);
        bar = new FlowPanel();
        initWidget(bar);
    }

    @Override
    protected void add(final AbstractGuiItem item) {
        bar.add(item);
    }

    @Override
    protected void insert(final AbstractGuiItem item, final int position) {
        bar.insert(item, position);
    }

}
