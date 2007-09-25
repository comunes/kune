package org.ourproject.kune.workspace.client.presence;

import org.ourproject.kune.platf.client.View;

public interface BuddiesPresenceView extends View {

    void removeRoster(String name, String category);

    void addRoster(String name, String category, int status);

}
