package org.ourproject.kune.platf.client.ui.upload;

public interface FileUploader {

    boolean hasUploadingFiles();

    void hide();

    void resetPermittedExtensions();

    void setPermittedExtensions(String extensions);

    void show();

}
