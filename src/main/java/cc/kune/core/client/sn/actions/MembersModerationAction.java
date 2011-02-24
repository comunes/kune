package cc.kune.core.client.sn.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.GroupServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.AdmissionType;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MembersModerationAction extends AbstractExtendedAction {

    private AdmissionType admissionType;
    private final Provider<GroupServiceAsync> groupServiceProvider;
    private final I18nTranslationService i18n;
    private final Session session;

    @Inject
    public MembersModerationAction(final Session session, final I18nTranslationService i18n,
            final Provider<GroupServiceAsync> groupServiceProvider) {
        this.session = session;
        this.i18n = i18n;
        this.groupServiceProvider = groupServiceProvider;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        groupServiceProvider.get().setGroupNewMembersJoiningPolicy(session.getUserHash(),
                session.getCurrentState().getGroup().getStateToken(), admissionType, new AsyncCallbackSimple<Void>() {
                    @Override
                    public void onSuccess(final Void result) {
                        NotifyUser.info(i18n.t("Members joining policy changed"));
                    }
                });
    }

    public void setAdmissionType(final AdmissionType admissionType) {
        this.admissionType = admissionType;
    }

}
