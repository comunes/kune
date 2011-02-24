package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.core.shared.domain.SocialNetworkVisibility;

import com.google.inject.Inject;

public class MembersVisibilityMenuItem extends MenuRadioItemDescriptor {

    private static final String GROUP_MEMBERS_VISIBILITY = "k-sn-gmembers-vis";

    @Inject
    public MembersVisibilityMenuItem(final MenuDescriptor parent, final MembersVisibilityAction action) {
        super(parent, action, GROUP_MEMBERS_VISIBILITY);
    }

    public MenuRadioItemDescriptor withVisibility(final SocialNetworkVisibility visibility) {
        ((MembersVisibilityAction) getAction()).setVisibility(visibility);
        return this;
    }

}