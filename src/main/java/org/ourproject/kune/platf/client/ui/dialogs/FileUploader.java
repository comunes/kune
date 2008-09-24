package org.ourproject.kune.platf.client.ui.dialogs;

public interface FileUploader {

    public static final String USERHASH = "userhash";
    public static final String CURRENT_STATE_TOKEN = "currentStateToken";
    public static final String FILENAME = "filename";

    void hide();

    void setPermittedExtensions(String extensions);

    void show();

}
