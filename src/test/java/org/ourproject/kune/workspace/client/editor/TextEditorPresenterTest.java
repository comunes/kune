package org.ourproject.kune.workspace.client.editor;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class TextEditorPresenterTest {
    private TextEditorPresenter presenter;
    private TextEditorView view;

    @Before
    public void createObjects() {
	presenter = new TextEditorPresenter(true, null, null);
	view = EasyMock.createStrictMock(TextEditorView.class);
    }

    @Test
    public void testSave() {
	viewInit();
	// view.setEnabledSaveButton(true);
	view.scheduleSave(10000);
	EasyMock.expect(view.getHTML()).andReturn("foo");
	viewReset();
	EasyMock.replay(view);
	// listener.onSave("foo");
	// EasyMock.replay(listener);
	presenter.init(view);
	presenter.onEdit();
	presenter.onSave();
	presenter.onSaved();
	EasyMock.verify(view);
	// EasyMock.verify(listener);
    }

    @Test
    public void testSavePending() {
	viewInit();
	// view.setEnabledSaveButton(true);
	view.scheduleSave(10000);
	EasyMock.replay(view);
	presenter.init(view);
	presenter.onEdit();
	EasyMock.verify(view);
    }

    @Test
    public void testSavePendingCancel() {
	viewInit();
	// view.setEnabledSaveButton(true);
	view.scheduleSave(10000);
	view.saveTimerCancel();
	view.showSaveBeforeDialog();
	EasyMock.expect(view.getHTML()).andReturn("foo");
	viewReset();
	EasyMock.replay(view);
	// listener.onSave("foo");
	// listener.onEditCancelled();
	// EasyMock.replay(listener);
	presenter.init(view);
	presenter.onEdit();
	presenter.onCancel();
	presenter.onSaveAndClose();
	presenter.onSaved();
	EasyMock.verify(view);
	// EasyMock.verify(listener);
    }

    @Test
    public void testSavePendingCancelSaveFails() {
	viewInit();
	// view.setEnabledSaveButton(true);
	view.scheduleSave(10000);
	view.saveTimerCancel();
	view.showSaveBeforeDialog();
	EasyMock.expect(view.getHTML()).andReturn("foo");
	view.scheduleSave(20000);
	EasyMock.replay(view);
	// listener.onSave("foo");
	// EasyMock.replay(listener);
	presenter.init(view);
	presenter.onEdit();
	presenter.onCancel();
	presenter.onSaveAndClose();
	presenter.onSaveFailed();
	EasyMock.verify(view);
	// EasyMock.verify(listener);
    }

    @Test
    public void testViewInitialization() {
	viewInit();
	EasyMock.replay(view);
	presenter.init(view);
	EasyMock.verify(view);
    }

    private void viewInit() {
	// view.setEnabledSaveButton(false);
	view.setEnabled(true);
    }

    private void viewReset() {
	view.saveTimerCancel();
	// view.setEnabledSaveButton(false);
    }
}
