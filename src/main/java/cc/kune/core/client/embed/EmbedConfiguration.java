package cc.kune.core.client.embed;

import cc.kune.common.client.events.EventBusInstance;
import cc.kune.core.client.events.EmbAppStartEvent;

import com.google.inject.Singleton;

@Singleton
public class EmbedConfiguration {
  private static EmbedConfJso conf;

  public static native void export() /*-{
    $doc.confEmbed = $entry(@cc.kune.core.client.embed.EmbedConfiguration::init(Lcc/kune/core/client/embed/EmbedConfJso;))
  }-*/;

  public static EmbedConfJso get() {
    return conf;
  }

  public static void init(final EmbedConfJso conf) {
    EmbedConfiguration.conf = conf;
    EventBusInstance.get().fireEvent(new EmbAppStartEvent(conf));
  }

  public static boolean isReady() {
    return conf != null;
  }

  public EmbedConfiguration() {
  }

}
