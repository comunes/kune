package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.SocialNetworkDataDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.SocialNetworkServiceAsync;
import org.ourproject.kune.platf.client.state.AccessRightsClientManager;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.img.ImgResources;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;

public class UnjoinAction extends RolAction {
    private final Provider<SocialNetworkServiceAsync> snServiceProvider;
    private String groupName;

    public UnjoinAction(final Session session, final Provider<SocialNetworkServiceAsync> snServiceProvider,
            final StateManager stateManager, final AccessRightsClientManager rightsManager,
            final I18nTranslationService i18n, final ImgResources imgResources) {
        super(session, stateManager, rightsManager, i18n, AccessRolDTO.Editor, i18n.t("Leave this group"),
                i18n.t("Do not participate anymore in this group"), imgResources.delGreen());
        this.snServiceProvider = snServiceProvider;
        super.setVisible(true, false);
        super.setMustBeAuthenticated(true);
    }

    public void actionPerformed(final ActionEvent event) {
        NotifyUser.askConfirmation(i18n.t("Leave this group"), i18n.t("Are you sure?"), new Listener0() {
            public void onEvent() {
                NotifyUser.showProgressProcessing();
                snServiceProvider.get().unJoinGroup(session.getUserHash(), new StateToken(getGroupName()),
                        new AsyncCallbackSimple<SocialNetworkDataDTO>() {
                            public void onSuccess(final SocialNetworkDataDTO result) {
                                NotifyUser.hideProgress();
                                NotifyUser.info(i18n.t("Removed as member"));
                                stateManager.reload();
                                // in the future with user info:
                                // stateManager.reloadSocialNetwork((SocialNetworkResultDTO)
                                // result);
                            }
                        });
            }
        });
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }
}
