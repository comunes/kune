package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.actions.ActionItem;
import org.ourproject.kune.platf.client.actions.ActionItemCollection;
import org.ourproject.kune.platf.client.actions.ActionManager;
import org.ourproject.kune.platf.client.actions.MenuItemsContainer;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.skel.SummaryPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.listener.Listener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.menu.Menu;

public class BuddiesSummaryPanel extends SummaryPanel implements BuddiesSummaryView {

    public class BuddieWidget extends Composite {
        private final Image icon;
        private final Label nick;

        public BuddieWidget(String nickName, ClickListener clickListener) {
            VerticalPanel vp = new VerticalPanel();
            icon = new Image("images/persons/person2-32.png");
            nick = new Label(nickName);
            vp.add(icon);
            vp.add(nick);
            vp.addStyleName("kune-Margin-Small-trbl");
            vp.addStyleName("kune-pointer");
            vp.addStyleName("kune-floatleft");
            nick.addClickListener(clickListener);
            icon.addClickListener(clickListener);
            initWidget(vp);
        }

        public void setIcon(String url) {
            icon.setUrl(url);
        }

        public void setNick(String nickName) {
            nick.setText(nickName);
        }
    }
    private final MenuItemsContainer<UserSimpleDTO> menuItemsContainer;
    private final FlowPanel flowPanel;
    private final Label otherBuddiesLabel;
    private final I18nTranslationService i18n;
    private final ActionManager actionManager;

    public BuddiesSummaryPanel(final BuddiesSummaryPresenter presenter, final WorkspaceSkeleton ws,
            I18nTranslationService i18n, ActionManager actionManager) {
        super(i18n.t("Buddies"), i18n.t("Buddies of this user"), ws);
        this.actionManager = actionManager;
        menuItemsContainer = new MenuItemsContainer<UserSimpleDTO>();
        this.i18n = i18n;
        VerticalPanel vp = new VerticalPanel();
        flowPanel = new FlowPanel();
        otherBuddiesLabel = new Label();
        otherBuddiesLabel.addStyleName("kune-Margin-Small-trbl");
        vp.add(flowPanel);
        vp.add(otherBuddiesLabel);
        super.add(vp);
        ws.addInSummary(this);
        clear();
    }

    public void addBuddie(final UserSimpleDTO user, ActionItemCollection<UserSimpleDTO> actionCollection) {
        ClickListener listener = new ClickListener() {
            public void onClick(Widget sender) {
                Menu menu = menuItemsContainer.get(user.getShortName());
                if (menu.getItems().length > 0) {
                    menu.showAt(sender.getAbsoluteLeft(), sender.getAbsoluteTop() + 5);
                }
            }
        };
        menuItemsContainer.createItemMenu(user.getShortName(), actionCollection,
                new Listener<ActionItem<UserSimpleDTO>>() {
                    public void onEvent(ActionItem<UserSimpleDTO> actionItem) {
                        doAction(actionItem);
                    }
                });
        flowPanel.add(new BuddieWidget(user.getShortName(), listener));
    }

    @Override
    public void clear() {
        flowPanel.clear();
        clearOtherUsers();
        menuItemsContainer.clear();
        super.doLayoutIfNeeded();
    }

    public void clearOtherUsers() {
        otherBuddiesLabel.setText("");
    }

    public void setNoBuddies() {
        otherBuddiesLabel.setText(i18n.t("This user has no buddies"));
    }

    public void setOtherUsers(String text) {
        otherBuddiesLabel.setText(text);
    }

    private void doAction(final ActionItem<UserSimpleDTO> actionItem) {
        actionManager.doAction(actionItem);
    }
}
