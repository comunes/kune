package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupDTO implements IsSerializable {
    public static final int TYPE_A = 1;
    public static final int TYPE_B = 1;
    public static final int TYPE_C = 1;

    private Long id;
    private String shortName;
    private String name;

    public GroupDTO(final String name, final String shortName) {
	this.name = name;
	this.shortName = shortName;
    }

    public GroupDTO() {
	this(null, null);
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getShortName() {
	return shortName;
    }

    public void setShortName(final String shortName) {
	this.shortName = shortName;
    }
}
