package org.ourproject.kune.workspace.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContextItemDTO implements IsSerializable {
    private String name;
    private String type;
    private String reference;
    private transient String token;

    public ContextItemDTO() {
        this(null, null, null);
    }

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

    public void setName(String name) {
    this.name = name;
    }

    public void setType(String type) {
    this.type = type;
    }

    public void setReference(String reference) {
    this.reference = reference;
    }

    public void setToken(String token) {
    this.token = token;
    }

    public String getToken() {
        return token;
    }


}
