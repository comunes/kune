package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.rpcservices.ContentServiceHelper;

import com.google.inject.Provider;

public abstract class AbstractMakeContentVisibleAction extends AbstractToggleShareItemAction {

  private final Provider<ContentServiceHelper> helper;

  AbstractMakeContentVisibleAction(final boolean value, final Provider<ContentServiceHelper> helper) {
    super(value);
    this.helper = helper;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    helper.get().setVisible(value, onSuccess);
  }

}
