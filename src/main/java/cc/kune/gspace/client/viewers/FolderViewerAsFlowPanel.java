package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.gxtui.AbstractGxtMenuGui;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.ui.BasicThumb;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class FolderViewerAsFlowPanel extends AbstractFolderViewerPanel {
    interface FolderViewerAsFlowPanelUiBinder extends UiBinder<Widget, FolderViewerAsFlowPanel> {
    }
    private static FolderViewerAsFlowPanelUiBinder uiBinder = GWT.create(FolderViewerAsFlowPanelUiBinder.class);

    @UiField
    FlowPanel flow;
    int ICONLABELMAXSIZE = 20;
    int ICONSIZE = 100;

    @Inject
    public FolderViewerAsFlowPanel(final GSpaceArmor gsArmor, final I18nTranslationService i18n) {
        super(gsArmor, i18n);
        widget = uiBinder.createAndBindUi(this);
    }

    @Override
    public void addItem(final FolderItemDescriptor item, final ClickHandler clickHandler,
            final DoubleClickHandler doubleClickHandler) {
        // In this viewer we don't use the clickHandler from the presenter
        flow.add(createThumb(item.getText(), item.getIcon(), item.getTooltip(), "", item.getActionCollection(),
                doubleClickHandler));
    }

    @Override
    public void clear() {
        flow.clear();
        super.clear();
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
    public void setContainer(final StateContainerDTO state) {
        super.setContainer(state);
        gsArmor.getDocContainer().add(new HTML("<b>Note:</b> This GUI is provisional<br/>"));
    }

}
