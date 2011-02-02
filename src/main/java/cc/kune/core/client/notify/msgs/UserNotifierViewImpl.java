package cc.kune.core.client.notify.msgs;

import cc.kune.common.client.ui.PopupBottomPanel;
import cc.kune.core.client.notify.msgs.UserNotifierPresenter.UserNotifierView;
import cc.kune.msgs.client.CloseCallback;
import cc.kune.msgs.client.UserMessagesPanel;
import cc.kune.msgs.client.UserMessagesPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupViewImpl;

public class UserNotifierViewImpl extends PopupViewImpl implements UserNotifierView {
    private final UserMessagesPresenter msgs;
    private final PopupBottomPanel popup;

    @Inject
    public UserNotifierViewImpl(final EventBus eventBus, final UserMessagesPresenter msgs, final UserMessagesPanel panel) {
        super(eventBus);
        this.msgs = msgs;
        msgs.init(panel);
        panel.setWidth("370px");
        popup = new PopupBottomPanel(false, false);
        popup.add(panel);
        popup.show();
    }

    @Override
    public Widget asWidget() {
        return popup;
    }

    @Override
    public void notify(final UserNotifyEvent event) {
        msgs.add(event.getLevel(), event.getTitle(), event.getMessage(), event.getId(), event.getCloseable(),
                new CloseCallback() {
                    @Override
                    public void onClose() {
                        popup.setCenterPosition();
                    }
                });
        popup.setCenterPosition();
        DOM.setStyleAttribute(popup.getElement(), "zIndex", "100000");
    }

}
