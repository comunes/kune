package cc.kune.msgs.client.panel;

import org.adamtacy.client.ui.effects.impl.SlideDown;

import cc.kune.msgs.client.msgs.UserMessage;
import cc.kune.msgs.client.msgs.UserMessageLevel;
import cc.kune.msgs.client.panel.UserMessagesPresenter.MessagesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserMessagesPanel extends Composite implements MessagesView {

    private static MessagesPanelUiBinder uiBinder = GWT.create(MessagesPanelUiBinder.class);
    @UiField
    VerticalPanel vp;
    private final SlideDown anim;

    interface MessagesPanelUiBinder extends UiBinder<Widget, UserMessagesPanel> {
    }

    public UserMessagesPanel() {
        initWidget(uiBinder.createAndBindUi(this));
        anim = new SlideDown(this.getElement());
        anim.setDuration(2);
    }

    @Override
    public void add(UserMessageLevel info, String message, boolean closeable) {
        UserMessage msg = new UserMessage(info, message, closeable);
        vp.add(msg);
        if (anim.isFinished()) {
            // anim.play();
        }
    }
}
