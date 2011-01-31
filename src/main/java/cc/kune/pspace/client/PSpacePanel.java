package cc.kune.pspace.client;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.pspace.client.PSpacePresenter.PSpaceView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class PSpacePanel extends ViewImpl implements PSpaceView {

    public interface PSpacePanelUiBinder extends UiBinder<Widget, PSpacePanel> {
    }
    private static PSpacePanelUiBinder uiBinder = GWT.create(PSpacePanelUiBinder.class);

    private final ActionFlowPanel actionPanel;
    @UiField
    SimplePanel actionPanelContainer;
    @UiField
    InlineLabel description;
    @UiField
    Frame frame;
    @UiField
    Image icon;
    @UiField
    LayoutPanel mainPanel;
    @UiField
    FlowPanel messagePanel;
    @UiField
    InlineLabel title;
    private final Widget widget;

    @Inject
    public PSpacePanel(final GuiProvider guiProvider, final CoreResources res) {
        widget = uiBinder.createAndBindUi(this);
        actionPanel = new ActionFlowPanel(guiProvider);
        actionPanelContainer.add(actionPanel);
        final Element layer = mainPanel.getWidgetContainerElement(messagePanel);
        layer.addClassName("k-publicspace-msg");
        layer.addClassName("k-opacity80");
        layer.addClassName("k-box-5shadow");
        layer.addClassName("k-5corners");
        icon.setResource(res.browser32());
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public IsActionExtensible getActionPanel() {
        return actionPanel;
    }

    @Override
    public HasText getDescription() {
        return description;
    }

    @Override
    public HasText getTitle() {
        return title;
    }

    @Override
    public void setContentGotoPublicUrl(final String publicUrl) {
        frame.setUrl(publicUrl);
    }

}
