package org.ourproject.massmob.client.ui;

import java.util.Iterator;

import org.ourproject.massmob.client.ui.img.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class CustomDisclosure extends Composite
    implements HasWidgets, HasCloseHandlers<DisclosurePanel>, HasOpenHandlers<DisclosurePanel> {

  interface CustomDisclosureUiBinder extends UiBinder<Widget, CustomDisclosure> {
  }

  private static CustomDisclosureUiBinder uiBinder = GWT.create(CustomDisclosureUiBinder.class);

  private final ImageResource arrowDown;

  private final ImageResource arrowRight;
  @UiField
  DisclosurePanel disclo;
  private final IconTitle header;

  public CustomDisclosure(final Images img) {
    this("", false, img);
  }

  public CustomDisclosure(final String text, final boolean open, final Images img) {
    initWidget(uiBinder.createAndBindUi(this));
    arrowRight = img.arrowRight();
    arrowDown = img.arrowDown();
    header = new IconTitle(text);
    disclo.setHeader(header);
    disclo.setOpen(open);
  }

  @Override
  public void add(final Widget w) {
    disclo.add(w);
  }

  @Override
  public HandlerRegistration addCloseHandler(final CloseHandler<DisclosurePanel> handler) {
    return disclo.addCloseHandler(handler);
  }

  @Override
  public HandlerRegistration addOpenHandler(final OpenHandler<DisclosurePanel> handler) {
    return disclo.addOpenHandler(handler);
  }

  @Override
  public void clear() {
    disclo.clear();
  }

  boolean isOpen() {
    return disclo.isOpen();
  }

  @Override
  public Iterator<Widget> iterator() {
    return disclo.iterator();
  }

  @UiHandler("disclo")
  public void onClose(final CloseEvent<DisclosurePanel> event) {
    header.setIcon(arrowRight);
  }

  @UiHandler("disclo")
  public void onOpen(final OpenEvent<DisclosurePanel> event) {
    header.setIcon(arrowDown);
  }

  @Override
  public boolean remove(final Widget w) {
    return disclo.remove(w);
  }

  public void setHeader(final Widget widget) {
    header.add(widget);
  }

  public void setOpen(final boolean open) {
    disclo.setOpen(open);
    if (open) {
      header.setIcon(arrowDown);
    } else {
      header.setIcon(arrowRight);
    }
  }

  public void setText(final String text) {
    header.setText(text);
  }

}
