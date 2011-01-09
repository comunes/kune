package cc.kune.core.client.notify.msgs;

import org.ourproject.common.client.notify.NotifyLevel;

import cc.kune.core.client.notify.msgs.UserNotifierPresenter.UserNotifierView;
import cc.kune.msgs.client.msgs.UserMessageLevel;
import cc.kune.msgs.client.panel.UserMessagesPanel;
import cc.kune.msgs.client.panel.UserMessagesPresenter;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class UserNotifierViewImpl extends PopupViewImpl implements UserNotifierView {
    private final UserMessagesPresenter msgs;
    private final PopupPanel popup;

    @Inject
    public UserNotifierViewImpl(EventBus eventBus, UserMessagesPresenter msgs, UserMessagesPanel panel) {
        super(eventBus);
        this.msgs = msgs;
        msgs.init(panel);
        panel.setWidth("370px");
        popup = new PopupPanel(false, false);
        popup.add(panel);
        popup.setPopupPosition(0, 0);
        popup.setStyleName("k-user-notif-popup");
        popup.show();
    }

    @Override
    public Widget asWidget() {
        return popup;
    }

    @Override
    public void notify(final NotifyLevel level, final String message, Boolean closeable) {
        switch (level) {
        case error:
            msgs.add(UserMessageLevel.error, message, closeable);
            break;
        case important:
            msgs.add(UserMessageLevel.important, message, closeable);
            break;
        case info:
            msgs.add(UserMessageLevel.info, message, closeable);
            break;
        case veryImportant:
            msgs.add(UserMessageLevel.veryImportant, message, false);
            break;
        default:
            break;
        }
    }

}
