package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;

public interface ParticipationView extends View {

    void hide();

    void addCategory(String name, String title);

    void addCategoryMember(String categoryName, String name, String title, MemberAction[] memberActions);

    void show();

    void setDropDownContentVisible(boolean visible);

    void clear();

}
