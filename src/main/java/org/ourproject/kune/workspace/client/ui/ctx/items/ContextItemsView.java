
package org.ourproject.kune.workspace.client.ui.ctx.items;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContainerSimpleDTO;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public interface ContextItemsView extends View {
    void clear();

    void addItem(String name, String type, String event, boolean editable);

    void selectItem(int index);

    void setControlsVisible(boolean isVisible);

    void setCurrentName(String name);

    void setParentButtonEnabled(boolean isEnabled);

    void setParentTreeVisible(boolean b);

    void registerType(String typeName, AbstractImagePrototype image);

    void addCommand(String typeName, String label, String eventName);

    void showCreationField(String typeName);

    void setAbsolutePath(ContainerSimpleDTO[] absolutePath);

}
