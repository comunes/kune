package cc.kune.core.client;

import cc.kune.core.client.rpcservices.AsyncCallbackSimple;

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
    @Override
    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(final Throwable e) {
                GWT.log("Error in 'onModuleLoad()' method", e);
                e.printStackTrace();
            }
        });

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
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
        AsyncCallbackSimple.init(ginjector.getErrorHandler());
        ginjector.getPlaceManager().revealCurrentPlace();
        ginjector.getI18n();
        ginjector.getGxtGuiProvider();
        ginjector.getUserNotifierPresenter();
        ginjector.getSpinerPresenter();
        ginjector.getStateManager();
        ginjector.getSitebarActionsPresenter();
    }
}
