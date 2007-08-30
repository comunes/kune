package org.ourproject.kune.chat.client.cnt.info;

import org.ourproject.kune.chat.client.ChatState;
import org.ourproject.kune.platf.client.Component;

public interface ChatInfo extends Component {
    void setChatState(ChatState state);
}
