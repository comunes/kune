package org.ourproject.kune.platf.client.actions.ui;

import java.util.ArrayList;

// @PMD:REVIEWED:AtLeastOneConstructor: by vjrj on 26/05/09 12:31
public class GuiActionDescCollection extends ArrayList<GuiActionDescrip> {

    private static final long serialVersionUID = 6759723760404227737L;

    public void add(final GuiActionDescrip... descriptors) {
        for (GuiActionDescrip descriptor : descriptors) {
            super.add(descriptor);
        }
    }

}
