package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.UIConstants;
import org.ourproject.kune.platf.client.ui.stacks.StackSubItemAction;
import org.ourproject.kune.platf.client.ui.stacks.StackedDropDownPanel;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.socialnet.GroupMembersView;
import org.ourproject.kune.workspace.client.socialnet.MemberAction;
import org.ourproject.kune.workspace.client.socialnet.ParticipationView;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class ParticipationPanel extends StackedDropDownPanel implements ParticipationView {

    private static final boolean COUNTS_VISIBLE = false;
    private final Images img = Images.App.getInstance();

    public ParticipationPanel(final AbstractPresenter presenter) {
        super(presenter, "#00D4AA", Kune.I18N.t("Participates as..."), Kune.I18N.t("Groups in which participates"),
                COUNTS_VISIBLE);
    }

    public void clear() {
        super.clear();
    }

    public void addCategory(final String name, final String title) {
        super.addStackItem(name, title, COUNTS_VISIBLE);
    }

    public void addCategory(final String name, final String title, final String iconType) {
        super.addStackItem(name, title, getIcon(iconType), UIConstants.ICON_HORIZ_ALIGN_RIGHT, COUNTS_VISIBLE);
    }

    public void addCategoryMember(final String categoryName, final String name, final String title,
            final MemberAction[] memberActions) {
        StackSubItemAction[] subItems = new StackSubItemAction[memberActions.length];
        for (int i = 0; i < memberActions.length; i++) {
            subItems[i] = new StackSubItemAction(getIconFronEvent(memberActions[i].getAction()), memberActions[i]
                    .getText(), memberActions[i].getAction());
        }

        super.addStackSubItem(categoryName, img.groupDefIcon(), name, title, subItems);
    }

    private AbstractImagePrototype getIcon(final String event) {
        if (event == GroupMembersView.ICON_ALERT) {
            return img.alert();
        }
        throw new IndexOutOfBoundsException("Icon unknown in ParticipationPanelk");
    }

    private AbstractImagePrototype getIconFronEvent(final String event) {
        if (event == WorkspaceEvents.UNJOIN_GROUP) {
            return img.del();
        }
        if (event == PlatformEvents.GOTO) {
            return img.groupHome();
        }
        throw new IndexOutOfBoundsException("Event unknown in ParticipationPanel");
    }

    public void show() {
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

}
