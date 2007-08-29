package org.ourproject.kune.chat.client.ui;

import org.ourproject.kune.chat.client.ui.rooms.RoomImages;
import org.ourproject.kune.chat.client.ui.rooms.RoomImages.App;

import com.google.gwt.user.client.ui.HTML;

public class ChatTextFormatter {

    public static HTML format(String message) {
        message = message.replaceAll("&", "&amp;");
        message = message.replaceAll("\"", "&quot;");
        message = message.replaceAll("<", "&lt;");
        message = message.replaceAll(">", "&gt;");
        message = message.replaceAll("\n", "<br>\n");
    
        // TODO: see emoticons.xml for more
        message = message.replaceAll(":\\)", RoomImages.App.getInstance().smile().getHTML());
        message = message.replaceAll(":-\\)", RoomImages.App.getInstance().smile().getHTML());
    
        message = message.replaceAll("X-\\(", RoomImages.App.getInstance().angry().getHTML());
        message = message.replaceAll("X\\(", RoomImages.App.getInstance().angry().getHTML());
    
        message = message.replaceAll(":-D", RoomImages.App.getInstance().grin().getHTML());
        message = message.replaceAll(":D", RoomImages.App.getInstance().grin().getHTML());
    
        message = message.replaceAll(":\\(", RoomImages.App.getInstance().sad().getHTML());
        message = message.replaceAll(":-\\(", RoomImages.App.getInstance().sad().getHTML());
    
        message = message.replaceAll(":P", RoomImages.App.getInstance().tongue().getHTML());
    
        message = message.replaceAll(":\'\\(", RoomImages.App.getInstance().crying().getHTML());
    
        message = message.replaceAll(":-O", RoomImages.App.getInstance().surprised().getHTML());
        message = message.replaceAll(":O", RoomImages.App.getInstance().surprised().getHTML());
    
        message = message.replaceAll(":-\\*", RoomImages.App.getInstance().kissing().getHTML());
        message = message.replaceAll(":\\*", RoomImages.App.getInstance().kissing().getHTML());
    
        message = message.replaceAll(":-/", RoomImages.App.getInstance().uncertain().getHTML());
    
        message = message.replaceAll(";\\)", RoomImages.App.getInstance().wink().getHTML());
        message = message.replaceAll(";-\\)", RoomImages.App.getInstance().wink().getHTML());
    
        message = message.replaceAll(":\\?", RoomImages.App.getInstance().wondering().getHTML());
    
        message = message.replaceAll(":-xX", RoomImages.App.getInstance().love().getHTML());
        message = message.replaceAll(":-X", RoomImages.App.getInstance().love().getHTML());
    
        return new HTML(message);
    }

}
