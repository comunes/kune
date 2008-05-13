package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CommentDTO implements IsSerializable {

    private Long id;
    // private ContentDTO content;
    private Long publishedOn;
    private UserSimpleDTO author;
    private CommentDTO parent;
    private List<CommentDTO> childs;
    private int positiveVotersCount;
    private int negativeVotersCount;
    private int abuseInformersCount;
    private String text;

    public int getAbuseInformersCount() {
        return abuseInformersCount;
    }

    public UserSimpleDTO getAuthor() {
        return author;
    }

    public List<CommentDTO> getChilds() {
        return childs;
    }

    public Long getId() {
        return id;
    }

    public int getNegativeVotersCount() {
        return negativeVotersCount;
    }

    public CommentDTO getParent() {
        return parent;
    }

    public int getPositiveVotersCount() {
        return positiveVotersCount;
    }

    public Long getPublishedOn() {
        return publishedOn;
    }

    public String getText() {
        return text;
    }

    public void setAbuseInformersCount(final int abuseInformersCount) {
        this.abuseInformersCount = abuseInformersCount;
    }

    public void setAuthor(final UserSimpleDTO author) {
        this.author = author;
    }

    public void setChilds(final List<CommentDTO> childs) {
        this.childs = childs;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setNegativeVotersCount(final int negativeVotersCount) {
        this.negativeVotersCount = negativeVotersCount;
    }

    public void setParent(final CommentDTO parent) {
        this.parent = parent;
    }

    public void setPositiveVotersCount(final int positiveVotersCount) {
        this.positiveVotersCount = positiveVotersCount;
    }

    public void setPublishedOn(final Long publishedOn) {
        this.publishedOn = publishedOn;
    }

    public void setText(final String text) {
        this.text = text;
    }

}
