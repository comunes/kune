package org.ourproject.kune.workspace.client.options.license;

import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.licensewizard.LicenseChangeAction;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizard;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class UserOptionsDefLicensePresenter extends EntityOptionsDefLicensePresenter implements UserOptionsDefLicense {

    public UserOptionsDefLicensePresenter(final EntityOptions entityOptions, final Session session,
            final Provider<LicenseWizard> licenseWizard, final Provider<LicenseChangeAction> licChangeAction) {
        super(entityOptions, session, licenseWizard, licChangeAction);
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                setState();
            }
        });
    }

    @Override
    protected boolean applicable() {
        return session.isCurrentStateAGroup();
    }

    @Override
    protected LicenseDTO getCurrentDefLicense() {
        return session.getCurrentState().getGroup().getDefaultLicense();
    }
}
