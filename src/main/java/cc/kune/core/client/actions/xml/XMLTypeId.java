package cc.kune.core.client.actions.xml;

import com.calclab.emite.core.client.packet.DelegatedPacket;
import com.calclab.emite.core.client.packet.IPacket;

public class XMLTypeId extends DelegatedPacket {

  public XMLTypeId(final IPacket delegate) {
    super(delegate);
  }

  public String getDestTypeId() {
    return getAttribute("destTypeId");
  }

  public String getOrigTypeId() {
    return getAttribute("origTypeId");
  }

}
