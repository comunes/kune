package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuRadioItemDescriptor;
import cc.kune.core.shared.domain.AdmissionType;

import com.google.inject.Inject;

public class MembersModerationMenuItem extends MenuRadioItemDescriptor {

    private static final String GROUP_MEMBERS_MODERATION = "k-sn-gmembers-vis";

    @Inject
    public MembersModerationMenuItem(final MenuDescriptor parent, final MembersModerationAction action) {
        super(parent, action, GROUP_MEMBERS_MODERATION);
    }

    public MenuRadioItemDescriptor withModeration(final AdmissionType admissionType) {
        ((MembersModerationAction) getAction()).setAdmissionType(admissionType);
        return this;
    }

}