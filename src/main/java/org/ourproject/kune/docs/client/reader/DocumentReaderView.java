package org.ourproject.kune.docs.client.reader;

import org.ourproject.kune.platf.client.View;

public interface DocumentReaderView extends View {
    void setContent(String content);
    void setContentName(String name);
}
