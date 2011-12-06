package cc.kune.msgs.client;

public interface UserMessage {

  void appendMsg(String message);

  void close();

  boolean isAttached();
}
