package cc.kune.core.client.tool;

import cc.kune.core.client.tool.ToolSelectorItemPresenter.ToolSelectorItemView;
import cc.kune.core.client.tool.ToolSelectorPresenter.ToolSelectorView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class ToolSelectorPanel extends ViewImpl implements ToolSelectorView {

    interface ToolSelectorPanelUiBinder extends UiBinder<Widget, ToolSelectorPanel> {
    }

    private static ToolSelectorPanelUiBinder uiBinder = GWT.create(ToolSelectorPanelUiBinder.class);

    @UiField
    FlowPanel flow;

    public ToolSelectorPanel() {
        uiBinder.createAndBindUi(this);
    }

    @Override
    public void addItem(final ToolSelectorItemView item) {
        flow.add(item.asWidget());
    }

    @Override
    public Widget asWidget() {
        return flow;
    }

}
