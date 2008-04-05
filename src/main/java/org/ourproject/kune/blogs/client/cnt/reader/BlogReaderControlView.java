package org.ourproject.kune.blogs.client.cnt.reader;

import org.ourproject.kune.platf.client.View;

public interface BlogReaderControlView extends View {

    public void setEditEnabled(boolean isEnabled);

    public void setDeleteEnabled(boolean isEnabled);

    public void setTranslateEnabled(boolean isEnabled);

}
