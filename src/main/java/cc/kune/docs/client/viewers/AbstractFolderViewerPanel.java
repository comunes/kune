package cc.kune.docs.client.viewers;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.docs.client.viewers.FolderViewerPresenter.FolderViewerView;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public abstract class AbstractFolderViewerPanel extends ViewImpl implements FolderViewerView {
    protected final GSpaceArmor gsArmor;
    protected Widget widget;

    public AbstractFolderViewerPanel(final GSpaceArmor gsArmor) {
        this.gsArmor = gsArmor;
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void attach() {
        final ForIsWidget docContainer = gsArmor.getDocContainer();
        docContainer.add(widget);
    }

    @Override
    public void clear() {
        gsArmor.getSubheaderToolbar().clear();
        UiUtils.clear(gsArmor.getDocContainer());
        UiUtils.clear(gsArmor.getDocHeader());
    }

    @Override
    public void detach() {
        clear();
    }

    @Override
    public void setActions(final GuiActionDescCollection actions) {
        gsArmor.getSubheaderToolbar().clear();
        gsArmor.getSubheaderToolbar().addAll(actions);
    }

    @Override
    public void setContainer(final StateContainerDTO state) {
        gsArmor.getDocHeader().add(new InlineLabel(state.getTitle()));
    }
}
