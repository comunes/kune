package org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia;

import org.ourproject.kune.platf.client.View;

public interface InsertMediaAbstractView extends View {

    String getSrc();

    boolean isValid();

    void reset();

    String setPosition(String embedElement);

}
