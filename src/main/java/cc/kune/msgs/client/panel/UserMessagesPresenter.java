package cc.kune.msgs.client.panel;

import cc.kune.msgs.client.msgs.UserMessageLevel;

public class UserMessagesPresenter {

    private MessagesView view;

    public interface MessagesView {
        void add(UserMessageLevel info, String message, boolean closable);
    }

    public UserMessagesPresenter() {
    }

    public void init(MessagesView view) {
        this.view = view;
    }

    public void add(UserMessageLevel info, String message, boolean closable) {
        view.add(info, message, closable);
    }
}
