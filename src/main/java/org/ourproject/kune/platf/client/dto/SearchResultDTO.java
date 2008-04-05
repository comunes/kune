package org.ourproject.kune.platf.client.dto;

import java.util.List;

public class SearchResultDTO<T> {
    List<T> list;
    Long size;

    public SearchResultDTO() {
        this(null, null);
    }

    public SearchResultDTO(final Long size, final List<T> list) {
        this.list = list;
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(final List<T> list) {
        this.list = list;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(final Long size) {
        this.size = size;
    }

}
