package org.ourproject.kune.platf.client.ui.rte;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.api.VerificationMode;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.i18n.I18nUITranslationService;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.platf.client.utils.TimerWrapper;
import org.ourproject.kune.workspace.client.sitebar.sitesign.SiteSignOutLink;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.testing.events.MockedListener;
import com.calclab.suco.testing.events.MockedListener0;
import com.google.gwt.libideas.resources.client.ImageResource;

public class RTESavingEditorPresenterTest {

    private RTESavingEditorPresenter presenter;
    private MockedListener<String> saveListener;
    private MockedListener0 cancelListener;
    private StateManager stateManager;
    private SiteSignOutLink signOutLink;
    private DeferredCommandWrapper deferredCommandWrapper;
    private RTEditor rteEditor;
    private TimerWrapper timer;
    @SuppressWarnings("unchecked")
    private ActionToolbar sndbar;
    private RTESavingEditorView view;

    @SuppressWarnings("unchecked")
    @Before
    public void createObjects() {
        I18nUITranslationService i18n = Mockito.mock(I18nUITranslationService.class);
        stateManager = Mockito.mock(StateManager.class);
        signOutLink = Mockito.mock(SiteSignOutLink.class);
        deferredCommandWrapper = Mockito.mock(DeferredCommandWrapper.class);
        rteEditor = Mockito.mock(RTEditor.class);
        RTEImgResources imgResources = Mockito.mock(RTEImgResources.class);
        ImageResource imageResource = Mockito.mock(ImageResource.class);
        Mockito.when(imageResource.getName()).thenReturn("save");
        Mockito.when(imgResources.save()).thenReturn(imageResource);
        sndbar = Mockito.mock(ActionToolbar.class);
        Mockito.when(rteEditor.getSndBar()).thenReturn(sndbar);
        timer = Mockito.mock(TimerWrapper.class);
        view = Mockito.mock(RTESavingEditorView.class);
        presenter = new RTESavingEditorPresenter(rteEditor, true, i18n, stateManager, signOutLink,
                deferredCommandWrapper, imgResources, timer);
        presenter.init(view);
        saveListener = new MockedListener<String>();
        cancelListener = new MockedListener0();
    }

    @Test
    public void historyChangeWithoutPendingMustAccept() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        boolean change = presenter.beforeTokenChange();
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
    public void initialEditWithCancel() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onCancel();
        assertTrue(saveListener.isNotCalled());
        assertTrue(cancelListener.isCalledOnce());
    }

    @Test
    public void initialEditWithEditionAndSave() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        String textModified = "Text modified";
        Mockito.when(rteEditor.getHtml()).thenReturn(textModified);
        presenter.onEdit();
        presenter.onDoSave();
        checkSaveBtnDisabled();
        assertTrue(saveListener.isCalledWithEquals(textModified));
        assertTrue(cancelListener.isNotCalled());
    }

    @Test
    public void initialEditWithSave() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onDoSave();
        checkSaveBtnDisabled();
        assertTrue(saveListener.isCalledOnce());
        assertTrue(cancelListener.isNotCalled());
    }

    @Test
    public void testSavePendingAndCancel() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        String textModified = "Text modified";
        Mockito.when(rteEditor.getHtml()).thenReturn(textModified);
        presenter.onEdit();
        presenter.onCancel();
        presenter.onCancelConfirmed();
        assertTrue(saveListener.isNotCalled());
        assertTrue(cancelListener.isCalledOnce());
    }

    @Test
    public void testSavePendingAndSaveFails() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        String textModified = "Text modified";
        Mockito.when(rteEditor.getHtml()).thenReturn(textModified);
        presenter.onEdit();
        presenter.onDoSave();
        presenter.onSaveFailed();
        presenter.onDoSave();
        Mockito.verify(timer, Mockito.times(1)).schedule(RTESavingEditorPresenter.AUTOSAVE_IN_MILLISECONDS);
        Mockito.verify(timer, Mockito.times(1)).schedule(RTESavingEditorPresenter.AUTOSAVE_AFTER_FAILS_IN_MILLISECONS);
        assertTrue(saveListener.isCalled(2));
    }

    @SuppressWarnings("unchecked")
    private void checkSaveBtnDisabled() {
        Mockito.verify(sndbar, Mockito.times(1)).setButtonEnable(presenter.saveBtn, false);
    }

    private String editAndChangeHistoryToken() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        String newToken = "somegroup";
        boolean change = presenter.beforeTokenChange();
        assertFalse(change);
        verifyAskConfirmationCalled(Mockito.times(1));
        return newToken;
    }

    private void verifyAskConfirmationCalled(VerificationMode mode) {
        Mockito.verify(view, mode).askConfirmation(Mockito.anyString(), Mockito.anyString(),
                (Listener0) Mockito.anyObject(), (Listener0) Mockito.anyObject());
    }

}
