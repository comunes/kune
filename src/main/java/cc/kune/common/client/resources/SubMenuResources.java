package cc.kune.common.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.MenuBar.Resources;

public interface SubMenuResources extends Resources {
    public static final SubMenuResources INSTANCE = GWT.create(SubMenuResources.class);

    @Override
    @Source("arrow-right-white.gif")
    ImageResource menuBarSubMenuIcon();
}
