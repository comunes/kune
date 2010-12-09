package cc.kune.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;

/**
 * The Class KuneEntryPoint.
 */
public class KuneEntryPoint implements EntryPoint {

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
     */
    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(final Throwable e) {
                GWT.log("Error in 'onModuleLoad()' method", e);
                e.printStackTrace();
            }
        });

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            public void execute() {
                onModuleLoadCont();
            }
        });
    }

    /**
     * On module load cont.
     */
    public void onModuleLoadCont() {
        final Mvp4gModule module = (Mvp4gModule) GWT.create(Mvp4gModule.class);
        module.createAndStartModule();
        RootLayoutPanel.get().add((Widget) module.getStartView());
    }

}
