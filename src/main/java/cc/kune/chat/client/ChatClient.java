package cc.kune.chat.client;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;

public interface ChatClient {

    void addNewBuddie(String shortName);

    void chat(XmppURI jid);

    boolean isBuddie(String localUserName);

    boolean isBuddie(XmppURI jid);

    boolean isLoggedIn();

    void joinRoom(String roomName, String userAlias);

    void joinRoom(String roomName, String subject, String userAlias);

    void login(XmppURI uri, String passwd);

    void logout();

    void setAvatar(String photoBinary);

    void show();

}
