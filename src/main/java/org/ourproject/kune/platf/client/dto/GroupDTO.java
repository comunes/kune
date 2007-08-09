package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GroupDTO implements IsSerializable {
    public static final int TYPE_ORGANIZATION = 0;
    public static final int TYPE_COMNUNITY = 1;
    public static final int TYPE_PROJECT = 2;
    public static final int TYPE_PERSONAL = 3;

    private Long id;
    private String shortName;
    private String longName;
    private String publicDesc;
    private String defaultLicenseShortName;
    private int type;

    public GroupDTO() {
	this(null, null, null, null, TYPE_ORGANIZATION);
    }

    public GroupDTO(final String name, final String shortName, final String publicDesc,
	    final String defaultLicenseShortName, final int type) {
	this.longName = name;
	this.shortName = shortName;
	this.publicDesc = publicDesc;
	this.defaultLicenseShortName = defaultLicenseShortName;
	this.type = type;
    }

    public String getDefaultLicense() {
	return defaultLicenseShortName;
    }

    public Long getId() {
	return id;
    }

    public String getLongName() {
	return longName;
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

    public void setDefaultLicense(final String defaultLicense) {
	this.defaultLicenseShortName = defaultLicense;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public void setLongName(final String name) {
	this.longName = name;
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
