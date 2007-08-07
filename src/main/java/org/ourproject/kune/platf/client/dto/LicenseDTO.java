package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LicenseDTO implements IsSerializable {

    private String shortName;

    private String longName;

    private String description;

    private String imageUrl;

    private boolean isCC;

    private boolean isCopyleft;

    private boolean isDeprecated;

    private String rdf;

    private String url;

    public LicenseDTO() {
	this(null, null, null, null, false, false, false, null, null);
    }

    public LicenseDTO(final String shortName, final String longName, final String description, final String imageUrl,
	    final boolean isCC, final boolean isCopyleft, final boolean isDeprecated, final String rdf, final String url) {
	this.shortName = shortName;
	this.longName = longName;
	this.description = description;
	this.imageUrl = imageUrl;
	this.isCC = isCC;
	this.isCopyleft = isCopyleft;
	this.isDeprecated = isDeprecated;
	this.rdf = rdf;
	this.url = url;
    }

    public String getShortName() {
	return shortName;
    }

    public void setShortName(final String shortName) {
	this.shortName = shortName;
    }

    public String getLongName() {
	return longName;
    }

    public void setLongName(final String longName) {
	this.longName = longName;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(final String description) {
	this.description = description;
    }

    public String getImageUrl() {
	return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
	this.imageUrl = imageUrl;
    }

    public String getRdf() {
	return rdf;
    }

    public void setRdf(final String rdf) {
	this.rdf = rdf;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(final String url) {
	this.url = url;
    }

    public boolean isCC() {
	return isCC;
    }

    public boolean isCopyleft() {
	return isCopyleft;
    }

    public boolean isDeprecated() {
	return isDeprecated;
    }

    public void setCC(final boolean isCC) {
	this.isCC = isCC;
    }

    public void setCopyleft(final boolean isCopyleft) {
	this.isCopyleft = isCopyleft;
    }

    public void setDeprecated(final boolean isDeprecated) {
	this.isDeprecated = isDeprecated;
    }

}
