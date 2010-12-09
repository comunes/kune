package org.ourproject.kune.workspace.client.options.license;

import org.ourproject.kune.workspace.client.licensewizard.LicenseChangeAction;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizard;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.StateToken;

import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class GroupOptionsDefLicensePresenter extends EntityOptionsDefLicensePresenter implements GroupOptionsDefLicense {

    public GroupOptionsDefLicensePresenter(final EntityOptions entityOptions, final StateManager stateManager,
            final Session session, final Provider<LicenseWizard> licenseWizard,
            final Provider<LicenseChangeAction> licChangeAction) {
        super(entityOptions, session, licenseWizard, licChangeAction);
        stateManager.onGroupChanged(new Listener2<String, String>() {
            public void onEvent(final String group1, final String group2) {
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

    @Override
    protected StateToken getOperationToken() {
        return session.getCurrentStateToken();
    }
}
