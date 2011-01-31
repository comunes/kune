package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.core.client.sitebar.SitebarActionsPresenter.SitebarActionsView;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SitebarActionsViewImpl extends ViewImpl implements SitebarActionsView {

    private final ActionSimplePanel toolbarLeft;
    private final ActionSimplePanel toolbarRight;

    @Inject
    public SitebarActionsViewImpl(final WsArmor armor, final ActionSimplePanel toolbarRight,
            final ActionSimplePanel toolbarLeft) {
        this.toolbarRight = toolbarRight;
        this.toolbarLeft = toolbarLeft;
        toolbarRight.addStyleName("k-sitebar");
        toolbarRight.addStyleName("k-floatright");
        toolbarLeft.addStyleName("k-sitebar");
        toolbarLeft.addStyleName("k-floatleft");
        armor.getSitebar().add(toolbarLeft);
        armor.getSitebar().add(toolbarRight);
    }

    @Override
    public Widget asWidget() {
        return toolbarRight;
    }

    @Override
    public IsActionExtensible getLeftBar() {
        return toolbarLeft;
    }

    @Override
    public IsActionExtensible getRightBar() {
        return toolbarRight;
    }

}
