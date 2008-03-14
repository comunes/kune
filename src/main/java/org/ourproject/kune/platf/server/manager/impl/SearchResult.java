package org.ourproject.kune.platf.server.manager.impl;

import java.util.List;

public class SearchResult<T> {
    List<T> list;
    Long size;

    public SearchResult() {
        this(null, null);
    }

    public SearchResult(final Long count, final List<T> list) {
        this.list = list;
        this.size = count;
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
