package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupDTO implements IsSerializable {
    public static final int TYPE_ORGANIZATION = 0;
    public static final int TYPE_COMNUNITY = 1;
    public static final int TYPE_PROJECT = 2;

    private Long id;
    private String shortName;
    private String name;
    private String publicDesc;
    private Long defaultLicense;
    private int type;

    public GroupDTO() {
	this(null, null, null, null, TYPE_ORGANIZATION);
    }

    public GroupDTO(final String name, final String shortName, final String publicDesc, final Long defaultLicense,
	    final int type) {
	this.name = name;
	this.shortName = shortName;
	this.publicDesc = publicDesc;
	this.defaultLicense = defaultLicense;
	this.type = type;
    }

    public Long getDefaultLicense() {
	return defaultLicense;
    }

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getPublicDesc() {
	return publicDesc;
    }

    public String getShortName() {
	return shortName;
    }

    public int getType() {
	return type;
    }

    public void setDefaultLicense(final Long defaultLicense) {
	this.defaultLicense = defaultLicense;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public void setPublicDesc(final String publicDesc) {
	this.publicDesc = publicDesc;
    }

    public void setShortName(final String shortName) {
	this.shortName = shortName;
    }

    public void setType(final int type) {
	this.type = type;
    }
}
