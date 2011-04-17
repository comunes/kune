package cc.kune.gspace.client.tool.selector;

import cc.kune.gspace.client.WsArmor;
import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.ToolSelectorItemView;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter.ToolSelectorView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ToolSelectorPanel extends ViewImpl implements ToolSelectorView {

    interface ToolSelectorPanelUiBinder extends UiBinder<Widget, ToolSelectorPanel> {
    }

    private static ToolSelectorPanelUiBinder uiBinder = GWT.create(ToolSelectorPanelUiBinder.class);

    @UiField
    FlowPanel flow;

    @Inject
    public ToolSelectorPanel(final WsArmor wsArmor) {
        wsArmor.getEntityToolsCenter().add(uiBinder.createAndBindUi(this));
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
