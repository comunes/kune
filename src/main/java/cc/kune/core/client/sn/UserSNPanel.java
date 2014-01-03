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
package cc.kune.core.client.sn;

import cc.kune.chat.client.LastConnectedManager;
import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.avatar.SmallAvatarDecorator;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.dnd.NotImplementedDropManager;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.sn.UserSNPresenter.UserSNView;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.gspace.client.armor.GSpaceArmor;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSNPanel.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class UserSNPanel extends AbstractSNPanel implements UserSNView {

  /** The i18n. */
  private final I18nTranslationService i18n;

  /** The no buddies. */
  private final Label noBuddies;

  /** The no public. */
  private final Label noPublic;

  /**
   * Instantiates a new user sn panel.
   * 
   * @param i18n
   *          the i18n
   * @param guiProvider
   *          the gui provider
   * @param armor
   *          the armor
   * @param avatarDecorator
   *          the avatar decorator
   * @param dragController
   *          the drag controller
   * @param notDrop
   *          the not drop
   * @param lastConnectedManager
   *          the last connected manager
   */
  @Inject
  public UserSNPanel(final I18nTranslationService i18n, final GuiProvider guiProvider,
      final GSpaceArmor armor, final Provider<SmallAvatarDecorator> avatarDecorator,
      final KuneDragController dragController, final NotImplementedDropManager notDrop,
      final LastConnectedManager lastConnectedManager) {
    super(i18n, guiProvider, armor, avatarDecorator, dragController, lastConnectedManager);
    this.i18n = i18n;

    setVisibleImpl(false);
    mainTitle.setText(i18n.t("His/her network:"));
    mainTitle.setTitle(i18n.t("This user's groups and buddies"));
    firstCategoryLabel.setText(i18n.t("Buddies"));
    setTooltip(firstCategoryLabel, i18n.t("This user's buddies"));
    sndCategoryLabel.setText(i18n.t("Collaborates in"));
    setTooltip(sndCategoryLabel, i18n.t("Groups which this user joined"));
    trdCategoryLabel.setText("NOT USED");
    setTooltip(trdCategoryLabel, "NOT USED");
    super.setTrdCategoryVisible(false, false);
    sndDeckLabel.setText("NOT USED");
    bottomActionsToolbar = new ActionFlowPanel(guiProvider, i18n);
    bottomPanel.add(bottomActionsToolbar);
    bottomActionsToolbar.setStyleName("k-sn-bottomPanel-actions");
    armor.getEntityToolsNorth().add(widget);
    deck.showWidget(2);
    noBuddies = new Label(i18n.t("This user has no buddies yet"));
    noBuddies.addStyleName("kune-Margin-Medium-l");
    noPublic = new Label(i18n.t(CoreMessages.BUDDIES_NOT_PUBLIC));
    noPublic.addStyleName("kune-Margin-Medium-l");
    notDrop.register(firstCategoryScroll);
    notDrop.register(sndCategoryScroll);
    notDrop.register(trdCategoryScroll);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sn.UserSNPresenter.UserSNView#addBuddie(cc.kune.core
   * .shared.dto.UserSimpleDTO, java.lang.String, java.lang.String,
   * java.lang.String,
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection, boolean)
   */
  @Override
  public void addBuddie(final UserSimpleDTO user, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menu, final boolean dragable) {
    final BasicDragableThumb thumb = createThumb(true, user.getShortName(), user.getShortName(),
        avatarUrl, tooltip, tooltipTitle, menu, user.getStateToken(), dragable);
    firstCategoryFlow.add((Widget) decorateAvatarWithXmppStatus(user.getShortName(), thumb));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sn.UserSNPresenter.UserSNView#addParticipation(cc.kune
   * .core.shared.dto.GroupDTO, java.lang.String, java.lang.String,
   * java.lang.String,
   * cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection, boolean)
   */
  @Override
  public void addParticipation(final GroupDTO group, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menu, final boolean dragable) {
    sndCategoryFlow.add(createThumb(group.isPersonal(), group.getShortName(), group.getCompoundName(),
        avatarUrl, tooltip, tooltipTitle, menu, group.getStateToken(), dragable));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sn.UserSNPresenter.UserSNView#addTextToBuddieList(java
   * .lang.String)
   */
  @Override
  public void addTextToBuddieList(final String text) {
    final Label label = new Label(text);
    label.addStyleName("k-sn-collabsCountlabel");
    firstCategoryFlow.add(label);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.sn.UserSNPresenter.UserSNView#setBuddiesCount(int)
   */
  @Override
  public void setBuddiesCount(final int count) {
    firstCategoryCount.setText(countAsString(count));
    firstCategoryLabel.setText(i18n.t("Buddies"));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sn.UserSNPresenter.UserSNView#setBuddiesVisible(boolean
   * , boolean)
   */
  @Override
  public void setBuddiesVisible(final boolean visible, final boolean areMany) {
    super.setFirstCategoryVisible(visible, areMany);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.sn.UserSNPresenter.UserSNView#setNoBuddies()
   */
  @Override
  public void setNoBuddies() {
    firstCategoryFlow.add(noBuddies);
    firstCategoryLabel.setText(i18n.t("Buddies"));
    super.setFirstCategoryVisible(true, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sn.UserSNPresenter.UserSNView#setParticipationCount
   * (int)
   */
  @Override
  public void setParticipationCount(final int count) {
    sndCategoryCount.setText(countAsString(count));
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sn.UserSNPresenter.UserSNView#setParticipationVisible
   * (boolean, boolean)
   */
  @Override
  public void setParticipationVisible(final boolean visible, final boolean areMany) {
    super.setSndCategoryVisible(visible, areMany);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.sn.UserSNPresenter.UserSNView#setVisible(boolean)
   */
  @Override
  public void setVisible(final boolean visible) {
    setVisibleImpl(visible);
  }

  /**
   * Sets the visible impl.
   * 
   * @param visible
   *          the new visible impl
   */
  private void setVisibleImpl(final boolean visible) {
    mainPanel.setVisible(visible);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.client.sn.UserSNPresenter.UserSNView#showBuddies()
   */
  @Override
  public void showBuddies() {
    deck.showWidget(2);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sn.UserSNPresenter.UserSNView#showBuddiesNotPublic()
   */
  @Override
  public void showBuddiesNotPublic() {
    firstCategoryFlow.add(noPublic);
    firstCategoryLabel.setText(i18n.t("Buddies"));
    super.setFirstCategoryVisible(true, true);
  }

}
