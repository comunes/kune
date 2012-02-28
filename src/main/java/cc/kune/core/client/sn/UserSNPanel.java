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
package cc.kune.core.client.sn;

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

public class UserSNPanel extends AbstractSNPanel implements UserSNView {

  private final I18nTranslationService i18n;
  private final Label noBuddies;
  private final Label noPublic;

  @Inject
  public UserSNPanel(final I18nTranslationService i18n, final GuiProvider guiProvider,
      final GSpaceArmor armor, final Provider<SmallAvatarDecorator> avatarDecorator,
      final KuneDragController dragController, final NotImplementedDropManager notDrop) {
    super(i18n, guiProvider, armor, avatarDecorator, dragController, notDrop);
    this.i18n = i18n;

    setVisibleImpl(false);
    mainTitle.setText(i18n.t("His/her network:"));
    mainTitle.setTitle(i18n.t("This user's groups and buddies"));
    firstCategoryLabel.setText(i18n.t("Buddies"));
    setTooltip(firstCategoryLabel, i18n.t("This user's buddies"));
    sndCategoryLabel.setText(i18n.t("Joins in"));
    setTooltip(sndCategoryLabel, i18n.t("Groups which this user Joined"));
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
  }

  @Override
  public void addBuddie(final UserSimpleDTO user, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menu) {
    final BasicDragableThumb thumb = createThumb(user.getShortName(), avatarUrl, tooltip, tooltipTitle,
        menu, user.getStateToken());
    firstCategoryFlow.add((Widget) decorateAvatarWithXmppStatus(user.getShortName(), thumb));
  }

  @Override
  public void addParticipation(final GroupDTO group, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menu) {
    sndCategoryFlow.add(createThumb(group.getCompoundName(), avatarUrl, tooltip, tooltipTitle, menu,
        group.getStateToken()));
  }

  @Override
  public void addTextToBuddieList(final String text) {
    final Label label = new Label(text);
    label.addStyleName("k-sn-collabsCountlabel");
    firstCategoryFlow.add(label);
  }

  @Override
  public void setBuddiesCount(final int count) {
    firstCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
    firstCategoryLabel.setText(i18n.t("Buddies"));
  }

  @Override
  public void setBuddiesVisible(final boolean visible, final boolean areMany) {
    super.setFirstCategoryVisible(visible, areMany);
  }

  @Override
  public void setNoBuddies() {
    firstCategoryFlow.add(noBuddies);
    firstCategoryLabel.setText(i18n.t("Buddies"));
    super.setFirstCategoryVisible(true, true);
  }

  @Override
  public void setParticipationCount(final int count) {
    sndCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
  }

  @Override
  public void setParticipationVisible(final boolean visible, final boolean areMany) {
    super.setSndCategoryVisible(visible, areMany);
  }

  @Override
  public void setVisible(final boolean visible) {
    setVisibleImpl(visible);
  }

  private void setVisibleImpl(final boolean visible) {
    mainPanel.setVisible(visible);
  }

  @Override
  public void showBuddies() {
    deck.showWidget(2);
  }

  @Override
  public void showBuddiesNotPublic() {
    firstCategoryFlow.add(noPublic);
    firstCategoryLabel.setText(i18n.t("Buddies"));
    super.setFirstCategoryVisible(true, true);
  }

}
