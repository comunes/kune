package cc.kune.msgs.client;

import org.adamtacy.client.ui.effects.examples.SlideUp;

import cc.kune.common.client.noti.NotifyLevel;
import cc.kune.msgs.client.UserMessagesPresenter.UserMessagesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserMessagesPanel extends Composite implements UserMessagesView {

    private static MessagesPanelUiBinder uiBinder = GWT.create(MessagesPanelUiBinder.class);
    @UiField
    VerticalPanel vp;

    interface MessagesPanelUiBinder extends UiBinder<Widget, UserMessagesPanel> {
    }

    public UserMessagesPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void add(NotifyLevel level, String title, String message, boolean closeable, CloseCallback closeCallback) {
        UserMessage msg = new UserMessage(level, title, message, closeable, closeCallback);
        vp.add(msg);
        SlideUp anim = new SlideUp(msg.getElement());
        anim.setDuration(.5);
        anim.play();
    }
}
