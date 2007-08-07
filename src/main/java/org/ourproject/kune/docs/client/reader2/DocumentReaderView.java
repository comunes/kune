package org.ourproject.kune.docs.client.reader2;

import org.ourproject.kune.platf.client.View;

public interface DocumentReaderView extends View {
    void setContent(String content);
    void setContentName(String name);
}
