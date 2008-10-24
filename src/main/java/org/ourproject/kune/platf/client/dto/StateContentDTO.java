package org.ourproject.kune.platf.client.dto;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StateContentDTO extends StateContainerDTO implements IsSerializable {

    private String documentId;
    private int version;
    private String content;
    private AccessRightsDTO contentRights;
    private ContentStatusDTO status;
    private boolean isRateable;
    private Double rate;
    private Integer rateByUsers;
    private Double currentUserRate;
    private BasicMimeTypeDTO mimeType;
    private Date publishedOn;
    private String tags;
    private List<UserSimpleDTO> authors;

    public StateContentDTO() {
    }

    public List<UserSimpleDTO> getAuthors() {
        return authors;
    }

    public String getContent() {
        return content;
    }

    public AccessRightsDTO getContentRights() {
        return contentRights;
    }

    public Double getCurrentUserRate() {
        return currentUserRate;
    }

    public String getDocumentId() {
        return documentId;
    }

    public BasicMimeTypeDTO getMimeType() {
        return mimeType;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getRateByUsers() {
        return rateByUsers;
    }

    public ContentStatusDTO getStatus() {
        return status;
    }

    public String getTags() {
        return tags;
    }

    public int getVersion() {
        return version;
    }

    public boolean isRateable() {
        return isRateable;
    }

    public void setAuthors(List<UserSimpleDTO> authors) {
        this.authors = authors;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentRights(AccessRightsDTO contentRights) {
        this.contentRights = contentRights;
    }

    public void setCurrentUserRate(Double currentUserRate) {
        this.currentUserRate = currentUserRate;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setMimeType(BasicMimeTypeDTO mimeType) {
        this.mimeType = mimeType;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setRateable(boolean isRateable) {
        this.isRateable = isRateable;
    }

    public void setRateByUsers(Integer rateByUsers) {
        this.rateByUsers = rateByUsers;
    }

    public void setStatus(ContentStatusDTO status) {
        this.status = status;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "StateDTO[" + getStateToken() + "/" + getTypeId() + (mimeType != null ? "-" + mimeType : "") + "]";
    }

}
