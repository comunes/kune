package org.ourproject.kune.platf.client.ui.upload;

public interface FileUploaderView {

    void destroy();

    boolean hasUploadingFiles();

    void hide();

    void resetPermittedExtensions();

    void setPermittedExtensions(String extensions);

    void setUploadParams(String userhash, String currentUploadStateToken);

    void show();

}
