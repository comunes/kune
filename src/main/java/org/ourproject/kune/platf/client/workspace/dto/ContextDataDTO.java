package org.ourproject.kune.platf.client.workspace.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ContextDataDTO implements IsSerializable {
    private ArrayList items;

    public ContextDataDTO() {
	items = new ArrayList();
    }

    public void setItems(ArrayList items) {
	this.items = items;
    }

    public ArrayList getItems() {
        return items;
    }

}
