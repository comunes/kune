package org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg;

import org.ourproject.kune.platf.client.View;

public interface InsertImageAbstractView extends View {

    boolean getClickOriginal();

    String getPosition();

    String getSize();

    String getSrc();

    boolean getWrapText();

    boolean isValid();

    void reset();

}
