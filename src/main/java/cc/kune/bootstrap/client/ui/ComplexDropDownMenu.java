package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.shared.event.HiddenEvent;
import org.gwtbootstrap3.client.shared.event.HideEvent;
import org.gwtbootstrap3.client.shared.event.HideHandler;
import org.gwtbootstrap3.client.shared.event.ShowEvent;
import org.gwtbootstrap3.client.shared.event.ShowHandler;
import org.gwtbootstrap3.client.shared.event.ShownEvent;
import org.gwtbootstrap3.client.shared.event.ShownHandler;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.base.ComplexWidget;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ComplexDropDownMenu<T extends ComplexWidget> {

  private static final String OPEN = "open";
  private final ComplexAnchorButton anchor;
  private final DropDownMenu menu;
  private final T widget;

  public ComplexDropDownMenu(final T widget) {
    this.widget = widget;
    menu = new DropDownMenu();
    anchor = new ComplexAnchorButton();
    anchor.setDataToggle(Toggle.DROPDOWN);
    final String dataTarget = HTMLPanel.createUniqueId();
    anchor.setDataTarget(dataTarget);
    // Attributes.DATA_TARGET
    anchor.getElement().setAttribute("aria-expanded", "true");
    menu.getElement().setAttribute("aria-labelledby", dataTarget);
    widget.add(anchor);
    widget.add(menu);
    widget.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (event.isAttached()) {
          bindJavaScriptEvents(widget.getElement());
        } else {
          unbindJavaScriptEvents(widget.getElement());
        }
      }
    });
  }

  public void add(final IsWidget child) {
    menu.add(child);
  }

  public void add(final Widget child) {
    menu.add(child);
  }

  public void addClickHandler(final ClickHandler clickHandler) {
    anchor.addClickHandler(clickHandler);
  }

  public HandlerRegistration addHideHandler(final HideHandler hideHandler) {
    return widget.addHandler(hideHandler, HideEvent.getType());
  }

  public HandlerRegistration addShowHandler(final ShowHandler showHandler) {
    return widget.addHandler(showHandler, ShowEvent.getType());
  }

  public HandlerRegistration addShownHandler(final ShownHandler shownHandler) {
    return widget.addHandler(shownHandler, ShownEvent.getType());
  }

  public void addStyleName(final String style) {
    widget.addStyleName(style);
  }

  private native void bindJavaScriptEvents(final com.google.gwt.dom.client.Element e) /*-{
		var target = this;
		var $dropdown = $wnd.jQuery(e);

		$dropdown
				.on(
						'show.bs.dropdown',
						function(evt) {
							target.@cc.kune.bootstrap.client.ui.ComplexDropDownMenu::onShow(Lcom/google/gwt/user/client/Event;)(evt);
						});

		$dropdown
				.on(
						'shown.bs.dropdown',
						function(evt) {
							target.@cc.kune.bootstrap.client.ui.ComplexDropDownMenu::onShown(Lcom/google/gwt/user/client/Event;)(evt);
						});

		$dropdown
				.on(
						'hide.bs.dropdown',
						function(evt) {
							target.@cc.kune.bootstrap.client.ui.ComplexDropDownMenu::onHide(Lcom/google/gwt/user/client/Event;)(evt);
						});

		$dropdown
				.on(
						'hidden.bs.dropdown',
						function(evt) {
							target.@cc.kune.bootstrap.client.ui.ComplexDropDownMenu::onHidden(Lcom/google/gwt/user/client/Event;)(evt);
						});
  }-*/;

  public void clear() {
    menu.clear();
  }

  public void ensureDebugId(final String id) {
    anchor.ensureDebugId(id);
  }

  public ComplexAnchorButton getAnchor() {
    return anchor;
  }

  public DropDownMenu getList() {
    return menu;
  }

  public String getText() {
    return anchor.getText();
  }

  public Widget getWidget() {
    return widget;
  }

  public int getWidgetCount() {
    return menu.getWidgetCount();
  }

  public void hide() {
    widget.removeStyleName(OPEN);
  }

  public void insert(final Widget uiObject, final int position) {
    menu.insert(uiObject, position);
  }

  public boolean isMenuVisible() {
    return menu.isVisible();
  }

  public boolean isVisible() {
    return menu.isVisible();
  }

  protected void onHidden(final Event evt) {
    widget.fireEvent(new HiddenEvent(evt));
  }

  protected void onHide(final Event evt) {
    widget.fireEvent(new HideEvent(evt));
  }

  protected void onShow(final Event evt) {
    widget.fireEvent(new ShowEvent(evt));
  }

  protected void onShown(final Event evt) {
    widget.fireEvent(new ShownEvent(evt));
  }

  public boolean remove(final int index) {
    return menu.remove(index);
  }

  public boolean remove(final Widget w) {
    return menu.remove(w);
  }

  public void setActive(final boolean active) {
    anchor.setActive(active);
  }

  public void setEnabled(final boolean enabled) {
    anchor.setEnabled(enabled);
  }

  public void setIcon(final IconType icon) {
    anchor.setIcon(icon);
  }

  public void setIcon(final KuneIcon icon) {
    anchor.setIcon(icon);
  }

  public void setIconBackColor(final String backgroundColor) {
    anchor.setIconBackColor(backgroundColor);
  }

  public void setIconResource(final ImageResource resource) {
    anchor.setIconResource(resource);
  }

  public void setIconRightResource(final ImageResource rightIcon) {
    anchor.setIconRightResource(rightIcon);
  }

  public void setIconStyle(final String style) {
    anchor.setIconStyle(style);
  }

  public void setIconUrl(final String url) {
    anchor.setIconUrl(url);

  }

  public void setInline(final Boolean inline) {
    menu.setInline(inline);
  }

  public void setMenuText(final String text) {
    anchor.setText(text);
  }

  private native void setPosition(final com.google.gwt.dom.client.Element e, int x, int y) /*-{
		$wnd.jQuery(e).offset({
			top : y,
			left : x
		})
  }-*/;

  public void setText(final String text) {
    anchor.setText(text);
  }

  public void setVisible(final boolean visible) {
    widget.setVisible(visible);
  }

  public void show() {
    widget.addStyleName(OPEN);
  }

  public void show(final int x, final int y) {
    show();
    setPosition(menu.getElement(), x, y);
  }

  private native void unbindJavaScriptEvents(final com.google.gwt.dom.client.Element e) /*-{
		$wnd.jQuery(e).off('show.bs.dropdown');
		$wnd.jQuery(e).off('shown.bs.dropdown');
		$wnd.jQuery(e).off('hide.bs.dropdown');
		$wnd.jQuery(e).off('hidden.bs.dropdown');
  }-*/;

}
