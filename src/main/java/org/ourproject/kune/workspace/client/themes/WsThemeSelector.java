package org.ourproject.kune.workspace.client.themes;

import org.ourproject.kune.platf.client.View;

import com.calclab.suco.client.events.Listener;

public interface WsThemeSelector {

    void addThemeSelected(Listener<WsTheme> listener);

    View getView();

    void select(String theme);

}
