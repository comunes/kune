package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.core.shared.domain.UserSNetVisibility;

import com.google.inject.Inject;

public class UserSNVisibilityMenuItem extends MenuRadioItemDescriptor {

    private static final String USER_BUDDIES_VISIBILITY_GROUP = "k-sn-userbuddies-vis";

    @Inject
    public UserSNVisibilityMenuItem(final MenuDescriptor parent, final UserSNVisibilityAction action) {
        super(parent, action, USER_BUDDIES_VISIBILITY_GROUP);
    }

    public MenuRadioItemDescriptor withVisibility(final UserSNetVisibility visibility) {
        ((UserSNVisibilityAction) getAction()).setVisibility(visibility);
        return this;
    }

}