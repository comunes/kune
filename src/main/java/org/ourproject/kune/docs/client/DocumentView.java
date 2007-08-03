package org.ourproject.kune.docs.client;

import org.ourproject.kune.platf.client.View;

public interface DocumentView extends View {
    void setContent(String content);
    void setContentName(String name);
}
