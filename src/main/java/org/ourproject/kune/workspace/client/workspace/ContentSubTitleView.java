
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.platf.client.View;

public interface ContentSubTitleView extends View {

    void setContentSubTitleLeft(String subTitle);

    void setContentSubTitleRight(String subTitle);

    void setContentSubTitleLeftVisible(boolean visible);

    void setContentSubTitleRightVisible(boolean visible);
}
