package cc.kune.blogs.client.actions;

import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.core.client.resources.CoreResources;

import com.google.inject.Inject;

public class ContentViewerOptionsMenu extends MenuDescriptor {

    @Inject
    public ContentViewerOptionsMenu(final CoreResources res) {
        super();
        this.withIcon(res.arrowdownsitebar()).withStyles("k-def-docbtn, k-fr, k-noborder, k-nobackcolor");
    }

}
