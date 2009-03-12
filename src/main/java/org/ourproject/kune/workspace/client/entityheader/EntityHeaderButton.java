package org.ourproject.kune.workspace.client.entityheader;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.IconLabel;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class EntityHeaderButton extends IconLabel implements View {
    public EntityHeaderButton(AbstractImagePrototype icon, String text) {
        super(icon, text, false);
        setDefStyle();
    }

    public EntityHeaderButton(String text, AbstractImagePrototype icon) {
        super(text, icon, false);
        setDefStyle();
    }

    private void setDefStyle() {
        addStyleName("kune-Margin-Medium-t");
        addStyleName("kune-pointer");
    }

}