package cc.kune.common.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class DottedTabPanel extends Composite {

  interface DottedTabPanelUiBinder extends UiBinder<Widget, DottedTabPanel> {
  }

  private static DottedTabPanelUiBinder uiBinder = GWT.create(DottedTabPanelUiBinder.class);

  @UiField
  TabLayoutPanel tabPanel;

  public DottedTabPanel(final String width, final String height) {
    initWidget(uiBinder.createAndBindUi(this));
    tabPanel.setSize(width, height);
  }

  public void addTab(final IsWidget view) {
    tabPanel.add(view, new DottedTab());
  }

  public int getWidgetIndex(final IsWidget view) {
    return tabPanel.getWidgetIndex(view.asWidget());
  }

  public void removeTab(final IsWidget view) {
    tabPanel.remove(view.asWidget());
  }
}
