package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LinkDTO implements IsSerializable {
    private String shortName;
    private String longName;
    private String iconUrl;
    private String link;
    private Double rate;
    private List<TagDTO> tags;

    public LinkDTO() {
        this(null, null, null, null);
    }

    public LinkDTO(final String shortName, final String longName, final String iconUrl, final String link) {
        this.shortName = shortName;
        this.longName = longName;
        this.iconUrl = iconUrl;
        this.link = link;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(final String longName) {
        this.longName = longName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(final String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(final Double rate) {
        this.rate = rate;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(final List<TagDTO> tags) {
        this.tags = tags;
    }

}
