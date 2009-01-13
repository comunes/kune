package org.ourproject.kune.workspace.client.options.license;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.licensewizard.LicenseChangeAction;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizard;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.events.Listener2;
import com.calclab.suco.client.ioc.Provider;

public class EntityOptionsDefLicensePresenter implements EntityOptionsDefLicense {

    private EntityOptionsDefLicenseView view;
    private final EntityOptions entityOptions;
    private final Session session;
    private final Provider<LicenseWizard> licenseWizard;
    private final Provider<LicenseChangeAction> licenseChangeAction;

    public EntityOptionsDefLicensePresenter(EntityOptions entityOptions, StateManager stateManager, Session session,
            Provider<LicenseWizard> licenseWizard, Provider<LicenseChangeAction> licenseChangeAction) {
        this.entityOptions = entityOptions;
        this.session = session;
        this.licenseWizard = licenseWizard;
        this.licenseChangeAction = licenseChangeAction;
        stateManager.onGroupChanged(new Listener2<String, String>() {
            public void onEvent(String group1, String group2) {
                setState();
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(EntityOptionsDefLicenseView view) {
        this.view = view;
        entityOptions.addOptionTab(view);
        setState();
    }

    public void onChange() {
        licenseWizard.get().start(new Listener<LicenseDTO>() {
            public void onEvent(final LicenseDTO license) {
                licenseChangeAction.get().changeLicense(license, new Listener0() {
                    public void onEvent() {
                        setLicense(license);
                    }
                });
            }
        });
    }

    public void onLicenseClick() {
        view.openWindow(getCurrentDefLicense().getUrl());
    }

    private LicenseDTO getCurrentDefLicense() {
        return session.getCurrentState().getGroup().getDefaultLicense();
    }

    private void setLicense(LicenseDTO license) {
        view.setLicense(license);
    }

    private void setState() {
        setLicense(getCurrentDefLicense());
    }
}
