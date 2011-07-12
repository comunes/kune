package cc.kune.core.client.actions.xml;

import com.calclab.emite.core.client.packet.DelegatedPacket;
import com.calclab.emite.core.client.packet.IPacket;

public class XMLWaveExtension extends DelegatedPacket {

  public XMLWaveExtension(final IPacket xml) {
    super(xml);
  }

  public String getExtName() {
    return getFirstChild("name").getText();
  }

  public String getGadgetUrl() {
    return getFirstChild("gadgetUrl").getText().trim();
  }

  public String getIconCss() {
    return getFirstChild("iconCss").getText().trim();
  }

  public String getIconUrl() {
    return getFirstChild("iconUrl").getText().trim();
  }

  public String getInstallerUrl() {
    return getFirstChild("installerUrl").getText().trim();
  }

}
