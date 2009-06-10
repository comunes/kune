/* PropertyChangeSupport.java -- support to manage property change listeners
   Copyright (C) 1998, 1999, 2000, 2002, 2005, 2006
   Free Software Foundation, Inc.

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;

/**
 * PropertyChangeSupport makes it easy to fire property change events and handle
 * listeners. It allows chaining of listeners, as well as filtering by property
 * name. In addition, it will serialize only those listeners which are
 * serializable, ignoring the others without problem. This class is thread-safe.
 * 
 * @author John Keiser
 * @author Eric Blake (ebb9@email.byu.edu)
 * @author Adapted version for GWT (C) The kune development team
 * @since 1.1
 * @status updated to 1.4
 */
public class PropertyChangeSupport {

    /**
     * Maps property names (String) to named listeners (PropertyChangeSupport).
     * If this is a child instance, this field will be null.
     * 
     * @serial the map of property names to named listener managers
     * @since 1.2
     */
    private HashMap<String, PropertyChangeSupport> children;

    /**
     * The non-null source object for any generated events.
     * 
     * @serial the event source
     */
    private final Object source;

    /**
     * The list of all registered property listeners. If this instance was
     * created by user code, this only holds the global listeners (ie. not tied
     * to a name), and may be null. If it was created by this class, as a helper
     * for named properties, then this vector will be non-null, and this
     * instance appears as a value in the <code>children</code> hashtable of
     * another instance, so that the listeners are tied to the key of that
     * hashtable entry.
     */
    private Vector<PropertyChangeListener> listeners;

    /**
     * Create a PropertyChangeSupport to work with a specific source bean.
     * 
     * @param source
     *            the source bean to use
     * @throws NullPointerException
     *             if source is null
     */
    public PropertyChangeSupport(final Object source) {
        this.source = source;
        if (source == null) {
            // @PMD:REVIEWED:AvoidThrowingNullPointerException: by vjrj on
            // 21/05/09 14:12
            throw new NullPointerException();
        }
    }

    /**
     * Adds a PropertyChangeListener to the list of global listeners. All
     * property change events will be sent to this listener. The listener add is
     * not unique: that is, <em>n</em> adds with the same listener will result
     * in <em>n</em> events being sent to that listener for every property
     * change. Adding a null listener is silently ignored. This method will
     * unwrap a PropertyChangeListenerProxy, registering the underlying delegate
     * to the named property list.
     * 
     * @param l
     *            the listener to add
     */
    public void addPropertyChangeListener(final PropertyChangeListener l) {
        if (l == null) {
            return;
        }

        if (l instanceof PropertyChangeListenerProxy) {
            final PropertyChangeListenerProxy p = (PropertyChangeListenerProxy) l;
            addPropertyChangeListener(p.propertyName, (PropertyChangeListener) p.getListener());
        } else {
            if (listeners == null) {
                listeners = new Vector<PropertyChangeListener>();
            }
            listeners.add(l);
        }
    }

    /**
     * Adds a PropertyChangeListener listening on the specified property. Events
     * will be sent to the listener only if the property name matches. The
     * listener add is not unique; that is, <em>n</em> adds on a particular
     * property for a particular listener will result in <em>n</em> events being
     * sent to that listener when that property is changed. The effect is
     * cumulative, too; if you are registered to listen to receive events on all
     * property changes, and then you register on a particular property, you
     * will receive change events for that property twice. Adding a null
     * listener is silently ignored. This method will unwrap a
     * PropertyChangeListenerProxy, registering the underlying delegate to the
     * named property list if the names match, and discarding it otherwise.
     * 
     * @param propertyName
     *            the name of the property to listen on
     * @param l
     *            the listener to add
     * @throws NullPointerException
     *             if propertyName is null
     */
    // @PMD:REVIEWED:AvoidReassigningParameters: by vjrj on 21/05/09 14:12
    public void addPropertyChangeListener(final String propertyName, PropertyChangeListener l) {
        if (l == null) {
            return;
        }

        while (l instanceof PropertyChangeListenerProxy) {
            final PropertyChangeListenerProxy p = (PropertyChangeListenerProxy) l;
            if (propertyName == null ? p.propertyName != null : !propertyName.equals(p.propertyName)) {
                return;
            }
            l = (PropertyChangeListener) p.getListener();
        }
        PropertyChangeSupport s = null;
        if (children == null) {
            children = new HashMap<String, PropertyChangeSupport>();
        } else {
            s = children.get(propertyName);
        }
        if (s == null) {
            s = new PropertyChangeSupport(source);
            s.listeners = new Vector<PropertyChangeListener>();
            children.put(propertyName, s);
        }
        s.listeners.add(l);
    }

