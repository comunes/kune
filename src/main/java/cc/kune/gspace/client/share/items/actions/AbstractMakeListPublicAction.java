package cc.kune.gspace.client.share.items.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.lists.client.rpc.ListsServiceHelper;

import com.google.inject.Provider;

public abstract class AbstractMakeListPublicAction extends AbstractToggleAction {

  private final Provider<ListsServiceHelper> helper;

  AbstractMakeListPublicAction(final boolean value, final Provider<ListsServiceHelper> helper) {
    super(value);
    this.helper = helper;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    helper.get().setPublic(value, onSuccess);
  }

}
