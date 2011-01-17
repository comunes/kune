package cc.kune.common.client.actions.ui.bind;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;

public interface GuiBinding {

    AbstractGuiItem create(AbstractGuiActionDescrip descriptor);

    boolean shouldBeAdded();

}
