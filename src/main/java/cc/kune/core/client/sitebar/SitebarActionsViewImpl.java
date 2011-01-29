package cc.kune.core.client.sitebar;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.core.client.sitebar.SitebarActionsPresenter.SitebarActionsView;
import cc.kune.wspace.client.WsArmor;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class SitebarActionsViewImpl extends ViewImpl implements SitebarActionsView {

    private final ActionSimplePanel panel;

    @Inject
    public SitebarActionsViewImpl(final WsArmor armor, final ActionSimplePanel panel) {
        this.panel = panel;
        panel.addStyleName("k-sitebar");
        panel.addStyleName("k-floatright");
        armor.getSitebar().add(panel);
        // armor.getDocContainer().add(panel);
        // panel.setWidth("100%");
    }

    @Override
    public void addAction(final AbstractGuiActionDescrip action) {
        panel.addAction(action);
    }

    @Override
    public void addActions(final AbstractGuiActionDescrip... actions) {
        for (final AbstractGuiActionDescrip action : actions) {
            this.addAction(action);
        }
    }

    @Override
    public void addActions(final GuiActionDescCollection actions) {
        for (final AbstractGuiActionDescrip action : actions) {
            this.addAction(action);
        }
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

}
