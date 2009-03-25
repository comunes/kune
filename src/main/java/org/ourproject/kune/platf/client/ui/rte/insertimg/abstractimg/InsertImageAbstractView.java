package org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg;

import org.ourproject.kune.platf.client.View;

public interface InsertImageAbstractView extends View {

    String getPosition();

    String getSize();

    String getSrc();

    boolean isValid();

    void reset();

    boolean wrapText();
}
