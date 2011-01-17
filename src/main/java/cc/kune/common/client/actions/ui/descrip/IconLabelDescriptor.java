package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.resources.client.ImageResource;

public class IconLabelDescriptor extends AbstractGuiActionDescrip {

    public IconLabelDescriptor(final AbstractAction action) {
        super(action);
    }

    public IconLabelDescriptor(final String text) {
        this(new BaseAction(text, null));
    }

    public IconLabelDescriptor(final String text, final ImageResource icon) {
        this(new BaseAction(text, null, icon));
    }

    public IconLabelDescriptor(final String text, final String tooltip) {
        this(new BaseAction(text, tooltip));
    }

    public IconLabelDescriptor(final String text, final String tooltip, final ImageResource icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    public IconLabelDescriptor(final String text, final String tooltip, final String icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    @Override
    public Class<?> getType() {
        return IconLabelDescriptor.class;
    }

}