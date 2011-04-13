package cc.kune.core.client.sn;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.BasicThumb;
import cc.kune.core.client.sn.GroupSNPresenter.GroupSNView;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.WsArmor;

import com.google.inject.Inject;

public class GroupSNPanel extends AbstractSNPanel implements GroupSNView {

    @Inject
    public GroupSNPanel(final I18nTranslationService i18n, final GuiProvider guiProvider, final WsArmor armor) {
        super(i18n, guiProvider, armor);
        setVisible(false);
        mainTitle.setText(i18n.t("Group members"));
        Tooltip.to(mainTitle, i18n.t("People and groups collaborating in this group"));
        firstCategoryLabel.setText(i18n.t("Admins"));
        setTooltip(firstCategoryLabel, i18n.t("People that can admin this group"));
        sndCategoryLabel.setText(i18n.t("Collaborators"));
        setTooltip(sndCategoryLabel, i18n.t("Other people that collaborate with this group"));
        trdCategoryLabel.setText(i18n.t("Pending"));
        setTooltip(trdCategoryLabel, i18n.t("People pending to be accepted in this group by the admins"));
        sndDeckLabel.setText(i18n.t("This is an orphaned project, if you are interested please request to join to work on it"));
        firstDeckLabel.setText(i18n.t("The members of this group are not public"));
        bottomActionsToolbar = new ActionFlowPanel(guiProvider);
        bottomPanel.add(bottomActionsToolbar);
        bottomActionsToolbar.setStyleName("k-sn-bottomPanel-actions");
        armor.getEntityToolsNorth().add(widget);
        deck.showWidget(2);
    }

    @Override
    public void addAdmin(final GroupDTO group, final String avatarUrl, final String tooltip, final String tooltipTitle,
            final GuiActionDescCollection menu) {
        final BasicThumb thumb = createThumb(group.getShortName(), avatarUrl, tooltip, tooltipTitle, menu);
        firstCategoryFlow.add(thumb);
    }

    @Override
    public void addCollab(final GroupDTO group, final String avatarUrl, final String tooltip,
            final String tooltipTitle, final GuiActionDescCollection menu) {
        final BasicThumb thumb = createThumb(group.getShortName(), avatarUrl, tooltip, tooltipTitle, menu);
        sndCategoryFlow.add(thumb);
    }

    @Override
    public void addPending(final GroupDTO group, final String avatarUrl, final String tooltip,
            final String tooltipTitle, final GuiActionDescCollection menu) {
        final BasicThumb thumb = createThumb(group.getShortName(), avatarUrl, tooltip, tooltipTitle, menu);
        trdCategoryFlow.add(thumb);
    }

    @Override
    public void setAdminsCount(final int count) {
        firstCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
    }

    @Override
    public void setCollabsCount(final int count) {
        sndCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
    }

    @Override
    public void setCollabsVisible(final boolean visible) {
        super.setSndCategoryVisible(visible);
    }

    @Override
    public void setPendingsCount(final int count) {
        trdCategoryCount.setText(new StringBuffer("(").append(count).append(")").toString());
    }

    @Override
    public void setPendingVisible(final boolean visible) {
        super.setTrdCategoryVisible(visible);
    }

    @Override
    public void setVisible(final boolean visible) {
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
