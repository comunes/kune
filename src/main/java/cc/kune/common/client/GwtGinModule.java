package cc.kune.common.client;

import cc.kune.common.client.actions.gwtui.GwtButtonGui;
import cc.kune.common.client.actions.gwtui.GwtIconLabelGui;
import cc.kune.common.client.actions.gwtui.GwtMenuGui;
import cc.kune.common.client.actions.gwtui.GwtMenuItemGui;
import cc.kune.common.client.actions.gwtui.GwtMenuSeparatorGui;
import cc.kune.common.client.actions.gwtui.GwtPushButtonGui;
import cc.kune.common.client.actions.gwtui.GwtSubMenuGui;
import cc.kune.common.client.actions.gwtui.GwtToolbarGui;
import cc.kune.common.client.actions.gwtui.GwtToolbarSeparatorGui;

import com.google.gwt.inject.client.AbstractGinModule;

public class GwtGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(GwtSubMenuGui.class);
        bind(GwtMenuGui.class);
        bind(GwtMenuItemGui.class);
        bind(GwtMenuSeparatorGui.class);
        bind(GwtPushButtonGui.class);
        bind(GwtButtonGui.class);
        bind(GwtIconLabelGui.class);
        bind(GwtToolbarGui.class);
        bind(GwtToolbarSeparatorGui.class);
    }

}
