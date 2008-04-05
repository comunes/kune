package org.ourproject.kune.workspace.client.workspace;

import java.util.Iterator;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.extend.UIExtensionElement;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.i18n.I18nTranslatorComponent;
import org.ourproject.kune.workspace.client.licensefoot.LicenseComponent;

public interface Workspace extends Component {

    public static final int MIN_WIDTH = 800;

    public static final int MIN_HEIGHT = 480;

    public void showError(Throwable caught);

    public void showGroup(GroupDTO group, boolean isAdmin);

    public void setTool(String toolName);

    public void setContext(WorkspaceComponent contextComponent);

    public void setContent(WorkspaceComponent contentComponent);

    public void attachTools(Iterator<ClientTool> iterator);

    public void adjustSize(int windowWidth, int clientHeight);

    public LicenseComponent getLicenseComponent();

    public ContentTitleComponent getContentTitleComponent();

    public ContentSubTitleComponent getContentSubTitleComponent();

    public GroupMembersComponent getGroupMembersComponent();

    public ParticipationComponent getParticipationComponent();

    public GroupSummaryComponent getGroupSummaryComponent();

    public GroupLiveSearchComponent getGroupLiveSearchComponent();

    public UserLiveSearchComponent getUserLiveSearchComponent();

    public TagsComponent getTagsComponent();

    public void setTheme(String theme);

    public ThemeMenuComponent getThemeMenuComponent();

    public void setVisible(boolean visible);

    public ContentBottomToolBarComponent getContentBottomToolBarComponent();

    public void attachToExtensionPoint(UIExtensionElement ext);

    public void detachFromExtensionPoint(UIExtensionElement ext);

    public void clearExtensionPoint(String id);

    public I18nTranslatorComponent getI18nTranslatorComponent();

    public int calculateWidth(int clientWidth);

    public int calculateHeight(int clientHeight);

}