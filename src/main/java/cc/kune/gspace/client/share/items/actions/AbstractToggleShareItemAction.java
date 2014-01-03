package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.gspace.client.share.items.AbstractShareItemWithMenuUi;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

public abstract class AbstractToggleShareItemAction extends AbstractExtendedAction {

  private ShareItemDescriptor onPerformShareItem;
  protected final SimpleCallback onSuccess;
  protected final boolean value;

  AbstractToggleShareItemAction(final boolean value) {
    this.value = value;
    onSuccess = new SimpleCallback() {
      @Override
      public void onCallback() {
        // On action success in server
        final AbstractShareItemWithMenuUi itemUi = getTarget();
        if (itemUi != null && onPerformShareItem != null) {
          itemUi.setValuesViaDescriptor(onPerformShareItem);
        }
      }
    };
  }

  protected AbstractShareItemWithMenuUi getTarget() {
    return (AbstractShareItemWithMenuUi) getValue(ShareItemDescriptor.ITEM);
  }

  public void init(final ShareItemDescriptor onPerformShareItem) {
    this.onPerformShareItem = onPerformShareItem;
  }

}
