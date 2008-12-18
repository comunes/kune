package org.ourproject.kune.workspace.client.licensewizard;

import org.mockito.Mockito;

import com.calclab.suco.client.SucoFactory;
import com.calclab.suco.client.ioc.Container;
import com.calclab.suco.client.ioc.Decorator;
import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.ioc.decorator.Singleton;

public class MockProvider {
    private static Container container;

    public static <T> Provider<T> mock(final Class<T> classToMock) {
        return mock(Singleton.instance, classToMock);
    }

    public static <T> Provider<T> mock(Decorator decorator, final Class<T> classToMock) {
        if (container == null) {
            container = SucoFactory.create();
        }
        if (!container.hasProvider(classToMock)) {
            container.registerProvider(decorator, classToMock, new Provider<T>() {
                public T get() {
                    return Mockito.mock(classToMock);
                }
            });
        }
        return container.getProvider(classToMock);
    }
}
