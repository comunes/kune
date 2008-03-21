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

package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.app.DesktopView;
import org.ourproject.kune.platf.client.app.ui.DesktopPanel;
import org.ourproject.kune.platf.client.extend.UIExtensionPointManager;
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
import org.ourproject.kune.workspace.client.tags.TagsPresenter;
import org.ourproject.kune.workspace.client.tags.TagsView;
import org.ourproject.kune.workspace.client.tags.ui.TagsPanel;
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
import org.ourproject.kune.workspace.client.workspace.TagsComponent;
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

    public static Workspace createWorkspace(final Session session,
            final UIExtensionPointManager extensionPointManager) {
        WorkspaceFactory.session = session;
        WorkspacePresenter workspace = new WorkspacePresenter(session);
        WorkspaceView view = new WorkspacePanel(workspace);
        workspace.init(view, extensionPointManager);
        return workspace;
    }

    public static TextEditor createDocumentEditor(final TextEditorListener listener) {
        TextEditorPresenter presenter = new TextEditorPresenter(listener, true);
        TextEditorPanel panel = new TextEditorPanel(presenter);
        presenter.init(panel);
        return presenter;
    }

    public static DesktopView createDesktop(final Workspace workspace, final SiteBarListener listener,
            final Session session) {
        return new DesktopPanel(workspace, listener, session);
    }

    public static LicenseComponent createLicenseComponent() {
        LicensePresenter presenter = new LicensePresenter();
        LicenseView view = new LicensePanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static ContentTitleComponent createContentTitleComponent() {
        ContentTitlePresenter presenter = new ContentTitlePresenter();
        ContentTitleView view = new ContentTitlePanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static ContentSubTitleComponent createContentSubTitleComponent() {
        ContentSubTitlePresenter presenter = new ContentSubTitlePresenter();
        ContentSubTitleView view = new ContentSubTitlePanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static ContextItems createContextItems() {
        ContextItemsPresenter presenter = new ContextItemsPresenter();
        ContextItemsPanel panel = new ContextItemsPanel(presenter);
        presenter.init(panel);
        return presenter;
    }

    public static GroupMembersComponent createGroupMembersComponent() {
        GroupMembersPresenter presenter = new GroupMembersPresenter();
        GroupMembersView view = new GroupMembersPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static ParticipationComponent createParticipationComponent() {
        ParticipationPresenter presenter = new ParticipationPresenter();
        ParticipationView view = new ParticipationPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static GroupSummaryComponent createGroupSummaryComponent() {
        GroupSummaryPresenter presenter = new GroupSummaryPresenter();
        GroupSummaryView view = new GroupSummaryPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static ThemeMenuComponent createThemeMenuComponent() {
        ThemeMenuPresenter presenter = new ThemeMenuPresenter();
        ThemeMenuView view = new ThemeMenuPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static TagsComponent createTagsComponent() {
        TagsPresenter presenter = new TagsPresenter(session);
        TagsView view = new TagsPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static ContentBottomToolBarComponent createContentBottomToolBarComponent() {
        ContentBottomToolBarPresenter presenter = new ContentBottomToolBarPresenter();
        ContentBottomToolBarView view = new ContentBottomToolBarPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static ContentToolBarComponent createContentToolBarComponent() {
        ContentToolBarPresenter presenter = new ContentToolBarPresenter();
        ContentToolBarView view = new ContentToolBarPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static GroupLiveSearchComponent createGroupLiveSearchComponent() {
        GroupLiveSearchPresenter presenter = new GroupLiveSearchPresenter();
        EntityLiveSearchView view = new GroupLiveSearchPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static UserLiveSearchComponent createUserLiveSearchComponent() {
        UserLiveSearchPresenter presenter = new UserLiveSearchPresenter();
        EntityLiveSearchView view = new UserLiveSearchPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static I18nTranslatorComponent createI18nTranslatorComponent() {
        I18nTranslatorPresenter presenter = new I18nTranslatorPresenter(session);
        I18nTranslatorView view = new I18nTranslatorPanel(presenter);
        presenter.init(view);
        return presenter;
    }

    public static LanguageSelectorComponent createLanguageSelectorComponent() {
        LanguageSelectorPresenter presenter = new LanguageSelectorPresenter(session);
        LanguageSelectorView view = new LanguageSelectorPanel(presenter);
        presenter.init(view);
        return presenter;
    }

}
