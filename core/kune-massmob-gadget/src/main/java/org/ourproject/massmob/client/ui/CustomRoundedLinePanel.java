package org.ourproject.massmob.client.ui;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class CustomRoundedLinePanel extends Composite implements HasWidgets {

  interface CustomRoundedLinePanelUiBinder extends UiBinder<Widget, CustomRoundedLinePanel> {
  }

  private static CustomRoundedLinePanelUiBinder uiBinder = GWT.create(
      CustomRoundedLinePanelUiBinder.class);

  @UiField
  FlowPanel rp;

  public CustomRoundedLinePanel() {
    this("#808080", "FFF", "FFF");
  }

  public CustomRoundedLinePanel(final String borderColor, final String innerTop,
      final String innertBottom) {
    initWidget(uiBinder.createAndBindUi(this));
    final Style style = rp.getElement().getStyle();
    style.setBorderColor(borderColor);
    style.setBorderStyle(BorderStyle.SOLID);
    style.setBorderWidth(1, Unit.PX);
    style.setProperty("borderRadius", "5px");
  }

  @Override
  public void add(final Widget w) {
    rp.add(w);
  }

  @Override
  public void clear() {
    rp.clear();
  }

  @Override
  public Iterator<Widget> iterator() {
    return rp.iterator();
  }

  @Override
  public boolean remove(final Widget w) {
    return rp.remove(w);
  }

}
