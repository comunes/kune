package cc.kune.msgs.client;

import cc.kune.common.client.noti.NotifyLevel;

public class UserMessagesPresenter {

    private UserMessagesView view;

    public interface UserMessagesView {
        void add(NotifyLevel level, String title, String message, boolean closable, CloseCallback callback);
    }

    public UserMessagesPresenter() {
    }

    public void init(UserMessagesView view) {
        this.view = view;
    }

    public void add(NotifyLevel level, String title, String message, boolean closable, CloseCallback closeCallback) {
        view.add(level, title, message, closable, closeCallback);
    }
}
