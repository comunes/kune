package cc.kune.gspace.client.tool.selector;

import cc.kune.gspace.client.tool.selector.ToolSelectorItemPresenter.ToolSelectorItemView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class ToolSelectorItemPanel extends Composite implements ToolSelectorItemView {

    interface ToolSelectorItemPanelUiBinder extends UiBinder<Widget, ToolSelectorItemPanel> {
    }

    private static ToolSelectorItemPanelUiBinder uiBinder = GWT.create(ToolSelectorItemPanelUiBinder.class);

    @UiField
    Image iconLeft;
    @UiField
    Image iconRight;
    @UiField
    InlineLabel label;
    @UiField
    FocusPanel self;

    public ToolSelectorItemPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    private void focus() {
        self.addStyleDependentName("focus");
        self.removeStyleDependentName("nofocus");
    }

    @Override
    public HasClickHandlers getFocus() {
        return self;
    }

    @Override
    public HasText getLabel() {
        return label;
    }

    @UiHandler("self")
    void onSelfMouseOut(final MouseOutEvent event) {
        unfocus();
    }

    // public void setTheme(final WsTheme oldTheme, final WsTheme newTheme) {
    // if (oldTheme != null) {
    // final String oldName = oldTheme.getName();
    // hl.removeStyleDependentName(oldName);
    // }
    // final String newName = newTheme.getName();
    // hl.addStyleDependentName(newName);
    // super.setCornerStyleName(hl.getStyleName());
    // }

    @UiHandler("self")
    void onSelfMouseOver(final MouseOverEvent event) {
        focus();
    }

    @Override
    public void setSelected(final boolean selected) {
        if (selected) {
            self.addStyleDependentName("selected");
            self.removeStyleDependentName("notselected");
            iconRight.setVisible(true);
        } else {
            self.addStyleDependentName("notselected");
            self.removeStyleDependentName("selected");
            iconRight.setVisible(false);
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        self.setVisible(visible);
    }

    private void unfocus() {
        self.addStyleDependentName("nofocus");
        self.removeStyleDependentName("focus");
    }
}
