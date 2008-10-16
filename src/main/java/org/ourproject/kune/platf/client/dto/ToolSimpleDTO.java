package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ToolSimpleDTO implements IsSerializable {
    private String name;
    private String rootName;

    public ToolSimpleDTO() {
        this(null, null);
    }

    public ToolSimpleDTO(String name, String rootName) {
        this.name = name;
        this.rootName = rootName;
    }

    public String getName() {
        return name;
    }

    public String getRootName() {
        return rootName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
}
