package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class FolderViewerAsTablePanel extends AbstractFolderViewerPanel {

    interface FolderViewerAsTablePanelUiBinder extends UiBinder<Widget, FolderViewerAsTablePanel> {
    }

    private static FolderViewerAsTablePanelUiBinder uiBinder = GWT.create(FolderViewerAsTablePanelUiBinder.class);

    @UiField
    FlexTable flex;

    private final GuiProvider guiProvider;
    private final CoreResources res;
    protected FolderItemWidget selected;

    @Inject
    public FolderViewerAsTablePanel(final GSpaceArmor gsArmor, final I18nTranslationService i18n,
            final GuiProvider guiProvider, final CoreResources res) {
        super(gsArmor, i18n);
        this.guiProvider = guiProvider;
        this.res = res;
        widget = uiBinder.createAndBindUi(this);
    }

    @Override
    public void addItem(final FolderItemDescriptor item, final ClickHandler clickHandler,
            final DoubleClickHandler doubleClickHandler) {
        final int rowCount = flex.getRowCount();
        final FolderItemWidget itemWidget = new FolderItemWidget((ImageResource) item.getIcon(), item.getText());
        final ActionSimplePanel toolbar = new ActionSimplePanel(guiProvider);
        itemWidget.setMenu(toolbar);
        // Tooltip.to(itemWidget, item.getTooltip());
        final MenuDescriptor menu = new MenuDescriptor(i18n.t("Actions"));
        menu.withIcon(res.arrowdown()).withStyles("k-def-docbtn, k-btn, k-button");
        menu.setStandalone(false);
        menu.setVisible(false);
        toolbar.add(menu);
        for (final GuiActionDescrip menuItem : item.getActionCollection()) {
            menuItem.setParent(menu);
            toolbar.add(menuItem);
        }
        itemWidget.getRowClick().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                if (selected != null) {
                    selected.setSelect(false);
                }
                itemWidget.setSelect(true);
                selected = itemWidget;
            }
        });
        itemWidget.getRowDoubleClick().addDoubleClickHandler(doubleClickHandler);
        itemWidget.getRowMouse().addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(final MouseOutEvent event) {
                menu.setVisible(false);
                // itemWidget.setSelect(false);
                // menu.hide();
            }
        });
        itemWidget.getRowMouse().addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(final MouseOverEvent event) {
                menu.setVisible(true);
            }
        });
        flex.setWidget(rowCount + 1, 0, itemWidget);
    }

    @Override
    public void clear() {
        flex.clear();
        super.clear();
    }

    @Override
    public void setContainer(final StateContainerDTO state) {
        super.setContainer(state);
    }

}
