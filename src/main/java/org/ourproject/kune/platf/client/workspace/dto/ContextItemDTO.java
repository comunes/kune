package org.ourproject.kune.platf.client.workspace.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContextItemDTO implements IsSerializable {
    private final String name;
    private final String type;
    private final String reference;

    public ContextItemDTO(String name, String type, String reference) {
	this.name = name;
	this.type = type;
	this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getReference() {
        return reference;
    }


}
