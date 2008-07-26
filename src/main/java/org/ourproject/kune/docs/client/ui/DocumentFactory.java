/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.docs.client.ui;

import org.ourproject.kune.docs.client.cnt.DocumentContent;
import org.ourproject.kune.docs.client.cnt.DocumentContentListener;
import org.ourproject.kune.docs.client.cnt.DocumentContentPresenter;
import org.ourproject.kune.docs.client.cnt.folder.FolderEditor;
import org.ourproject.kune.docs.client.cnt.folder.FolderEditorPresenter;
import org.ourproject.kune.docs.client.cnt.folder.ui.FolderEditorPanel;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewer;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewerPresenter;
import org.ourproject.kune.docs.client.cnt.folder.viewer.FolderViewerView;
import org.ourproject.kune.docs.client.cnt.folder.viewer.ui.FolderViewerPanel;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReader;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControl;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControlPresenter;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderControlView;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderListener;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderPresenter;
import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderView;
import org.ourproject.kune.docs.client.cnt.reader.ui.DocumentReaderControlPanel;
import org.ourproject.kune.docs.client.cnt.reader.ui.DocumentReaderPanel;
import org.ourproject.kune.docs.client.ctx.DocumentContext;
import org.ourproject.kune.docs.client.ctx.DocumentContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContext;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextView;
import org.ourproject.kune.docs.client.ctx.admin.ui.AdminContextPanel;
import org.ourproject.kune.docs.client.ctx.folder.FolderContext;
import org.ourproject.kune.docs.client.ctx.folder.FolderContextPresenter;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.rate.RateIt;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.component.WorkspaceDeckPanel;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.workspace.TagsSummary;

import com.calclab.suco.client.container.Provider;

public class DocumentFactory {

    private final I18nTranslationService i18n;
    private final Session session;
    private final Provider<TagsSummary> tagsSummaryProvider;
    private final WorkspaceSkeleton ws;
    private final RateIt rateIt;

    public DocumentFactory(final I18nTranslationService i18n, final Session session,
	    final Provider<TagsSummary> tagsSummaryProvider, final WorkspaceSkeleton ws, final RateIt rateIt) {
	this.i18n = i18n;
	this.session = session;
	this.tagsSummaryProvider = tagsSummaryProvider;
	this.ws = ws;
	this.rateIt = rateIt;
    }

    public AdminContext createAdminContext() {
	final AdminContextPresenter presenter = new AdminContextPresenter(session, tagsSummaryProvider);
	final AdminContextView view = new AdminContextPanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public DocumentContent createDocumentContent(final DocumentContentListener listener) {
	final WorkspaceDeckPanel panel = new WorkspaceDeckPanel();
	final DocumentContentPresenter presenter = new DocumentContentPresenter(this, listener, panel, session, rateIt);
	return presenter;
    }

    public DocumentContext createDocumentContext() {
	final WorkspaceDeckPanel view = new WorkspaceDeckPanel();
	final DocumentContextPresenter presenter = new DocumentContextPresenter(this, view);
	return presenter;
    }

    public DocumentReader createDocumentReader(final DocumentReaderListener listener) {
	final DocumentReaderView view = new DocumentReaderPanel();
	final DocumentReaderPresenter presenter = new DocumentReaderPresenter(view);
	return presenter;
    }

    public DocumentReaderControl createDocumentReaderControl(final DocumentReaderListener listener) {
	final DocumentReaderControlView view = new DocumentReaderControlPanel(listener, i18n, ws);
	final DocumentReaderControlPresenter presenter = new DocumentReaderControlPresenter(view);
	return presenter;
    }

    public FolderContext createFolderContext() {
	final ContextItems contextItems = WorkspaceFactory.createContextItems();
	final FolderContextPresenter presenter = new FolderContextPresenter(contextItems, i18n);
	return presenter;
    }

    public FolderEditor createFolderEditor() {
	final FolderEditorPanel view = new FolderEditorPanel();
	final FolderEditorPresenter presenter = new FolderEditorPresenter(view);
	return presenter;
    }

    public FolderViewer createFolderViewer() {
	final FolderViewerView view = new FolderViewerPanel();
	final FolderViewerPresenter presenter = new FolderViewerPresenter(view);
	return presenter;
    }

}
