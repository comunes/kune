package cc.kune.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * The Class KuneEntryPoint.
 */
public class KuneEntryPoint implements EntryPoint {
    public final CoreGinjector ginjector = GWT.create(CoreGinjector.class);

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
        DelayedBindRegistry.bind(ginjector);

        ginjector.getPlaceManager().revealCurrentPlace();
    }

}
