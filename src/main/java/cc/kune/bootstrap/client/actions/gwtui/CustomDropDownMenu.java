package cc.kune.bootstrap.client.actions.gwtui;

import org.gwtbootstrap3.client.shared.event.HideEvent;
import org.gwtbootstrap3.client.shared.event.HideHandler;
import org.gwtbootstrap3.client.shared.event.ShowEvent;
import org.gwtbootstrap3.client.shared.event.ShowHandler;
import org.gwtbootstrap3.client.ui.DropDownMenu;

import com.google.gwt.event.shared.HandlerRegistration;

public class CustomDropDownMenu extends DropDownMenu {

  public HandlerRegistration addHideHandler(final HideHandler hideHandler) {
    return addHandler(hideHandler, HideEvent.getType());
  }

  public HandlerRegistration addShowHandler(final ShowHandler showHandler) {
    return addHandler(showHandler, ShowEvent.getType());
  }

  private native void bindJavaScriptEvents(final com.google.gwt.dom.client.Element e) /*-{
		var target = this;
		var $collapse = $wnd.jQuery(e);

		$collapse
				.on(
						'show.bs.collapse',
						function(evt) {
							target.@org.gwtbootstrap3.client.ui.Collapse::onShow(Lcom/google/gwt/user/client/Event;)(evt);
						});

		$collapse
				.on(
						'shown.bs.collapse',
						function(evt) {
							target.@org.gwtbootstrap3.client.ui.Collapse::onShown(Lcom/google/gwt/user/client/Event;)(evt);
						});

		$collapse
				.on(
						'hide.bs.collapse',
						function(evt) {
							target.@org.gwtbootstrap3.client.ui.Collapse::onHide(Lcom/google/gwt/user/client/Event;)(evt);
						});

		$collapse
				.on(
						'hidden.bs.collapse',
						function(evt) {
							target.@org.gwtbootstrap3.client.ui.Collapse::onHidden(Lcom/google/gwt/user/client/Event;)(evt);
						});
  }-*/;

  private native void collapse(final com.google.gwt.dom.client.Element e, final boolean toggle) /*-{
		$wnd.jQuery(e).collapse({
			toggle : toggle
		});
  }-*/;

  private native void fireMethod(final com.google.gwt.dom.client.Element e, int slideNumber) /*-{
		$wnd.jQuery(e).collapse(slideNumber);
  }-*/;

  private native void fireMethod(final com.google.gwt.dom.client.Element e, String method) /*-{
		$wnd.jQuery(e).collapse(method);
  }-*/;

  @Override
  protected void onLoad() {
    super.onLoad();
    // Bind jquery events
    bindJavaScriptEvents(getElement());
    // Configure the collapse
    collapse(getElement(), true);
  }

  @Override
  protected void onUnload() {
    super.onUnload();
    // Unbind the events
    unbindJavaScriptEvents(getElement());
  }

  @Override
  public void setVisible(final boolean visible) {
    super.setVisible(visible);
    if (visible) {
      fireEvent(new ShowEvent());
    } else {
      fireEvent(new HideEvent());
    }
  }

  private native void unbindJavaScriptEvents(final com.google.gwt.dom.client.Element e) /*-{
		$wnd.jQuery(e).off('show.bs.collapse');
		$wnd.jQuery(e).off('shown.bs.collapse');
		$wnd.jQuery(e).off('hide.bs.collapse');
		$wnd.jQuery(e).off('hidden.bs.collapse');
  }-*/;

}
