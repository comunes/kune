package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;

public interface BuddiesSummaryView extends View {

    void addBuddie(UserSimpleDTO user, ActionItemCollection<UserSimpleDTO> actionCollection);

    void clear();

    void clearOtherUsers();

    void hide();

    void setNoBuddies();

    void setOtherUsers(String text);

    void show();
}
