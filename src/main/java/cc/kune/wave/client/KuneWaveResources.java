package cc.kune.wave.client;

import org.waveprotocol.wave.client.wavepanel.view.dom.full.TopConversationViewBuilder;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

public class KuneWaveResources {
  // Not used now
  public interface Conversation extends ClientBundle {
    // Note: the CSS file contains a gwt-image reference, so must be defined
    // after the referenced images in this interface.
    @Source("KuneConversation.css")
    TopConversationViewBuilder.Css css();

    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    @Source("toolbar_empty.png")
    ImageResource emptyToolbar();
  }
}
