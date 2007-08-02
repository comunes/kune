package org.ourproject.kune.platf.client.workspace.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContextDataDTO implements IsSerializable {
    private ArrayList children;
    private ArrayList parents;
    // seleccionado, si es que hay alguno seleccionado
    // pensar algo así como NONE = -1
    private int defaultIndex;
    private String contextRef;

    public ContextDataDTO() {
	this(null);
    }

    public ContextDataDTO(String contextRef) {
	this.contextRef = contextRef;
	children = new ArrayList();
	defaultIndex = 0;
    }

    public void setChildren(ArrayList items) {
	this.children = items;
    }

    public ArrayList getChildren() {
	return children;
    }

    public int getDefaultIndex() {
	return defaultIndex;
    }

    public String getContextRef() {
	return contextRef;
    }

    public void add(ContextItemDTO item) {
	children.add(item);
    }

    public ArrayList getParents() {
	return parents;
    }

    public void setParents(ArrayList parents) {
	this.parents = parents;
    }

    public void setContextRef(String contextRef) {
	this.contextRef = contextRef;
    }

    public void setDefaultIndex(int defaultIndex) {
	this.defaultIndex = defaultIndex;
    }

}
