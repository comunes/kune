package org.ourproject.kune.workspace.client.wave;

import org.ourproject.kune.platf.client.View;

public interface WaveInsertView extends View {

    String getWaveId();

    void hide();

    void reset();

    void show();

}
