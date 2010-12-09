package org.ourproject.kune.platf.client.ui.dialogs.upload;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.workspace.client.upload.FileUploaderPresenter;
import org.ourproject.kune.workspace.client.upload.FileUploaderView;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.StateToken;

public class FileUploaderPresenterTest {

    private static final String SOMEUSER_HASH = "someuserHash";
    private FileUploaderPresenter presenter;
    private FileUploaderView view;
    private Session session;

    @Before
    public void before() {
        session = Mockito.mock(Session.class);
        view = Mockito.mock(FileUploaderView.class);
        presenter = new FileUploaderPresenter(session);
        presenter.init(view);
        Mockito.when(session.getUserHash()).thenReturn(SOMEUSER_HASH);
    }

    @Test
    public void testFirstAddFromDocInSameContainer() {
        Mockito.when(session.getCurrentStateToken()).thenReturn(new StateToken("group.docs.1.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }

    @Test
    public void testFirstAddInSameContainer() {
        Mockito.when(session.getCurrentStateToken()).thenReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }

    @Test
    public void testSomeAddsInDiffContainersButNotUploading() {
        Mockito.when(session.getCurrentStateToken()).thenReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.when(view.hasUploadingFiles()).thenReturn(false);
        Mockito.when(session.getCurrentStateToken()).thenReturn(new StateToken("group.docs.2"));
        assertTrue(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.2",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }

    @Test
    public void testSomeAddsInDiffContainersButUploading() {
        Mockito.when(session.getCurrentStateToken()).thenReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.when(view.hasUploadingFiles()).thenReturn(true);
        Mockito.when(session.getCurrentStateToken()).thenReturn(new StateToken("group.docs.2"));
        assertFalse(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
        Mockito.verify(view, Mockito.never()).setUploadParams(SOMEUSER_HASH, "group.docs.2",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }

    @Test
    public void testSomeAddsInSameContainer() {
        Mockito.when(session.getCurrentStateToken()).thenReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.when(session.getCurrentStateToken()).thenReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(2)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }
}
