package cc.kune.core.client.sn;

import cc.kune.common.client.actions.gxtui.AbstractGxtMenuGui;
import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.ActionSimplePanel;
import cc.kune.common.client.actions.ui.IsActionExtensible;
import cc.kune.common.client.actions.ui.bind.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.BasicThumb;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.GSpaceArmor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class AbstractSNPanel extends ViewImpl {

  interface AbstractSNPanelUiBinder extends UiBinder<Widget, AbstractSNPanel> {
  }

  private final static int AVATARLABELMAXSIZE = 4;
  private final static int AVATARSIZE = 22;
  private final static String CATEG_HEIGHT = "80px";
  private static AbstractSNPanelUiBinder uiBinder = GWT.create(AbstractSNPanelUiBinder.class);

  protected final ActionSimplePanel actions;
  protected final GSpaceArmor armor;
  ActionFlowPanel bottomActionsToolbar;
  @UiField
  FlowPanel bottomPanel;
  @UiField
  FlowPanel categoriesFlow;
  @UiField
  DeckPanel deck;
  @UiField
  Label firstCategoryCount;
  @UiField
  FlowPanel firstCategoryFlow;
  @UiField
  Label firstCategoryLabel;
  @UiField
  DockLayoutPanel firstCategoryPanel;
  @UiField
  Label firstDeckLabel;
  @UiField
  FlowPanel mainPanel;
  @UiField
  Label mainTitle;
  @UiField
  Label sndCategoryCount;
  @UiField
  FlowPanel sndCategoryFlow;
  @UiField
  Label sndCategoryLabel;
  @UiField
  DockLayoutPanel sndCategoryPanel;
  @UiField
  ScrollPanel sndCategoryScroll;
  @UiField
  Label sndDeckLabel;
  @UiField
  Label trdCategoryCount;
  @UiField
  FlowPanel trdCategoryFlow;
  @UiField
  Label trdCategoryLabel;
  @UiField
  DockLayoutPanel trdCategoryPanel;
  @UiField
  ScrollPanel trdCategoryScroll;
  protected final Widget widget;

  public AbstractSNPanel(final I18nTranslationService i18n, final GuiProvider guiProvider,
      final GSpaceArmor armor) {
    this.armor = armor;
    widget = uiBinder.createAndBindUi(this);
    actions = new ActionSimplePanel(guiProvider);
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

  public void clear() {
    trdCategoryFlow.clear();
    firstCategoryFlow.clear();
    sndCategoryFlow.clear();
    actions.clear();
  }

  public BasicThumb createThumb(final String text, final String avatarUrl, final String tooltip,
      final String tooltipTitle, final GuiActionDescCollection menuitems) {
    final BasicThumb thumb = new BasicThumb(avatarUrl, AVATARSIZE, text, AVATARLABELMAXSIZE, false);
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
    actions.add(menu);
    actions.add(menuitems);
    thumb.setTooltip(tooltipTitle, tooltip);
    thumb.setLabelVisible(false);
    return thumb;
  }

  public IsActionExtensible getBottomToolbar() {
    return bottomActionsToolbar;
  }

  public void setFirstCategoryVisible(final boolean visible) {
    firstCategoryPanel.setVisible(visible);
    firstCategoryPanel.setHeight(visible ? CATEG_HEIGHT : "0px");
  }

  public void setSndCategoryVisible(final boolean visible) {
    sndCategoryPanel.setVisible(visible);
    sndCategoryPanel.setHeight(visible ? CATEG_HEIGHT : "0px");
  }

  protected void setTooltip(final Widget widget, final String title) {
    Tooltip.to(widget, title);
  }

  public void setTrdCategoryVisible(final boolean visible) {
    trdCategoryPanel.setVisible(visible);
    trdCategoryPanel.setHeight(visible ? CATEG_HEIGHT : "0px");
  }
}
