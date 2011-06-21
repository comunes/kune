package cc.kune.gspace.client.options.logo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;

public class EntityOptionsUploadButton extends Composite implements HasClickHandlers {
  DecoratorPanel widget = new DecoratorPanel();

  public EntityOptionsUploadButton(final String text) {
    final DecoratorPanel widget = new DecoratorPanel();
    final Button btn = new Button(text);
    btn.addStyleName("k-button");
    initWidget(widget);
    widget.setWidget(btn);
    widget.setHeight("50px");
  }

  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    return addDomHandler(handler, ClickEvent.getType());
  }
}