package org.ourproject.massmob.client.ui.date;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasMouseWheelHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import cc.kune.common.client.tooltip.Tooltip;

public class DatePanel extends Composite implements DatePresenter.DateView {

  interface DatePanelUiBinder extends UiBinder<Widget, DatePanel> {
  }

  private static DatePanelUiBinder uiBinder = GWT.create(DatePanelUiBinder.class);

  @UiField
  DateBox datebox;
  private final DateTimeFormat dateFormat;

  private final DateTimeFormat dateTimeFormat;

  @UiField
  FocusPanel focus;

  public DatePanel() {
    initWidget(uiBinder.createAndBindUi(this));
    dateTimeFormat = DateTimeFormat.getFormat("MMM d, yyyy h:mm a");
    dateFormat = DateTimeFormat.getFormat("MMM d, yyyy");
    Tooltip.to(datebox, "Click to pick a date or use shift + mouse wheel");
  }

  @Override
  public Widget asWidget() {
    return this;
  }

  @Override
  public Date getDate() {
    return datebox.getValue();
  }

  @Override
  public HasMouseWheelHandlers getWheel() {
    return focus;
  }

  @Override
  public HasValue<Date> hasValue() {
    return datebox;
  }

  @Override
  public void setAllTime(final boolean allDay) {
    datebox.setFormat(new DateBox.DefaultFormat(allDay ? dateFormat : dateTimeFormat));
    datebox.setWidth(allDay ? "100px" : "160px");
  }

  @Override
  public void setDate(final Date date) {
    datebox.setValue(date);
  }

  @Override
  public void setEnabled(final boolean enabled) {
    datebox.setEnabled(enabled);
  }

}
