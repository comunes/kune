package cc.kune.core.client.sn;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.sn.UserSNPresenter.UserSNView;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.dto.UserSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class UserSNPanel extends AbstractSNPanel implements UserSNView {

  private final I18nTranslationService i18n;

  @Inject
  public UserSNPanel(final I18nTranslationService i18n, final GuiProvider guiProvider,
      final GSpaceArmor armor) {
    super(i18n, guiProvider, armor);
    this.i18n = i18n;
    setVisibleImpl(false);
    mainTitle.setText(i18n.t("His/her network:"));
    mainTitle.setTitle(i18n.t("This user buddies and groups where participes"));
    firstCategoryLabel.setText(i18n.t("Buddies"));
    setTooltip(firstCategoryLabel, i18n.t("This user buddies"));
    sndCategoryLabel.setText(i18n.t("Participates in"));
    setTooltip(sndCategoryLabel, i18n.t("Groups in which this user participates"));
    trdCategoryLabel.setText("NOT USED");
    setTooltip(trdCategoryLabel, "NOT USED");
    super.setTrdCategoryVisible(false);
    sndDeckLabel.setText("NOT USED");
    bottomActionsToolbar = new ActionFlowPanel(guiProvider);
    bottomPanel.add(bottomActionsToolbar);
    bottomActionsToolbar.setStyleName("k-sn-bottomPanel-actions");
    armor.getEntityToolsNorth().add(widget);
    deck.showWidget(2);
  }

  @Override
  public void addBuddie(final UserSimpleDTO user, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menu) {
    firstCategoryFlow.add(createThumb(user.getShortName(), avatarUrl, tooltip, tooltipTitle, menu));
  }

  @Override
  public void addParticipation(final GroupDTO group, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menu) {
    sndCategoryFlow.add(createThumb(group.getShortName(), avatarUrl, tooltip, tooltipTitle, menu));
  }

  @Override
  public void addTextToBuddieList(final String text) {
    firstCategoryFlow.add(new Label(text));
  }

  @Override
  public void setBuddiesCount(final int count) {
    firstCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
  }

  @Override
  public void setBuddiesVisible(final boolean visible) {
    super.setFirstCategoryVisible(visible);
  }

  @Override
  public void setNoBuddies() {
    firstDeckLabel.setText(i18n.t("This user has no buddies yet"));
    deck.showWidget(0);
  }

  @Override
  public void setParticipationCount(final int count) {
    sndCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
  }

  @Override
  public void setParticipationVisible(final boolean visible) {
    super.setSndCategoryVisible(visible);
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
    firstDeckLabel.setText(i18n.t(CoreMessages.BUDDIES_NOT_PUBLIC));
    deck.showWidget(0);
  }

}
