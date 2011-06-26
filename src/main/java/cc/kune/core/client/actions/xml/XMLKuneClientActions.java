package cc.kune.core.client.actions.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.calclab.emite.core.client.packet.DelegatedPacket;
import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.services.Services;

public class XMLKuneClientActions extends DelegatedPacket {

  private Map<String, XMLWaveExtension> extensions;
  private List<XMLGuiActionDescriptor> guiActionDescriptors;

  public XMLKuneClientActions(final Services services, final String xmlS) {
    super(services.toXML(xmlS));
  }

  public Map<String, XMLWaveExtension> getExtensions() {
    if (extensions == null) {
      extensions = new HashMap<String, XMLWaveExtension>();
      for (final IPacket x : getFirstChild("waveExtensions").getChildren()) {
        final XMLWaveExtension extension = new XMLWaveExtension(x);
        extensions.put(extension.getExtName(), extension);
      }
    }
    return extensions;
  }

  public List<XMLGuiActionDescriptor> getGuiActionDescriptors() {
    if (guiActionDescriptors == null) {
      guiActionDescriptors = new ArrayList<XMLGuiActionDescriptor>();
      for (final IPacket x : getFirstChild("guiActionDescriptors").getChildren()) {
        guiActionDescriptors.add(new XMLGuiActionDescriptor(x));
      }
    }
    return guiActionDescriptors;
  }
}
