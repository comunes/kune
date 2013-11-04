package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.core.client.rpcservices.ContentServiceHelper;

import com.google.inject.Provider;

public abstract class AbstractMakeContentEditableAction extends AbstractToggleShareItemAction {

  private final Provider<ContentServiceHelper> helper;

  AbstractMakeContentEditableAction(final boolean value, final Provider<ContentServiceHelper> helper) {
    super(value);
    this.helper = helper;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    helper.get().setEditableByAnyone(value, onSuccess);
  }

}
