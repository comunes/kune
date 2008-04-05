package org.ourproject.kune.blogs.client.cnt;

import org.ourproject.kune.blogs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.blogs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReader;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderControl;
import org.ourproject.kune.blogs.client.ui.BlogFactory;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.editor.TextEditor;

class BlogComponents {
    private BlogReader reader;
    private TextEditor editor;
    private final BlogContentPresenter blogContentPresenter;
    private FolderViewer folderViewer;
    private FolderEditor folderEditor;
    private BlogReaderControl readerControl;

    public BlogComponents(final BlogContentPresenter blogContentPresenter) {
        this.blogContentPresenter = blogContentPresenter;
    }

    public BlogReader getDocumentReader() {
        if (reader == null) {
            reader = BlogFactory.createDocumentReader(blogContentPresenter);
        }
        return reader;
    }

    public BlogReaderControl getDocumentReaderControl() {
        if (readerControl == null) {
            readerControl = BlogFactory.createDocumentReaderControl(blogContentPresenter);
        }
        return readerControl;
    }

    public TextEditor getDocumentEditor() {
        if (editor == null) {
            editor = WorkspaceFactory.createDocumentEditor(blogContentPresenter);
        }
        return editor;
    }

    public FolderViewer getFolderViewer() {
        if (folderViewer == null) {
            folderViewer = BlogFactory.createFolderViewer();
        }
        return folderViewer;
    }

    public FolderEditor getFolderEditor() {
        if (folderEditor == null) {
            folderEditor = BlogFactory.createFolderEditor();
        }
        return folderEditor;
    }

}
