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
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.avatar.SmallAvatarDecorator;
import cc.kune.core.client.dnd.KuneDragController;
import cc.kune.core.client.dnd.NotImplementedDropManager;
import cc.kune.core.client.sn.GroupSNPresenter.GroupSNView;
import cc.kune.core.client.ui.BasicDragableThumb;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GroupSNPanel extends AbstractSNPanel implements GroupSNView {

  @Inject
  public GroupSNPanel(final I18nTranslationService i18n, final GuiProvider guiProvider,
      final GSpaceArmor armor, final Provider<SmallAvatarDecorator> avatarDecorator,
      final KuneDragController dragController, final NotImplementedDropManager notDrop) {
    super(i18n, guiProvider, armor, avatarDecorator, dragController, notDrop);
    setVisibleImpl(false);
    mainTitle.setText(i18n.t("Group members"));
    Tooltip.to(mainTitle, i18n.t("Users and groups collaborating in this group"));
    firstCategoryLabel.setText(i18n.t("Administrators"));
    setTooltip(firstCategoryLabel, i18n.t("Users who can administrate this group"));
    sndCategoryLabel.setText(i18n.t("Collaborators"));
    setTooltip(sndCategoryLabel, i18n.t("Others who collaborate with this group"));
    trdCategoryLabel.setText(i18n.t("Pending"));
    setTooltip(trdCategoryLabel,
        i18n.t("Users pending to be accepted in this group by the administrators"));
    sndDeckLabel.setText(i18n.t("This is an orphaned project, if you are interested in contributing please request to join"));
    firstDeckLabel.setText(i18n.t("The members of this group are not public"));
    bottomActionsToolbar = new ActionFlowPanel(guiProvider, i18n);
    bottomPanel.add(bottomActionsToolbar);
    bottomActionsToolbar.setStyleName("k-sn-bottomPanel-actions");
    armor.getEntityToolsNorth().add(widget);
    deck.showWidget(2);
  }

  @Override
  public void addAdmin(final GroupDTO group, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menu) {
    final BasicDragableThumb thumb = createThumb(group.getShortName(), avatarUrl, tooltip, tooltipTitle,
        menu);
    firstCategoryFlow.add(group.isPersonal() ? (Widget) decorateAvatarWithXmppStatus(
        group.getShortName(), thumb) : thumb);
  }

  @Override
  public void addCollab(final GroupDTO group, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menu) {
    final BasicDragableThumb thumb = createThumb(group.getShortName(), avatarUrl, tooltip, tooltipTitle,
        menu);
    sndCategoryFlow.add(group.isPersonal() ? (Widget) decorateAvatarWithXmppStatus(group.getShortName(),
        thumb) : thumb);
  }

  @Override
  public void addPending(final GroupDTO group, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menu) {
    final BasicDragableThumb thumb = createThumb(group.getShortName(), avatarUrl, tooltip, tooltipTitle,
        menu);
    trdCategoryFlow.add(thumb);
  }

  @Override
  public void setAdminsCount(final int count) {
    armor.getEntityToolsNorth();
    firstCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
  }

  @Override
  public void setAdminsVisible(final boolean visible, final boolean areMany) {
    super.setFirstCategoryVisible(visible, areMany);
  }

  @Override
  public void setCollabsCount(final int count) {
    sndCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
  }

  @Override
  public void setCollabsVisible(final boolean visible, final boolean areMany) {
    super.setSndCategoryVisible(visible, areMany);
  }

  @Override
  public void setPendingsCount(final int count) {
    trdCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
  }

  @Override
  public void setPendingVisible(final boolean visible, final boolean areMany) {
    super.setTrdCategoryVisible(visible, areMany);
  }

  @Override
  public void setVisible(final boolean visible) {
    setVisibleImpl(visible);
  }

  private void setVisibleImpl(final boolean visible) {
    mainPanel.setVisible(visible);
  }

  @Override
  public void showMemberNotPublic() {
    deck.showWidget(0);
  }

  @Override
  public void showMembers() {
    deck.showWidget(2);
  }

  @Override
  public void showOrphan() {
    deck.showWidget(1);
  }

}
