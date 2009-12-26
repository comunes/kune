package org.ourproject.kune.platf.client.ui.rte.saving;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.api.VerificationMode;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.i18n.I18nTranslationServiceMocked;
import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.platf.client.utils.TimerWrapper;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.testing.events.MockedListener;
import com.calclab.suco.testing.events.MockedListener0;
import com.google.gwt.resources.client.ImageResource;

public class RTESavingEditorPresenterTest {

    private RTESavingEditorPresenter presenter;
    private MockedListener<String> saveListener;
    private MockedListener0 cancelListener;
    private StateManager stateManager;
    private DeferredCommandWrapper deferredCommandWrapper;
    private TimerWrapper timer;
    private RTESavingEditorView view;

    @Before
    public void createObjects() {
        final I18nTranslationService i18n = new I18nTranslationServiceMocked();
        new Resources(i18n);
        stateManager = Mockito.mock(StateManager.class);
        deferredCommandWrapper = Mockito.mock(DeferredCommandWrapper.class);

        final RTEImgResources imgResources = Mockito.mock(RTEImgResources.class);
        final ImageResource img = Mockito.mock(ImageResource.class);
        Mockito.when(img.getName()).thenReturn("save");
        Mockito.when(imgResources.save()).thenReturn(img);
        Mockito.when(imgResources.fontheight()).thenReturn(img);
        Mockito.when(imgResources.charfontname()).thenReturn(img);
        timer = Mockito.mock(TimerWrapper.class);
        view = Mockito.mock(RTESavingEditorView.class);

        presenter = new RTESavingEditorPresenter(i18n, null, imgResources, null, null, null, null, null, null, null,
                deferredCommandWrapper, true, stateManager, timer);
        presenter.init(view);
        saveListener = new MockedListener<String>();
        cancelListener = new MockedListener0();
    }

    @Test
    public void historyChangeWithoutPendingMustAccept() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        final boolean change = presenter.beforeTokenChange();
        assertTrue(change);
        verifyAskConfirmationCalled(Mockito.never());
        Mockito.verify(deferredCommandWrapper, Mockito.times(1)).addCommand((Listener0) Mockito.anyObject());
    }

    @Test
    public void historyChangeWithPendingSaveAndCancelMustPosponeIt() {
        editAndChangeHistoryToken();
        presenter.onCancelConfirmed();
        assertTrue(saveListener.isNotCalled());
        Mockito.verify(stateManager, Mockito.times(1)).resumeTokenChange();
    }

    @Test
    public void historyChangeWithPendingSaveMustPosponeIt() {
        editAndChangeHistoryToken();
        presenter.onDoSaveAndClose();
        presenter.onSavedSuccessful();
        assertTrue(saveListener.isCalledOnce());
        Mockito.verify(stateManager, Mockito.times(1)).resumeTokenChange();
    }

    @Test
    public void initialEdit() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        checkSaveBtnDisabled();
        assertTrue(cancelListener.isNotCalled());
    }

    @Test
    public void initialEditWithCancel() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onCancel();
        assertTrue(saveListener.isNotCalled());
        assertTrue(cancelListener.isCalledOnce());
    }

    @Test
    public void initialEditWithEditionAndSave() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        presenter.onDoSave();
        presenter.onSavedSuccessful();
        checkSaveBtnDisabled();
        assertTrue(cancelListener.isNotCalled());
    }

    @Test
    public void testSavePendingAndCancel() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        presenter.onCancel();
        presenter.onCancelConfirmed();
        assertTrue(saveListener.isNotCalled());
        assertTrue(cancelListener.isCalledOnce());
    }

    @Test
    public void testSavePendingAndSaveFails() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        presenter.onDoSave();
        presenter.onSaveFailed();
        presenter.onDoSave();
        Mockito.verify(timer, Mockito.times(1)).schedule(RTESavingEditorPresenter.AUTOSAVE_IN_MILLIS);
        Mockito.verify(timer, Mockito.times(1)).schedule(RTESavingEditorPresenter.AUTOSAVE_AFTER_FAIL_MILLS);
        assertTrue(saveListener.isCalled(2));
    }

    private void checkSaveBtnDisabled() {
        assertFalse(presenter.saveAction.isEnabled());
    }

    private String editAndChangeHistoryToken() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        final String newToken = "somegroup";
        final boolean change = presenter.beforeTokenChange();
        assertFalse(change);
        verifyAskConfirmationCalled(Mockito.times(1));
        return newToken;
    }

    private void verifyAskConfirmationCalled(final VerificationMode mode) {
        Mockito.verify(view, mode).askConfirmation(Mockito.anyString(), Mockito.anyString(),
                (Listener0) Mockito.anyObject(), (Listener0) Mockito.anyObject());
    }

}
