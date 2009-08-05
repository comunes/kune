package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.AbstractAction;

public class MenuCheckItemDescriptor extends MenuItemDescriptor {

    public static final String CHECKED = "checked";

    private boolean checked = false;

    public MenuCheckItemDescriptor(final MenuDescriptor parent, final AbstractAction action) {
        super(parent, action);
    }

    @Override
    public Class<?> getType() {
        return MenuCheckItemDescriptor.class;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(final boolean checked) {
        if (checked != this.checked) {
            this.checked = checked;
            putValue(CHECKED, this.checked);
        }
    }
}
