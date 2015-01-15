package cc.kune.core.client.notify.confirm;

import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;

import cc.kune.common.client.notify.ConfirmAskEvent;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class UserConfirmBS { // extends ViewImpl implements UserConfirmView {

  @Inject
  UserConfirmBS(final EventBus eventbus) {
    eventbus.addHandler(ConfirmAskEvent.getType(), new ConfirmAskEvent.ConfirmAskHandler() {
      @Override
      public void onConfirmAsk(final ConfirmAskEvent event) {
        confirmAsk(event);
      }
    });
  }

  /**
   * Displays a message in a modal dialog box, along with the standard 'OK' and
   * 'Cancel' buttons.
   *
   * @param msg
   *          the message to be displayed.
   * @param callback
   *          the callback handler.
   */
  public native void confirm(String title, String msg, String acceptMsg, String cancelMsg,
      ConfirmCallback callback) /*-{
		$wnd.bootbox
				.dialog({
					message : msg,
					title : title,
					buttons : {
						success : {
							label : acceptMsg,
							//className : "btn-success",
							callback : function() {
								callback.@org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback::callback(Z)(true);
							}
						},
						main : {
							label : cancelMsg,
							//							className : "btn-primary",
							callback : function() {
								callback.@org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback::callback(Z)(false);
							}
						}
					}
				});
  }-*/;

  public void confirmAsk(final ConfirmAskEvent event) {
    confirm(event.getTitle(), event.getMessage(), event.getAcceptBtnMsg(), event.getCancelBtnMsg(),
        new ConfirmCallback() {
          @Override
          public void callback(final boolean result) {
            if (result) {
              event.getCallback().onSuccess();
            } else {
              event.getCallback().onCancel();
            }
          }
        });
  }
}
