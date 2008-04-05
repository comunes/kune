
package org.ourproject.kune.docs.client.cnt;

import org.ourproject.kune.docs.client.actions.DocsEvents;
import org.ourproject.kune.docs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControl;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderListener;
import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.SaveDocumentActionParams;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.extend.UIExtensionElement;
import org.ourproject.kune.platf.client.extend.UIExtensionPoint;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;

public class DocumentContentPresenter implements DocumentContent, DocumentReaderListener, TextEditorListener {
    private final WorkspaceDeckView view;
    private final DocumentContentComponents components;
    private StateDTO content;
    private final DocumentContentListener listener;
    private final DocumentReader reader;
    private final DocumentReaderControl readerControl;

    public DocumentContentPresenter(final DocumentContentListener listener, final WorkspaceDeckView view) {
        this.listener = listener;
        this.view = view;
        this.components = new DocumentContentComponents(this);
        reader = components.getDocumentReader();
        readerControl = components.getDocumentReaderControl();
    }

    public void setContent(final StateDTO content) {
        this.content = content;
        showContent();
    }

    public void onSaved() {
        components.getDocumentEditor().onSaved();
    }

    public void onSaveFailed() {
        components.getDocumentEditor().onSaveFailed();
    }

    public void onEdit() {
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ONLY_CHECK_USER_SESSION,
                new AsyncCallbackSimple<Object>() {
                    public void onSuccess(final Object result) {
                        if (content.hasDocument()) {
                            // Don't permit rate content while your are editing
                            DefaultDispatcher.getInstance().fire(WorkspaceEvents.DISABLE_RATEIT, null);
                            TextEditor editor = components.getDocumentEditor();
                            editor.setContent(content.getContent());
                            view.show(editor.getView());
                            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT,
                                    UIExtensionPoint.CONTENT_TOOLBAR_LEFT);
                            DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXT_POINT,
                                    new UIExtensionElement(UIExtensionPoint.CONTENT_TOOLBAR_LEFT, editor.getToolBar()));
                            DefaultDispatcher.getInstance().fire(WorkspaceEvents.RECALCULATE_WORKSPACE_SIZE, null);
                        } else {
                            FolderEditor editor = components.getFolderEditor();
                            editor.setFolder(content.getFolder());
                            view.show(editor.getView());
                        }
                        listener.onEdit();
                    }
                });
    }

    public void onSave(final String text) {
        content.setContent(text);
        DefaultDispatcher.getInstance().fire(DocsEvents.SAVE_DOCUMENT, new SaveDocumentActionParams(content, this));
        // Re-enable rateIt widget
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ENABLE_RATEIT, null);
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.RECALCULATE_WORKSPACE_SIZE, null);
    }

    public void onCancel() {
        showContent();
        listener.onCancel();
        // Re-enable rateIt widget
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ENABLE_RATEIT, null);
    }

    public void onDelete() {
        DefaultDispatcher.getInstance().fire(DocsEvents.DEL_CONTENT, content.getDocumentId());
    }

    public View getView() {
        return view;
    }

    public void attach() {
    }

    public void detach() {
    }

    public void onTranslate() {
    }

    private void showContent() {
        if (content.hasDocument()) {
            reader.showDocument(content.getContent());
            components.getDocumentEditor().reset();
            readerControl.setRights(content.getContentRights());
            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT, UIExtensionPoint.CONTENT_TOOLBAR_LEFT);
            DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXT_POINT,
                    new UIExtensionElement(UIExtensionPoint.CONTENT_TOOLBAR_LEFT, readerControl.getView()));
            view.show(reader.getView());
        } else {
            FolderViewer viewer = components.getFolderViewer();
            viewer.setFolder(content.getFolder());
            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT, UIExtensionPoint.CONTENT_TOOLBAR_LEFT);
            view.show(viewer.getView());
        }
    }

}
