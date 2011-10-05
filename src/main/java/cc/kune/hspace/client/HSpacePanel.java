package cc.kune.hspace.client;

import java.util.List;

import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;
import cc.kune.hspace.client.HSpacePresenter.HSpaceView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;

public class HSpacePanel extends ViewImpl implements HSpaceView {

  interface HSpacePanelUiBinder extends UiBinder<Widget, HSpacePanel> {
  }

  private static HSpacePanelUiBinder uiBinder = GWT.create(HSpacePanelUiBinder.class);

  private final FileDownloadUtils downUtils;
  @UiField
  FlowPanel globalStats;
  private final RootPanel globalStatsParent;
  @UiField
  public Label globalStatsTitle;
  @UiField
  public InlineLabel globalStatsTotalGroupsCount;
  @UiField
  public InlineLabel globalStatsTotalGroupsTitle;
  @UiField
  public InlineLabel globalStatsTotalUsersCount;
  @UiField
  public InlineLabel globalStatsTotalUsersTitle;
  @UiField
  FlowPanel groupStats;
  private final RootPanel groupStatsParent;
  @UiField
  public FlowPanel lastActivityInYourGroup;
  @UiField
  public Label lastActivityInYourGroupTitle;
  @UiField
  public FlowPanel lastGroups;
  @UiField
  public Label lastGroupsTitle;
  @UiField
  public FlowPanel lastPublishedContents;
  @UiField
  public Label lastPublishedContentsTitle;

  private final Provider<GroupContentHomeLink> linkProv;

  private final Widget widget;

  @Inject
  public HSpacePanel(final I18nTranslationService i18n, final GSpaceArmor armor,
      final Provider<GroupContentHomeLink> linkProv, final FileDownloadUtils downUtils) {
    this.linkProv = linkProv;
    this.downUtils = downUtils;
    widget = uiBinder.createAndBindUi(this);
    globalStatsTitle.setText(i18n.t("Stats"));
    globalStatsTotalGroupsTitle.setText(i18n.t("Hosted groups:"));
    globalStatsTotalUsersTitle.setText(i18n.t("Registered users:"));
    lastActivityInYourGroupTitle.setText(i18n.t("Latest created groups"));
    lastGroupsTitle.setText(i18n.t("Latest publications"));
    lastPublishedContentsTitle.setText(i18n.t("Latest activity in your groups"));
    globalStats.removeFromParent();
    groupStats.removeFromParent();
    globalStatsParent = RootPanel.get("k-home-global-stats");
    globalStatsParent.add(globalStats);
    groupStatsParent = RootPanel.get("k-home-group-stats");
    groupStatsParent.add(groupStats);
    armor.getHomeSpace().add(RootPanel.get("k-home-wrapper"));
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  @Override
  public HasText getGlobalStatsTotalGroupsCount() {
    return globalStatsTotalGroupsCount;
  }

  @Override
  public HasText getGlobalStatsTotalUsersCount() {
    return globalStatsTotalUsersCount;
  }

  @Override
  public void setLastContentsOfMyGroup(final List<ContentSimpleDTO> lastContentsOfMyGroupsList) {
    lastActivityInYourGroup.clear();
    for (final ContentSimpleDTO content : lastContentsOfMyGroupsList) {
      final GroupContentHomeLink link = linkProv.get();
      final StateToken token = content.getStateToken();
      link.setValues(downUtils.getLogoImageUrl(token.copy().clearDocument().clearFolder()),
          content.getName(), token.toString());
      lastActivityInYourGroup.add(link);
    }
  }

  @Override
  public void setLastGroups(final List<GroupDTO> lastGroupsList) {
    lastGroups.clear();
    for (final GroupDTO group : lastGroupsList) {
      final GroupContentHomeLink link = linkProv.get();
      link.setValues(downUtils.getLogoImageUrl(group.getStateToken()), group.getLongName(),
          group.getShortName());
      lastGroups.add(link);
    }
  }

  @Override
  public void setLastPublishedContents(final List<ContentSimpleDTO> lastPublishedContentsList) {
    lastPublishedContents.clear();
    for (final ContentSimpleDTO content : lastPublishedContentsList) {
      final GroupContentHomeLink link = linkProv.get();
      final StateToken token = content.getStateToken();
      link.setValues(downUtils.getLogoImageUrl(token.copy().clearDocument().clearFolder()),
          content.getName(), token.toString());
      lastPublishedContents.add(link);
    }
  }

  @Override
  public void setStatsVisible(final boolean visible) {
    globalStatsParent.setVisible(visible);
    groupStatsParent.setVisible(visible);
  }

  @Override
  public void setUserGroupsActivityVisible(final boolean logged) {
    lastActivityInYourGroup.setVisible(logged);
    lastActivityInYourGroupTitle.setVisible(logged);
  }

}
