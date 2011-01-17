package cc.kune.common.client.actions.ui.bind;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;

public abstract class AbstractGuiBinding implements GuiBinding {

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        return null;
    }

    @Override
    public boolean shouldBeAdded() {
        return true;
    }

}
