package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BasicMimeTypeDTO implements IsSerializable {

    private String type;
    private String subtype;

    public BasicMimeTypeDTO() {
	this(null, null);
    }

    public BasicMimeTypeDTO(final String mimetype) {
	if (mimetype != null) {
	    final String[] split = mimetype.split("/", 2);
	    type = split[0];
	    if (split.length > 1 && split[1].length() > 0) {
		subtype = split[1];
	    }
	}
    }

    public BasicMimeTypeDTO(final String type, final String subtype) {
	this.type = type;
	this.subtype = subtype;
    }

    public String getSubtype() {
	return subtype;
    }

    public String getType() {
	return type;
    }

    public void setSubtype(final String subtype) {
	this.subtype = subtype;
    }

    public void setType(final String type) {
	this.type = type;
    }

    public String toString() {
	return subtype == null ? type : type + "/" + subtype;
    }

}
