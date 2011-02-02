package cc.kune.core.client.ui.dialogs;

import cc.kune.common.client.noti.NotifyLevel;
import cc.kune.common.client.noti.NotifyLevelImages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MessagePanel extends Composite implements MessagePanelView {

    interface MessagePanelUiBinder extends UiBinder<Widget, MessagePanel> {
    }
    private static MessagePanelUiBinder uiBinder = GWT.create(MessagePanelUiBinder.class);
    @UiField
    Label description;
    @UiField
    FlowPanel flowPanel;
    @UiField
    Image icon;
    private final NotifyLevelImages images;
    @UiField
    DockLayoutPanel mainPanel;
    @UiField
    Label title;

    public MessagePanel(final NotifyLevelImages images, final String errorLabelId) {
        this.images = images;
        initWidget(uiBinder.createAndBindUi(this));
        description.ensureDebugId(errorLabelId);
    }

    @Override
    public IsWidget getPanel() {
        return this;
    }

    @Override
    public void hideErrorMessage() {
        icon.setVisible(false);
        title.setText("");
        description.setText("");
        this.setVisible(false);
    }

    @Override
    public void setErrorMessage(final String message, final NotifyLevel level) {
        description.setText(message);
        final ImageResource resource = images.getImage(level);
        icon.setResource(resource);
        icon.setVisible(true);
        this.setVisible(true);
    }
}
