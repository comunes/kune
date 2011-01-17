package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.BaseAction;

import com.google.gwt.resources.client.ImageResource;

public class ImageLabelDescriptor extends AbstractGuiActionDescrip {

    public ImageLabelDescriptor(final AbstractAction action) {
        super(action);
    }

    public ImageLabelDescriptor(final String text) {
        this(new BaseAction(text, null));
    }

    public ImageLabelDescriptor(final String text, final ImageResource icon) {
        this(new BaseAction(text, null, icon));
    }

    public ImageLabelDescriptor(final String text, final String tooltip) {
        this(new BaseAction(text, tooltip));
    }

    public ImageLabelDescriptor(final String text, final String tooltip, final ImageResource icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    public ImageLabelDescriptor(final String text, final String tooltip, final String icon) {
        this(new BaseAction(text, tooltip, icon));
    }

    @Override
    public Class<?> getType() {
        return ImageLabelDescriptor.class;
    }

}