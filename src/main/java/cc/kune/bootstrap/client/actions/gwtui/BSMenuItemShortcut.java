package cc.kune.bootstrap.client.actions.gwtui;

import cc.kune.common.client.actions.KeyStroke;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.user.client.ui.Widget;

public class BSMenuItemShortcut extends Widget {
  public BSMenuItemShortcut(final KeyStroke key, final String style) {
    final SpanElement span = Document.get().createSpanElement();
    setElement(span);
    setStyleName(style);
    span.setInnerText(key.toString());
  }

}
