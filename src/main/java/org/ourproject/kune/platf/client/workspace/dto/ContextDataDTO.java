package org.ourproject.kune.platf.client.workspace.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContextDataDTO implements IsSerializable {
    private String contextRef;
    private ArrayList children;
    private ArrayList parents;
    // seleccionado, si es que hay alguno seleccionado
    // pensar algo así como NONE = -1
    private int defaultIndex;

    public ContextDataDTO() {
	this(null);
    }

    public ContextDataDTO(final String contextRef) {
	this.contextRef = contextRef;
	children = new ArrayList();
	defaultIndex = 0;
    }

    public void setChildren(final ArrayList items) {
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

    public void add(final ContextItemDTO item) {
	children.add(item);
    }

    public ArrayList getParents() {
	return parents;
    }

    public void setParents(final ArrayList parents) {
	this.parents = parents;
    }

    public void setContextRef(final String contextRef) {
	this.contextRef = contextRef;
    }

    public void setDefaultIndex(final int defaultIndex) {
	this.defaultIndex = defaultIndex;
    }

}
