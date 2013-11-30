package cc.kune.core.client.embed;

import cc.kune.common.client.events.EventBusInstance;

import com.google.inject.Singleton;

@Singleton
public class EmbedJsActions {

  public static void embed(final String stateToken) {
    EventBusInstance.get().fireEvent(new EmbedOpenEvent(stateToken));
  }

  public static void embed(final String containerId, final String stateToken) {
    // Not implemented
  }

  public static native void export() /*-{
    $doc.embed = $entry(@cc.kune.core.client.embed.EmbedJsActions::embed(Ljava/lang/String;))
  }-*/;

  public EmbedJsActions() {
  }

}