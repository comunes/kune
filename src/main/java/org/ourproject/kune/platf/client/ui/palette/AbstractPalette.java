package org.ourproject.kune.platf.client.ui.palette;

import com.calclab.suco.client.events.Listener;

public interface AbstractPalette {

    void hide();

    void show(int left, int top, Listener<String> onColorSelected);

}
