package org.ourproject.kune.workspace.client.licensewizard;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardFrdFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardSndFormView;
import org.ourproject.kune.workspace.client.licensewizard.pages.LicenseWizardTrdFormView;

public class LicenseWizardPresenterTest {

    private LicenseWizardView view;
    private LicenseWizardPresenter licenseWizard;
    private LicenseWizardFirstFormView firstForm;
    private LicenseWizardSndFormView sndForm;
    private LicenseWizardTrdFormView trdForm;
    private LicenseWizardFrdFormView frdForm;

    @Before
    public void before() {
        view = Mockito.mock(LicenseWizardView.class);
        firstForm = Mockito.mock(LicenseWizardFirstFormView.class);
        sndForm = Mockito.mock(LicenseWizardSndFormView.class);
        trdForm = Mockito.mock(LicenseWizardTrdFormView.class);
        frdForm = Mockito.mock(LicenseWizardFrdFormView.class);
        Session session = Mockito.mock(Session.class);
        licenseWizard = new LicenseWizardPresenter(firstForm, sndForm, trdForm, frdForm, session);
        licenseWizard.init(view);
    }

    @Test
    public void onAnotherSelected() {
        licenseWizard.onAnotherLicenseSelecterd();
        Mockito.verify(view).setEnabled(false, true, true, false);
        Mockito.when(view.isCurrentPage(firstForm)).thenReturn(true);
        licenseWizard.onNext();
        Mockito.verify(view).show(sndForm);
        Mockito.verify(view).setEnabled(true, true, true, false);
        Mockito.when(view.isCurrentPage(sndForm)).thenReturn(true);
        licenseWizard.onBack();
        Mockito.verify(view).show(firstForm);
        Mockito.verify(view).setEnabled(false, true, true, true);
    }

    @Test
    public void onCopyleftSelected() {
        licenseWizard.onCopyLeftLicenseSelected();
        Mockito.verify(view).setEnabled(false, false, true, true);
    }

    @Test
    public void onShowResetWidget() {
        licenseWizard.start(null);
        Mockito.verify(view).clear();
        Mockito.verify(view).center();
        Mockito.verify(view).setEnabled(false, false, true, true);
        Mockito.verify(view).show(firstForm);
        Mockito.verify(firstForm).reset();
        Mockito.verify(view).show();
    }
}
