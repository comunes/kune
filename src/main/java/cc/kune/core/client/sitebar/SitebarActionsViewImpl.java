package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.client.sitebar.SitebarActionsPresenter.SitebarActionsView;
import cc.kune.wspace.client.WsArmor;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SitebarActionsViewImpl extends ViewImpl implements SitebarActionsView {

    private final ActionFlowPanel panel;

    @Inject
    public SitebarActionsViewImpl(WsArmor armor, ActionFlowPanel panel) {
        this.panel = panel;
        // panel.setWidth("100%");
        armor.getDocContainer().add(panel);
    }

    @Override
    public void addAction(AbstractGuiActionDescrip action) {
        panel.add(action);
    }

    @Override
    public void addActions(AbstractGuiActionDescrip... actions) {
        panel.addActions(actions);
    }

    @Override
    public void addActions(GuiActionDescCollection actions) {
        panel.addActions(actions);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

}
