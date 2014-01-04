package cc.kune.gspace.client.share.items.actions;

import cc.kune.core.client.rpcservices.ContentServiceHelper;
import cc.kune.core.shared.dto.AccessRolDTO;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ChangeToEditorForContentsAction extends AbstractSetRoleOnContentAction {
  @Inject
  ChangeToEditorForContentsAction(final Provider<ContentServiceHelper> helper) {
    super(AccessRolDTO.Editor, helper);
  }
}