    /**
     * Fire an indexed property change event. This will only fire an event if
     * the old and new values are not equal.
     * 
     * @param name
     *            the name of the property which changed
     * @param index
     *            the index of the property which changed
     * @param oldValue
     *            the old value of the property
     * @param newValue
     *            the new value of the property
     * @since 1.5
     */
    public void fireIndexedPropertyChange(final String name, final int index, final boolean oldValue,
            final boolean newValue) {
        if (oldValue != newValue) {
            fireIndexedPropertyChange(name, index, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
        }
    }

    /**
     * Fire an indexed property change event. This will only fire an event if
     * the old and new values are not equal.
     * 
     * @param name
     *            the name of the property which changed
     * @param index
     *            the index of the property which changed
     * @param oldValue
     *            the old value of the property
     * @param newValue
     *            the new value of the property
     * @since 1.5
     */
    public void fireIndexedPropertyChange(final String name, final int index, final int oldValue, final int newValue) {
        if (oldValue != newValue) {
            fireIndexedPropertyChange(name, index, Integer.valueOf(oldValue), Integer.valueOf(newValue));
        }
    }

    /**
     * Fire a PropertyChangeEvent to all the global listeners, and to all the
     * listeners for the specified property name. This does nothing if old and
     * new values of the event are equal.
     * 
     * @param event
     *            the event to fire
     * @throws NullPointerException
     *             if event is null
     */
    public void firePropertyChange(final PropertyChangeEvent event) {
        if (event.oldValue != null && event.oldValue.equals(event.newValue)) {
            return;
        }
        Vector<PropertyChangeListener> v = listeners; // Be thread-safe.
        if (v != null) {
            int i = v.size();
            while (--i >= 0) {
                (v.get(i)).propertyChange(event);
            }
        }
        final HashMap<String, PropertyChangeSupport> h = children; // Be
        // thread-safe.
        if (h != null && event.propertyName != null) {
            final PropertyChangeSupport s = h.get(event.propertyName);
            if (s != null) {
                v = s.listeners; // Be thread-safe.
                int i = v == null ? 0 : v.size();
                while (--i >= 0) {
                    (v.get(i)).propertyChange(event);
                }
            }
        }
    }

    /**
     * Fire a PropertyChangeEvent containing the old and new values of the
     * property to all the global listeners, and to all the listeners for the
     * specified property name. This does nothing if old and new are equal.
     * 
     * @param propertyName
     *            the name of the property that changed
     * @param oldVal
     *            the old value
     * @param newVal
     *            the new value
     */
    public void firePropertyChange(final String propertyName, final boolean oldVal, final boolean newVal) {
        if (oldVal != newVal) {
            firePropertyChange(new PropertyChangeEvent(source, propertyName, Boolean.valueOf(oldVal),
                    Boolean.valueOf(newVal)));
        }
    }

    /**
     * Fire a PropertyChangeEvent containing the old and new values of the
     * property to all the global listeners, and to all the listeners for the
     * specified property name. This does nothing if old and new are equal.
     * 
     * @param propertyName
     *            the name of the property that changed
     * @param oldVal
     *            the old value
     * @param newVal
     *            the new value
     */
    public void firePropertyChange(final String propertyName, final int oldVal, final int newVal) {
        if (oldVal != newVal) {
            firePropertyChange(new PropertyChangeEvent(source, propertyName, Integer.valueOf(oldVal),
                    Integer.valueOf(newVal)));
        }
    }

    /**
     * Fire a PropertyChangeEvent containing the old and new values of the
     * property to all the global listeners, and to all the listeners for the
     * specified property name. This does nothing if old and new are non-null
     * and equal.
     * 
     * @param propertyName
     *            the name of the property that changed
     * @param oldVal
     *            the old value
     * @param newVal
     *            the new value
     */
    public void firePropertyChange(final String propertyName, final Object oldVal, final Object newVal) {
        firePropertyChange(new PropertyChangeEvent(source, propertyName, oldVal, newVal));
    }

    /**
     * Returns an array of all registered property change listeners. Those that
     * were registered under a name will be wrapped in a
     * <code>PropertyChangeListenerProxy</code>, so you must check whether the
     * listener is an instance of the proxy class in order to see what name the
     * real listener is registered under. If there are no registered listeners,
     * this returns an empty array.
     * 
     * @return the array of registered listeners
     * @see PropertyChangeListenerProxy
     * @since 1.4
     */
    public PropertyChangeListener[] getPropertyChangeListeners() {
        final ArrayList<PropertyChangeListener> list = new ArrayList<PropertyChangeListener>();
        if (listeners != null) {
            list.addAll(listeners);
        }
        if (children != null) {
            int i = children.size();
            final Iterator<Entry<String, PropertyChangeSupport>> iter = children.entrySet().iterator();
            while (--i >= 0) {
                final Entry<String, PropertyChangeSupport> e = iter.next();
                final String name = e.getKey();
                final Vector<PropertyChangeListener> v = (e.getValue()).listeners;
                int j = v.size();
                while (--j >= 0) {
                    list.add(new PropertyChangeListenerProxy(name, v.get(j)));
                }
            }
        }
        return list.toArray(new PropertyChangeListener[list.size()]);
    }

    /**
     * Returns an array of all property change listeners registered under the
     * given property name. If there are no registered listeners, or
     * propertyName is null, this returns an empty array.
     * 
     * @return the array of registered listeners
     * @since 1.4
     */
    public PropertyChangeListener[] getPropertyChangeListeners(final String propertyName) {
        if (children == null || propertyName == null) {
            return new PropertyChangeListener[0];
        }
        final PropertyChangeSupport s = children.get(propertyName);
        if (s == null) {
            return new PropertyChangeListener[0];
        }
        return s.listeners.toArray(new PropertyChangeListener[s.listeners.size()]);
    }

    /**
     * Tell whether the specified property is being listened on or not. This
     * will only return <code>true</code> if there are listeners on all
     * properties or if there is a listener specifically on this property.
     * 
     * @param propertyName
     *            the property that may be listened on
     * @return whether the property is being listened on
     */
    public boolean hasListeners(final String propertyName) {
        return listeners != null || (children != null && children.get(propertyName) != null);
    }

    /**
     * Removes a PropertyChangeListener from the list of global listeners. If
     * any specific properties are being listened on, they must be deregistered
     * by themselves; this will only remove the general listener to all
     * properties. If <code>add()</code> has been called multiple times for a
     * particular listener, <code>remove()</code> will have to be called the
     * same number of times to deregister it. This method will unwrap a
     * PropertyChangeListenerProxy, removing the underlying delegate from the
     * named property list.
     * 
     * @param l
     *            the listener to remove
     */
    public void removePropertyChangeListener(final PropertyChangeListener l) {
        if (l instanceof PropertyChangeListenerProxy) {
            final PropertyChangeListenerProxy p = (PropertyChangeListenerProxy) l;
            removePropertyChangeListener(p.propertyName, (PropertyChangeListener) p.getListener());
        } else if (listeners != null) {
            listeners.remove(l);
            if (listeners.isEmpty()) {
                listeners = null;
            }
        }
    }

    /**
     * Removes a PropertyChangeListener from listening to a specific property.
     * If <code>add()</code> has been called multiple times for a particular
     * listener on a property, <code>remove()</code> will have to be called the
     * same number of times to deregister it. This method will unwrap a
     * PropertyChangeListenerProxy, removing the underlying delegate from the
     * named property list if the names match.
     * 
     * @param propertyName
     *            the property to stop listening on
     * @param l
     *            the listener to remove
     * @throws NullPointerException
     *             if propertyName is null
     */
    // @PMD:REVIEWED:AvoidReassigningParameters: by vjrj on 21/05/09 14:12
    public void removePropertyChangeListener(final String propertyName, PropertyChangeListener l) {
        if (children == null) {
            return;
        }
        final PropertyChangeSupport s = children.get(propertyName);
        if (s == null) {
            return;
        }
        while (l instanceof PropertyChangeListenerProxy) {
            final PropertyChangeListenerProxy p = (PropertyChangeListenerProxy) l;
            if (propertyName == null ? p.propertyName != null : !propertyName.equals(p.propertyName)) {
                return;
            }
            l = (PropertyChangeListener) p.getListener();
        }
        s.listeners.remove(l);
        if (s.listeners.isEmpty()) {
            children.remove(propertyName);
            if (children.isEmpty()) {
                children = null;
            }
        }
    }
} // class PropertyChangeSupport
