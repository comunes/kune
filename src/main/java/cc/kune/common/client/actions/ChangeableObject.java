package cc.kune.common.client.actions;

import java.util.HashMap;

/**
 * A base class for implementing the {@link Action} interface.
 * 
 * @author Andrew Selkirk
 * @author Adapted version for GWT (C) The kune development team
 */
public class ChangeableObject {

    /**
     * Provides support for property change event notification.
     */
    protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    /**
     * store
     */
    protected HashMap<String, Object> store = new HashMap<String, Object>();

    /**
     * Registers a listener to receive {@link PropertyChangeEvent} notifications
     * from this action.
     * 
     * @param listener
     *            the listener.
     * 
     * @see #removePropertyChangeListener(PropertyChangeListener)
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Returns an array of the keys for the property values that have been
     * defined via the {@link #putValue(String, Object)} method (or the class
     * constructor).
     * 
     * @return An array of keys.
     */
    public Object[] getKeys() {
        return store.keySet().toArray();
    }

    /**
     * Returns all registered listeners.
     * 
     * @return An array of listeners.
     * 
     * @since 1.4
     */
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return changeSupport.getPropertyChangeListeners();
    }

    /**
     * Returns the value associated with the specified key.
     * 
     * @param key
     *            the key (not <code>null</code>).
     * 
     * @return The value associated with the specified key, or <code>null</code>
     *         if the key is not found.
     * 
     * @see #putValue(String, Object)
     */
    public Object getValue(final String key) {
        return store.get(key);
    }

    /**
     * Sets the value associated with the specified key and sends a
     * {@link java.beans.PropertyChangeEvent} to all registered listeners.
     * 
     * Any existing value associated with the key will be overwritten.
     * 
     * @param key
     *            the key (not <code>null</code>).
     * @param value
     *            the value (<code>null</code> permitted).
     */
    public void putValue(final String key, final Object value) {
        final Object old = getValue(key);
        if ((old == null && value != null) || (old != null && !old.equals(value))) {
            store.put(key, value);
            firePropertyChange(key, old, value);
        }
    }

    /**
     * Deregisters a listener so that it no longer receives
     * {@link PropertyChangeEvent} notifications from this action.
     * 
     * @param listener
     *            the listener.
     * 
     * @see #addPropertyChangeListener(PropertyChangeListener)
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Sends a {@link PropertyChangeEvent} for the named property to all
     * registered listeners. This private method is called by the
     * {@link #setEnabled(boolean)} method.
     * 
     * @param propertyName
     *            the property name.
     * @param oldValue
     *            the old value of the property.
     * @param newValue
     *            the new value of the property.
     */
    protected void firePropertyChange(final String propertyName, final boolean oldValue, final boolean newValue) {
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Sends a {@link PropertyChangeEvent} for the named property to all
     * registered listeners.
     * 
     * @param propertyName
     *            the property name.
     * @param oldValue
     *            the old value of the property.
     * @param newValue
     *            the new value of the property.
     */
    protected void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

}
