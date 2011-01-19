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
        armor.getSitebar().add(panel);
        // panel.setWidth("100%");
    }

    @Override
    public void addAction(AbstractGuiActionDescrip action) {
        panel.addAction(action);
    }

    @Override
    public void addActions(AbstractGuiActionDescrip... actions) {
        for (AbstractGuiActionDescrip action : actions) {
            this.addAction(action);
        }
    }

    @Override
    public void addActions(GuiActionDescCollection actions) {
        for (AbstractGuiActionDescrip action : actions) {
            this.addAction(action);
        }
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

}
