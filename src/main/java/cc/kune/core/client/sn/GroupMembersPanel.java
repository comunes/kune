package cc.kune.core.client.sn;

import cc.kune.common.client.actions.gxtui.AbstractGxtMenuGui;
import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.ui.BasicThumb;
import cc.kune.core.client.sn.GroupMembersPresenter.GroupMembersView;
import cc.kune.core.shared.dto.GroupDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class GroupMembersPanel extends ViewImpl implements GroupMembersView {

    interface GroupMembersPanelUiBinder extends UiBinder<Widget, GroupMembersPanel> {
    }

    private static GroupMembersPanelUiBinder uiBinder = GWT.create(GroupMembersPanelUiBinder.class);
    private final ActionSimplePanel actions;
    @UiField
    Label adminsCount;
    @UiField
    FlowPanel adminsFlow;
    @UiField
    Label adminsLabel;
    ActionFlowPanel bottomActionsToolbar;
    @UiField
    FlowPanel bottomPanel;
    @UiField
    Label collabsCount;
    @UiField
    FlowPanel collabsFlow;
    @UiField
    Label collabsLabel;
    @UiField
    DockLayoutPanel collabsPanel;

    @UiField
    ScrollPanel collabsScroll;
    @UiField
    DeckPanel deck;
    @UiField
    FlowPanel mainPanel;
    @UiField
    Label mainTitle;
    @UiField
    FlowPanel membersFlow;
    @UiField
    Label noMembersVisible;
    @UiField
    Label orphanProject;
    @UiField
    Label pendingsCount;
    @UiField
    FlowPanel pendingsFlow;
    @UiField
    Label pendingsLabel;
    @UiField
    DockLayoutPanel pendingsPanel;
    @UiField
    ScrollPanel pendingsScroll;
    private final Widget widget;

    @Inject
    public GroupMembersPanel(final I18nTranslationService i18n, final GuiProvider guiProvider, final WsArmor armor) {
        widget = uiBinder.createAndBindUi(this);
        mainTitle.setText(i18n.t("Group members"));
        mainTitle.setTitle(i18n.t("People and groups collaborating in this group"));
        adminsLabel.setText(i18n.t("Admins"));
        adminsLabel.setTitle(i18n.t("People that can admin this group"));
        collabsLabel.setText(i18n.t("Collaborators"));
        collabsLabel.setTitle(i18n.t("Other people that collaborate with this group"));
        pendingsLabel.setText(i18n.t("Pending"));
        pendingsLabel.setTitle(i18n.t("People pending to be accepted in this group by the admins"));
        orphanProject.setText(i18n.t("This is an orphaned project, if you are interested please request to join to work on it"));
        noMembersVisible.setText(i18n.t("The members of this group are not public"));
        bottomActionsToolbar = new ActionFlowPanel(guiProvider);
        actions = new ActionSimplePanel(guiProvider);
        bottomPanel.add(bottomActionsToolbar);
        bottomActionsToolbar.setStyleName("k-sn-bottomPanel-actions");
        armor.getEntityToolsNorth().add(widget);
        deck.showWidget(2);
    }

    @Override
    public void addAdmin(final GroupDTO group, final String avatarUrl, final String tooltip, final String tooltipTitle,
            final GuiActionDescCollection menu) {
        final BasicThumb thumb = createThumb(group, avatarUrl, tooltip, tooltipTitle, menu);
        adminsFlow.add(thumb);
    }

    @Override
    public void addCollab(final GroupDTO group, final String avatarUrl, final String tooltip,
            final String tooltipTitle, final GuiActionDescCollection menu) {
        final BasicThumb thumb = createThumb(group, avatarUrl, tooltip, tooltipTitle, menu);
        collabsFlow.add(thumb);
    }

    @Override
    public void addPending(final GroupDTO group, final String avatarUrl, final String tooltip,
            final String tooltipTitle, final GuiActionDescCollection menu) {
        final BasicThumb thumb = createThumb(group, avatarUrl, tooltip, tooltipTitle, menu);
        pendingsFlow.add(thumb);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void clear() {
        pendingsFlow.clear();
        adminsFlow.clear();
        collabsFlow.clear();
        actions.clear();
    }

    private BasicThumb createThumb(final GroupDTO group, final String avatarUrl, final String tooltip,
            final String tooltipTitle, final GuiActionDescCollection menuitems) {
        final BasicThumb thumb = new BasicThumb(avatarUrl, AVATARSIZE, group.getShortName(), AVATARLABELMAXSIZE, false);
        final MenuDescriptor menu = new MenuDescriptor();
        menu.setStandalone(true);
        menu.putValue(AbstractGxtMenuGui.MENU_POSITION, AbstractGxtMenuGui.MenuPosition.bl);
        for (final GuiActionDescrip item : menuitems) {
            item.setParent(menu);
        }
        final ClickHandler clickHand = new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                GWT.log("Show event");
                menu.show(thumb);
            }
        };
        thumb.addClickHandler(clickHand);
        actions.add(menu);
        actions.add(menuitems);
        thumb.setTooltip(tooltipTitle, tooltip);
        thumb.setLabelVisible(false);
        return thumb;
    }

    @Override
    public IsActionExtensible getBottomToolbar() {
        return bottomActionsToolbar;
    }

    @Override
    public void setAdminsCount(final int count) {
        adminsCount.setText(new StringBuffer("(").append(count).append(")").toString());
    }

    @Override
    public void setCollabsCount(final int count) {
        collabsCount.setText(new StringBuffer("(").append(count).append(")").toString());
    }

    @Override
    public void setCollabsVisible(final boolean visible) {
        collabsPanel.setVisible(visible);
    }

    @Override
    public void setPendingsCount(final int count) {
        pendingsCount.setText(new StringBuffer("(").append(count).append(")").toString());
    }

    @Override
    public void setPendingVisible(final boolean visible) {
        pendingsPanel.setVisible(visible);
    }

    @Override
    public void setVisible(final boolean visible) {
        deck.setVisible(visible);
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
