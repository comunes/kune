package cc.kune.blogs.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.core.client.resources.CoreResources;

public abstract class AbstractOptionsMenu extends MenuDescriptor {

    public AbstractOptionsMenu(final CoreResources res) {
        super();
        this.withIcon(res.arrowDownBlack()).withStyles("k-fr");
    }

}
