package cc.kune.common.client;

import cc.kune.common.client.actions.gwtui.GwtIconLabelGui;
import cc.kune.common.client.actions.gxtui.GxtButtonGui;
import cc.kune.common.client.actions.gxtui.GxtMenuGui;
import cc.kune.common.client.actions.gxtui.GxtMenuItemGui;
import cc.kune.common.client.actions.gxtui.GxtMenuSeparatorGui;
import cc.kune.common.client.actions.gxtui.GxtPushButtonGui;
import cc.kune.common.client.actions.gxtui.GxtSubMenuGui;
import cc.kune.common.client.actions.gxtui.GxtToolbarGui;
import cc.kune.common.client.actions.gxtui.GxtToolbarSeparatorGui;

import com.google.gwt.inject.client.AbstractGinModule;

public class GxtGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(GxtSubMenuGui.class);
        bind(GxtMenuGui.class);
        bind(GxtMenuItemGui.class);
        bind(GxtMenuSeparatorGui.class);
        bind(GxtPushButtonGui.class);
        bind(GxtButtonGui.class);
        bind(GwtIconLabelGui.class);
        bind(GxtToolbarGui.class);
        bind(GxtToolbarSeparatorGui.class);
    }

}
