package cc.kune.core.client.embed;

import com.google.inject.Singleton;

@Singleton
public class EmbedJsActions {

  public static void embed(final String containerId, final String stateToken) {
  }

  public static native void export() /*-{
    $doc.embed = $entry(@cc.kune.core.client.embed.EmbedJsActions::embed(Ljava/lang/String;Ljava/lang/String;))
  }-*/;

  public EmbedJsActions() {
  }

}