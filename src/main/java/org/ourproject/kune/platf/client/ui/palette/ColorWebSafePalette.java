package org.ourproject.kune.platf.client.ui.palette;

import com.calclab.suco.client.listener.Listener;

public interface ColorWebSafePalette {

    void hide();

    void show(int left, int top, Listener<String> onColorSelected);

}
