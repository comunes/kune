package cc.kune.chat.client;

import cc.kune.common.client.noti.NotifyUser;

import com.calclab.hablar.signals.client.notifications.HablarNotifier;

public class KuneChatNotifier implements HablarNotifier {

    @Override
    public String getDisplayName() {
        return "Bottom notifier";
    }

    @Override
    public String getId() {
        return "kuneChatNotifier";
    }

    @Override
    public void show(final String userMessage, final String messageType) {
        NotifyUser.info(userMessage);
    }

}
