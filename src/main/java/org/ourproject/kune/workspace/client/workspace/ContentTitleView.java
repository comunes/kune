
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;

public interface ContentTitleView extends View {

    void setContentTitle(String title);

    void setContentDate(String date);

    void setDateVisible(boolean visible);

    void setContentTitleEditable(boolean editable);

    void restoreOldTitle();

}
