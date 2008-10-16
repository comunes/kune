package org.ourproject.kune.workspace.client.socialnet;

import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.workspace.client.skel.SummaryPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BuddiesSummaryPanel extends SummaryPanel implements BuddiesSummaryView {

    public class BuddieWidget extends Composite {
        private final Image icon;
        private final Label nick;

        public BuddieWidget(String nickName) {
            VerticalPanel vp = new VerticalPanel();
            icon = new Image("images/persons/person2-32.png");
            nick = new Label(nickName);
            vp.add(icon);
            vp.add(nick);
            initWidget(vp);
        }

        public void setIcon(String url) {
            icon.setUrl(url);
        }

        public void setNick(String nickName) {
            nick.setText(nickName);
        }
    }
    private final FlowPanel flowPanel;
    private final Label otherBuddiesLabel;
    private final I18nTranslationService i18n;

    public BuddiesSummaryPanel(final BuddiesSummaryPresenter presenter, final WorkspaceSkeleton ws,
            I18nTranslationService i18n) {
        super(i18n.t("Buddies"), i18n.t("Buddies of this user"), ws);
        this.i18n = i18n;
        VerticalPanel vp = new VerticalPanel();
        flowPanel = new FlowPanel();
        otherBuddiesLabel = new Label();
        vp.add(flowPanel);
        vp.add(otherBuddiesLabel);
        super.add(vp);
    }

    public void addBuddie(UserSimpleDTO user) {
        flowPanel.add(new BuddieWidget(user.getShortName()));
    }

    @Override
    public void clear() {
        flowPanel.clear();
        otherBuddiesLabel.setText(i18n.t("This user has no buddies"));
    }

    public void setOtherUsers(int otherExternalBuddies) {
        if (otherExternalBuddies > 0) {
            otherBuddiesLabel.setText(i18n.t("and [%d] external users", otherExternalBuddies));
        } else {
            otherBuddiesLabel.setText("");
        }
    }
}
