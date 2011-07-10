package cc.kune.common.client.ui;

import cc.kune.common.client.tooltip.Tooltip;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class AbstractDecorator extends Composite {

  interface Binder extends UiBinder<Widget, AbstractDecorator> {
  }
  private static final Binder BINDER = GWT.create(Binder.class);
  @UiField
  Image decorationImage;
  private int left = 0;

  @UiField
  FlowPanel mainPanel;
  private int offset = 0;
  private int top = 0;
  @UiField
  SimplePanel widgetContainer;

  public AbstractDecorator() {
    initWidget(BINDER.createAndBindUi(this));
  }

  public void clearImage() {
    decorationImage.setVisible(false);
  }

  public void setDecoratorVisible(final boolean visible) {
    decorationImage.setVisible(visible);
  }

  public void setImage(final ImageResource img) {
    decorationImage.setResource(img);
    decorationImage.setVisible(true);
    setPosition();
  }

  public void setImagePosition(final int top, final int left, final int offset) {
    this.top = top;
    this.left = left;
    this.offset = offset;
    setPosition();
  }

  public void setImageTooltip(final String text) {
    Tooltip.to(decorationImage, text);
  }

  private void setPosition() {
    final Element elem = decorationImage.getElement();
    elem.getStyle().setPropertyPx("left", left);
    elem.getStyle().setPropertyPx("top", top);
    elem.getStyle().setPosition(Position.RELATIVE);
    elem.getStyle().setFloat(Float.LEFT);
    elem.getStyle().setMarginRight(offset, Unit.PX);
  }

  public void setWidget(final IsWidget widget) {
    widgetContainer.clear();
    widgetContainer.add(widget);
  }

}
