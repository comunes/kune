package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupListDTO implements IsSerializable {

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.GroupDTO>
     */
    private List list;

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

}
