package org.ourproject.kune.platf.client.ui.dialogs.upload;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.upload.FileUploaderPresenter;
import org.ourproject.kune.platf.client.ui.upload.FileUploaderView;

public class FileUploaderPresenterTest {

    private static final String SOMEUSER_HASH = "someuserHash";
    private FileUploaderPresenter presenter;
    private FileUploaderView view;
    private Session session;

    @Before
    public void before() {
        session = Mockito.mock(Session.class);
        view = Mockito.mock(FileUploaderView.class);
        presenter = new FileUploaderPresenter(session, null);
        presenter.init(view);
        Mockito.stub(session.getUserHash()).toReturn(SOMEUSER_HASH);
    }

    @Test
    public void testFirstAddFromDocInSameContainer() {
        Mockito.stub(session.getCurrentStateToken()).toReturn(new StateToken("group.docs.1.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }

    @Test
    public void testFirstAddInSameContainer() {
        Mockito.stub(session.getCurrentStateToken()).toReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }

    @Test
    public void testSomeAddsInDiffContainersButNotUploading() {
        Mockito.stub(session.getCurrentStateToken()).toReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.stub(view.hasUploadingFiles()).toReturn(false);
        Mockito.stub(session.getCurrentStateToken()).toReturn(new StateToken("group.docs.2"));
        assertTrue(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.2",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }

    @Test
    public void testSomeAddsInDiffContainersButUploading() {
        Mockito.stub(session.getCurrentStateToken()).toReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.stub(view.hasUploadingFiles()).toReturn(true);
        Mockito.stub(session.getCurrentStateToken()).toReturn(new StateToken("group.docs.2"));
        assertFalse(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(1)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
        Mockito.verify(view, Mockito.never()).setUploadParams(SOMEUSER_HASH, "group.docs.2",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }

    @Test
    public void testSomeAddsInSameContainer() {
        Mockito.stub(session.getCurrentStateToken()).toReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.stub(session.getCurrentStateToken()).toReturn(new StateToken("group.docs.1"));
        assertTrue(presenter.checkFolderChange());
        Mockito.verify(view, Mockito.times(2)).setUploadParams(SOMEUSER_HASH, "group.docs.1",
                DocumentClientTool.TYPE_UPLOADEDFILE);
    }
}
