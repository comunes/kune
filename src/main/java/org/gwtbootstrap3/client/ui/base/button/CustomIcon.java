package org.gwtbootstrap3.client.ui.base.button;

import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.ElementTags;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.HasText;

public class CustomIcon extends ComplexWidget implements HasText {

  public CustomIcon() {
    setElement(Document.get().createElement(ElementTags.I));
  }

  public CustomIcon(final String text) {
    this();
    setText(text);
  }

  @Override
  public String getText() {
    return getElement().getInnerText();
  }

  @Override
  public void setText(final String text) {
    getElement().setInnerText(text);
  }
}
