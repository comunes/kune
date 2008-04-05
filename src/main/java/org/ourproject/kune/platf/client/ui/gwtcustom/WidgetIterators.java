/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
/*
 * Copyright 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.ourproject.kune.platf.client.ui.gwtcustom;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

/**
 * A collection of convenience factories for creating iterators for widgets.
 * This mostly helps developers support {@link HasWidgets} without having to
 * implement their own {@link Iterator}.
 */
class WidgetIterators {

    private static final Widget[] copyWidgetArray(final Widget[] widgets) {
        final Widget[] clone = new Widget[widgets.length];
        for (int i = 0; i < widgets.length; i++) {
            clone[i] = widgets[i];
        }
        return clone;
    }

    /**
     * Wraps an array of widgets to be returned during iteration.
     * <code>null</code> is allowed in the array and will be skipped during
     * iteration.
     * 
   * @param container the container of the widgets in <code>contained</code>
   * @param contained the array of widgets
     * @return the iterator
     */
  static final Iterator<Widget> createWidgetIterator(final HasWidgets container,
      final Widget[] contained) {
    return new Iterator<Widget>() {
            int index = -1, last = -1;
            boolean widgetsWasCopied = false;
            Widget[] widgets = contained;

            {
                gotoNextIndex();
            }

            private void gotoNextIndex() {
                ++index;
                while (index < contained.length) {
                    if (contained[index] != null) {
                        return;
                    }
                    ++index;
                }
            }

            public boolean hasNext() {
        return (index < contained.length);
            }

      public Widget next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                last = index;
                final Widget w = contained[index];
                gotoNextIndex();
                return w;
            }

            public void remove() {
                if (last < 0) {
                    throw new IllegalStateException();
                }

                if (!widgetsWasCopied) {
                    widgets = copyWidgetArray(widgets);
                    widgetsWasCopied = true;
                }

                container.remove(contained[last]);
                last = -1;
            }
        };
    }

    private WidgetIterators() {
        // Not instantiable.
    }
}
