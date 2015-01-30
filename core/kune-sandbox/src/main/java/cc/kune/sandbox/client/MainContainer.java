package cc.kune.sandbox.client;

import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.LabelType;
import org.gwtbootstrap3.client.ui.html.Text;

import cc.kune.common.shared.utils.LoremUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainContainer extends Composite {

  interface MainContainerUiBinder extends UiBinder<Widget, MainContainer> {
  }

  private static MainContainerUiBinder uiBinder = GWT.create(MainContainerUiBinder.class);

  @UiField
  Column bottomColumn;
  @UiField
  DeckPanel deck;
  @UiField
  FlowPanel flow;
  @UiField
  ScrollPanel scroll;
  @UiField
  Column topColumn;

  public MainContainer() {
    initWidget(uiBinder.createAndBindUi(this));
    deck.add(flow);
    flow.add(new Text(LoremUtils.LOREM_MULTI + LoremUtils.LOREM_MULTI));
    deck.add(new Label("Deck 1"));
    final Label label2 = new Label("Deck 2");
    label2.setType(LabelType.WARNING);
    deck.add(label2);
    final Label label3 = new Label("Deck 3");
    label3.setType(LabelType.DANGER);
    deck.add(label3);

    scroll.getElement().getStyle().setOverflowX(Overflow.AUTO);
    scroll.getElement().getStyle().setOverflowY(Overflow.SCROLL);

    // scroll.setTouchScrollingDisabled(false);
    // NotifyUser.info("Touch enabled: " + !scroll.isTouchScrollingDisabled());

    // getScrollableElement().getStyle().setOverflowX(Overflow.HIDDEN);
    // scroll.getScrollableElement().getStyle().setOverflowY(Overflow.SCROLL);
    //
    // scroll.addScrollHandler(new ScrollHandler() {
    //
    // @Override
    // public void onScroll(final ScrollEvent event) {
    // // NotifyUser.info("scrolling");
    // }
    // });

    // addTouchEndHandler(new TouchEndHandler() {
    // @Override
    // public void onTouchEnd(final TouchEndEvent event) {
    // // NotifyUser.info("Touch end event");
    // next();
    // }
    // });
    // addTouchStartHandler(new TouchStartHandler() {
    // @Override
    // public void onTouchStart(final TouchStartEvent event) {
    // // NotifyUser.info("Touch start event");
    // }
    // });
    // addTouchCancelHandler(new TouchCancelHandler() {
    // @Override
    // public void onTouchCancel(final TouchCancelEvent event) {
    // // NotifyUser.info("Touch cancel event");
    // }
    // });
    // addTouchMoveHandler(new TouchMoveHandler() {
    // @Override
    // public void onTouchMove(final TouchMoveEvent event) {
    // // NotifyUser.info("Touch move event");
    //
    // }
    // });
  }

  public FlowPanel getFlow() {
    return flow;
  }

  public ComplexWidget getFooter() {
    return bottomColumn;
  }

  public ComplexWidget getSitebar() {
    return topColumn;
  }

  public void next() {
    showNextInc(1);
  }

  public void prev() {
    showNextInc(-1);
  }

  private void showNextInc(final int inc) {
    final int proposed = deck.getVisibleWidget() + inc;
    final int total = deck.getWidgetCount();
    final int next = proposed >= total ? 0 : (proposed < 0) ? total - 1 : proposed;
    deck.showWidget(next);
  }

  public void showWidget(final int i) {
    deck.showWidget(i);
  }

  // @Override
  // public HandlerRegistration addTouchCancelHandler(final TouchCancelHandler
  // handler) {
  // return scroll.addDomHandler(handler, TouchCancelEvent.getType());
  // }
  //
  // @Override
  // public HandlerRegistration addTouchEndHandler(final TouchEndHandler
  // handler) {
  // return scroll.addDomHandler(handler, TouchEndEvent.getType());
  // }
  //
  // @Override
  // public HandlerRegistration addTouchMoveHandler(final TouchMoveHandler
  // handler) {
  // return scroll.addDomHandler(handler, TouchMoveEvent.getType());
  // }
  //
  // @Override
  // public HandlerRegistration addTouchStartHandler(final TouchStartHandler
  // handler) {
  // return scroll.addDomHandler(handler, TouchStartEvent.getType());
  // }
}
