package cc.kune.core.client.actions.xml;

import java.util.ArrayList;
import java.util.List;

import com.calclab.emite.core.client.packet.DelegatedPacket;
import com.calclab.emite.core.client.packet.IPacket;

public class XMLGuiActionDescriptor extends DelegatedPacket {

  private XMLRol rol;

  private List<XMLTypeId> typeIds;

  public XMLGuiActionDescriptor(final IPacket xml) {
    super(xml);
  }

  public String getDescName() {
    return getFirstChild("name").getText();
  }

  public String getDescription() {
    return getFirstChild("description").getText();
  }

  public String getExtensionName() {
    return getFirstChild("extensionName").getText();
  }

  public String getNewContentTextIntro() {
    return getFirstChild("new-content-textintro").getText();
  }

  public String getNewContentTitle() {
    return getFirstChild("new-content-title").getText();
  }

  public String getParticipants() {
    return getFirstChild("participants").getText();
  }

  public String getPath() {
    return getFirstChild("path").getText();
  }

  public XMLRol getRol() {
    if (rol == null) {
      rol = new XMLRol(getFirstChild("rol"));
    }
    return rol;
  }

  public String getType() {
    return getFirstChild("type").getText();
  }

  public List<XMLTypeId> getTypeIds() {
    if (typeIds == null) {
      typeIds = new ArrayList<XMLTypeId>();
      for (final IPacket p : getFirstChild("typeIds").getChildren()) {
        typeIds.add(new XMLTypeId(p));
      }
    }
    return typeIds;
  }

  public boolean isEnabled() {
    return Boolean.valueOf(getFirstChild("enabled").getText());
  }
}
