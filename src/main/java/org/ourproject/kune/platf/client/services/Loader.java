package org.ourproject.kune.platf.client.services;

import java.util.ArrayList;

import com.calclab.suco.client.Suco;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Container;
import com.calclab.suco.client.ioc.module.SucoModule;

public class Loader extends Suco {

    private static ArrayList<SucoModule> pendingModules = new ArrayList<SucoModule>();
    private static boolean deferred = true;
    private static boolean initialized = false;

    /**
     * Obtain a instance from the Suco container
     *
     * @param <T>
     * @param componentType
     * @return
     */
    public static <T> T get(final Class<T> componentType) {
        return Suco.get(componentType);
    }

    /**
     * Get the Suco container itself.
     *
     * @return the Suco singleton container
     */
    public static Container getComponents() {
        return Suco.getComponents();
    }

    /**
     * Install the given modules into the container
     *
     * @param modules
     *            the list of modules to be installed in the singleton Suco
     *            container
     */
    public static void install(final SucoModule... modules) {
        init();
        if (deferred) {
            for (final SucoModule module : modules) {
                pendingModules.add(module);
            }
        } else {
            Suco.install(modules);
        }
    }

    private static void init() {
        if (!initialized) {
            Suco.install(new CoreModule(new Listener0() {
                public void onEvent() {
                    for (final SucoModule module : pendingModules) {
                        Suco.install(module);
                    }
                    deferred = false;
                }
            }));
            initialized = true;
        }
    }
}
