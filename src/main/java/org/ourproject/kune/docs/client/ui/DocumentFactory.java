package org.ourproject.kune.docs.client.ui;

import org.ourproject.kune.docs.client.ui.cnt.DocumentContent;
import org.ourproject.kune.docs.client.ui.cnt.DocumentContentPresenter;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderPanel;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.ui.cnt.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ui.ctx.DocumentContextPresenter;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContext;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContextPanel;
import org.ourproject.kune.docs.client.ui.ctx.folder.FolderContextPresenter;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;

public class DocumentFactory {
    private static DocumentContent documentContent;
    private static DocumentContext documentContext;
    private static DocumentReader documentReader;
    private static FolderContext folderContext;

    public static DocumentContent createDocumentContent() {
	if (documentContent == null) {
	    WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	    DocumentContentPresenter presenter = new DocumentContentPresenter(panel);
	    documentContent = presenter;
	}
	return documentContent;
    }

    public static DocumentContext createDocumentContext() {
	if (documentContext == null) {
	    WorkspaceDeckPanel view = new WorkspaceDeckPanel();
	    DocumentContextPresenter presenter = new DocumentContextPresenter(view);
	    documentContext = presenter;
	}
	return documentContext;
    }

    public static DocumentReader createDocumentReader() {
	if (documentReader == null) {
	    DocumentReaderView view = new DocumentReaderPanel();
	    DocumentReaderPresenter presenter = new DocumentReaderPresenter(view);
	    documentReader = presenter;
	}
	return documentReader;
    }

    public static FolderContext getFolderContext() {
	if (folderContext == null) {
	    FolderContextPanel view = new FolderContextPanel();
	    FolderContextPresenter presenter = new FolderContextPresenter(view);
	    folderContext = presenter;
	}
	return folderContext;
    }

}
