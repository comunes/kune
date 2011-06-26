package cc.kune.core.client.actions.xml;

import cc.kune.core.shared.dto.AccessRolDTO;

import com.calclab.emite.core.client.packet.DelegatedPacket;
import com.calclab.emite.core.client.packet.IPacket;

public class XMLRol extends DelegatedPacket {

  public XMLRol(final IPacket delegate) {
    super(delegate);
  }

  public AccessRolDTO getRolRequired() {
    return AccessRolDTO.valueOf(getFirstChild("rolRequired").getText());
  }

  public boolean isAuthNeed() {
    return Boolean.valueOf(getFirstChild("authNeed").getText());
  }
}
