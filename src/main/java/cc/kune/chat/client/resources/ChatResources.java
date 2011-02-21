package cc.kune.chat.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface ChatResources extends ClientBundle {
    public interface ChatIconCssResource extends CssResource {
    }

    @Source("add.png")
    ImageResource add();

    @Source("away.png")
    ImageResource away();

    @Source("busy.png")
    ImageResource busy();

    @Source("cancel.png")
    ImageResource cancel();

    @Source("e-icon.gif")
    ImageResource chat();

    @Source("e-icon-a.gif")
    ImageResource chatBlink();

    @Source("chat-new-message-small.png")
    ImageResource chatNewMessageSmall();

    @Source("chat.png")
    ImageResource chatNormal();

    @Source("chat-small.png")
    ImageResource chatSmall();

    @Source("chat-icons.css")
    ChatIconCssResource css();

    @Source("del.png")
    ImageResource del();

    @Source("group-chat.png")
    ImageResource groupChat();

    @Source("info.png")
    ImageResource info();

    @Source("info-lamp.png")
    ImageResource infoLamp();

    @Source("invisible.png")
    ImageResource invisible();

    @Source("message.png")
    ImageResource message();

    @Source("new-chat.png")
    ImageResource newChat();

    @Source("new-email.png")
    ImageResource newEmail();

    @Source("new-message.png")
    ImageResource newMessage();

    @Source("not-authorized.png")
    ImageResource notAuthorized();

    @Source("offline.png")
    ImageResource offline();

    @Source("online.png")
    ImageResource online();

    @Source("question.png")
    ImageResource question();

    @Source("room-new-message-small.png")
    ImageResource roomNewMessageSmall();

    @Source("room-small.png")
    ImageResource roomSmall();

    @Source("unavailable.png")
    ImageResource unavailable();

    @Source("user_add.png")
    ImageResource user_add();

    @Source("xa.png")
    ImageResource xa();
}