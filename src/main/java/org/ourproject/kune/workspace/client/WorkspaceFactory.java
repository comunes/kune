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
package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.app.DesktopView;
import org.ourproject.kune.platf.client.app.ui.DesktopPanel;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetsManager;
import org.ourproject.kune.platf.client.services.ColorTheme;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.workspace.client.editor.TextEditor;
import org.ourproject.kune.workspace.client.editor.TextEditorListener;
import org.ourproject.kune.workspace.client.editor.TextEditorPanel;
import org.ourproject.kune.workspace.client.editor.TextEditorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorComponent;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorPresenter;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorView;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorComponent;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorPresenter;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorView;
import org.ourproject.kune.workspace.client.i18n.ui.I18nTranslatorPanel;
import org.ourproject.kune.workspace.client.i18n.ui.LanguageSelectorPanel;
import org.ourproject.kune.workspace.client.licensefoot.LicenseComponent;
import org.ourproject.kune.workspace.client.licensefoot.LicensePresenter;
import org.ourproject.kune.workspace.client.licensefoot.LicenseView;
import org.ourproject.kune.workspace.client.licensefoot.ui.LicensePanel;
import org.ourproject.kune.workspace.client.presence.ui.GroupSummaryPanel;
import org.ourproject.kune.workspace.client.sitebar.bar.SiteBarListener;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchView;
import org.ourproject.kune.workspace.client.socialnet.GroupLiveSearchPresenter;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersPresenter;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersView;
import org.ourproject.kune.workspace.client.socialnet.ParticipationPresenter;
import org.ourproject.kune.workspace.client.socialnet.ParticipationView;
import org.ourproject.kune.workspace.client.socialnet.UserLiveSearchPresenter;
import org.ourproject.kune.workspace.client.socialnet.ui.GroupLiveSearchPanel;
import org.ourproject.kune.workspace.client.socialnet.ui.GroupMembersPanel;
import org.ourproject.kune.workspace.client.socialnet.ui.ParticipationPanel;
import org.ourproject.kune.workspace.client.socialnet.ui.UserLiveSearchPanel;
import org.ourproject.kune.workspace.client.summary.GroupSummaryPresenter;
import org.ourproject.kune.workspace.client.summary.GroupSummaryView;
import org.ourproject.kune.workspace.client.theme.ThemeMenuPresenter;
import org.ourproject.kune.workspace.client.theme.ThemeMenuView;
import org.ourproject.kune.workspace.client.theme.ui.ThemeMenuPanel;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItems;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsPanel;
import org.ourproject.kune.workspace.client.ui.ctx.items.ContextItemsPresenter;
import org.ourproject.kune.workspace.client.workspace.ContentBottomToolBarComponent;
import org.ourproject.kune.workspace.client.workspace.ContentBottomToolBarPresenter;
import org.ourproject.kune.workspace.client.workspace.ContentBottomToolBarView;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitleComponent;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.ContentSubTitleView;
import org.ourproject.kune.workspace.client.workspace.ContentTitleComponent;
import org.ourproject.kune.workspace.client.workspace.ContentTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.ContentTitleView;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarComponent;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarPresenter;
import org.ourproject.kune.workspace.client.workspace.ContentToolBarView;
import org.ourproject.kune.workspace.client.workspace.GroupLiveSearchComponent;
import org.ourproject.kune.workspace.client.workspace.GroupMembersComponent;
import org.ourproject.kune.workspace.client.workspace.GroupSummaryComponent;
import org.ourproject.kune.workspace.client.workspace.ParticipationComponent;
import org.ourproject.kune.workspace.client.workspace.ThemeMenuComponent;
import org.ourproject.kune.workspace.client.workspace.UserLiveSearchComponent;
import org.ourproject.kune.workspace.client.workspace.Workspace;
import org.ourproject.kune.workspace.client.workspace.WorkspacePresenter;
import org.ourproject.kune.workspace.client.workspace.WorkspaceView;
import org.ourproject.kune.workspace.client.workspace.ui.ContentBottomToolBarPanel;
import org.ourproject.kune.workspace.client.workspace.ui.ContentSubTitlePanel;
import org.ourproject.kune.workspace.client.workspace.ui.ContentTitlePanel;
import org.ourproject.kune.workspace.client.workspace.ui.ContentToolBarPanel;
import org.ourproject.kune.workspace.client.workspace.ui.WorkspacePanel;

public class WorkspaceFactory {

    private static Session session;
    private static I18nTranslationService i18n;
    private static ColorTheme colorTheme;
    private static KuneErrorHandler errorHandler;

