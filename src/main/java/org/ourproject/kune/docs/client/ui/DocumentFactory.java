package org.ourproject.kune.docs.client.ui;

import org.ourproject.kune.docs.client.ui.cnt.DocumentContent;
import org.ourproject.kune.docs.client.ui.cnt.DocumentContentPresenter;
import org.ourproject.kune.docs.client.ui.cnt.folder.editor.FolderEditor;
import org.ourproject.kune.docs.client.ui.cnt.folder.editor.FolderEditorPanel;
import org.ourproject.kune.docs.client.ui.cnt.folder.editor.FolderEditorPresenter;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewerPanel;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewerPresenter;
import org.ourproject.kune.docs.client.ui.cnt.folder.viewer.FolderViewerView;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderPanel;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContextPresenter;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContextListener;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContextPanel;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContextPresenter;
import org.ourproject.kune.docs.client.ui.forms.newdoc.NewDocumentForm;
import org.ourproject.kune.docs.client.ui.forms.newdoc.NewDocumentFormPanel;
import org.ourproject.kune.docs.client.ui.forms.newdoc.NewDocumentFormPresenter;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.ui.form.FormListener;

public class DocumentFactory {

    public static DocumentContent createDocumentContent() {
	WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	DocumentContentPresenter presenter = new DocumentContentPresenter(panel);
	return presenter;
    }

    public static DocumentContext createDocumentContext() {
	WorkspaceDeckPanel view = new WorkspaceDeckPanel();
	DocumentContextPresenter presenter = new DocumentContextPresenter(view);
	return presenter;
    }

    public static DocumentReader createDocumentReader(final DocumentReaderListener listener) {
	DocumentReaderView view = new DocumentReaderPanel(listener);
	DocumentReaderPresenter presenter = new DocumentReaderPresenter(view);
	return presenter;
    }

    public static FolderContext createFolderContext(final FolderContextListener listener) {
	FolderContextPanel view = new FolderContextPanel(listener);
	FolderContextPresenter presenter = new FolderContextPresenter(view);
	return presenter;
    }

    public static FolderViewer createFolderViewer() {
	FolderViewerView view = new FolderViewerPanel();
	FolderViewerPresenter presenter = new FolderViewerPresenter(view);
	return presenter;
    }

    public static FolderEditor createFolderEditor() {
	FolderEditorPanel view = new FolderEditorPanel();
	FolderEditorPresenter presenter = new FolderEditorPresenter(view);
	return presenter;
    }

    public static NewDocumentForm createNewDocumentForm(final FormListener listener) {
	NewDocumentFormPanel view = new NewDocumentFormPanel(listener);
	NewDocumentFormPresenter presenter = new NewDocumentFormPresenter(view);
	return presenter;
    }

}
