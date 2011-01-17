package cc.kune.common.client.actions.ui.descrip;

import java.util.ArrayList;


// @PMD:REVIEWED:AtLeastOneConstructor: by vjrj on 26/05/09 12:31
public class GuiActionDescCollection extends ArrayList<AbstractGuiActionDescrip> {

    private static final long serialVersionUID = 6759723760404227737L;

    public void add(final AbstractGuiActionDescrip... descriptors) {
        for (AbstractGuiActionDescrip descriptor : descriptors) {
            super.add(descriptor);
        }
    }

}
