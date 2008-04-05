
package org.ourproject.kune.workspace.client.workspace;

import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorComponent;
import org.ourproject.kune.workspace.client.licensefoot.LicenseComponent;

public class WorkspaceUIComponents {
    private LicenseComponent license;
    private ContentTitleComponent contentTitle;
    private GroupMembersComponent groupMembers;
    private ParticipationComponent participatesInGroups;
    private GroupSummaryComponent groupSummary;
    private ContentSubTitleComponent contentSubTitle;
    private ContentBottomToolBarComponent contentBottomToolBar;
    private ThemeMenuComponent themeMenu;
    private TagsComponent tags;
    private ContentToolBarComponent contentToolBar;
    private GroupLiveSearchComponent groupLiveSearch;
    private I18nTranslatorComponent i18nTranslatorSearch;
    private UserLiveSearchComponent userLiveSearch;

    public WorkspaceUIComponents(final WorkspacePresenter presenter) {
    }

    public LicenseComponent getLicenseComponent() {
        if (license == null) {
            license = WorkspaceFactory.createLicenseComponent();
        }
        return license;
    }

    public ContentTitleComponent getContentTitleComponent() {
        if (contentTitle == null) {
            contentTitle = WorkspaceFactory.createContentTitleComponent();
        }
        return contentTitle;
    }

    public ContentSubTitleComponent getContentSubTitleComponent() {
        if (contentSubTitle == null) {
            contentSubTitle = WorkspaceFactory.createContentSubTitleComponent();
        }
        return contentSubTitle;
    }

    public GroupMembersComponent getGroupMembersComponent() {
        if (groupMembers == null) {
            groupMembers = WorkspaceFactory.createGroupMembersComponent();
        }
        return groupMembers;
    }

    public GroupSummaryComponent getGroupSummaryComponent() {
        if (groupSummary == null) {
            groupSummary = WorkspaceFactory.createGroupSummaryComponent();
        }
        return groupSummary;
    }

    public ParticipationComponent getParticipationComponent() {
        if (participatesInGroups == null) {
            participatesInGroups = WorkspaceFactory.createParticipationComponent();
        }
        return participatesInGroups;
    }

    public ThemeMenuComponent getThemeMenuComponent() {
        if (themeMenu == null) {
            themeMenu = WorkspaceFactory.createThemeMenuComponent();
        }
        return themeMenu;
    }

    public TagsComponent getTagsComponent() {
        if (tags == null) {
            tags = WorkspaceFactory.createTagsComponent();
        }
        return tags;
    }

    public ContentBottomToolBarComponent getContentBottomToolBarComponent() {
        if (contentBottomToolBar == null) {
            contentBottomToolBar = WorkspaceFactory.createContentBottomToolBarComponent();
        }
        return contentBottomToolBar;
    }

    public ContentToolBarComponent getContentToolBarComponent() {
        if (contentToolBar == null) {
            contentToolBar = WorkspaceFactory.createContentToolBarComponent();
        }
        return contentToolBar;
    }

    public GroupLiveSearchComponent getGroupLiveSearchComponent() {
        if (groupLiveSearch == null) {
            groupLiveSearch = WorkspaceFactory.createGroupLiveSearchComponent();
        }
        return groupLiveSearch;
    }

    public UserLiveSearchComponent getUserLiveSearchComponent() {
        if (userLiveSearch == null) {
            userLiveSearch = WorkspaceFactory.createUserLiveSearchComponent();
        }
        return userLiveSearch;
    }

    public I18nTranslatorComponent getI18nTranslatorComponent() {
        if (i18nTranslatorSearch == null) {
            i18nTranslatorSearch = WorkspaceFactory.createI18nTranslatorComponent();
        }
        return i18nTranslatorSearch;
    }

}
