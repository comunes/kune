package cc.kune.docs.client.viewers;

import cc.kune.common.client.actions.gxtui.AbstractGxtMenuGui;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.ui.BasicThumb;
import cc.kune.common.client.ui.UiUtils;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.docs.client.viewers.FolderViewerPresenter.FolderViewerView;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class FolderViewerPanel extends ViewImpl implements FolderViewerView {
    interface FolderViewerPanelUiBinder extends UiBinder<Widget, FolderViewerPanel> {
    }
    private static FolderViewerPanelUiBinder uiBinder = GWT.create(FolderViewerPanelUiBinder.class);

    @UiField
    FlowPanel flow;

    private final GSpaceArmor gsArmor;

    int ICONLABELMAXSIZE = 20;
    int ICONSIZE = 100;
    private final Widget widget;

    @Inject
    public FolderViewerPanel(final GSpaceArmor wsArmor) {
        this.gsArmor = wsArmor;
        widget = uiBinder.createAndBindUi(this);
    }

    @Override
    public void addItem(final FolderItemDescriptor item, final DoubleClickHandler doubleClickHandler) {
        flow.add(createThumb(item.getText(), item.getIcon(), item.getTooltip(), "", item.getActionCollection(),
                doubleClickHandler));
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
        flow.clear();
        gsArmor.getSubheaderToolbar().clear();
        UiUtils.clear(gsArmor.getDocContainer());
        UiUtils.clear(gsArmor.getDocHeader());
    }

    public BasicThumb createThumb(final String text, final Object icon, final String tooltip,
            final String tooltipTitle, final GuiActionDescCollection menuitems,
            final DoubleClickHandler doubleClickHandler) {
        final BasicThumb thumb = new BasicThumb(icon, ICONSIZE, text, ICONLABELMAXSIZE, false);
        final MenuDescriptor menu = new MenuDescriptor();
        menu.setStandalone(true);
        menu.putValue(AbstractGxtMenuGui.MENU_POSITION, AbstractGxtMenuGui.MenuPosition.bl);
        for (final GuiActionDescrip item : menuitems) {
            item.setParent(menu);
        }
        final ClickHandler clickHand = new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                menu.show(thumb);
            }
        };

        thumb.addClickHandler(clickHand);
        thumb.addDoubleClickHandler(doubleClickHandler);
        gsArmor.getSubheaderToolbar().add(menu);
        gsArmor.getSubheaderToolbar().addAll(menuitems);
        thumb.setTooltip(tooltipTitle, tooltip);
        thumb.setLabelVisible(true);
        return thumb;
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
        gsArmor.getDocContainer().add(new HTML("<b>Note:</b> This GUI is provisional<br/>"));
        gsArmor.getDocHeader().add(new InlineLabel(state.getTitle()));
    }

}
