package org.ourproject.kune.platf.client.workspace.ui;

public interface SiteMessage {
    void info(String message);
    void important(String message);
    void veryImportant(String message);
    void error(String message);
}
