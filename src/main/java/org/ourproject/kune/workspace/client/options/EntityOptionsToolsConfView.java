package org.ourproject.kune.workspace.client.options;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ToolSimpleDTO;

public interface EntityOptionsToolsConfView extends View {

    void add(ToolSimpleDTO tool);

    void clear();

    void setChecked(String tool, boolean checked);

    void setEnabled(String tool, boolean enabled);

    void setTooltip(String tool, String tip);
}
