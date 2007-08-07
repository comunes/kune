package org.ourproject.kune.docs.client.reader;

import org.ourproject.kune.platf.client.View;

public interface DocumentReaderView extends View {
    public void setContent(String content);

    public void setEditEnabled(boolean isEnabled);

    public String getContent();
}
