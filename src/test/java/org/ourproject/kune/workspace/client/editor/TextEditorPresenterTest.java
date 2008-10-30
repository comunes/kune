package org.ourproject.kune.workspace.client.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.calclab.suco.testing.listener.MockListener;
import com.calclab.suco.testing.listener.MockListener0;

public class TextEditorPresenterTest {
    private TextEditorPresenter presenter;
    private TextEditorView view;
    private MockListener<String> saveListener;
    private MockListener0 cancelListener;
    @SuppressWarnings("unchecked")
    private ActionToolbar toolbar;
    private StateManager stateManager;

    @SuppressWarnings("unchecked")
    @Before
    public void createObjects() {
        I18nUITranslationService i18n = Mockito.mock(I18nUITranslationService.class);
        stateManager = Mockito.mock(StateManager.class);
        toolbar = Mockito.mock(ActionToolbar.class);
        presenter = new TextEditorPresenter(true, toolbar, i18n, stateManager);
        view = Mockito.mock(TextEditorView.class);
        presenter.init(view);
        saveListener = new MockListener<String>();
        cancelListener = new MockListener0();
    }

    @Test
    public void historyChangeWithoutPendingMustAccept() {
        presenter.editContent("Text to edit", saveListener, cancelListener);
        boolean change = presenter.beforeTokenChange("somegroup");
        assertTrue(change);
        Mockito.verify(view, Mockito.never()).showSaveBeforeDialog();
        Mockito.verify(toolbar, Mockito.times(1)).detach();
    }

    @Test
    public void historyChangeWithPendingSaveAndCancelMustPosponeIt() {
        String newToken = editAndChangeHistoryToken();
        presenter.onCancelConfirmed();
        assertTrue(saveListener.isNotCalled());
        Mockito.verify(stateManager, Mockito.times(1)).gotoToken(newToken);
    }

    @Test
    public void historyChangeWithPendingSaveMustPosponeIt() {
        String newToken = editAndChangeHistoryToken();
        presenter.onSaveAndClose();
        presenter.onSaved();
        assertTrue(saveListener.isCalledOnce());
        Mockito.verify(stateManager, Mockito.times(1)).gotoToken(newToken);
    }

    @Test
    public void initialEditWithCancel() {
        presenter.editContent("Text to edit", saveListener, cancelListener);
        presenter.onCancel();
        assertTrue(saveListener.isNotCalled());
        assertTrue(cancelListener.isCalledOnce());
    }

    @Test
    public void initialEditWithEditionAndSave() {
        presenter.editContent("Text to edit", saveListener, cancelListener);
        String textModified = "Text modified";
        Mockito.stub(view.getHTML()).toReturn(textModified);
        presenter.onEdit();
        presenter.onSave();
        assertTrue(saveListener.isCalledWithEquals(textModified));
        assertTrue(cancelListener.isNotCalled());
    }

    @Test
    public void initialEditWithSave() {
        presenter.editContent("Text to edit", saveListener, cancelListener);
        presenter.onSave();
        assertTrue(saveListener.isCalledOnce());
        assertTrue(cancelListener.isNotCalled());
    }

    @Test
    public void testSavePendingAndCancel() {
        presenter.editContent("Text to edit", saveListener, cancelListener);
        String textModified = "Text modified";
        Mockito.stub(view.getHTML()).toReturn(textModified);
        presenter.onEdit();
        presenter.onCancel();
        presenter.onCancelConfirmed();
        assertTrue(saveListener.isNotCalled());
        assertTrue(cancelListener.isCalledOnce());
    }

    @Test
    public void testSavePendingAndSaveFails() {
        presenter.editContent("Text to edit", saveListener, cancelListener);
        String textModified = "Text modified";
        Mockito.stub(view.getHTML()).toReturn(textModified);
        presenter.onEdit();
        presenter.onSave();
        presenter.onSaveFailed();
        presenter.onSave();
        Mockito.verify(view, Mockito.times(1)).scheduleSave(TextEditorPresenter.AUTOSAVE_IN_MILLISECONDS);
        Mockito.verify(view, Mockito.times(1)).scheduleSave(TextEditorPresenter.AUTOSAVE_AFTER_FAILS_IN_MILLISECONS);
        assertTrue(saveListener.isCalled(2));
    }

    private String editAndChangeHistoryToken() {
        presenter.editContent("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        String newToken = "somegroup";
        boolean change = presenter.beforeTokenChange(newToken);
        assertFalse(change);
        Mockito.verify(view, Mockito.times(1)).showSaveBeforeDialog();
        return newToken;
    }

}
