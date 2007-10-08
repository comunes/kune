package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.socialnet.ui.MemberAction;

public interface SocialNetworkView extends View {

    public static final String ICON_ALERT = "alert";

    void clear();

    void setDropDownContentVisible(boolean visible);

    void addCategory(String name, String title);

    void addCategory(String name, String title, String iconType);

    void addCategoryMember(String categoryName, String name, String title, MemberAction[] memberActions);

    void addJoinLink();

    void addAddMemberLink();

    void show();

    void hide();

}
