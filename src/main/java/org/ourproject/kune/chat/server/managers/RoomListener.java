
package org.ourproject.kune.chat.server.managers;

public interface RoomListener {

    void onMessage(String from, String to, String body);

}
