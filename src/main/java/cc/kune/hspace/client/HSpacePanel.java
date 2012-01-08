/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.hspace.client;

import java.util.List;

import org.waveprotocol.wave.client.common.util.DateUtils;

import cc.kune.common.client.ui.DottedTabPanel;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.utils.SharedFileDownloadUtils;
import cc.kune.gspace.client.armor.GSpaceArmor;
import cc.kune.hspace.client.HSpacePresenter.HSpaceView;

import com.calclab.emite.core.client.packet.TextUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Hyperlink;
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
  public static final String K_HOME_GLOBAL_STATS = "k-home-global-stats";

  public static final String K_HOME_GROUP_STATS = "k-home-group-stats";

  private static HSpacePanelUiBinder uiBinder = GWT.create(HSpacePanelUiBinder.class);

  private final SharedFileDownloadUtils downUtils;
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
  private final RootPanel groupStatsParent;
  @UiField
  public FlowPanel lastActivityInYourGroup;
  @UiField
  public Label lastActivityInYourGroupTitle;
  @UiField
  FlowPanel lastActivityPanel;
  @UiField
  public FlowPanel lastGroups;
  @UiField
  FlowPanel lastGroupsPanel;
  @UiField
  public Label lastGroupsTitle;
  @UiField
  public FlowPanel lastPublishedContents;
  @UiField
  public Label lastPublishedContentsTitle;
  @UiField
  FlowPanel lastPublishedPanel;

  private final Provider<GroupContentHomeLink> linkProv;

  private final DottedTabPanel tabPanel;

  @UiField
  public Hyperlink unreadInYourInbox;

  private final Widget widget;

  @Inject
  public HSpacePanel(final I18nTranslationService i18n, final GSpaceArmor armor,
      final Provider<GroupContentHomeLink> linkProv, final SharedFileDownloadUtils downUtils) {
    this.linkProv = linkProv;
    this.downUtils = downUtils;
    widget = uiBinder.createAndBindUi(this);
    globalStatsTitle.setText(i18n.t("Stats"));
    globalStatsTotalGroupsTitle.setText(i18n.t("Hosted groups:"));
    globalStatsTotalUsersTitle.setText(i18n.t("Registered users:"));
    lastGroupsTitle.setText(i18n.t("Latest created groups"));
    lastPublishedContentsTitle.setText(i18n.t("Latest publications"));
    lastActivityInYourGroupTitle.setText(i18n.t("Latest activity in your groups"));
    tabPanel = new DottedTabPanel("440px", "200px");
    tabPanel.addTab(lastGroupsPanel);
    tabPanel.addTab(lastPublishedPanel);
    globalStats.removeFromParent();
    unreadInYourInbox.setTargetHistoryToken(SiteTokens.WAVEINBOX);
    globalStatsParent = RootPanel.get(K_HOME_GLOBAL_STATS);
    groupStatsParent = RootPanel.get(K_HOME_GROUP_STATS);
    if (globalStatsParent != null) {
      globalStatsParent.add(globalStats);
    }
    if (groupStatsParent != null) {
      groupStatsParent.add(tabPanel);
    }
    armor.getHomeSpace().add(RootPanel.get("k-home-wrapper"));
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  private String format(final Long modifiedOn, final String name) {
    final String modOn = DateUtils.getInstance().formatPastDate(modifiedOn);
    return TextUtils.ellipsis(modOn + " ~ " + name, 50);
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
  public HasText getUnreadInYourInbox() {
    return unreadInYourInbox;
  }

  @Override
  public void setInboxUnreadVisible(final boolean visible) {
    unreadInYourInbox.setVisible(visible);
  }

  @Override
  public void setLastContentsOfMyGroup(final List<ContentSimpleDTO> lastContentsOfMyGroupsList) {
    lastActivityInYourGroup.clear();
    for (final ContentSimpleDTO content : lastContentsOfMyGroupsList) {
      final GroupContentHomeLink link = linkProv.get();
      final StateToken token = content.getStateToken();
      link.setValues(downUtils.getLogoImageUrl(token.getGroup()),
          format(content.getModifiedOn(), content.getName()), token.toString());
      lastActivityInYourGroup.add(link);
    }
  }

  @Override
  public void setLastGroups(final List<GroupDTO> lastGroupsList) {
    lastGroups.clear();
    for (final GroupDTO group : lastGroupsList) {
      final GroupContentHomeLink link = linkProv.get();
      link.setValues(downUtils.getLogoImageUrl(group.getShortName()),
          format(group.getCreatedOn(), group.getLongName()), group.getShortName());
      lastGroups.add(link);
    }
  }

  @Override
  public void setLastPublishedContents(final List<ContentSimpleDTO> lastPublishedContentsList) {
    lastPublishedContents.clear();
    for (final ContentSimpleDTO content : lastPublishedContentsList) {
      final GroupContentHomeLink link = linkProv.get();
      final StateToken token = content.getStateToken();
      link.setValues(
          downUtils.getLogoImageUrl(token.getGroup()),
          format(content.getModifiedOn(),
              "(" + content.getStateToken().getGroup() + ") " + content.getName()), token.toString());
      lastPublishedContents.add(link);
    }
  }

  @Override
  public void setStatsVisible(final boolean visible) {
    if (globalStatsParent != null) {
      globalStatsParent.setVisible(visible);
    }
    if (groupStatsParent != null) {
      groupStatsParent.setVisible(visible);
    }
  }

  @Override
  public void setUserGroupsActivityVisible(final boolean visible) {
    final boolean isAttached = tabPanel.getWidgetIndex(lastActivityPanel) != -1;
    if (visible && !isAttached) {
      tabPanel.insertTab(lastActivityPanel, 0);
    } else if (!visible && isAttached) {
      tabPanel.removeTab(lastActivityPanel);
    }
    tabPanel.selectTab(0);
  }

}
