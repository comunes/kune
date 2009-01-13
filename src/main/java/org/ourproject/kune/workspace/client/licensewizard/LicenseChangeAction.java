package org.ourproject.kune.workspace.client.licensewizard;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.site.Site;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LicenseChangeAction {
    private final StateManager stateManager;
    private final Provider<GroupServiceAsync> groupService;
    private final Session session;
    private final I18nTranslationService i18n;

    public LicenseChangeAction(Provider<GroupServiceAsync> groupService, Session session, I18nTranslationService i18n,
            StateManager stateManager) {
        this.groupService = groupService;
        this.session = session;
        this.i18n = i18n;
        this.stateManager = stateManager;
    }

    public void changeLicense(final LicenseDTO license, final Listener0 onSuccess) {
        Site.showProgressProcessing();
        groupService.get().changeDefLicense(session.getUserHash(), session.getCurrentStateToken(), license,
                new AsyncCallback<Object>() {
                    public void onFailure(Throwable caught) {
                        Site.hideProgress();
                        Site.error(i18n.t("Error changing default group license"));
                    }

                    public void onSuccess(Object result) {
                        stateManager.reload();
                        onSuccess.onEvent();
                    }
                });
    }
}
