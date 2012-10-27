/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.gspace.client.licensewizard;

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
