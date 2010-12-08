package cc.kune.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;

public class KuneEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        final Mvp4gModule module = (Mvp4gModule) GWT.create(Mvp4gModule.class);
        module.createAndStartModule();
        RootLayoutPanel.get().add((Widget) module.getStartView());
    }

}
