package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupListDTO implements IsSerializable {
    public static final String EVERYONE = "EVERYONE";
    public static final String NOBODY = "NOBODY";
    public static final String NORMAL = "NORMAL";
    private List<GroupDTO> list;
    private String mode;

    public GroupListDTO() {
        this(null);
    }

    public GroupListDTO(final List<GroupDTO> list) {
        this.list = list;
    }

    public List<GroupDTO> getList() {
        return list;
    }

    public void setList(final List<GroupDTO> list) {
        this.list = list;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

}
