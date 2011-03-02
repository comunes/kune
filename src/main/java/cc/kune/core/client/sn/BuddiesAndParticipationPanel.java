package cc.kune.core.client.sn;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.BasicThumb;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.sn.BuddiesAndParticipationPresenter.BuddiesAndParticipationView;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.WsArmor;

import com.google.inject.Inject;

public class BuddiesAndParticipationPanel extends AbstractSNPanel implements BuddiesAndParticipationView {

    @Inject
    public BuddiesAndParticipationPanel(final I18nTranslationService i18n, final GuiProvider guiProvider,
            final WsArmor armor) {
        super(i18n, guiProvider, armor);
        setVisible(false);
        mainTitle.setText(i18n.t("His/her Net:"));
        mainTitle.setTitle(i18n.t("This user buddies and groups where participes"));
        firstCategoryLabel.setText(i18n.t("Buddies"));
        firstCategoryLabel.setTitle(i18n.t("This user buddies"));
        sndCategoryLabel.setText(i18n.t("and participates in"));
        sndCategoryLabel.setTitle(i18n.t("Groups in which this user participates"));
        firstDeckLabel.setText(i18n.t(CoreMessages.BUDDIES_NOT_PUBLIC));
        trdCategoryLabel.setText("NOT USED");
        trdCategoryLabel.setTitle("NOT USED");
        trdCategoryPanel.setVisible(false);
        sndDeckLabel.setText("NOT USED");
        bottomActionsToolbar = new ActionFlowPanel(guiProvider);
        bottomPanel.add(bottomActionsToolbar);
        bottomActionsToolbar.setStyleName("k-sn-bottomPanel-actions");
        armor.getEntityToolsNorth().add(widget);
        deck.showWidget(2);
    }

    @Override
    public void addBuddie(final GroupDTO group, final String avatarUrl, final String tooltip,
            final String tooltipTitle, final GuiActionDescCollection menu) {
        final BasicThumb thumb = createThumb(group, avatarUrl, tooltip, tooltipTitle, menu);
        firstCategoryFlow.add(thumb);
    }

    @Override
    public void addParticipation(final GroupDTO group, final String avatarUrl, final String tooltip,
            final String tooltipTitle, final GuiActionDescCollection menu) {
        final BasicThumb thumb = createThumb(group, avatarUrl, tooltip, tooltipTitle, menu);
        sndCategoryFlow.add(thumb);
    }

    @Override
    public void setBuddiesCount(final int count) {
        firstCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
    }

    @Override
    public void setParticipationCount(final int count) {
        sndCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
    }

    @Override
    public void setParticipationVisible(final boolean visible) {
        sndCategoryPanel.setVisible(visible);
    }

    @Override
    public void setVisible(final boolean visible) {
        mainPanel.setVisible(visible);
    }

    @Override
    public void showBuddies() {
        deck.showWidget(2);
    }

    @Override
    public void showBuddiesNotPublic() {
        deck.showWidget(0);
    }

}
