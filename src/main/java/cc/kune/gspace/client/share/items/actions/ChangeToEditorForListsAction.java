package cc.kune.gspace.client.share.items.actions;

import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.lists.client.rpc.ListsServiceHelper;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChangeToEditorForListsAction extends AbstractSetRoleOnListAction {
  @Inject
  ChangeToEditorForListsAction(final Provider<ListsServiceHelper> helper) {
    super(AccessRolDTO.Editor, helper);
  }
}