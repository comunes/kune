package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;

public class MenuCheckItemDescriptor extends MenuItemDescriptor {

    public static final String CHECKED = "checked";

    public MenuCheckItemDescriptor(final MenuDescriptor parent, final AbstractAction action) {
        super(parent, action);
        setCheckedImpl(false);
    }

    @Override
    public Class<?> getType() {
        return MenuCheckItemDescriptor.class;
    }

    public boolean isChecked() {
        return (Boolean) getValue(CHECKED);
    }

    public void setChecked(final boolean checked) {
        setCheckedImpl(checked);
    }

    private void setCheckedImpl(final boolean checked) {
        putValue(CHECKED, checked);
    }
}
