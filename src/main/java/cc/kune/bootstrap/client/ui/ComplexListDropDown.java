package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.shared.event.HiddenEvent;
import org.gwtbootstrap3.client.shared.event.HideEvent;
import org.gwtbootstrap3.client.shared.event.HideHandler;
import org.gwtbootstrap3.client.shared.event.ShowEvent;
import org.gwtbootstrap3.client.shared.event.ShowHandler;
import org.gwtbootstrap3.client.shared.event.ShownEvent;
import org.gwtbootstrap3.client.shared.event.ShownHandler;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.ListDropDown;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import cc.kune.common.shared.res.KuneIcon;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ComplexListDropDown extends ListDropDown {

  private final ComplexAnchorButton anchor;
  private final DropDownMenu menu;

  public ComplexListDropDown() {
    menu = new DropDownMenu();
    anchor = new ComplexAnchorButton();
    anchor.setDataToggle(Toggle.DROPDOWN);
    super.add(anchor);
    super.add(menu);
  }

  @Override
  public void add(final IsWidget child) {
    menu.add(child);
  }

  @Override
  public void add(final Widget child) {
    menu.add(child);
  }

  public void addClickHandler(final ClickHandler clickHandler) {
    anchor.addClickHandler(clickHandler);
  }

  public HandlerRegistration addHideHandler(final HideHandler hideHandler) {
    return addHandler(hideHandler, HideEvent.getType());
  }

  public HandlerRegistration addShowHandler(final ShowHandler showHandler) {
    return addHandler(showHandler, ShowEvent.getType());
  }

  public HandlerRegistration addShownHandler(final ShownHandler shownHandler) {
    return addHandler(shownHandler, ShownEvent.getType());
  }

  private native void bindJavaScriptEvents(final com.google.gwt.dom.client.Element e) /*-{
		var target = this;
		var $dropdown = $wnd.jQuery(e);

		$dropdown
				.on(
						'show.bs.dropdown',
						function(evt) {
							target.@cc.kune.bootstrap.client.ui.ComplexListDropDown::onShow(Lcom/google/gwt/user/client/Event;)(evt);
						});

		$dropdown
				.on(
						'shown.bs.dropdown',
						function(evt) {
							target.@cc.kune.bootstrap.client.ui.ComplexListDropDown::onShown(Lcom/google/gwt/user/client/Event;)(evt);
						});

		$dropdown
				.on(
						'hide.bs.dropdown',
						function(evt) {
							target.@cc.kune.bootstrap.client.ui.ComplexListDropDown::onHide(Lcom/google/gwt/user/client/Event;)(evt);
						});

		$dropdown
				.on(
						'hidden.bs.dropdown',
						function(evt) {
							target.@cc.kune.bootstrap.client.ui.ComplexListDropDown::onHidden(Lcom/google/gwt/user/client/Event;)(evt);
						});
  }-*/;

  public String getText() {
    return anchor.getText();
  }

  public void hide() {
    hide(menu.getElement());
  }

  private native void hide(final com.google.gwt.dom.client.Element e) /*-{
		$wnd.jQuery(e).hide();
  }-*/;

  public boolean isMenuVisible() {
    return menu.isVisible();
  }

  protected void onHidden(final Event evt) {
    fireEvent(new HiddenEvent(evt));
  }

  protected void onHide(final Event evt) {
    fireEvent(new HideEvent(evt));
  }

  @Override
  protected void onLoad() {
    super.onLoad();
    // Bind jquery events
    bindJavaScriptEvents(getElement());
  }

  protected void onShow(final Event evt) {
    fireEvent(new ShowEvent(evt));
  }

  protected void onShown(final Event evt) {
    fireEvent(new ShownEvent(evt));
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    // Unbind the events
    unbindJavaScriptEvents(getElement());
  }

  @Override
  public boolean remove(final int index) {
    return menu.remove(index);
  }

  @Override
  public boolean remove(final Widget w) {
    return menu.remove(w);
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

  public void show() {
    show(menu.getElement());
  }

  private native void show(final com.google.gwt.dom.client.Element e) /*-{
		$wnd.jQuery(e).show();
  }-*/;

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
