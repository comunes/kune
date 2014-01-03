/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.ui.DottedTabPanel;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.services.ClientFileDownloadUtils;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.GroupDTO;
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

// TODO: Auto-generated Javadoc
/**
 * The Class HSpacePanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class HSpacePanel extends ViewImpl implements HSpaceView {

  /**
   * The Interface HSpacePanelUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface HSpacePanelUiBinder extends UiBinder<Widget, HSpacePanel> {
  }

  /** The Constant K_HOME_GLOBAL_STATS. */
  public static final String K_HOME_GLOBAL_STATS = "k-home-global-stats";

  /** The Constant K_HOME_GROUP_STATS. */
  public static final String K_HOME_GROUP_STATS = "k-home-group-stats";

  /** The Constant K_HOME_TOOLBAR. */
  public static final String K_HOME_TOOLBAR = "k-home-toolbar";

  /** The ui binder. */
  private static HSpacePanelUiBinder uiBinder = GWT.create(HSpacePanelUiBinder.class);

  /** The down utils. */
  private final ClientFileDownloadUtils downUtils;

  /** The global stats. */
  @UiField
  FlowPanel globalStats;

  /** The global stats parent. */
  private final RootPanel globalStatsParent;

  /** The global stats title. */
  @UiField
  public Label globalStatsTitle;

  /** The global stats total groups count. */
  @UiField
  public InlineLabel globalStatsTotalGroupsCount;

  /** The global stats total groups title. */
  @UiField
  public InlineLabel globalStatsTotalGroupsTitle;

  /** The global stats total users count. */
  @UiField
  public InlineLabel globalStatsTotalUsersCount;

  /** The global stats total users title. */
  @UiField
  public InlineLabel globalStatsTotalUsersTitle;

  /** The group stats parent. */
  private final RootPanel groupStatsParent;

  /** The home toolbar. */
  private final ActionSimplePanel homeToolbar;

  /** The last activity in your group. */
  @UiField
  public FlowPanel lastActivityInYourGroup;

  /** The last activity in your groups text. */
  private final String lastActivityInYourGroupsText;

  /** The last activity in your group title. */
  @UiField
  public Label lastActivityInYourGroupTitle;

  /** The last activity panel. */
  @UiField
  FlowPanel lastActivityPanel;

  /** The last groups. */
  @UiField
  public FlowPanel lastGroups;

  /** The last groups panel. */
  @UiField
  FlowPanel lastGroupsPanel;

  /** The last groups title. */
  @UiField
  public Label lastGroupsTitle;

  /** The last published contents. */
  @UiField
  public FlowPanel lastPublishedContents;

  /** The last published contents title. */
  @UiField
  public Label lastPublishedContentsTitle;

  /** The last published panel. */
  @UiField
  FlowPanel lastPublishedPanel;

  /** The link prov. */
  private final Provider<GroupContentHomeLink> linkProv;

  /** The tab panel. */
  private final DottedTabPanel tabPanel;

  /** The unread in your inbox. */
  @UiField
  public Hyperlink unreadInYourInbox;

  /** The widget. */
  private final Widget widget;

  /**
   * Instantiates a new h space panel.
   * 
   * @param i18n
   *          the i18n
   * @param armor
   *          the armor
   * @param linkProv
   *          the link prov
   * @param downUtils
   *          the down utils
   * @param homeToolbar
   *          the home toolbar
   */
  @Inject
  public HSpacePanel(final I18nTranslationService i18n, final GSpaceArmor armor,
      final Provider<GroupContentHomeLink> linkProv, final ClientFileDownloadUtils downUtils,
      final ActionSimplePanel homeToolbar) {
    this.linkProv = linkProv;
    this.downUtils = downUtils;
    this.homeToolbar = homeToolbar;
    widget = uiBinder.createAndBindUi(this);
    globalStatsTitle.setText(i18n.t("Stats"));
    globalStatsTotalGroupsTitle.setText(i18n.t("Hosted groups:"));
    globalStatsTotalUsersTitle.setText(i18n.t("Registered users:"));
    final String lastCreatedGroupsText = i18n.t("Latest created groups");
    final String lastPublicationsText = i18n.t("Latest publications");
    lastActivityInYourGroupsText = i18n.t("Latest activity in your groups");
    lastGroupsTitle.setText(lastCreatedGroupsText);
    lastPublishedContentsTitle.setText(lastPublicationsText);
    lastActivityInYourGroupTitle.setText(lastActivityInYourGroupsText);
    tabPanel = new DottedTabPanel("440px", "200px");
    tabPanel.addTab(lastGroupsPanel, lastCreatedGroupsText);
    tabPanel.addTab(lastPublishedPanel, lastPublicationsText);
    globalStats.removeFromParent();
    unreadInYourInbox.setTargetHistoryToken(SiteTokens.WAVE_INBOX);
    globalStatsParent = RootPanel.get(K_HOME_GLOBAL_STATS);
    groupStatsParent = RootPanel.get(K_HOME_GROUP_STATS);
    final RootPanel homeToolbarParent = RootPanel.get(K_HOME_TOOLBAR);
    if (homeToolbarParent != null) {
      homeToolbarParent.add(homeToolbar);
    }
    if (globalStatsParent != null) {
      globalStatsParent.add(globalStats);
    }
    if (groupStatsParent != null) {
      groupStatsParent.add(tabPanel);
    }
    armor.getHomeSpace().add(RootPanel.get("k-home-wrapper"));
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return widget;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.hspace.client.HSpacePresenter.HSpaceView#blinkCurrentTab()
   */
  @Override
  public void blinkCurrentTab() {
    tabPanel.blinkCurrentTab();
  }

  /**
   * Format.
   * 
   * @param modifiedOn
   *          the modified on
   * @param name
   *          the name
   * @return the string
   */
  private String format(final Long modifiedOn, final String name) {
    final String modOn = DateUtils.getInstance().formatPastDate(modifiedOn);
    return TextUtils.ellipsis(modOn + " ~ " + name, 50);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.HSpacePresenter.HSpaceView#getGlobalStatsTotalGroupsCount
   * ()
   */
  @Override
  public HasText getGlobalStatsTotalGroupsCount() {
    return globalStatsTotalGroupsCount;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.HSpacePresenter.HSpaceView#getGlobalStatsTotalUsersCount
   * ()
   */
  @Override
  public HasText getGlobalStatsTotalUsersCount() {
    return globalStatsTotalUsersCount;
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.hspace.client.HSpacePresenter.HSpaceView#getToolbar()
   */
  @Override
  public IsActionExtensible getToolbar() {
    return homeToolbar;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.HSpacePresenter.HSpaceView#getUnreadInYourInbox()
   */
  @Override
  public HasText getUnreadInYourInbox() {
    return unreadInYourInbox;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.HSpacePresenter.HSpaceView#setInboxUnreadVisible(
   * boolean)
   */
  @Override
  public void setInboxUnreadVisible(final boolean visible) {
    unreadInYourInbox.setVisible(visible);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.HSpacePresenter.HSpaceView#setLastContentsOfMyGroup
   * (java.util.List)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.HSpacePresenter.HSpaceView#setLastGroups(java.util
   * .List)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.HSpacePresenter.HSpaceView#setLastPublishedContents
   * (java.util.List)
   */
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.HSpacePresenter.HSpaceView#setStatsVisible(boolean)
   */
  @Override
  public void setStatsVisible(final boolean visible) {
    if (globalStatsParent != null) {
      globalStatsParent.setVisible(visible);
    }
    if (groupStatsParent != null) {
      groupStatsParent.setVisible(visible);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.hspace.client.HSpacePresenter.HSpaceView#setUserGroupsActivityVisible
   * (boolean)
   */
  @Override
  public void setUserGroupsActivityVisible(final boolean visible) {
    final boolean isAttached = tabPanel.getWidgetIndex(lastActivityPanel) != -1;
    if (visible && !isAttached) {
      tabPanel.insertTab(lastActivityPanel, lastActivityInYourGroupsText, 0);
    } else if (!visible && isAttached) {
      tabPanel.removeTab(lastActivityPanel);
    }
    tabPanel.selectTab(0);
  }

}
