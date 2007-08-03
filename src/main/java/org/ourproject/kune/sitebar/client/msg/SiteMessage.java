package org.ourproject.kune.sitebar.client.msg;

public interface SiteMessage {
    void info(String message);

    void important(String message);

    void veryImportant(String message);

    void error(String message);
}