    public static ContentBottomToolBarComponent createContentBottomToolBarComponent() {
	final ContentBottomToolBarPresenter presenter = new ContentBottomToolBarPresenter();
	final ContentBottomToolBarView view = new ContentBottomToolBarPanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public static ContentSubTitleComponent createContentSubTitleComponent() {
	final ContentSubTitlePresenter presenter = new ContentSubTitlePresenter(i18n);
	final ContentSubTitleView view = new ContentSubTitlePanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public static ContentTitleComponent createContentTitleComponent() {
	final ContentTitlePresenter presenter = new ContentTitlePresenter(i18n, errorHandler);
	final ContentTitleView view = new ContentTitlePanel(presenter);
	presenter.init(view);
	return presenter;
    }

    public static ContentToolBarComponent createContentToolBarComponent() {
	final ContentToolBarPresenter presenter = new ContentToolBarPresenter();
	final ContentToolBarView view = new ContentToolBarPanel(presenter);
	presenter.init(view);
	return presenter;
    }

    public static ContextItems createContextItems() {
	final ContextItemsPresenter presenter = new ContextItemsPresenter(i18n);
	final ContextItemsPanel panel = new ContextItemsPanel(presenter, i18n);
	presenter.init(panel);
	return presenter;
    }

    public static DesktopView createDesktop(final Workspace workspace, final SiteBarListener listener,
	    final Session session) {
	return new DesktopPanel(workspace, listener, session, i18n);
    }

    public static TextEditor createDocumentEditor(final TextEditorListener listener) {
	final TextEditorPresenter presenter = new TextEditorPresenter(listener, true);
	final TextEditorPanel panel = new TextEditorPanel(presenter, i18n);
	presenter.init(panel);
	return presenter;
    }

    public static GroupLiveSearchComponent createGroupLiveSearchComponent() {
	final GroupLiveSearchPresenter presenter = new GroupLiveSearchPresenter();
	final EntityLiveSearchView view = new GroupLiveSearchPanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public static GroupMembersComponent createGroupMembersComponent() {
	final GroupMembersPresenter presenter = new GroupMembersPresenter(i18n);
	final GroupMembersView view = new GroupMembersPanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public static GroupSummaryComponent createGroupSummaryComponent() {
	final GroupSummaryPresenter presenter = new GroupSummaryPresenter();
	final GroupSummaryView view = new GroupSummaryPanel(presenter, i18n, colorTheme);
	presenter.init(view);
	return presenter;
    }

    public static I18nTranslatorComponent createI18nTranslatorComponent() {
	final I18nTranslatorPresenter presenter = new I18nTranslatorPresenter(session);
	final I18nTranslatorView view = new I18nTranslatorPanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public static LanguageSelectorComponent createLanguageSelectorComponent() {
	final LanguageSelectorPresenter presenter = new LanguageSelectorPresenter(session);
	final LanguageSelectorView view = new LanguageSelectorPanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public static LicenseComponent createLicenseComponent() {
	final LicensePresenter presenter = new LicensePresenter();
	final LicenseView view = new LicensePanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public static ParticipationComponent createParticipationComponent() {
	final ParticipationPresenter presenter = new ParticipationPresenter(i18n);
	final ParticipationView view = new ParticipationPanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public static ThemeMenuComponent createThemeMenuComponent() {
	final ThemeMenuPresenter presenter = new ThemeMenuPresenter();
	final ThemeMenuView view = new ThemeMenuPanel(presenter, i18n, colorTheme);
	presenter.init(view);
	return presenter;
    }

    public static UserLiveSearchComponent createUserLiveSearchComponent() {
	final UserLiveSearchPresenter presenter = new UserLiveSearchPresenter();
	final EntityLiveSearchView view = new UserLiveSearchPanel(presenter, i18n);
	presenter.init(view);
	return presenter;
    }

    public static Workspace createWorkspace(final Session session,
	    final ExtensibleWidgetsManager extensionPointManager, final I18nTranslationService i18n,
	    final ColorTheme colorTheme, final KuneErrorHandler errorHandler) {
	WorkspaceFactory.session = session;
	WorkspaceFactory.i18n = i18n;
	WorkspaceFactory.colorTheme = colorTheme;
	WorkspaceFactory.errorHandler = errorHandler;
	final WorkspacePresenter workspace = new WorkspacePresenter(session);
	final WorkspaceView view = new WorkspacePanel(workspace, i18n, colorTheme);
	workspace.init(view, extensionPointManager);
	return workspace;
    }

}
