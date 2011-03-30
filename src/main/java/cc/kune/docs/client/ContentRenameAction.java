package cc.kune.docs.client;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.noti.NotifyUser;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.shared.dto.AccessRolDTO;

public class ContentRenameAction extends RolAction {

    public ContentRenameAction() {
        super(AccessRolDTO.Editor, true);
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        NotifyUser.info(TextUtils.IN_DEVELOPMENT);
    }

}