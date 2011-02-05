package cc.kune.chat.client;

import cc.kune.chat.client.ChatClientDefault.ChatClientAction;

import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.signals.client.SignalPreferences;
import com.calclab.hablar.signals.client.unattended.UnattendedChatsChangedEvent;
import com.calclab.hablar.signals.client.unattended.UnattendedChatsChangedHandler;
import com.calclab.hablar.signals.client.unattended.UnattendedPagesManager;

/**
 * Handles the presentation of unattended chats
 */
public class KuneUnattendedPresenter {
    private boolean active;

    public KuneUnattendedPresenter(final HablarEventBus hablarEventBus, final SignalPreferences preferences,
            final UnattendedPagesManager unattendedManager, final ChatClientAction action) {
        active = false;
        hablarEventBus.addHandler(UnattendedChatsChangedEvent.TYPE, new UnattendedChatsChangedHandler() {
            @Override
            public void handleUnattendedChatChange(final UnattendedChatsChangedEvent event) {
                final int unattendedChatsCount = unattendedManager.getSize();
                if (unattendedChatsCount > 0 && active == false) {
                    active = true;
                    action.setBlink(true);
                } else if (unattendedChatsCount == 0 && active == true) {
                    action.setBlink(false);
                    active = false;
                }
            }

        });
    }
}
