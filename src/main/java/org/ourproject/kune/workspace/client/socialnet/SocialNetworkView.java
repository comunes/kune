package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.workspace.client.socialnet.ui.MemberAction;

public interface SocialNetworkView extends View {

    void clear();

    void setDropDownContentVisible(boolean visible);

    void addCategory(String name, String title);

    void addCategoryMember(String categoryName, String name, String title, MemberAction[] memberActions);

    void addJoinLink();

    void addAddMemberLink();

}
