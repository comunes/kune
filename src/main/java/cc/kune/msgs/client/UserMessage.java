package cc.kune.msgs.client;

import com.google.gwt.event.dom.client.HasClickHandlers;

public interface UserMessage extends HasClickHandlers {

  void appendMsg(String message);

  void close();

  boolean isAttached();
}
