/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.blogs.client.cnt;

import org.ourproject.kune.blogs.client.actions.BlogsEvents;
import org.ourproject.kune.blogs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.blogs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReader;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderControl;
import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderListener;
import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.extend.UIExtensionPoint;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckView;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;

public class BlogContentPresenter implements BlogContent, BlogReaderListener, TextEditorListener {
    private final WorkspaceDeckView view;
    private final BlogComponents components;
    private StateDTO content;
    private final BlogContentListener listener;
    private final BlogReader reader;
    private final BlogReaderControl readerControl;

    public BlogContentPresenter(final BlogContentListener listener, final WorkspaceDeckView view) {
        this.listener = listener;
        this.view = view;
        this.components = new BlogComponents(this);
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
                            DefaultDispatcher.getInstance().fire(WorkspaceEvents.DISABLE_RATEIT, null, null);
                            TextEditor editor = components.getDocumentEditor();
                            editor.setContent(content.getContent());
                            view.show(editor.getView());
                            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT,
                                    UIExtensionPoint.CONTENT_TOOLBAR_LEFT, null);
                            DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXT_POINT,
                                    UIExtensionPoint.CONTENT_TOOLBAR_LEFT, editor.getToolBar());
                        } else {
                            FolderEditor editor = components.getFolderEditor();
                            editor.setFolder(content.getFolder());
                            view.show(editor.getView());
                        }
                        listener.onEdit();
                    }
                }, null);
    }

    public void onSave(final String text) {
        content.setContent(text);
        DefaultDispatcher.getInstance().fire(BlogsEvents.SAVE_DOCUMENT, content, this);
        // Re-enable rateIt widget
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ENABLE_RATEIT, null, null);
    }

    public void onCancel() {
        showContent();
        listener.onCancel();
        // Re-enable rateIt widget
        DefaultDispatcher.getInstance().fire(WorkspaceEvents.ENABLE_RATEIT, null, null);
    }

    public void onDelete() {
        DefaultDispatcher.getInstance().fire(BlogsEvents.DEL_CONTENT, content.getDocumentId(), null);
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
            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT, UIExtensionPoint.CONTENT_TOOLBAR_LEFT,
                    null);
            DefaultDispatcher.getInstance().fire(PlatformEvents.ATTACH_TO_EXT_POINT,
                    UIExtensionPoint.CONTENT_TOOLBAR_LEFT, readerControl.getView());
            view.show(reader.getView());
        } else {
            FolderViewer viewer = components.getFolderViewer();
            viewer.setFolder(content.getFolder());
            DefaultDispatcher.getInstance().fire(PlatformEvents.CLEAR_EXT_POINT, UIExtensionPoint.CONTENT_TOOLBAR_LEFT,
                    null);
            view.show(viewer.getView());
        }
    }

}
