package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupListDTO implements IsSerializable {
    public static final String EVERYONE = "EVERYONE";
    public static final String NOBODY = "NOBODY";
    public static final String NORMAL = "NORMAL";
    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.GroupDTO>
     */
    private List list;
    private String mode;

    public GroupListDTO() {
	this(null);
    }

    public GroupListDTO(final List list) {
	this.list = list;
    }

    public List getList() {
	return list;
    }

    public void setList(final List list) {
	this.list = list;
    }

    public String getMode() {
	return mode;
    }

    public void setMode(String mode) {
	this.mode = mode;
    }

}
