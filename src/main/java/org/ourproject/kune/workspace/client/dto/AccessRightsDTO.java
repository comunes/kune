package org.ourproject.kune.workspace.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AccessRightsDTO implements IsSerializable {
    public boolean isAdministrable;
    public boolean isEditable;
    public boolean isVisible;
}
