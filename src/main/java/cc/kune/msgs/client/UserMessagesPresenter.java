package cc.kune.msgs.client;

import cc.kune.common.client.noti.NotifyLevel;

public class UserMessagesPresenter {

    public interface UserMessagesView {
        void add(NotifyLevel level, String title, String message, String id, boolean closable, CloseCallback callback);
    }

    private UserMessagesView view;

    public UserMessagesPresenter() {
    }

    public void add(final NotifyLevel level, final String title, final String message, final String id,
            final boolean closable, final CloseCallback closeCallback) {
        view.add(level, title, message, id, closable, closeCallback);
    }

    public void init(final UserMessagesView view) {
        this.view = view;
    }
}
