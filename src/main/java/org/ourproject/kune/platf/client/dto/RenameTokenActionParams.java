package org.ourproject.kune.platf.client.dto;

public class RenameTokenActionParams {

    private final String token;
    private final String newName;

    public RenameTokenActionParams(final String token, final String newName) {
        this.token = token;
        this.newName = newName;
    }

    public String getToken() {
        return token;
    }

    public String getNewName() {
        return newName;
    }

}
