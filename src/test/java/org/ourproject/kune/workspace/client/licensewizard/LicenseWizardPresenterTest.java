package org.ourproject.kune.workspace.client.licensewizard;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardSndFormView;

import com.calclab.suco.client.ioc.Provider;

public class LicenseWizardPresenterTest {

    private LicenseWizardView view;
    private LicenseWizardPresenter licenseWizard;
    private LicenseWizardFirstFormView firstForm;
    private LicenseWizardSndFormView sndForm;
    private Provider<GroupServiceAsync> groupService;

    @Before
    public void before() {
        view = Mockito.mock(LicenseWizardView.class);
        firstForm = Mockito.mock(LicenseWizardFirstFormView.class);
        sndForm = Mockito.mock(LicenseWizardSndFormView.class);
        groupService = MockProvider.mock(GroupServiceAsync.class);
        licenseWizard = new LicenseWizardPresenter(firstForm, sndForm, groupService);
        licenseWizard.init(view);
    }

    @Test
    public void onAnotherSelected() {
        licenseWizard.onAnotherLicenseSelecterd();
        Mockito.verify(view).setEnabled(false, true, true, false);
        licenseWizard.onNext();
        Mockito.verify(view).add(sndForm);
        Mockito.verify(view).setEnabled(true, false, true, true);
        licenseWizard.onBack();
        Mockito.verify(view).add(firstForm);
        Mockito.verify(view).setEnabled(false, true, true, true);
    }

    @Test
    public void onCopyleftSelected() {
        licenseWizard.onCopyLeftLicenseSelected();
        Mockito.verify(view).setEnabled(false, false, true, true);
    }

    @Test
    public void onShowResetWidget() {
        licenseWizard.show();
        Mockito.verify(view).clear();
        Mockito.verify(view).center();
        Mockito.verify(view).setEnabled(false, false, true, true);
        Mockito.verify(view).add(firstForm);
        Mockito.verify(firstForm).reset();
        Mockito.verify(view).show();
    }
}
