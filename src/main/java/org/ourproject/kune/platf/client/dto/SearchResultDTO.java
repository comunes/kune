package org.ourproject.kune.platf.client.dto;

import java.util.List;

public class SearchResultDTO {
    List list;
    Integer size;

    public SearchResultDTO() {
        this(null, null);
    }

    public SearchResultDTO(final Integer size, final List list) {
        this.list = list;
        this.size = size;
    }

    public List getList() {
        return list;
    }

    public void setList(final List list) {
        this.list = list;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(final Integer size) {
        this.size = size;
    }

}
