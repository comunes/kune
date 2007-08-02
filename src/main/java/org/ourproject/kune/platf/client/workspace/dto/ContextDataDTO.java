package org.ourproject.kune.platf.client.workspace.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContextDataDTO implements IsSerializable {
    private ArrayList items;
    private int defaultIndex;
    private String contextRef;

    public ContextDataDTO() {
        this(null);
    }

    public ContextDataDTO(String contextRef) {
    this.contextRef = contextRef;
    items = new ArrayList();
    defaultIndex = 0;
    }

    public void setItems(ArrayList items) {
    this.items = items;
    }

    public ArrayList getItems() {
        return items;
    }

    public int getDefaultIndex() {
    return defaultIndex;
    }

    public String getContextRef() {
    return contextRef;
    }

    public void add(ContextItemDTO item) {
    items.add(item);
    }

    public void setContextRef(String contextRef) {
        this.contextRef = contextRef;
    }

    public void setDefaultIndex(int defaultIndex) {
        this.defaultIndex = defaultIndex;
    }

}
