package cc.kune.gspace.client.options.logo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasText;

public class EntityOptUploadButton extends Composite implements HasClickHandlers {
  private final Button btn;
  DecoratorPanel widget = new DecoratorPanel();

  public EntityOptUploadButton(final String text) {
    final DecoratorPanel widget = new DecoratorPanel();
    btn = new Button(text);
    btn.addStyleName("k-button");
    initWidget(widget);
    widget.setWidget(btn);
    widget.setHeight("50px");
  }

  @Override
  public HandlerRegistration addClickHandler(final ClickHandler handler) {
    return addDomHandler(handler, ClickEvent.getType());
  }

  public HasText getBtn() {
    return btn;
  }
}