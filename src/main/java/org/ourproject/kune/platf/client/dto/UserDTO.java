package org.ourproject.kune.platf.client.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDTO implements IsSerializable {
    private String name;

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public String getChatName() {
	return "testUser1";
    }

    public String getChatPassword() {
	return "easy1";
    }

}
