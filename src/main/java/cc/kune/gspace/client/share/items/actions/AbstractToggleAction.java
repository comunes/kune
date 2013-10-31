package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.gspace.client.share.items.AbstractShareItemWithMenuUi;
import cc.kune.gspace.client.share.items.ShareItemDescriptor;

public abstract class AbstractToggleAction extends AbstractExtendedAction {

  private ShareItemDescriptor complementaryItem;
  protected final SimpleCallback onSuccess;
  protected final boolean value;

  AbstractToggleAction(final boolean value) {
    this.value = value;
    onSuccess = new SimpleCallback() {
      @Override
      public void onCallback() {
        // On action success in server
        final AbstractShareItemWithMenuUi itemUi = getTarget();
        if (itemUi != null && complementaryItem != null) {
          itemUi.setValuesViaDescriptor(complementaryItem);
        }
      }
    };
  }

  protected AbstractShareItemWithMenuUi getTarget() {
    return (AbstractShareItemWithMenuUi) getValue(ShareItemDescriptor.ITEM);
  }

  public void init(final ShareItemDescriptor complementaryItem) {
    this.complementaryItem = complementaryItem;
  }

}
