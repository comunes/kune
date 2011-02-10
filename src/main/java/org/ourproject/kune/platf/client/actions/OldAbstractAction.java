/* AbstractAction.java --
   Copyright (C) 2002, 2004, 2005, 2006  Free Software Foundation, Inc.

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package org.ourproject.kune.platf.client.actions;

import java.util.HashMap;

import com.google.gwt.resources.client.ImageResource;

/**
 * A base class for implementing the {@link Action} interface.
 * 
 * @author Andrew Selkirk
 * @author Adapted version for GWT (C) The kune development team
 */
@Deprecated
public abstract class OldAbstractAction extends ChangebleObject implements Action {

    /**
     * A flag that indicates whether or not the action is enabled.
     */
    protected boolean enabled = true;

    /**
     * Creates a new action with no properties set.
     */
    public OldAbstractAction() {
        // Nothing to do.
    }

    /**
     * Copy constructor used as a substitute of clone
     */
    @SuppressWarnings("unchecked")
    public OldAbstractAction(final OldAbstractAction action) {
        enabled = action.enabled;
        store = (HashMap<String, Object>) action.store.clone();
    }

    /**
     * Creates a new action with the specified name. The name is stored as a
     * property with the key {@link Action#NAME}, and no other properties are
     * initialised.
     * 
     * @param name
     *            the name (<code>null</code> permitted).
     */
    public OldAbstractAction(final String name) {
        // @PMD:REVIEWED:ConstructorCallsOverridableMethod: by vjrj on 21/05/09
        // 15:19
        putValue(NAME, name);
    }

    /**
     * Creates a new action with the specified name and icon. The name is stored
     * as a property with the key {@link Action#NAME}, the icon is stored as a
     * property with the key {@link Action#SMALL_ICON}, and no other properties
     * are initialised.
     * 
     * @param name
     *            the name (<code>null</code> permitted).
     * @param icon
     *            the icon (<code>null</code> permitted).
     */
    public OldAbstractAction(final String name, final ImageResource icon) {
        // @PMD:REVIEWED:ConstructorCallsOverridableMethod: by vjrj on 21/05/09
        // 15:19
        putValue(NAME, name);
        // @PMD:REVIEWED:ConstructorCallsOverridableMethod: by vjrj on 21/05/09
        // 15:19
        putValue(SMALL_ICON, icon);
    }

    /**
     * Returns the flag that indicates whether or not the action is enabled.
     * 
     * @return The flag.
     * 
     * @see #setEnabled(boolean)
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    /**
     * Sets the value associated with the specified key and sends a
     * {@link java.beans.PropertyChangeEvent} to all registered listeners. The
     * standard keys are:
     * <ul>
     * <li>{@link #NAME}</li>
     * <li>{@link #SHORT_DESCRIPTION}</li>
     * <li>{@link #LONG_DESCRIPTION}</li>
     * <li>{@link #SMALL_ICON}</li>
     * <li>{@link #ACTION_COMMAND_KEY}</li>
     * <li>{@link #ACCELERATOR_KEY}</li>
     * <li>{@link #MNEMONIC_KEY}</li>
     * </ul>
     * Any existing value associated with the key will be overwritten.
     *
     * @param key
     *            the key (not <code>null</code>).
     * @param value
     *            the value (<code>null</code> permitted).
     */
    public void putValue(final String key, final Object value) {
        // TODO Auto-generated method stub
        super.putValue(key, value);
    }

    /**
     * Sets the flag that indicates whether or not the action is enabled and, if
     * the value of the flag changed from the previous setting, sends a
     * {@link java.beans.PropertyChangeEvent} to all registered listeners (using
     * the property name 'enabled').
     * 
     * @param enabled
     *            the new flag value.
     * 
     * @see #isEnabled()
     */
    @Override
    public void setEnabled(final boolean enabled) {
        if (enabled != this.enabled) {
            this.enabled = enabled;
            firePropertyChange(ENABLED, !this.enabled, this.enabled);
        }
    }

    public void setShortcut(final KeyStroke key) {
        putValue(Action.ACCELERATOR_KEY, key);
    }
}
