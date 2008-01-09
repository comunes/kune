package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TagDTO implements IsSerializable {

    private String name;

    public TagDTO() {
        this(null);
    }

    public TagDTO(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
