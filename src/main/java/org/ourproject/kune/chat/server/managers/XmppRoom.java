
package org.ourproject.kune.chat.server.managers;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class XmppRoom implements Room, PacketListener {
    private final String alias;
    private RoomListener listener;
    private final MultiUserChat muc;

    public XmppRoom(final MultiUserChat muc, final String alias) {
	this.muc = muc;
	this.alias = alias;
    }

    public String getAlias() {
	return alias;
    }

    public void processPacket(final Packet packet) {
	if (packet instanceof Message) {
	    processMessage((Message) packet);
	}
    }

    private void processMessage(final Message message) {
	listener.onMessage(message.getFrom(), message.getTo(), message.getBody());
    }

    public void setListener(final RoomListener listener) {
	this.listener = listener;
    }

    public MultiUserChat getMuc() {
	return muc;
    }

}
