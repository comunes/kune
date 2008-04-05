
package org.ourproject.kune.docs.client.cnt.reader;

import org.ourproject.kune.platf.client.View;

public interface DocumentReaderControlView extends View {

    public void setEditEnabled(boolean isEnabled);

    public void setDeleteEnabled(boolean isEnabled);

    public void setTranslateEnabled(boolean isEnabled);

}
