package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;

public interface BuddiesSummaryView extends View {

    void addBuddie(UserSimpleDTO user);

    void clear();

    void hide();

    void setOtherUsers(int otherExternalBuddies);

    void show();
}
